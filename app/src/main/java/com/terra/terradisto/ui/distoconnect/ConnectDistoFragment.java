package com.terra.terradisto.ui.distoconnect;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultCaller;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.terra.terradisto.databinding.FragmentConnectDistoBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import ch.leica.sdk.Devices.Device;
import ch.leica.sdk.Devices.DeviceManager;
import ch.leica.sdk.ErrorHandling.ErrorDefinitions;
import ch.leica.sdk.ErrorHandling.ErrorObject;
import ch.leica.sdk.ErrorHandling.IllegalArgumentCheckedException;
import ch.leica.sdk.LeicaSdk;
import ch.leica.sdk.Listeners.ErrorListener;
import ch.leica.sdk.Types;
import ch.leica.sdk.Utilities.WifiHelper;

import com.terra.terradisto.R;
import com.terra.terradisto.distosdkapp.AppLicenses;
import com.terra.terradisto.distosdkapp.clipboard.Clipboard;
import com.terra.terradisto.distosdkapp.clipboard.InformationActivityData;
import com.terra.terradisto.distosdkapp.device.AvailableDevicesListener;
import com.terra.terradisto.distosdkapp.device.FindDevices;
import com.terra.terradisto.distosdkapp.permissions.PermissionsHelper;
import com.terra.terradisto.distosdkapp.utilities.dialog.DialogHandler;

import org.json.JSONException;

public class ConnectDistoFragment extends Fragment
        implements Device.ConnectionListener,
        ErrorListener,
        AvailableDevicesListener,
        DeviceListAdapter.OnDeviceClickListener {

    private static final String TAG = "ConnectDistoFragment";

    private FragmentConnectDistoBinding b;

    // UI
    private DeviceListAdapter adapter;
    private DialogHandler connectingDialog;
    private DialogHandler alertsDialog;

    // SDK / 디바이스
    private DeviceManager deviceManager;
    private FindDevices findDevices;
    private Device currentDevice;

    // 권한
    private PermissionsHelper permissionsHelper;

    // 연결 시도 취소/타임아웃 관리
    private final Map<Device, Boolean> connectionAttempts = new HashMap<>();
    private Device currentConnectionAttemptToDevice = null;
    private Timer connectionTimeoutTimer;
    private TimerTask connectionTimeoutTask;

    // 최초 1회 안내 다이얼로그
    private static boolean searchInfoShown = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        b = FragmentConnectDistoBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Dialogs
        connectingDialog = new DialogHandler();
        alertsDialog = new DialogHandler();

        // RecyclerView
        adapter = new DeviceListAdapter(new ArrayList<>(), this);
        b.rvDevices.setLayoutManager(new LinearLayoutManager(requireContext()));
        b.rvDevices.setAdapter(adapter);

        // 권한 헬퍼 (Activity 컨텍스트 필요)
        permissionsHelper = new PermissionsHelper(requireActivity());

        // SDK / 스캐닝 구성
        initSDK();
        deviceManager = DeviceManager.getInstance(requireContext().getApplicationContext());
        deviceManager.setErrorListener(this);

        findDevices = new FindDevices(requireContext().getApplicationContext(), this);
        findDevices.registerReceivers();

        // 클립보드에 이전 디바이스 남아있으면 리스너 붙이기
        InformationActivityData info = Clipboard.INSTANCE.getInformationActivityData();
        if (info != null && info.device != null) {
            currentDevice = info.device;
            currentDevice.setConnectionListener(this);
            currentDevice.setErrorListener(this);
        }

        // 타이머
        connectionTimeoutTimer = new Timer();

        // SDK 버전 표시는 필요 시 b.tvSectionTitle 등에 append 가능
        // String version = LeicaSdk.getVersion();

        // 진입 시 목록 갱신
        updateList();
    }

    @Override
    public void onResume() {
        super.onResume();

        updateList();

        if (!searchInfoShown) {
            searchInfoShown = true;
            alertsDialog.setAlert(requireActivity(), "장치를 찾기 위해 블루투스를 켜주세요");
            alertsDialog.show();
        }

        // 저장 권한 등
        permissionsHelper.requestStoragePermission();

        // 이미 연결된 디바이스만 우선 보여주고
        findDevices.requestConnectedDevices();
        // 즉시 스캔 시작
        findAvailableDevices();
    }

    @Override
    public void onPause() {
        super.onPause();
        // 필요 시 중단 로직 추가 가능
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (findDevices != null) {
            findDevices.stopFindingDevices();
            findDevices.unregisterReceivers();
            findDevices.onDestroy();
        }

        stopConnectionTimeOutTimer();

        if (connectingDialog != null) connectingDialog.dismiss();

        b = null;
    }

    /* =========================
       SDK 초기화
       ========================= */
    private void initSDK() {
        if (!LeicaSdk.isInit) {
            LeicaSdk.InitObject initObject = new LeicaSdk.InitObject("commands.json");
            try {
                LeicaSdk.init(requireContext().getApplicationContext(), initObject);
                LeicaSdk.setLogLevel(android.util.Log.VERBOSE);
                LeicaSdk.setMethodCalledLog(false);
                LeicaSdk.setScanConfig(true, true, true, true);

                AppLicenses appLicenses = new AppLicenses();
                LeicaSdk.setLicenses(appLicenses.keys);

                // 기본값: 시작 시 어댑터 off (원 코드 유지)
                LeicaSdk.scanConfig.setWifiAdapterOn(false);
                LeicaSdk.scanConfig.setBleAdapterOn(false);
            } catch (JSONException e) {
                Log.e(TAG, "SDK init JSON 구조 오류", e);
            } catch (IllegalArgumentCheckedException e) {
                Log.e(TAG, "SDK init 데이터 오류", e);
            } catch (IOException e) {
                Log.e(TAG, "SDK init 파일 읽기 오류", e);
            }
        }
    }

    /* =========================
       권한 콜백
       ========================= */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissionsHelper != null) {
            permissionsHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /* =========================
       스캔 제어
       ========================= */
    public void findAvailableDevices() {
        updateList();

        InformationActivityData data = Clipboard.INSTANCE.getInformationActivityData();
        if (data != null && data.isSearchingEnabled) {
            permissionsHelper.requestNetworkPermissions();
            if (deviceManager != null) deviceManager.setErrorListener(this);
            if (findDevices != null) findDevices.findAvailableDevices(requireContext());
        } else {
            Log.i(TAG, "findAvailableDevices: 재생성 이슈 회피로 스킵");
        }
    }

    /* =========================
       AvailableDevicesListener
       ========================= */
    @Override
    public void onAvailableDeviceFound() {
        updateList();
    }

    @Override
    public void onAvailableDevicesChanged(java.util.List<Device> availableDevices) {
        updateList();
    }

    private void updateList() {
        if (b == null || adapter == null || findDevices == null) return;
        requireActivity().runOnUiThread(() -> {
            adapter.setItems(new ArrayList<>(findDevices.getAvailableDevices()));
            adapter.notifyDataSetChanged();
        });
    }

    /* =========================
       클릭 콜백 (Recycler item)
       ========================= */
    @Override
    public void onDeviceClick(Device device) {
        // SearchDevicesActivity.OnItemClickListener 내용을 그대로 포팅
        if (findDevices != null) findDevices.stopFindingDevices();

        if (device == null) {
            Log.i(TAG, "device not found");
            return;
        }

        currentDevice = device;

        if (device.getConnectionState() == Device.ConnectionState.connected) {
            goToInfoScreen(device);
            return;
        }

        // 연결 다이얼로그
        String title = "Connecting";
        String message = "Connecting... This may take up to 30 seconds... ";
        String negativeText = "Cancel";
        Runnable negativeAction = () -> {
            stopConnectionAttempt();
            findAvailableDevices();
        };
        connectingDialog.setDialog(requireActivity(),
                title, message, false,
                null, null,
                negativeText, negativeAction);
        connectingDialog.show();

        if (currentDevice.getConnectionType().equals(Types.ConnectionType.wifiHotspot)) {
            String wifiName = WifiHelper.getWifiName(requireContext().getApplicationContext());
            if (wifiName == null || !wifiName.equalsIgnoreCase(currentDevice.getDeviceName())) {
                gotoWifiPanel();
                return;
            } else {
                connectToDevice(currentDevice);
                return;
            }
        }

        // BLE 타임아웃 타이머
        startConnectionTimeOutTimer();

        connectToDevice(currentDevice);
    }

    /* =========================
       연결 제어
       ========================= */
    private void connectToDevice(final Device device) {
        Log.d(TAG, "connectToDevice: connecting...");

        currentConnectionAttemptToDevice = device;
        connectionAttempts.put(device, Boolean.FALSE);

        device.setConnectionListener(this);
        device.setErrorListener(this);
        if (deviceManager != null) deviceManager.stopFindingDevices();

        InformationActivityData data = Clipboard.INSTANCE.getInformationActivityData();
        if (data != null) data.isSearchingEnabled = false;

        device.connect();
    }

    private synchronized void stopConnectionAttempt() {
        InformationActivityData data = Clipboard.INSTANCE.getInformationActivityData();
        if (data != null) data.isSearchingEnabled = true;

        if (currentConnectionAttemptToDevice != null) {
            connectionAttempts.put(currentConnectionAttemptToDevice, Boolean.TRUE);
        }

        stopConnectionTimeOutTimer();

        if (connectingDialog != null) connectingDialog.dismiss();

        if (currentDevice != null) currentDevice.disconnect();
    }

    private void startConnectionTimeOutTimer() {
        if (currentDevice != null && Types.ConnectionType.ble.equals(currentDevice.getConnectionType())) {
            final long timeoutMs = 90 * 1000;
            connectionTimeoutTask = new TimerTask() {
                @Override
                public void run() {
                    stopConnectionAttempt();
                    showConnectionTimedOutDialog();

                    if (currentDevice != null) {
                        findDevices.requestConnectedDevices();
                        updateList();
                        currentDevice.disconnect();
                    }
                    findAvailableDevices();
                }
            };
            connectionTimeoutTimer.schedule(connectionTimeoutTask, timeoutMs);
        }
    }

    private void stopConnectionTimeOutTimer() {
        if (connectionTimeoutTask != null) {
            connectionTimeoutTask.cancel();
            connectionTimeoutTask = null;
        }
        if (connectionTimeoutTimer != null) {
            connectionTimeoutTimer.purge();
        }
    }

    /* =========================
       연결 상태/에러 콜백
       ========================= */
    @Override
    public void onConnectionStateChanged(Device device, Device.ConnectionState state) {
        Log.i(TAG, "onConnectionStateChanged: " + device.getDeviceID() + ", " + state);

        switch (state) {
            case connected:
                if (connectingDialog != null) connectingDialog.dismiss();

                Boolean canceled = connectionAttempts.get(device);
                if (canceled != null && canceled) {
                    device.disconnect();
                    connectionAttempts.remove(device);
                    updateList();
                    return;
                }
                goToInfoScreen(device);
                break;

            case disconnected:
                stopConnectionTimeOutTimer();
                break;
        }
    }

    @Override
    public void onError(final ErrorObject errorObject, final Device device) {
        Log.i(TAG, "onError: " + errorObject.getErrorMessage() + ", code: " + errorObject.getErrorCode());

        String message = "";

        if (connectingDialog != null) connectingDialog.dismiss();

        int code = errorObject.getErrorCode();
        if (code == ErrorDefinitions.BLUETOOTH_DEVICE_133_ERROR_CODE
                || code == ErrorDefinitions.BLUETOOTH_DEVICE_62_ERROR_CODE) {

            if (device != null && currentConnectionAttemptToDevice != null
                    && device.getDeviceID().equalsIgnoreCase(currentConnectionAttemptToDevice.getDeviceID())) {
                String title = "Device not found";
                message = "The Device can not be found, please verify the device is turned ON and in range";
                connectingDialog.setDialog(requireActivity(), title, message, true);
                connectingDialog.show();
                return;
            }

            stopConnectionAttempt();
            showError(errorObject, message);
            return;
        }

        if (code == ErrorDefinitions.HOTSPOT_DEVICE_IP_NOT_REACHABLE_CODE
                || code == ErrorDefinitions.AP_DEVICE_IP_NOT_REACHABLE_CODE) {
            showError(errorObject, message);
            return;
        }

        if (code == ErrorDefinitions.BLUETOOTH_DEVICE_UNABLE_TO_PAIR_CODE) {
            message = String.format(Locale.getDefault(),
                    "%s \n %s",
                    "Please Reset Device and remove pairing Settings manually in Android settings.",
                    "and try again."
            );
            showError(errorObject, message);
            stopConnectionTimeOutTimer();
            return;
        }

        showError(errorObject, message);
    }

    /* =========================
       화면 전환 / 보조 UI
       ========================= */
    // ConnectDistoFragment.java

    private void goToInfoScreen(Device device) {
        stopConnectionTimeOutTimer();

        // 1) 전역 저장 (이미 하고 있지만 확실히 유지)
        Clipboard.INSTANCE.setInformationActivityData(
                new InformationActivityData(device, null, deviceManager)
        );

        // 2) 액티비티 전환 제거 (주석 처리)
        // Class<?> nextActivity = ... (전부 제거)
        // startActivity(intent);

        // 3) 원하는 화면으로 네비게이션 (Navigation Component 쓰면)
        // NavHostFragment.findNavController(this)
        //        .navigate(R.id.action_connectDisto_to_surveyDiameter);

        // 또는 탭 전환/콜백 등으로 SurveyDiameter로 이동
        Log.i(TAG, "Device connected. Ready to use in other fragments.");
        currentDevice = null;
    }


    private void gotoWifiPanel() {
        String title = "Wifi Settings";
        String message = "Please connect to the WIFI HOTSPOT from the device.";
        String positive = "OK";
        Runnable action = () -> {
            connectingDialog.dismiss();
            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
        };
        DialogHandler wifiDialog = new DialogHandler();
        wifiDialog.setDialog(requireActivity(), title, message, false, positive, action, null, null);
        wifiDialog.show();
    }

    private void showConnectionTimedOutDialog() {
        if (currentDevice != null) {
            String title = "Connection Timeout";
            String message = String.format(
                    "Could not connect to \n%s\nPlease check your device and adapters and try again.",
                    currentDevice.getDeviceID()
            );
            connectingDialog.setDialog(requireActivity(), title, message, true);
            connectingDialog.show();
        }
    }

    private void showError(ErrorObject error, String message) {
        alertsDialog.setAlert(
                requireActivity(),
                String.format(Locale.getDefault(),
                        "errorCode: %d, %s \n %s",
                        error.getErrorCode(),
                        error.getErrorMessage(),
                        message)
        );
        alertsDialog.show();
    }
}

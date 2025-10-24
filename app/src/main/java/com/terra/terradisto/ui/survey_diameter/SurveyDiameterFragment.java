package com.terra.terradisto.ui.survey_diameter;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.terra.terradisto.R;
import com.terra.terradisto.distosdkapp.SharedViewModel;
import com.terra.terradisto.databinding.FragmentSurveyDiameterBinding;

// Leica SDK / 앱 내부 클래스들
import ch.leica.sdk.Devices.BleDevice;
import ch.leica.sdk.Devices.Device;

import com.terra.terradisto.distosdkapp.clipboard.Clipboard;
import com.terra.terradisto.distosdkapp.clipboard.InformationActivityData;
import com.terra.terradisto.distosdkapp.data.AppDatabase;
import com.terra.terradisto.distosdkapp.data.SurveyDiameterDao;
import com.terra.terradisto.distosdkapp.data.SurveyDiameterEntity;
import com.terra.terradisto.distosdkapp.device.YetiDeviceController;

import java.util.concurrent.Executors;

import ch.leica.sdk.ErrorHandling.ErrorObject;
import ch.leica.sdk.Types;
import ch.leica.sdk.commands.response.Response;

public class SurveyDiameterFragment extends Fragment
        implements YetiDeviceController.YetiDataListener {

    private static final String TAG = "SurveyDiameterFragment";

    private FragmentSurveyDiameterBinding binding;
    private SharedViewModel sharedViewModel;
//    private int currentProjectId = -1;

    // 컨트롤러
    private YetiDeviceController yetiController;

    // 측정 스케줄링
    private final Handler measureHandler = new Handler(Looper.getMainLooper());
    private Runnable measureTask;
    private boolean isMeasuring = false;

    // 실시간/최대값 추적
    private double lastDistance = Double.NaN;
    private boolean trendingUp = false;           // 최근에 증가 흐름을 보였는지
    private static final double EPS = 0.002;      // 감소 판단을 위한 임계값(단위: m 기준 2mm 정도)

    private double maxDistance = Double.NEGATIVE_INFINITY;
    private String maxDistanceUnit = "";
    private double maxAngle = Double.NEGATIVE_INFINITY;
    private String maxAngleUnit = "";

    private LinearLayout ll_measure_controll;
    private float dX, dY;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        yetiController = new YetiDeviceController(
                requireContext().getApplicationContext(),
                this // YetiDataListener
        );

        // 🔗 연결된 디바이스 주입
        InformationActivityData info = Clipboard.INSTANCE.getInformationActivityData();
        Log.e(TAG, "Clipboard device: " + (info != null && info.device != null ? info.device.getDeviceName() : "null"));

        if (info != null && info.device != null) {
            Device device = info.device;
            if (device.getConnectionState() == Device.ConnectionState.connected) {
                if (info.device.getDeviceType() == ch.leica.sdk.Types.DeviceType.Yeti) {
                    yetiController.setCurrentDevice(info.device);
                    yetiController.setListeners(); // 리스너 재바인딩
                } else {
                    Log.w(TAG, "Connected device is not Yeti. Current type=" + info.device.getDeviceType());
                }
            }
        } else {
            Log.w(TAG, "No device in Clipboard; connect first in ConnectDistoFragment.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSurveyDiameterBinding.inflate(inflater, container, false);



        /* 버튼 이동 동작 삭제(보류) */
//        binding.mcAutoBtn.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        // 손가락을 눌렀을 때 기준 좌표 저장
//                        dX = v.getX() - event.getRawX();
//                        dY = v.getY() - event.getRawY();
//                        return true;
//
//                    case MotionEvent.ACTION_MOVE:
//                        // 움직일 때 View 위치 갱신 (애니메이션 사용)
//                        v.animate()
//                                .x(event.getRawX() + dX)
//                                .y(event.getRawY() + dY)
//                                .setDuration(0) // 즉시 이동
//                                .start();
//                        return true;
//
//                    case MotionEvent.ACTION_UP:
//                        // 접근성 이벤트 보장 (Kotlin 코드와 동일)
//                        v.performClick();
//                        return true;
//
//                    default:
//                        return false;
//                }
//            }
//        });

        /* 측정 시작 버튼 */
        binding.mcAutoBtn.setOnClickListener(view -> onClickSurveyToggle());

        /* 측정값 확정 */
        binding.mcMeasureResultFix.setOnClickListener(view -> {
            String distanceValue = binding.tvDistance.getText().toString();
            int color = android.graphics.Color.parseColor("#E9ECEF");
            Log.e("khj", "측정 상태 - distance >>  : " + binding.tvDistance.getText().toString());
            Log.e("khj", "입력 전 상태 - 01 >>> : " + binding.tvSceneryFirst.getText().toString());
            Log.e("khj", "입력 전  상태 - 02 >>> : " + binding.tvScenerySecond.getText().toString());
            Log.e("khj", "입력 전  상태 - 03 >>> : " + binding.tvSceneryThird.getText().toString());
            Log.e("khj", "입력 전  상태 - 04 >>> : " + binding.tvSceneryFourth.getText().toString());

            // TextUtils.isEmpty()를 사용하여 null 또는 빈 문자열을 안전하게 체크합니다.
            // 1. 첫 번째 칸 확인
            if (android.text.TextUtils.isEmpty(binding.tvSceneryFirst.getText().toString())) {
                binding.tvSceneryFirst.setText(distanceValue);
                binding.mtMeasureResultFix.setBackgroundColor(color); // change gray
                binding.mcAutoBtn.setCardBackgroundColor(Color.BLACK); // change black
                binding.tvDistance.setText("");
                showToast("첫번째 측정 값이 입력되었습니다.");
                return;
            }

            // 2. 두 번째 칸 확인
            if (android.text.TextUtils.isEmpty(binding.tvScenerySecond.getText().toString())) {
                binding.tvScenerySecond.setText(distanceValue);
                binding.mtMeasureResultFix.setBackgroundColor(color); // change gray
                binding.mcAutoBtn.setCardBackgroundColor(Color.BLACK); // change black
                binding.tvDistance.setText("");
                showToast("두번째 측정 값이 입력되었습니다.");
                return;
            }

            // 3. 세 번째 칸 확인
            if (android.text.TextUtils.isEmpty(binding.tvSceneryThird.getText().toString())) {
                binding.tvSceneryThird.setText(distanceValue);
                binding.mtMeasureResultFix.setBackgroundColor(color); // change gray
                binding.mcAutoBtn.setCardBackgroundColor(Color.BLACK); // change black
                binding.tvDistance.setText("");
                showToast("세번째 측정 값이 입력되었습니다.");
                return;
            }

            // 4. 네 번째 칸 확인
            if (android.text.TextUtils.isEmpty(binding.tvSceneryFourth.getText().toString())) {
                binding.tvSceneryFourth.setText(distanceValue);
                binding.mtMeasureResultFix.setBackgroundColor(color); // change gray
                binding.mcAutoBtn.setCardBackgroundColor(color); // change black
                binding.tvDistance.setText("");
                showToast("네번째 측정 값이 입력되었습니다.");
                return;
            }

            // 모두 채워진 경우
            showToast("모든 측정 값이 이미 입력되었습니다.");
            binding.mtMeasureComplite.setBackgroundColor(color);
            return;
        });

        Log.e("khj", "측정 상태 - 관경 >>  : " + binding.tvDistance.getText().toString());
        Log.e("khj", "입력 후 상태 - 01 >>> : " + binding.tvSceneryFirst.getText().toString());
        Log.e("khj", "입력 후  상태 - 02 >>> : " + binding.tvScenerySecond.getText().toString());
        Log.e("khj", "입력 후  상태 - 03 >>> : " + binding.tvSceneryThird.getText().toString());
        Log.e("khj", "입력 후  상태 - 04 >>> : " + binding.tvSceneryFourth.getText().toString());
        binding.mcPicture.setOnClickListener(view -> openExternalApp());

        // MeasureResultButton
//        binding.mcMeasureResult.setOnClickListener(view -> measureInputData());
        binding.mcMeasureResult.setOnClickListener(view -> { saveMeasureData(); });
        binding.mcMeasureComplite.setOnClickListener(view -> { surveyDiameterComplete(); });

        // 숫자 눌렀을 경우
        binding.mcNumberFirst.setOnClickListener(view -> { dataReplcaDataFirst(); });
        binding.mcNumberSecond.setOnClickListener(view -> { dataReplcaDataSecond(); });
        binding.mcNumberThird.setOnClickListener(view -> { dataReplcaDataThird(); });
        binding.mcNumberFourth.setOnClickListener(view -> { dataReplcaDataFourth(); });

        return binding.getRoot();
    }

    private void surveyDiameterComplete() {
        NavHostFragment.findNavController(this).navigate(R.id.surveyDiameterComplite);
    }

    private void dataReplcaDataFirst() {
        String distanceValue = binding.tvDistance.getText().toString();

        if (android.text.TextUtils.isEmpty(binding.tvSceneryFirst.getText().toString())) {
            binding.tvSceneryFirst.setText(distanceValue);
        }

        if (!android.text.TextUtils.isEmpty(binding.tvSceneryFirst.getText().toString())) {
            binding.tvSceneryFirst.setText(distanceValue);
        }
    }

    private void dataReplcaDataSecond() {
        String distanceValue = binding.tvDistance.getText().toString();

        if (android.text.TextUtils.isEmpty(binding.tvScenerySecond.getText().toString())) {
            binding.tvScenerySecond.setText(distanceValue);
        }

        if (!android.text.TextUtils.isEmpty(binding.tvScenerySecond.getText().toString())) {
            binding.tvScenerySecond.setText(distanceValue);
        }
    }

    private void dataReplcaDataThird() {
        String distanceValue = binding.tvDistance.getText().toString();

        if (android.text.TextUtils.isEmpty(binding.tvSceneryThird.getText().toString())) {
            binding.tvSceneryThird.setText(distanceValue);
        }

        if (!android.text.TextUtils.isEmpty(binding.tvSceneryThird.getText().toString())) {
            binding.tvSceneryThird.setText(distanceValue);
        }
    }

    private void dataReplcaDataFourth() {
        String distanceValue = binding.tvDistance.getText().toString();

        if (android.text.TextUtils.isEmpty(binding.tvSceneryFourth.getText().toString())) {
            binding.tvSceneryFourth.setText(distanceValue);
        }

        if (!android.text.TextUtils.isEmpty(binding.tvSceneryFourth.getText().toString())) {
            binding.tvSceneryFourth.setText(distanceValue);
        }
    }

    private void saveMeasureData() {

        // 1. SharedViewModel에서 현재 프로젝트 ID를 가져옵니다. (가장 먼저)
        int currentProjectId = sharedViewModel.getSelectedProjectId().getValue() != null ?
                sharedViewModel.getSelectedProjectId().getValue() : -1;

        if (currentProjectId == -1) {
            showToast("🚨 먼저 프로젝트 목록 화면에서 프로젝트를 선택하세요!");
            return;
        }

        // 2. 화면의 입출력값/측정 값 수집(순서 변경)

        // 도엽 번호 / 맨홀 타입(갯수)
        String mapNumber = binding.tvPipingNumber.getText().toString().trim(); // 도엽 번호
        String manholType = binding.spinnerManholeCount.getSelectedItem().toString(); // 맨홀 타입(갯수)

        // 관경 (Scenery)
        String tvSceneryFirst = binding.tvSceneryFirst.getText().toString().trim();
        String tvScenerySecond = binding.tvScenerySecond.getText().toString().trim();
        String tvSceneryThird = binding.tvSceneryThird.getText().toString().trim();
        String tvSceneryFourth = binding.tvSceneryFourth.getText().toString().trim();

        // 수기 입력값 (Pipe Material)
        String etInputFirst = binding.etInputFirst.getText().toString().trim();
        String etInputSecond = binding.etInputSecond.getText().toString().trim();
        String etInputThird = binding.etInputThird.getText().toString().trim();
        String etInputFourth = binding.etInputFourth.getText().toString().trim();

        // 재질 (Pipe Material)
        String pipMaterialFirst = binding.etPipMaterialFirst.getText().toString().trim();
        String pipMaterialSecond = binding.etPipMaterialSecond.getText().toString().trim();
        String pipMaterialThird = binding.etPipMaterialThird.getText().toString().trim();
        String pipMaterialFourth = binding.etPipMaterialFourth.getText().toString().trim();

        // 3. 유효성 검사 (필요한 경우 관경/재질 필드까지 검사 로직 추가)
        if (mapNumber.isEmpty() || manholType.isEmpty()) {
            showToast("도엽 번호와 맨홀 타입은 필수 입력 사항입니다.");
            return;
        }

        // 4. SurveyDiameterEntity 객체 생성
        SurveyDiameterEntity entity = new SurveyDiameterEntity(
                currentProjectId,
                mapNumber, manholType,
                tvSceneryFirst, tvScenerySecond, tvSceneryThird, tvSceneryFourth,
                etInputFirst, etInputSecond, etInputThird, etInputFourth,
                pipMaterialFirst, pipMaterialSecond, pipMaterialThird, pipMaterialFourth
        );

        // [삭제]: Bundle 관련 로직은 제거 (다음 화면으로 전달하지 않으므로)
        /*
        SurveyDiameterData data = (SurveyDiameterData) getArguments().getSerializable("surveyData");
        if (data != null) { ... }
        */


        // 5. 로그로 확인
        Log.e(TAG, "저장 데이터 (Project ID / Entity) : " + currentProjectId + " / " + entity.getMapNumber());

        // 6. Room DB에 저장 (비동기 처리)
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                // Fragment이므로 requireContext() 사용
                AppDatabase db = AppDatabase.getDatabase(requireContext());
                SurveyDiameterDao dao = db.surveyDiameterDao();     // 맨홀번호 중복값 체크

                Log.e("khj", "count >>> " + dao);
                int count = dao.countExistingMapNumber(currentProjectId, mapNumber);
                Log.e("khj", "count >>> " + count);
                if (count > 0) {
                    requireActivity().runOnUiThread(() -> {
                        showToast("이미 존재하는 맨홀번호 입니다.");
                    });
                    return;
                }

                db.surveyDiameterDao().insert(entity);




                Log.e(TAG, "Room DB에 데이터 저장 완료 : ID=" + entity.getId());

                // UI 피드백을 위한 메인 스레드 전환
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        showToast("측정 데이터가 저장되었습니다. ✅");

                        String input = binding.tvPipingNumber.getText().toString().trim();
                        Log.e("khj", "test_01 >>> " + input);
                        binding.tvPipingNumber.setText(input + 1);

                        // 문자열에서 숫자 부분만 추출 (정규식 사용)
                        String perfix = input.replaceAll("\\d+$", ""); // 숫자가 아닌 앞 부분
                        String numberPart = input.replaceAll("^\\D+", "");

                        if (!numberPart.isEmpty()) {
                            try {
                                int number = Integer.parseInt(numberPart);
                                number++; // 숫자 + 1
                                String newText = perfix + number;
                                binding.tvPipingNumber.setText(newText);
                            } catch (NumberFormatException e) {
                                // 숫자 변환 시, 원래 텍스트는 유지
                                binding.tvPipingNumber.setText(input);
                            }
                        } else {
                            // 숫자가 포함되지 않은 경우, 단순히 "_1" 추가
                            binding.tvPipingNumber.setText(input + "_1");
                        }

                        // 저장 완료 후 모든 필드 초기화
                        binding.tvSceneryFirst.setText("");
                        binding.tvScenerySecond.setText("");
                        binding.tvSceneryThird.setText("");
                        binding.tvSceneryFourth.setText("");

                        binding.etInputFirst.setText("");
                        binding.etInputSecond.setText("");
                        binding.etInputThird.setText("");
                        binding.etInputFourth.setText("");

                        binding.etPipMaterialFirst.setText("");
                        binding.etPipMaterialSecond.setText("");
                        binding.etPipMaterialThird.setText("");
                        binding.etPipMaterialFourth.setText("");

                        // 필요하다면 Spinner도 초기화
                        binding.spinnerManholeCount.setSelection(0);
                    });
                }
            } catch (Exception e) {
                Log.e(TAG, "Room DB 저장 실패", e);

                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        showToast("데이터 저장에 실패했습니다.");
                    });
                }
            }
        });
    }

    private void measureInputData() {
        /**
         * 1. 화면의 입력값/측정 값 수집
         * 2. 엑셀 다운로드를 대비하기 위한 필요한 데이터 확인 -
         *  - 도엽번호
         *  - 맨홀타입
         *  - 1번 재질, 관경, 평면, 심도
         *  - 2번 재질, 관경, 평면, 심도
         *  - 3번 재질, 관경, 평면, 심도
         *  - 4번 재질, 관경, 평면, 심도
         */

        /**
         * variableName(Kor) : 도엽 번호 / 맨홀 타입(갯수)
         * variableName : tv_piping_number / spinner_manhole_count
         */
        String mapNumber = binding.tvPipingNumber.getText().toString().trim(); // 도엽 번호
        String manholType = binding.spinnerManholeCount.getSelectedItem().toString(); // 맨홀 타입(갯수)

        /**
         * variableName(Kor) : 관경
         * variableName : tv_scenery_first / Second / Third / Fourth
         */
        String tvSceneryFirst = binding.tvSceneryFirst.getText().toString().trim(); // 1번 재질
        String tvScenerySecond = binding.tvScenerySecond.getText().toString().trim(); // 2번 재질
        String tvSceneryThird = binding.tvSceneryThird.getText().toString().trim(); // 3번 재질
        String tvSceneryFourth = binding.tvSceneryFourth.getText().toString().trim(); // 4번 재질
        Log.e("Disto", "tvSceneryFirst : " + tvSceneryFirst + "tvScenerySecond : " + tvScenerySecond
                + "tvSceneryThird : " + tvSceneryThird + "tvSceneryFourth : " + tvSceneryFourth);

        /**
         * variableName(Kor) : 재질
         * variableName : et_pip_material_first / Second / Third / Fourth
         */
        String pipMaterialFirst = binding.etPipMaterialFirst.getText().toString().trim(); // 1번 재질
        String pipMaterialSecond = binding.etPipMaterialSecond.getText().toString().trim(); // 2번 재질
        String pipMaterialThird = binding.etPipMaterialThird.getText().toString().trim(); // 3번 재질
        String pipMaterialFourth = binding.etPipMaterialFourth.getText().toString().trim(); // 4번 재질
        Log.e("Disto", "pipMaterialFirst : " + pipMaterialFirst + "pipMaterialSecond : " + pipMaterialSecond
                + "pipMaterialThird : " + pipMaterialThird + "pipMaterialFourth : " + pipMaterialFourth);

        // 2. 유효성 검사
        if (mapNumber.isEmpty() || manholType.isEmpty()) {
            showToast("도엽 번호와 맨홀 타입은 필수 입력 사항입니다.");
            return;
        }

    }

    private void handleBackButtonClick() {
        NavController navController= Navigation.findNavController(requireView());

        navController.popBackStack();
        Log.e("khj", "backStack");
    }

    private void openExternalApp() {
        final String packageName = "com.joyhonest.sports_dv";

        PackageManager packageManager = requireContext().getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);

        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                startActivity(intent);
                Log.e("khj", "앱 실행 성공!");
            } catch (Exception e) {
                // 시스템 보안 문제 등으로 실행에 실패할 경우
                Log.e("AppLauncher", "앱 실행 중 오류 발생: " + e.getMessage());
                Toast.makeText(requireContext(), "앱을 실행할 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        } else {
            // 앱이 설치되어 있지 않은 경우 사용자에게 알림 및 플레이 스토어로 이동 시도
            Toast.makeText(requireContext(), "Sports DV 앱이 설치되어 있지 않습니다. 스토어로 이동합니다.", Toast.LENGTH_LONG).show();

            try {
                // 앱이 없으므로, 플레이 스토어에서 해당 패키지명으로 검색하도록 Intent 생성
                Intent storeIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + packageName));

                // 패키지가 구글 플레이 스토어가 아닐 수 있으므로, 브라우저로도 열리도록 대체 Intent 설정
                if (storeIntent.resolveActivity(packageManager) == null) {
                    storeIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
                }

                startActivity(storeIntent);

            } catch (Exception e) {
                // 플레이 스토어 또는 브라우저가 없는 예외 상황
                Log.e("AppLauncher", "Play Store로 이동 실패: " + e.getMessage());
            }
        }
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//
//        Device dev = yetiController.getCurrentDevice();
//
//        if (dev != null && dev.getConnectionState() == Device.ConnectionState.connected) {
//            Log.d(TAG, "Device already connected in onResume");
//            return;
//        }
//
//        // 이미 페어링/선택된 디바이스가 있다면 자동 재연결 시도
//        if (getContext() != null) {
//            yetiController.checkForReconnection(requireContext());
//        }
//    }

    @Override
    public void onResume() {
        super.onResume();

        Device dev = yetiController.getCurrentDevice();

        // 연결 상태 체크
        if (dev != null && dev.getConnectionState() == Device.ConnectionState.connected) {
            Log.d(TAG, "Device already connected in onResume");
            Log.d(TAG, "onResume - Device: " + dev.getDeviceName()
                    + ", State: " + dev.getConnectionState());

            // 혹시 진행 중인 측정이 있다면 정리
            if (isMeasuring) {
                stopMeasuring(false);
            }

            return; // 재연결 시도 안함
        }

        // 연결 끊어진 경우에만 재연결
        if (getContext() != null) {
//            yetiController.checkForReconnection(requireContext());
        }
    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        // 화면 떠날 땐 측정 중지 + 노티 멈춤
//        stopMeasuring(false);
//        try {
//            yetiController.pauseBTConnection(new BleDevice.BTConnectionCallback() {
//                @Override
//                public void onFinished() {
//                    Log.d(TAG, "Notifications deactivated.");
//                }
//            });
//        } catch (Exception ignore) {}
//    }
    @Override
    public void onStop() {
        super.onStop();

        // ✅ 측정 완전히 중지
        if (isMeasuring) {
            isMeasuring = false; // 플래그 먼저 false
            measureHandler.removeCallbacksAndMessages(null);
        }

        // ✅ 연결 pause는 하지 않음 (다음 진입 시 문제 방지)
        // yetiController.pauseBTConnection(...); // 주석 처리

        Log.d(TAG, "onStop - measurement stopped, connection maintained");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 핸들러 콜백 제거
        measureHandler.removeCallbacksAndMessages(null);
        binding = null;
    }

    /* =========================
       버튼: 측정 토글
       ========================= */
//    private void onClickSurveyToggle() {
//        Device dev = yetiController.getCurrentDevice();
//
//        Log.e(TAG, "측정 버튼 클릭 - Device: " + (dev != null ? dev.getDeviceName() : "null"));
//        Log.e(TAG, "측정 버튼 클릭 - State: " + (dev != null ? dev.getConnectionState() : "N/A"));
//
//        if (dev == null) {
//            showToast("먼저 Connect 화면에서 기기를 연결하세요.");
//            return;
//        }
//        if (dev.getConnectionState() != Device.ConnectionState.connected) {
//            showToast("기기 재연결 시도 중...");
//            yetiController.checkForReconnection(requireContext());
//            return;
//        }
//
//        if (!isMeasuring) {
//            startMeasuring();
//        } else {
//            stopMeasuring(true);
//        }
//    }

    private void onClickSurveyToggle() {
        Device dev = yetiController.getCurrentDevice();
        if (dev == null) {
            showToast("먼저 Connect 화면에서 기기를 연결하세요.");
            return;
        }
        if (dev.getConnectionState() != Device.ConnectionState.connected) {
            showToast("기기 재연결 시도 중...");
            yetiController.checkForReconnection(requireContext());
            return;
        }

        if (!isMeasuring) {
            // ✅ 측정 시작 전 SDK 상태 초기화
            clearPendingCommands();

            // 짧은 딜레이 후 측정 시작 (SDK 정리 완료 대기)
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                startMeasuring();
            }, 300);
        } else {
            stopMeasuring(true);
        }
    }

    // 새로운 메서드: SDK 명령 큐 초기화
    private void clearPendingCommands() {
        Device dev = yetiController.getCurrentDevice();
        if (dev == null) return;

        try {
            // SDK에 더미 명령을 보내서 큐를 비움
            // (에러가 나도 상관없음 - 목적은 큐 정리)
            new Thread(() -> {
                try {
                    // 간단한 명령으로 큐 flush
                    Response response = dev.sendCommand(Types.Commands.GetBrandDistocom);
                    if (response != null) {
                        // 응답은 무시
                    }
                } catch (Exception e) {
                    // 에러 무시 - 큐 정리가 목적
                    Log.d(TAG, "clearPendingCommands: " + e.getMessage());
                }
            }).start();

            // 약간 대기
            Thread.sleep(200);

        } catch (Exception e) {
            Log.e(TAG, "Error clearing pending commands", e);
        }
    }

    /* =========================
       측정 시작/정지
       ========================= */
//    private void startMeasuring() {
//        // 상태 초기화
//        isMeasuring = true;
//        lastDistance = Double.NaN;
//        trendingUp = false;
//
//        maxDistance = Double.NEGATIVE_INFINITY;
//        maxDistanceUnit = "";
//        maxAngle = Double.NEGATIVE_INFINITY;
//        maxAngleUnit = "";
//
//        // UI 초기화
//        if (binding != null) {
//            String distanceValue = binding.tvDistance.getText().toString();
//            int color = android.graphics.Color.parseColor("#E9ECEF");
////            binding.tvRealtimeDistance.setText("");
////            binding.tvRealtimeAngle.setText("");
////            binding.tvMaxDistance.setText("");
//            binding.tvDistance.setText("");
////            binding.tvMaxAngle.setText("");
//            binding.btnSurvey.setText("측정 정지");
//            binding.mcAutoBtn.setCardBackgroundColor(Color.BLACK); // change black
//            binding.mtMeasureResultFix.setBackgroundColor(color); // change gray
//        }
//
//        // 1초 간격 측정 태스크
//        measureTask = new Runnable() {
//            @Override
//            public void run() {
//                if (!isMeasuring) return;
//                sendDistanceCommandOnWorker();
//                // 다음 예약
//                measureHandler.postDelayed(this, 1000);
//            }
//        };
//
//        // 즉시 1회 + 주기 시작
//        measureHandler.post(measureTask);
//    }
    private void startMeasuring() {
        // ✅ 이미 측정 중이면 중지 후 재시작
        if (isMeasuring) {
            Log.w(TAG, "Already measuring. Stopping first...");
            stopMeasuring(false);

            // 짧은 딜레이 후 재시작
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                startMeasuringInternal();
            }, 300);
            return;
        }

        startMeasuringInternal();
    }

    private void startMeasuringInternal() {
        // 상태 초기화
        isMeasuring = true;
        lastDistance = Double.NaN;
        trendingUp = false;

        maxDistance = Double.NEGATIVE_INFINITY;
        maxDistanceUnit = "";
        maxAngle = Double.NEGATIVE_INFINITY;
        maxAngleUnit = "";

        // UI 초기화
        if (binding != null) {
            int color = android.graphics.Color.parseColor("#E9ECEF");
            binding.tvDistance.setText("");
            binding.btnSurvey.setText("측정 정지");
            binding.mcAutoBtn.setCardBackgroundColor(Color.BLACK);
            binding.mtMeasureResultFix.setBackgroundColor(color);
        }

        // 1초 간격 측정 태스크
        measureTask = new Runnable() {
            @Override
            public void run() {
                if (!isMeasuring) return;
                sendDistanceCommandOnWorker();
                measureHandler.postDelayed(this, 1000);
            }
        };

        // 즉시 1회 + 주기 시작
        measureHandler.post(measureTask);
    }


//    private void stopMeasuring(boolean showToast) {
//        if (!isMeasuring) return;
//        isMeasuring = false;
//        measureHandler.removeCallbacksAndMessages(null);
//
//        if (binding != null) {
//            binding.btnSurvey.setText(getString(com.terra.terradisto.R.string.survey_diameter));
//            binding.mtMeasureResultFix.setBackgroundColor(Color.BLACK); // change black
//
//        }
//        if (showToast) showToast("측정을 중지했습니다.");
//    }

    private void stopMeasuring(boolean showToast) {
        if (!isMeasuring) return;

        isMeasuring = false;

        measureHandler.removeCallbacksAndMessages(null);

        Device dev = yetiController.getCurrentDevice();
        if (dev != null && dev.getConnectionState() == Device.ConnectionState.connected) {
            try {
                // SDK에 명령 취소 요청 (있다면)
                // dev.cancelCurrentCommand(); // SDK에 이런 메서드가 있는지 확인

                // 또는 짧은 대기 후 상태 초기화
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    Log.d(TAG, "Command queue cleared");
                }, 100);

            } catch (Exception e) {
                Log.e(TAG, "Error canceling command", e);
            }
        }

        if (binding != null) {
            binding.btnSurvey.setText(getString(R.string.survey_diameter));
            binding.mtMeasureResultFix.setBackgroundColor(Color.BLACK);
        }

        if (showToast) showToast("측정을 중지했습니다.");
    }

    /* =========================
       명령 전송(백그라운드)
       ========================= */
//    private void sendDistanceCommandOnWorker() {
//        new Thread(() -> {
//            ErrorObject error = yetiController.sendDistanceCommand();
//            if (error != null && isAdded()) {
//                requireActivity().runOnUiThread(() -> showToast(formatErrorMessage(error)));
//            }
//        }).start();
//    }

    private void sendDistanceCommandOnWorker() {
        if (!isMeasuring) {
            Log.d(TAG, "Measurement stopped, skip command");
            return;
        }

        new Thread(() -> {
            try {
                ErrorObject error = yetiController.sendDistanceCommand();

                // null이면 정상 (또는 재시도 대기)
                if (error == null) {
                    return;
                }

                // 에러가 있으면 UI에 표시
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        showToast(formatErrorMessage(error));
                        // 에러 발생 시 측정 중지
                        stopMeasuring(false);
                    });
                }
            } catch (Exception e) {
                Log.e(TAG, "Error sending command", e);
                // 예외 발생 시에도 측정 중지
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> stopMeasuring(false));
                }
            }
        }).start();
    }

    private void showToast(String msg) {
        if (!isAdded()) return;
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private String formatErrorMessage(ErrorObject error) {
        return error.getErrorMessage();
    }

    /* =========================
       YetiDataListener 콜백
       ========================= */
    @Override
    public void onBasicMeasurements_Received(YetiDeviceController.BasicData basicData) {
        // distance / inclination 값은 문자열일 수 있으니 안전 파싱
        final double distance = parseDoubleSafe(basicData.distance);
        final String distanceUnit = basicData.distanceUnit == null ? "" : basicData.distanceUnit;
        final double angle = parseDoubleSafe(basicData.inclination);
        final String angleUnit = basicData.inclinationUnit == null ? "" : basicData.inclinationUnit;

        if (!isAdded()) return;

        requireActivity().runOnUiThread(() -> {
            // 실시간 표시
            if (binding != null) {
                if (!Double.isNaN(distance)) {
//                    binding.tvRealtimeDistance.setText(basicData.distance + " " + distanceUnit);
                }
                if (!Double.isNaN(angle)) {
//                    binding.tvRealtimeAngle.setText(basicData.inclination + " " + angleUnit);
                }
            }

            // 최대값 갱신 (독립적으로 추적)
            if (!Double.isNaN(distance) && distance > maxDistance) {
                maxDistance = distance;
                maxDistanceUnit = distanceUnit;
                if (binding != null) {
//                    binding.tvMaxDistance.setText(basicData.distance + " " + distanceUnit);
                    binding.tvDistance.setText(basicData.distance + " " + distanceUnit);
                }
            }
            if (!Double.isNaN(angle) && angle > maxAngle) {
                maxAngle = angle;
                maxAngleUnit = angleUnit;
                if (binding != null) {
//                    binding.tvMaxAngle.setText(basicData.inclination + " " + angleUnit);
                }
            }

            // 감소 감지 → 자동 정지
            // (최근에 증가 흐름을 보였고, 현재 값이 이전 값보다 EPS 이상 작아졌다면 정지)
            if (!Double.isNaN(distance)) {
                if (Double.isNaN(lastDistance)) {
                    lastDistance = distance; // 첫 샘플 세팅
                } else {
                    if (distance > lastDistance + EPS) {
                        trendingUp = true;      // 증가 흐름 진입
                    } else if (trendingUp && distance < lastDistance - EPS) {
                        // 피크 이후 하강 시작 → 측정 종료
                        stopMeasuring(true);
                    }
                    lastDistance = distance;
                }
            }
        });
    }

    @Override
    public void onP2PMeasurements_Received(YetiDeviceController.P2PData p2pData) {
        Log.d(TAG, "[P2P] hz=" + p2pData.hzValue + ", ve=" + p2pData.veValue
                + ", inclStatus=" + p2pData.inclinationStatus
                + ", ts=" + p2pData.timestamp);
    }

    @Override
    public void onQuaternionMeasurement_Received(YetiDeviceController.QuaternionData quaternionData) {
        Log.d(TAG, "[QUAT] x=" + quaternionData.quaternionX
                + ", y=" + quaternionData.quaternionY
                + ", z=" + quaternionData.quaternionZ
                + ", w=" + quaternionData.quaternionW
                + ", ts=" + quaternionData.timestamp);
    }

    @Override
    public void onAccRotationMeasurement_Received(YetiDeviceController.AccRotData accRotatonMeasurement) {
        Log.d(TAG, "[ACC/ROT] ax=" + accRotatonMeasurement.accelerationX
                + ", ay=" + accRotatonMeasurement.accelerationY
                + ", az=" + accRotatonMeasurement.accelerationZ
                + ", rx=" + accRotatonMeasurement.rotationX
                + ", ry=" + accRotatonMeasurement.rotationY
                + ", rz=" + accRotatonMeasurement.rotationZ
                + ", ts=" + accRotatonMeasurement.timestamp);
    }

    @Override
    public void onMagnetometerMeasurement_Received(YetiDeviceController.MagnetometerData magnetometerData) {
        Log.d(TAG, "[MAG] mx=" + magnetometerData.magnetometerX
                + ", my=" + magnetometerData.magnetometerY
                + ", mz=" + magnetometerData.magnetometerZ
                + ", ts=" + magnetometerData.timestamp);
    }

    @Override
    public void onDistocomTransmit_Received(String data) {
        Log.d(TAG, "[DISTOCOM RESP] " + data);
    }

    @Override
    public void onDistocomEvent_Received(String data) {
        Log.d(TAG, "[DISTOCOM EVENT] " + data);
    }

    @Override
    public void onBrand_Received(String data) { Log.d(TAG, "[INFO] brand=" + data); }

    @Override
    public void onAPPSoftwareVersion_Received(String data) { Log.d(TAG, "[INFO] appSW=" + data); }

    @Override
    public void onId_Received(String data) { Log.d(TAG, "[INFO] id=" + data); }

    @Override
    public void onEDMSoftwareVersion_Received(String data) { Log.d(TAG, "[INFO] edmSW=" + data); }

    @Override
    public void onFTASoftwareVersion_Received(String data) { Log.d(TAG, "[INFO] ftaSW=" + data); }

    @Override
    public void onAPPSerial_Received(String data) { Log.d(TAG, "[INFO] appSerial=" + data); }

    @Override
    public void onEDMSerial_Received(String data) { Log.d(TAG, "[INFO] edmSerial=" + data); }

    @Override
    public void onFTASerial_Received(String data) { Log.d(TAG, "[INFO] ftaSerial=" + data); }

    @Override
    public void onModel_Received(String data) { Log.d(TAG, "[INFO] model=" + data); }

    /* =========================
       유틸
       ========================= */
    private static double parseDoubleSafe(String s) {
        if (s == null) return Double.NaN;
        try {
            // "12,34" 같은 포맷/공백/문자 제거
            String normalized = s.trim()
                    .replace(",", ".")
                    .replaceAll("[^0-9+\\-Ee.]", "");
            if (normalized.isEmpty()) return Double.NaN;
            return Double.parseDouble(normalized);
        } catch (Exception ignore) {
            return Double.NaN;
        }
    }
}

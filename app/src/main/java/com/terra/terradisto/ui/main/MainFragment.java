package com.terra.terradisto.ui.main;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.terra.terradisto.R;
import com.terra.terradisto.distosdkapp.SharedViewModel;
import com.terra.terradisto.databinding.FragmentMainBinding;

public class MainFragment extends Fragment {

    FragmentMainBinding binding;
    private SharedViewModel sharedViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
    }

    // LiveData를 통해 View를 업데이트 하는 메서드를 추가
    private void setUpObservers() {
        sharedViewModel.getSelectedProjectName().observe(getViewLifecycleOwner(), projectName -> {
            if (binding == null) return; // (NullPointerException 방지)

            if (projectName != null && !projectName.isEmpty()) {
                binding.mcProjectStatus.setText("선택된 프로젝트 : " + projectName);
            } else {
                // 선택된 프로젝트가 없을 경우(SharedViewModel의 초기값 null/empty인 경우)
                binding.mcProjectStatus.setText("프로젝트를 선택해주세요");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);

        // LiveData 확인
        setUpObservers();

        // Bundle 처리 : 프로젝트 ID와 이름 받기
        Bundle bundle = getArguments();
        if (bundle != null) {
            int selectedProjectId = bundle.getInt("PROJECT_ID", -1); // 프로젝트 ID 수신
            String selectedProjectName = bundle.getString("PROJECT_NAME"); // 프로젝트 Name 수신

            if (selectedProjectName != null || selectedProjectId != -1) {
                sharedViewModel.setSeletedProjectName(selectedProjectName);
                showToast(selectedProjectName + " 프로젝트가 선택되었습니다.");
            } else {
                // 이름이 Bundle로 넘어오지 않은 경우(예외)
                sharedViewModel.setSeletedProjectName("프로젝트를 선택해주세요.");
            }

            // ID와 이름을 사용했으니 Bundle을 제거
            setArguments(null);

            // 초기 로드 시에 LiveData의 현재 값이 null/empty인 경우 기본 텍스트를 설정
            String currentProjectName = sharedViewModel.getSelectedProjectName().getValue();
            if (currentProjectName == null || currentProjectName.isEmpty()) {
                binding.mcProjectStatus.setText("프로젝트를 선택해주세요.");
            }

            if (selectedProjectId != -1) {
                // 프로젝트 ID를 SharedViewModel에 저장
                sharedViewModel.setProjectId(selectedProjectId);
                Toast.makeText(requireContext(), selectedProjectId + "번 프로젝트가 선택되었습니다.", Toast.LENGTH_SHORT).show();
            }

            setArguments(null);
        }
//        binding.btnConnectDisto.setOnClickListener(v -> {
//            NavHostFragment.findNavController(MainFragment.this)
//                    .navigate(R.id.action_mainFragment_to_connectDisto);
//        });
//
//        binding.btnSurveyDiameter.setOnClickListener(v -> {
//            NavHostFragment.findNavController(MainFragment.this)
//                    .navigate(R.id.action_mainFragment_to_surveyDiameterFragment);
//        });

        binding.mcDistioConn.setOnClickListener(v -> {
            NavHostFragment.findNavController(MainFragment.this)
                    .navigate(R.id.action_mainFragment_to_connectDisto);
        });

        // mc_camera_conn
        binding.mcCameraConn.setOnClickListener(v -> {
//            Toast.makeText(requireContext(), "서비스 준비중 입니다.", Toast.LENGTH_SHORT).show();
            openExternalApp(); // 카메라 앱으로 연결
        });

        // mc_measurement
        binding.mcMeasurement.setOnClickListener(v -> {
            NavHostFragment.findNavController(MainFragment.this)
                    .navigate(R.id.action_mainFragment_to_surveyDiameterFragment);
        });

        // project_create
        binding.mcProjectCreate.setOnClickListener(v -> {
            NavHostFragment.findNavController(MainFragment.this)
                    .navigate(R.id.action_mainFragment_to_createProjectFragment);
//            Toast.makeText(requireContext(), "서비스 준비중 입니다.", Toast.LENGTH_SHORT).show();
        });

        // project_list
        binding.mcProjectSelect.setOnClickListener(v -> {
            NavHostFragment.findNavController(MainFragment.this)
                    .navigate(R.id.action_mainFragment_to_projectListFragment);
//            Toast.makeText(requireContext(), "서비스 준비중 입니다.", Toast.LENGTH_SHORT).show();
        });

        // Measurement
        binding.mcMeasurementList.setOnClickListener( v -> {
            Bundle measurementBundel = new Bundle();
            int currentSeletedId = sharedViewModel.getSelectedProjectId().getValue() != null ?
                    sharedViewModel.getSelectedProjectId().getValue() : -1;

            measurementBundel.putInt("PROJECT_ID", currentSeletedId);

            NavHostFragment.findNavController(MainFragment.this)
                    .navigate(R.id.action_mainFragment_to_measurementListFragment, measurementBundel);
//            Toast.makeText(requireContext(), "서비스 준비중 입니다.", Toast.LENGTH_SHORT).show();
        });

        return binding.getRoot();
    }

    // 카메라 앱으로 연결
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

    private void showToast(String msg) {
        if (!isAdded()) return;
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
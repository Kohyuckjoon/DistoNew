package com.terra.terradisto.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

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
            Toast.makeText(requireContext(), "서비스 준비중 입니다.", Toast.LENGTH_SHORT).show();
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

    private void showToast(String msg) {
        if (!isAdded()) return;
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
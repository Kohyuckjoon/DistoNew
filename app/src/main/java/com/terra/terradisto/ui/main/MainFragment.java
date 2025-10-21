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
import com.terra.terradisto.SharedViewModel;
import com.terra.terradisto.databinding.FragmentMainBinding;

public class MainFragment extends Fragment {

    FragmentMainBinding binding;
    private SharedViewModel sharedViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            int selectedProjectId = bundle.getInt("PROJECT_ID", -1);

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
}
package com.terra.terradisto.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.terra.terradisto.R;
import com.terra.terradisto.databinding.FragmentMainBinding;

public class MainFragment extends Fragment {

    FragmentMainBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false);

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

        binding.mcMeasurementList.setOnClickListener( v -> {
//            NavHostFragment.findNavController(MainFragment.this)
//                    .navigate(R.id.action_mainFragment_to_measurementListFragment);
            Toast.makeText(requireContext(), "서비스 준비중 입니다.", Toast.LENGTH_SHORT).show();
        });

        return binding.getRoot();
    }
}
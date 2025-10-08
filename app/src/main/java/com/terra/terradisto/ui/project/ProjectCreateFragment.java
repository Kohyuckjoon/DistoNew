package com.terra.terradisto.ui.project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.terra.terradisto.databinding.FragmentProjectCreateBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProjectCreateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProjectCreateFragment extends Fragment {

    private FragmentProjectCreateBinding binding;

    public ProjectCreateFragment() {
        // Required empty public constructor
    }
    public static ProjectCreateFragment newInstance(String param1, String param2) {
        ProjectCreateFragment fragment = new ProjectCreateFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProjectCreateBinding.inflate(inflater, container, false);

        binding.mcCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(requireContext(), "서비스 준비중 입니다.", Toast.LENGTH_SHORT).show();
//                String name = binding.etProjectName.getText().toString();
//                String location = String.valueOf(binding.etLocationName.getText().toString());
//                String sheetNumber = String.valueOf(binding.etSheetNumber.getText().toString());
//                String projectMemo = String.valueOf(binding.etProjectCreateMemo.getText().toString());
//                Log.e("DistoCreate", "create value name : " + name
//                                                + "\nlocation : " + location
//                                                + "\nsheetNumber : " + sheetNumber
//                                                + "\nprojectMemo : " + projectMemo);
//
//                // 필수 항목 입력 사항 확인
//                if (name.isEmpty() || location.isEmpty() || sheetNumber.isEmpty()) {
//                    Toast.makeText(requireContext(), "필수 항목을 모두 입력하세요.", Toast.LENGTH_SHORT).show();
//                    return;
//                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
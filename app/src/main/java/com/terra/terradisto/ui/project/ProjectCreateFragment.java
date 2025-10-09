package com.terra.terradisto.ui.project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.terra.terradisto.distosdkapp.data.AppDatabase;
import com.terra.terradisto.ProjectCreate;
import com.terra.terradisto.databinding.FragmentProjectCreateBinding;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
//                Toast.makeText(requireContext(), "서비스 준비중 입니다.", Toast.LENGTH_SHORT).show();
                String name = binding.etProjectName.getText().toString();
                String location = String.valueOf(binding.etLocationName.getText().toString());
                String sheetNumber = String.valueOf(binding.etSheetNumber.getText().toString());
                String projectMemo = String.valueOf(binding.etProjectCreateMemo.getText().toString());
                Log.e("DistoCreate", "create value name : " + name + "\nlocation : " + location
                                                + "\nsheetNumber : " + sheetNumber + "\nprojectMemo : " + projectMemo);

                // 필수 항목 입력 사항 확인
                if (name.isEmpty() || location.isEmpty() || sheetNumber.isEmpty()) {
                    Toast.makeText(requireContext(), "필수 항목을 모두 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(() -> {
                    // DB 객체 가져오기
                    AppDatabase db = AppDatabase.getDatabase(requireContext());

                    // 저장할 데이터 생성
                    ProjectCreate project = new ProjectCreate(
                            name,
                            location,
                            sheetNumber,
                            projectMemo.isEmpty() ? null : projectMemo
                    );

                    // DB에 삽입
                    db.projectDao().insertProject(project);

                    // UI 스레드에서 Toast 및 화면 전환
                    requireActivity().runOnUiThread(() -> {
                        Toast.makeText(requireContext(), "프로젝트가 생성되었습니다.", Toast.LENGTH_SHORT).show();
                        requireActivity().getSupportFragmentManager().popBackStack();
                    });

                    List<ProjectCreate> projectList = db.projectDao().getAllProjects();
                    for (ProjectCreate item : projectList) {
                        Log.e("DB_TEST", "저장된 데이터 → " +
                                "name: " + project.name +
                                ", location: " + project.location +
                                ", sheetNumber: " + project.sheetNumber +
                                ", memo: " + project.memo);
                    }
                });
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
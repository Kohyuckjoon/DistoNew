package com.terra.terradisto.ui.project;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.terra.terradisto.distosdkapp.SharedViewModel;
import com.terra.terradisto.distosdkapp.data.AppDatabase;
import com.terra.terradisto.databinding.FragmentProjectCreateBinding;
import com.terra.terradisto.distosdkapp.data.ProjectCreate;
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
    private SharedViewModel sharedViewModel;
    // ExecutorService를 멤버 변수로 유지하고 onDestroy에서 종료합니다.
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

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

        int currentProjectId = sharedViewModel.getSelectedProjectId().getValue() != null ?
                sharedViewModel.getSelectedProjectId().getValue() : -1;
        Log.e("khj", "test ----> " + currentProjectId);

        binding.mcCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // UI에서 입력 값을 가져옵니다. (Main Thread)
                String name = binding.etProjectName.getText().toString().trim(); // 프로젝트 이름
                String location = binding.etLocationName.getText().toString().trim();
                String sheetNumber = binding.etSheetNumber.getText().toString().trim();
                String projectMemo = binding.etProjectCreateMemo.getText().toString().trim();

                Log.e("DistoCreate", "create value name : " + name + "\nlocation : " + location
                        + "\nsheetNumber : " + sheetNumber + "\nprojectMemo : " + projectMemo);

                // 필수 항목 입력 사항 확인 (DB 접근 전에 체크)
                if (name.isEmpty() || location.isEmpty() || sheetNumber.isEmpty()) {
                    Toast.makeText(requireContext(), "필수 항목을 모두 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 💡 DB 접근이 필요한 모든 로직을 백그라운드 스레드로 이동합니다.
                executorService.execute(() -> {

                    AppDatabase db = AppDatabase.getDatabase(requireContext());
//                    ProjectDao dao = db.projectDao();

                    // 1. 중복 값 체크 (백그라운드에서 실행)
                    int count = db.projectDao().countExistingProjectName(name);
                    Log.e("khj", "count >>> " + count);

                    if (count > 0) {
                        // 중복 시 UI 스레드에서 토스트 메시지 표시 후 종료
                        requireActivity().runOnUiThread(() -> {
                            showToast("이미 존재하는 프로젝트 입니다.");
                        });
                        return; // DB 스레드 작업 중단
                    }

                    // 2. 프로젝트 저장 로직 (백그라운드에서 실행)
                    ProjectCreate project = new ProjectCreate(
                            name,
                            location,
                            sheetNumber,
                            projectMemo.isEmpty() ? null : projectMemo
                    );

                    // DB에 삽입
                    // ProjectDao 접근 역시 백그라운드에서 안전하게 처리됩니다.
                    db.projectDao().insertProject(project);

                    // 3. UI 스레드에서 Toast 및 화면 전환
                    requireActivity().runOnUiThread(() -> {
                        Toast.makeText(requireContext(), "프로젝트가 생성되었습니다. ✅", Toast.LENGTH_SHORT).show();
                        // 이전 화면 (프로젝트 목록 등)으로 돌아갑니다.
                        requireActivity().getSupportFragmentManager().popBackStack();
                    });

                    // 4. 테스트 로직 (백그라운드에서 실행)
                    List<ProjectCreate> projectList = db.projectDao().getAllProjects();
                    for (ProjectCreate item : projectList) {
                        Log.e("DB_TEST", "저장된 데이터 → " +
                                "name: " + item.name +
                                ", location: " + item.location +
                                ", sheetNumber: " + item.sheetNumber +
                                ", memo: " + item.memo);
                    }
                });
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ViewModel 초기화는 그대로 onCreate에 유지합니다.
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
    }

    private void showToast(String msg) {
        if (!isAdded()) return;
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 메모리 누수를 방지하기 위해 ExecutorService를 종료합니다.
        executorService.shutdown();
    }
}
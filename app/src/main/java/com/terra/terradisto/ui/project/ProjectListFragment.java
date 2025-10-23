package com.terra.terradisto.ui.project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.terra.terradisto.R;
import com.terra.terradisto.databinding.FragmentProjectListBinding;
import com.terra.terradisto.distosdkapp.data.AppDatabase;
import com.terra.terradisto.distosdkapp.data.ProjectCreate;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProjectListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProjectListFragment extends Fragment implements ProjectListAdapter.OnProjectSelectListener{

    private FragmentProjectListBinding binding;
    private ProjectListAdapter adapter;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public ProjectListFragment() {
        // Required empty public constructor
    }
    public static ProjectListFragment newInstance(String param1, String param2) {
        ProjectListFragment fragment = new ProjectListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProjectListBinding.inflate(inflater, container, false);

        adapter = new ProjectListAdapter(null, this);
        binding.rcProjectList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rcProjectList.setAdapter(adapter);

        loadProjectsFromDB();

        return binding.getRoot();
    }

    private void loadProjectsFromDB() {
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            AppDatabase db = AppDatabase.getDatabase(requireContext());
            List<ProjectCreate> projects = db.projectDao().getAllProjects();

            requireActivity().runOnUiThread(() -> {
                if (binding == null) return; // 안전 가드 추가
                if (projects == null || projects.isEmpty()) {
                    binding.mcNoData.setVisibility(View.VISIBLE);
                    binding.rcProjectList.setVisibility(View.GONE);
                } else {
                    binding.mcNoData.setVisibility(View.GONE);
                    binding.rcProjectList.setVisibility(View.VISIBLE);
                    adapter.setProjects(projects);
                }
            });
        });
    }

    @Override
    public void onProjectSelected(ProjectCreate project) {
        int selectedProjectId = project.id;        // 프로젝트 id
        String selectedProjectName = project.name; // 프로젝트 Name

        Bundle bundle = new Bundle();
        bundle.putInt("PROJECT_ID", selectedProjectId);
        bundle.putString("PROJECT_NAME", selectedProjectName);

        try {
            NavHostFragment.findNavController(this).navigate(R.id.action_projectList_to_mainFragment, bundle);
        } catch (Exception e) {
            Log.e("Disto", "Navigation 오류: Project ID 전달 실패", e);
        }
    }

    @Override
    public void onProjectDeleted(ProjectCreate project) {
        if (!isAdded()) return;

        executorService.execute(() -> {
            try {
                AppDatabase db = AppDatabase.getDatabase(requireContext());
                db.projectDao().delete(project);

                requireActivity().runOnUiThread(() -> {
                    showToast(project.name + " 프로젝트가 선택되었습니다.");
                    loadProjectsFromDB();
                });
            } catch (Exception e) {
                requireActivity().runOnUiThread(() -> {
                    showToast("프로젝트 삭제에 실패했습니다.");
                });
            }
        });
    }

    private void showToast(String msg) {
        if (!isAdded()) return;
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
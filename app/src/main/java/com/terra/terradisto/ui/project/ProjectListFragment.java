package com.terra.terradisto.ui.project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class ProjectListFragment extends Fragment {

    private FragmentProjectListBinding binding;
    private ProjectListAdapter adapter;


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

        adapter = new ProjectListAdapter(null);
        binding.rcProjectList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rcProjectList.setAdapter(adapter);

        loadProjectsFromDB();

        return binding.getRoot();
    }

    private void loadProjectsFromDB() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            AppDatabase db = AppDatabase.getDatabase(requireContext());
            List<ProjectCreate> projects = db.projectDao().getAllProjects();

            requireActivity().runOnUiThread(() -> {
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
}
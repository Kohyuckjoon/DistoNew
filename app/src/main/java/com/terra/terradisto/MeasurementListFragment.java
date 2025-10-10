package com.terra.terradisto;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.terra.terradisto.databinding.FragmentMeasurementListBinding;
import com.terra.terradisto.distosdkapp.data.AppDatabase;
import com.terra.terradisto.distosdkapp.data.SurveyDiameterDao;
import com.terra.terradisto.ui.survey_diameter.adapter.ResultListAdapter;
import com.terra.terradisto.ui.survey_diameter.model.SurveyResult;

import java.util.List;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeasurementListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeasurementListFragment extends Fragment {

    private FragmentMeasurementListBinding binding;
    private ResultListAdapter adapter;
    private SurveyDiameterDao surveyDiameterDao;

    public MeasurementListFragment() {

    }

    public static MeasurementListFragment newInstance(String param1, String param2) {
        MeasurementListFragment fragment = new MeasurementListFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMeasurementListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        surveyDiameterDao = AppDatabase.getDatabase(requireContext()).surveyDiameterDao();

        adapter = new ResultListAdapter();
        binding.recyclerViewResults.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewResults.setAdapter(adapter);

        loadResultsFromDb();
    }

    private void loadResultsFromDb() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<SurveyResult> resultList = surveyDiameterDao.getAllResults();

            if (isAdded()) {
                requireActivity().runOnUiThread(() -> {
                    adapter.setResults(resultList);

                    binding.recyclerViewResults.scrollToPosition(0);
                });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
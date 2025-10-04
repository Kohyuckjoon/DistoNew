package com.terra.terradisto.ui.popup;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.terra.terradisto.R;
import com.terra.terradisto.databinding.FragmentSaveCompliteBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SaveCompliteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SaveCompliteFragment extends Fragment {

    private FragmentSaveCompliteBinding binding;

    public SaveCompliteFragment() {
        // Required empty public constructor
    }

    public static SaveCompliteFragment newInstance(String param1, String param2) {
        SaveCompliteFragment fragment = new SaveCompliteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSaveCompliteBinding.inflate(inflater, container, false);
        binding.mcSaveComplite.setOnClickListener(view -> saveMeasureData());

        return binding.getRoot();
    }


    private void saveMeasureData() {
        NavController navController = Navigation.findNavController(requireView());

        navController.navigate(R.id.mainFragment);
    }
}
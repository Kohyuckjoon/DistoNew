package com.terra.terradisto.ui.popup;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.terra.terradisto.R;
import com.terra.terradisto.databinding.FragmentSaveDialogBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SaveDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SaveDialogFragment extends Fragment {

    private FragmentSaveDialogBinding binding;

    public SaveDialogFragment() {
        // Required empty public constructor
    }

    public static SaveDialogFragment newInstance(String param1, String param2) {
        SaveDialogFragment fragment = new SaveDialogFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSaveDialogBinding.inflate(inflater, container, false);
        binding.mcSaveButton.setOnClickListener(view -> saveMeasureData());

        // Cancel Button Click
        binding.mcCancel.setOnClickListener(view -> {  });

        return binding.getRoot();
    }

    private void saveMeasureData() {
        NavController navController = Navigation.findNavController(requireView());

        navController.navigate(R.id.fragmentSaveComplite);
    }
}
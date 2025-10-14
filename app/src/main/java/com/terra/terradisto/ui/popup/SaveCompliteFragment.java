package com.terra.terradisto.ui.popup;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.terra.terradisto.R;
import com.terra.terradisto.databinding.FragmentSaveCompliteBinding;
import com.terra.terradisto.distosdkapp.data.SurveyDiameterData;

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

        Bundle args = getArguments();
        if (args != null) {
            SurveyDiameterData data = (SurveyDiameterData) args.getSerializable("surveyData");

            if (data != null) {
                Log.e("SaveCompliteFragment", "저장된 데이터 : " + data.toString());

                binding.tvPipNumber.setText("배관 번호 : " + data.getManholType());
//                binding.tvDistanceNumber.setText("관 경 : " + data.getDistance());
//                binding.tvClMaterialNumber.setText("관 재질 : " + data.getPipeMaterial());
            }
        }

        binding.mcSaveComplite.setOnClickListener(view -> saveMeasureData());

        return binding.getRoot();
    }


    private void saveMeasureData() {
        NavController navController = Navigation.findNavController(requireView());
        navController.popBackStack(R.id.mainFragment, false);
        navController.navigate(R.id.mainFragment);
    }
}
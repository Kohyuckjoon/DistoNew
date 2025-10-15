package com.terra.terradisto.ui.popup;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.terra.terradisto.R;
import com.terra.terradisto.databinding.FragmentSaveDialogBinding;
import com.terra.terradisto.distosdkapp.data.AppDatabase;
import com.terra.terradisto.distosdkapp.data.SurveyDiameterDao;
import com.terra.terradisto.distosdkapp.data.SurveyDiameterData;
import com.terra.terradisto.distosdkapp.data.SurveyDiameterEntity;

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
//        View view = inflater.inflate(R.layout.fragment_save_dialog, container, false);
        binding = FragmentSaveDialogBinding.inflate(inflater, container, false);

        Bundle args = getArguments();
        if (args != null) {
            SurveyDiameterData data = (SurveyDiameterData) args.getSerializable("surveyData");

            if (data != null) {
                Log.e("SaveDialogFragment", "받은 데이터 : " + data.toString());

                binding.tvPipNumber.setText("배관 번호 : " + data.getManholType());
//                binding.tvDistanceNumber.setText("관 경 : " + data.getDistance());
//                binding.tvClMaterialNumber.setText("관 재질 : " + data.getPipeMaterial());
            }
        }

        binding.mcSaveButton.setOnClickListener(v -> saveMeasureData());
        binding.mcCancel.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack() );

        return binding.getRoot();
    }

    private void saveMeasureData() {
        SurveyDiameterData data = (SurveyDiameterData) getArguments().getSerializable("surveyData");

//        if (data != null) {
//            // 1. Room DB 저장
//            new Thread(() -> {
//                AppDatabase db = AppDatabase.getDatabase(requireContext());
//                SurveyDiameterEntity entity = new SurveyDiameterEntity(
//                        data.getManholType(),
//                        data.getDistance(),
//                        data.getPipeMaterial()
//                );
//                db.surveyDiameterDao().insert(entity);
//                Log.e("DB", "저장 완료 : " + entity.toString());
//            }).start();
//        }

        // 2. 다음 화면으로 이동 (Bundle 전달)
        Bundle bundle = new Bundle();

        bundle.putSerializable("surveyData", data);

        NavController navController = Navigation.findNavController(requireView());
        navController.popBackStack(R.id.fragmentSaveDialog, false);
        navController.navigate(R.id.fragmentSaveComplite, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // 메모리 누수 방지
    }
}
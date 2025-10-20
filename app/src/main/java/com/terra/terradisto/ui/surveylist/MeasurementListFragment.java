package com.terra.terradisto.ui.surveylist;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.terra.terradisto.R;
import com.terra.terradisto.databinding.FragmentMeasurementListBinding;
import com.terra.terradisto.distosdkapp.data.AppDatabase;
import com.terra.terradisto.distosdkapp.data.SurveyDiameterDao;
import com.terra.terradisto.distosdkapp.utilities.ExcelExportHelper;
import com.terra.terradisto.ui.survey_diameter.adapter.ResultListAdapter;
import com.terra.terradisto.ui.survey_diameter.model.SurveyResult;

import java.util.List;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeasurementListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeasurementListFragment extends Fragment implements ResultListAdapter.OnItemDeleteListener {

    private FragmentMeasurementListBinding binding;
    private ResultListAdapter adapter;
    private SurveyDiameterDao surveyDiameterDao;

    public MeasurementListFragment() { }

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

    private String getResString(int resId) {
        if (isAdded() && getResources() != null) {
            return getResources().getString(resId);
        }
        return "알 수 없는 메시지";
    }

    //내보내기 버튼
    void makeFile() {
        // 엑셀 내보내기 작업은 시간이 걸리므로 백그라운드 스레드에서 실행
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                String filePath = null;

                try {
                    // 1. Room DB -> Select * from survey_diameter
                    List<SurveyResult> surveyDataList = surveyDiameterDao.getAllResults();

                    Log.e("Disto", "surveyDataList >>>> " + surveyDataList);
                    // 2. 엑셀 파일 생성 헬퍼 호출
                    if (surveyDataList != null && !surveyDataList.isEmpty()) {
                        filePath = ExcelExportHelper.makeSurveyExcel(requireContext(), surveyDataList);
                        Log.e("MAKEFILE", "DB 조회 또는 엑셀 생성 실패 : " + filePath);
                    }
                } catch (Exception e) {
                    Log.e("MAKEFILE", "DB 조회 또는 엑셀 생성 실패", e);
                    filePath = null;
                }

                final String finalFilePath = filePath;

                // 3. UI 업데이트(메인 스레드에서 실행)
                if (isAdded()) {
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (finalFilePath != null) {
                                new AlertDialog.Builder(requireContext())
                                        .setTitle(getResString(R.string.msg_output_file))
                                        .setMessage(getResString(R.string.save_path) + "\n" + finalFilePath)
                                        .setPositiveButton(getResString(R.string.msg_ok), (dialog, which) -> dialog.dismiss())
                                        .show();
                            } else {
                                new AlertDialog.Builder(requireContext())
                                        .setTitle("내보내기 실패")
                                        .setMessage("엑셀 파일 생성에 실패했거나 데이터가 없습니다.")
                                        .setPositiveButton(getResString(R.string.msg_ok), (dialog, which) -> dialog.dismiss())
                                        .show();
                            }
                        }
                    });
                }
            }
        });
    }

    // 삭제 버튼
    @Override
    public void onDeleteClick(SurveyResult resultToDelete, int position) {
        // Room DB 삭제는 메인 스레드에서 실행할 수 없으므로 백그라운드 스레드에서 처리
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // DB에서 삭제
                    surveyDiameterDao.deleteById(resultToDelete.id); // SurveyResult.id가 int로 가정

                    // 삭제 후, UI 갱신을 위해 메인 스레드로 돌아옴
                    if (isAdded()) {
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // 1. 리스트에서 항목 제거 및 애니메이션 처리
                                List<SurveyResult> currentList = adapter.getResults(); // 💡 이제 getResults() 호출 가능
                                if (currentList != null && position != RecyclerView.NO_POSITION) {
                                    currentList.remove(position);
                                    adapter.notifyItemRemoved(position);
                                    adapter.notifyItemRangeChanged(position, currentList.size());
                                }

                                Toast.makeText(getContext(), resultToDelete.id + "번 측정 데이터가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                } catch (Exception e) {
                    Log.e("DB_DELETE", "데이터 삭제 오류: " + e.getMessage());
                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. DAO 초기화
        surveyDiameterDao = AppDatabase.getDatabase(requireContext()).surveyDiameterDao();

        // 2. Adapter 초기화 및 리스너 연결
        adapter = new ResultListAdapter();
        adapter.setOnItemDeleteListener(this); // 💡 리스너 연결

        // 3. RecyclerView 설정
        binding.recyclerViewResults.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewResults.setAdapter(adapter);

        // 4. "내보내기" 버튼 리스너(xlsx)
        binding.mcExportButton.setOnClickListener(v -> makeFile());

        // 5. 데이터 로드
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
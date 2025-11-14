package com.terra.terradisto.ui.surveylist;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
public class MeasurementListFragment extends Fragment implements
        ResultListAdapter.OnItemDeleteListener,

        ResultListAdapter.OnItemEditFirstListener,
        ResultListAdapter.OnItemEditSecondListener,
        ResultListAdapter.OnItemEditThirdListener,
        ResultListAdapter.OnItemEditFourthListener,
        ResultListAdapter.OnItemEditFifthListener,
        ResultListAdapter.OnItemEditSixthListener,

        ResultListAdapter.OnItemEditSceneryFirstListener,
        ResultListAdapter.OnItemEditScenerySecondListener,
        ResultListAdapter.OnItemEditSceneryThirdListener,
        ResultListAdapter.OnItemEditSceneryFourthListener,
        ResultListAdapter.OnItemEditSceneryFifthListener,
        ResultListAdapter.OnItemEditScenerySixthListener,

        ResultListAdapter.OnItemPipFirstListener,
        ResultListAdapter.OnItemPipSecondListener,
        ResultListAdapter.OnItemPipThirdListener,
        ResultListAdapter.OnItemPipFourthListener,
        ResultListAdapter.OnItemPipFifthListener,
        ResultListAdapter.OnItemPipSixthListener,

        ResultListAdapter.OnItemThicknessFirstListener,
        ResultListAdapter.OnItemThicknessSecondListener,
        ResultListAdapter.OnItemThicknessThirdListener,
        ResultListAdapter.OnItemThicknessFourthListener,
        ResultListAdapter.OnItemThicknessFifthListener,
        ResultListAdapter.OnItemThicknessSixthListener {

    private static final String FIELD_1 = "1";
    private static final String FIELD_2 = "2";
    private static final String FIELD_3 = "3";
    private static final String FIELD_4 = "4";
    private static final String FIELD_5 = "5";
    private static final String FIELD_6 = "6";

    private FragmentMeasurementListBinding binding;
    private ResultListAdapter adapter;
    private SurveyDiameterDao surveyDiameterDao;
    private int currentProjectId = -1;

    public MeasurementListFragment() { }

    public static MeasurementListFragment newInstance(String param1, String param2) {
        MeasurementListFragment fragment = new MeasurementListFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Argument에서 프로젝트 ID전달 받기
        if (getArguments() != null) {
            currentProjectId = getArguments().getInt("PROJECT_ID", -1);
        }
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

    // 삭제 버튼 리스너 처리
    @Override
    public void onDeleteClick(SurveyResult resultToDelete, int position) {
        // 1. AlertDialog 생성 및 표시
        new AlertDialog.Builder(requireContext())
                .setTitle(getResString(R.string.msg_delete))
                .setMessage(getResString(R.string.msg_delete_alert))
                .setPositiveButton(getResString(R.string.mag_yse), (dialog, which) -> {
                    performDelete(resultToDelete, position);
                })
                .setNegativeButton(getResString(R.string.mag_no), ((dialog, which) -> {
                    dialog.dismiss();
                })).show();
    }

    // 1번 높이 수정 버튼 리스너 처리
    @Override
    public void onEditClick(SurveyResult resultToEdit, int position) {
        showEditDialog(resultToEdit, position, FIELD_1);
    }

    // 2번 높이 수정 버튼 리스너 처리
    @Override
    public void onEditClickSecond(SurveyResult resultToEdit, int position) {
        showEditDialog(resultToEdit, position, FIELD_2);
    }

    // 3번 높이 수정 버튼 리스너 처리
    @Override
    public void onEditClickThird(SurveyResult resultToEdit, int position) {
        showEditDialog(resultToEdit, position, FIELD_3);
    }

    // 4번 높이 수정 버튼 리스너 처리
    @Override
    public void onEditClickFourth(SurveyResult resultToEdit, int position) {
        showEditDialog(resultToEdit, position, FIELD_4);
    }

    // 5번 높이 수정 버튼 리스너 처리
    @Override
    public void onEditClickFifth(SurveyResult resultToEdit, int position) {
        showEditDialog(resultToEdit, position, FIELD_5);
    }

    // 6번 높이 수정 버튼 리스너 처리
    @Override
    public void onEditClickSixth(SurveyResult resultToEdit, int position) {
        showEditDialog(resultToEdit, position, FIELD_6);
    }


    // 1번 재질 수정 버튼 리스너 처리
    @Override
    public void onEditClickPipFirst(SurveyResult resultToEdit, int position) {
        showEditPipDialog(resultToEdit, position, FIELD_1);
    }

    // 2번 재질 수정 버튼 리스너 처리
    @Override
    public void onEditClickPipSecond(SurveyResult resultToEdit, int position) {
        showEditPipDialog(resultToEdit, position, FIELD_2);
    }

    // 3번 재질 수정 버튼 리스너 처리
    @Override
    public void onEditClickPipThird(SurveyResult resultToEdit, int position) {
        showEditPipDialog(resultToEdit, position, FIELD_3);
    }

    // 4번 재질 수정 버튼 리스너 처리
    @Override
    public void onEditClickPipFourth(SurveyResult resultToEdit, int position) {
        showEditPipDialog(resultToEdit, position, FIELD_4);
    }

    // 5번 재질 수정 버튼 리스너 처리
    @Override
    public void onEditClickPipFifth(SurveyResult resultToEdit, int position) {
        showEditPipDialog(resultToEdit, position, FIELD_5);
    }

    // 6번 재질 수정 버튼 리스너 처리
    @Override
    public void onEditClickPipSixth(SurveyResult resultToEdit, int position) {
        showEditPipDialog(resultToEdit, position, FIELD_6);
    }

    // 1번 두께 수정 버튼 리스너 처리
    @Override
    public void onEditClickThicknessFirst(SurveyResult resultToEdit, int position) {
        showEditThicknessDialog(resultToEdit, position, FIELD_1);
    }

    // 2번 두께 수정 버튼 리스너 처리
    @Override
    public void onEditClickThicknessSecond(SurveyResult resultToEdit, int position) {
        showEditThicknessDialog(resultToEdit, position, FIELD_2);
    }

    // 3번 두께 수정 버튼 리스너 처리
    @Override
    public void onEditClickThicknessThird(SurveyResult resultToEdit, int position) {
        showEditThicknessDialog(resultToEdit, position, FIELD_3);
    }

    // 4번 두께 수정 버튼 리스너 처리
    @Override
    public void onEditClickThicknessFourth(SurveyResult resultToEdit, int position) {
        showEditThicknessDialog(resultToEdit, position, FIELD_4);
    }

    // 5번 두께 수정 버튼 리스너 처리
    @Override
    public void onEditClickThicknessFifth(SurveyResult resultToEdit, int position) {
        showEditThicknessDialog(resultToEdit, position, FIELD_5);
    }

    // 6번 두께 수정 버튼 리스너 처리
    @Override
    public void onEditClickThicknessSixth(SurveyResult resultToEdit, int position) {
        showEditThicknessDialog(resultToEdit, position, FIELD_6);
    }

    // 수정 입력 다이얼로그 표시 메서드
    private void showEditDialog(SurveyResult resultToEdit, int position, String fieldIdentifier) {
        final EditText input = new EditText(requireContext());
        String currentTitle = "";
        String currentValue = "";

        // 💡 필드 식별자에 따라 제목 및 현재 값 설정
        switch (fieldIdentifier) {
            case FIELD_1:
                currentTitle = "수기 입력값 수정 (1번 높이)";
                currentValue = resultToEdit.getTvInputFirst();
                break;
            case FIELD_2:
                currentTitle = "수기 입력값 수정 (2번 높이)";
                currentValue = resultToEdit.getTvInputSecond(); // SurveyResult에 getEtInputSecond()가 필요
                break;
            case FIELD_3:
                currentTitle = "수기 입력값 수정 (3번 높이)";
                currentValue = resultToEdit.getTvInputThird(); // SurveyResult에 getEtInputThird()가 필요
                break;
            case FIELD_4:
                currentTitle = "수기 입력값 수정 (4번 높이)";
                currentValue = resultToEdit.getTvInputFourth(); // SurveyResult에 getEtInputFourth()가 필요
                break;
            case FIELD_5:
                currentTitle = "수기 입력값 수정 (5번 높이)";
                currentValue = resultToEdit.getTvInputFifth(); // SurveyResult에 getEtInputFourth()가 필요
                break;
            case FIELD_6:
                currentTitle = "수기 입력값 수정 (6번 높이)";
                currentValue = resultToEdit.getTvInputSixth(); // SurveyResult에 getEtInputFourth()가 필요
                break;
        }

        // 현재 값을 기본값으로 표시합니다.
//        input.setText(resultToEdit.getEtInputFirst());
        input.setText(currentValue);

        // 입력 타입을 숫자/소수점으로 설정합니다.
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        new AlertDialog.Builder(requireContext())
                .setTitle(currentTitle)
                .setMessage("새로운 값을 입력하고 저장하세요:")
                .setView(input)
                .setPositiveButton(getResString(R.string.msg_ok), (dialog, which) -> {
                    String newValue = input.getText().toString().trim();
                    if (!newValue.isEmpty()) {
                        // DB 업데이트 로직 실행
                        performEdit(resultToEdit.id, newValue, position, fieldIdentifier);
                    } else {
                        Toast.makeText(getContext(), "수정 값이 비어있습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(getResString(R.string.mag_no), (dialog, which) -> dialog.cancel())
                .show();
    }

    /**
     * @param id 수정할 항목의 ID
     * @param newValue 새로운 입력 값 (EtInputFirst)
     * @param position RecyclerView 위치
     * Room DB 수정은 메인 스레드에서 실행할 수 없으므로 백그라운드 스레드에서 처리
     */
    private void performEdit(int id, String newValue, int position, String fieldIdentifier) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                // 💡 필드 식별자에 따라 호출할 DAO 메서드를 분기
                switch (fieldIdentifier) {
                    case FIELD_1:
                        surveyDiameterDao.updateInputFirst(id, newValue);
                        break;
                    case FIELD_2:
                        surveyDiameterDao.updateInputSecond(id, newValue);
                        break;
                    case FIELD_3:
                        surveyDiameterDao.updateInputThird(id, newValue);
                        break;
                    case FIELD_4:
                        surveyDiameterDao.updateInputFourth(id, newValue);
                        break;
                    case FIELD_5:
                        surveyDiameterDao.updateInputFifth(id, newValue);
                        break;
                    case FIELD_6:
                        surveyDiameterDao.updateInputSixth(id, newValue);
                        break;
                }

                // DB에서 해당 ID의 '1번 수기 입력 데이터' 필드만 업데이트
                // (DAO에 updateInputFirst(int id, String newValue) 메서드가 정의되어 있어야 함)
//                surveyDiameterDao.updateInputFirst(id, newValue);

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        // 1. Adapter 데이터 모델 업데이트 (Adapter 목록에 있는 객체의 값을 직접 수정)
                        SurveyResult updatedItem = adapter.getResults().get(position);
//                        updatedItem.setEtInputFirst(newValue); // SurveyResult 모델에 setEtInputFirst()가 필요함

                        switch (fieldIdentifier) {
                            case FIELD_1:
                                updatedItem.setTvInputFirst(newValue);
                                break;
                            case FIELD_2:
                                updatedItem.setTvInputSecond(newValue); // SurveyResult에 setEtInputSecond()가 필요
                                break;
                            case FIELD_3:
                                updatedItem.setTvInputThird(newValue); // SurveyResult에 setEtInputThird()가 필요
                                break;
                            case FIELD_4:
                                updatedItem.setTvInputFourth(newValue); // SurveyResult에 setEtInputFourth()가 필요
                                break;
                            case FIELD_5:
                                updatedItem.setTvInputFifth(newValue); // SurveyResult에 setEtInputFourth()가 필요
                                break;
                            case FIELD_6:
                                updatedItem.setTvInputSixth(newValue); // SurveyResult에 setEtInputFourth()가 필요
                                break;
                        }

                        // 2. UI 갱신 (해당 항목만 갱신하여 깜빡임 최소화)
                        adapter.notifyItemChanged(position);

                        Toast.makeText(getContext(), id + "번 데이터가 성공적으로 수정되었습니다.", Toast.LENGTH_SHORT).show();
                    });
                }
            } catch (Exception e) {
                Log.e("DB_UPDATE", "데이터 수정 오류: " + e.getMessage());
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "데이터 수정 실패: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });
                }
            }
        });
    }

    /**
     * 관경 데이터
     */
    private void showEditSceneryDialog(SurveyResult resultToEdit, int position, String fieldIdentifier) {
        final EditText input = new EditText(requireContext());
        String currentTitle = "";
        String currentValue = "";

        switch (fieldIdentifier) {
            case FIELD_1:
                currentTitle = "관경 수정 (1번 관로)";
                currentValue = resultToEdit.getTvSceneryFirst();
                break;
            case FIELD_2:
                currentTitle = "관경 수정 (2번 관로)";
                currentValue = resultToEdit.getTvScenerySecond();
                break;
            case FIELD_3:
                currentTitle = "관경 수정 (3번 관로)";
                currentValue = resultToEdit.getTvSceneryThird();
                break;
            case FIELD_4:
                currentTitle = "관경 수정 (4번 관로)";
                currentValue = resultToEdit.getTvSceneryFourth();
                break;
            case FIELD_5:
                currentTitle = "관경 수정 (5번 관로)";
                currentValue = resultToEdit.getTvSceneryFifth();
                break;
            case FIELD_6:
                currentTitle = "관경 수정 (6번 관로)";
                currentValue = resultToEdit.getTvScenerySixth();
                break;
        }

        input.setText(currentValue);
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        new AlertDialog.Builder(requireContext())
                .setTitle(currentTitle)
                .setMessage("새로운 관경 값을 입력하고 저장하세요 (단위: mm):")
                .setView(input)
                .setPositiveButton(getResString(R.string.msg_ok), (dialog, which) -> {
                    String newValue = input.getText().toString().trim();
                    if (!newValue.isEmpty()) {
                        performSceneryEdit(resultToEdit.id, newValue, position, fieldIdentifier);
                    } else {
                        Toast.makeText(getContext(), "수정 값이 비어있습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(getResString(R.string.mag_no), (dialog, which) -> dialog.cancel())
                .show();
    }

    /**
     * 관경 데이터를 Room DB에서 수정하고 UI를 갱신
     */
    private void performSceneryEdit(int id, String newValue, int position, String fieldIdentifier) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                // 💡 DB 업데이트: 관경 필드
                switch (fieldIdentifier) {
                    case FIELD_1:
                        surveyDiameterDao.updateSceneryFirst(id, newValue);
                        break;
                    case FIELD_2:
                        surveyDiameterDao.updateScenerySecond(id, newValue);
                        break;
                    case FIELD_3:
                        surveyDiameterDao.updateSceneryThird(id, newValue);
                        break;
                    case FIELD_4:
                        surveyDiameterDao.updateSceneryFourth(id, newValue);
                        break;
                    case FIELD_5:
                        surveyDiameterDao.updateSceneryFifth(id, newValue);
                        break;
                    case FIELD_6:
                        surveyDiameterDao.updateScenerySixth(id, newValue);
                        break;
                }

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        // 1. Adapter 데이터 모델 업데이트
                        SurveyResult updatedItem = adapter.getResults().get(position);

                        switch (fieldIdentifier) {
                            case FIELD_1:
                                updatedItem.setTvSceneryFirst(newValue);
                                break;
                            case FIELD_2:
                                updatedItem.setTvScenerySecond(newValue);
                                break;
                            case FIELD_3:
                                updatedItem.setTvSceneryThird(newValue);
                                break;
                            case FIELD_4:
                                updatedItem.setTvSceneryFourth(newValue);
                                break;
                            case FIELD_5:
                                updatedItem.setTvSceneryFifth(newValue);
                                break;
                            case FIELD_6:
                                updatedItem.setTvScenerySixth(newValue);
                                break;
                        }

                        // 2. UI 갱신
                        adapter.notifyItemChanged(position);
//                        Toast.makeText(getContext(), id + "번 데이터의 관경이 성공적으로 수정되었습니다.", Toast.LENGTH_SHORT).show();
                    });
                }
            } catch (Exception e) {
                Log.e("DB_SCENERY_UPDATE", "관경 데이터 수정 오류: " + e.getMessage());
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "관경 데이터 수정 실패: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });
                }
            }
        });
    }



    /// //////////////////////
    /**
     * 관경 데이터
     */
    private void showEditPipDialog(SurveyResult resultToEdit, int position, String fieldIdentifier) {
        final EditText input = new EditText(requireContext());
        String currentTitle = "";
        String currentValue = "";

        switch (fieldIdentifier) {
            case FIELD_1:
                currentTitle = "재질 수정 (1번 재질)";
                currentValue = resultToEdit.getEtPipMaterialFirst();
                break;
            case FIELD_2:
                currentTitle = "재질 수정 (2번 재질)";
                currentValue = resultToEdit.getEtPipMaterialSecond();
                break;
            case FIELD_3:
                currentTitle = "재질 수정 (3번 재질)";
                currentValue = resultToEdit.getEtPipMaterialThird();
                break;
            case FIELD_4:
                currentTitle = "재질 수정 (4번 재질)";
                currentValue = resultToEdit.getEtPipMaterialFourth();
                break;
            case FIELD_5:
                currentTitle = "재질 수정 (5번 재질)";
                currentValue = resultToEdit.getEtPipMaterialFifth();
                break;
            case FIELD_6:
                currentTitle = "재질 수정 (6번 재질)";
                currentValue = resultToEdit.getEtPipMaterialSixth();
                break;
        }

        input.setText(currentValue);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        new AlertDialog.Builder(requireContext())
                .setTitle(currentTitle)
                .setMessage("새로운 재질을 입력하고 저장하세요.")
                .setView(input)
                .setPositiveButton(getResString(R.string.msg_ok), (dialog, which) -> {
                    String newValue = input.getText().toString().trim();
                    if (!newValue.isEmpty()) {
                        performPipEdit(resultToEdit.id, newValue, position, fieldIdentifier);
                    } else {
                        Toast.makeText(getContext(), "수정 값이 비어있습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(getResString(R.string.mag_no), (dialog, which) -> dialog.cancel())
                .show();
    }
    /// ///////////////////////
    /**
     * 재질 데이터를 Room DB에서 수정하고 UI를 갱신
     */
    private void performPipEdit(int id, String newValue, int position, String fieldIdentifier) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                // 💡 DB 업데이트: 관경 필드
                switch (fieldIdentifier) {
                    case FIELD_1:
                        surveyDiameterDao.updatePipFirst(id, newValue);
                        break;
                    case FIELD_2:
                        surveyDiameterDao.updatePipSecond(id, newValue);
                        break;
                    case FIELD_3:
                        surveyDiameterDao.updatePipThird(id, newValue);
                        break;
                    case FIELD_4:
                        surveyDiameterDao.updatePipFourth(id, newValue);
                        break;
                    case FIELD_5:
                        surveyDiameterDao.updatePipFifth(id, newValue);
                        break;
                    case FIELD_6:
                        surveyDiameterDao.updatePipSixth(id, newValue);
                        break;
                }

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        // 1. Adapter 데이터 모델 업데이트
                        SurveyResult updatedItem = adapter.getResults().get(position);

                        switch (fieldIdentifier) {
                            case FIELD_1:
                                updatedItem.setEtPipMaterialFirst(newValue);
                                break;
                            case FIELD_2:
                                updatedItem.setEtPipMaterialSecond(newValue);
                                break;
                            case FIELD_3:
                                updatedItem.setEtPipMaterialThird(newValue);
                                break;
                            case FIELD_4:
                                updatedItem.setEtPipMaterialFourth(newValue);
                                break;
                            case FIELD_5:
                                updatedItem.setEtPipMaterialFifth(newValue);
                                break;
                            case FIELD_6:
                                updatedItem.setEtPipMaterialSixth(newValue);
                                break;
                        }

                        // 2. UI 갱신
                        adapter.notifyItemChanged(position);
//                        Toast.makeText(getContext(), id + "번 데이터의 관경이 성공적으로 수정되었습니다.", Toast.LENGTH_SHORT).show();
                    });
                }
            } catch (Exception e) {
                Log.e("DB_SCENERY_UPDATE", "관경 데이터 수정 오류: " + e.getMessage());
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "관경 데이터 수정 실패: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });
                }
            }
        });
    }
    /// ///////////////////////

    /***************************/
    private void showEditThicknessDialog(SurveyResult resultToEdit, int position, String fieldIdentifier) {
        final EditText input = new EditText(requireContext());
        String currentTitle = "";
        String currentValue = "";

        switch (fieldIdentifier) {
            case FIELD_1:
                currentTitle = "두께 수정 (1번)";
                currentValue = resultToEdit.getThicknessFirst();
                break;
            case FIELD_2:
                currentTitle = "두께 수정 (2번)";
                currentValue = resultToEdit.getThicknessSecond();
                break;
            case FIELD_3:
                currentTitle = "두께 수정 (3번)";
                currentValue = resultToEdit.getThicknessThird();
                break;
            case FIELD_4:
                currentTitle = "두께 수정 (4번)";
                currentValue = resultToEdit.getThicknessFourth();
                break;
            case FIELD_5:
                currentTitle = "두께 수정 (5번)";
                currentValue = resultToEdit.getThicknessFifth();
                break;
            case FIELD_6:
                currentTitle = "두께 수정 (6번)";
                currentValue = resultToEdit.getThicknessSixth();
                break;
        }

        input.setText(currentValue);
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        new AlertDialog.Builder(requireContext())
                .setTitle(currentTitle)
                .setMessage("새로운 재질을 입력하고 저장하세요.")
                .setView(input)
                .setPositiveButton(getResString(R.string.msg_ok), (dialog, which) -> {
                    String newValue = input.getText().toString().trim();
                    if (!newValue.isEmpty()) {
                        performThicknessEdit(resultToEdit.id, newValue, position, fieldIdentifier);
                    } else {
                        Toast.makeText(getContext(), "수정 값이 비어있습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(getResString(R.string.mag_no), (dialog, which) -> dialog.cancel())
                .show();
    }

    /**
     * 재질 데이터를 Room DB에서 수정하고 UI를 갱신
     */
    private void performThicknessEdit(int id, String newValue, int position, String fieldIdentifier) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                // 💡 DB 업데이트: 관경 필드
                switch (fieldIdentifier) {
                    case FIELD_1:
                        surveyDiameterDao.updateThicknessFirst(id, newValue);
                        break;
                    case FIELD_2:
                        surveyDiameterDao.updateThicknessSecond(id, newValue);
                        break;
                    case FIELD_3:
                        surveyDiameterDao.updateThicknessThird(id, newValue);
                        break;
                    case FIELD_4:
                        surveyDiameterDao.updateThicknessFourth(id, newValue);
                        break;
                    case FIELD_5:
                        surveyDiameterDao.updateThicknessFifth(id, newValue);
                        break;
                    case FIELD_6:
                        surveyDiameterDao.updateThicknessSixth(id, newValue);
                        break;
                }

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        // 1. Adapter 데이터 모델 업데이트
                        SurveyResult updatedItem = adapter.getResults().get(position);

                        switch (fieldIdentifier) {
                            case FIELD_1:
                                updatedItem.setThicknessFirst(newValue);
                                break;
                            case FIELD_2:
                                updatedItem.setThicknessSecond(newValue);
                                break;
                            case FIELD_3:
                                updatedItem.setThicknessThird(newValue);
                                break;
                            case FIELD_4:
                                updatedItem.setThicknessFourth(newValue);
                                break;
                            case FIELD_5:
                                updatedItem.setThicknessFifth(newValue);
                                break;
                            case FIELD_6:
                                updatedItem.setThicknessSixth(newValue);
                                break;
                        }

                        // 2. UI 갱신
                        adapter.notifyItemChanged(position);
//                        Toast.makeText(getContext(), id + "번 데이터의 두께값이 성공적으로 수정되었습니다.", Toast.LENGTH_SHORT).show();
                    });
                }
            } catch (Exception e) {
                Log.e("DB_SCENERY_UPDATE", "관경 데이터 수정 오류: " + e.getMessage());
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "두께 데이터 수정 실패: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });
                }
            }
        });
    }



    /**
     * @param resultToDelete
     * @param position
     * Room DB 삭제는 메인 스레드에서 실행할 수 없으므로 백그라운드 스레드에서 처리
     */
    private void performDelete (SurveyResult resultToDelete, int position) {

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
                                List<SurveyResult> currentList = adapter.getResults(); // getResults() 호출 가능
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
        adapter.setOnItemDeleteListener(this);

        // 높이
        adapter.setOnItemEditListener(this);        // 1번 수정 리스너 연결
        adapter.setOnItemEditSecondListener(this);  // 2번 수정 리스너 연결
        adapter.setOnItemEditThirdListener(this);   // 3번 수정 리스너 연결
        adapter.setOnItemEditFourthListener(this);  // 4번 수정 리스너 연결
        adapter.setOnItemEditFifthListener(this);  // 5번 수정 리스너 연결
        adapter.setOnItemEditSixthListener(this);  // 6번 수정 리스너 연결

        // 관경
        adapter.setOnItemEditSceneryFirstListener(this);  // 1번 수정 리스너 연결
        adapter.setOnItemEditScenerySecondListener(this);  // 2번 수정 리스너 연결
        adapter.setOnItemEditSceneryThirdListener(this);  // 3번 수정 리스너 연결
        adapter.setOnItemEditSceneryFourthListener(this);  // 4번 수정 리스너 연결
        adapter.setOnItemEditSceneryFifthListener(this);  // 5번 수정 리스너 연결
        adapter.setOnItemEditScenerySixthListener(this);  // 6번 수정 리스너 연결

        // 재질
        adapter.setOnItemEditPipFirstListener(this);
        adapter.setOnItemEditPipSecondListener(this);
        adapter.setOnItemEditPipThirdListener(this);
        adapter.setOnItemEditPipFourthListener(this);
        adapter.setOnItemEditPipFifthListener(this);
        adapter.setOnItemEditPipSixthListener(this);

        adapter.setOnItemEditThicknessFirstListener(this);
        adapter.setOnItemEditThicknessSecondListener(this);
        adapter.setOnItemEditThicknessThirdListener(this);
        adapter.setOnItemEditThicknessFourthListener(this);
        adapter.setOnItemEditThicknessFifthListener(this);
        adapter.setOnItemEditThicknessSixthListener(this);

        // 3. RecyclerView 설정
        binding.recyclerViewResults.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewResults.setAdapter(adapter);

        // 4. "내보내기" 버튼 리스너(xlsx)
        binding.mcExportButton.setOnClickListener(v -> makeFile());

        // 5. 데이터 로드
        loadResultsFromDb();
    }

    private void loadResultsFromDb() {

        final int projectIdToFilter = currentProjectId;

        Executors.newSingleThreadExecutor().execute(() -> {
//            List<SurveyResult> resultList = surveyDiameterDao.getAllResults();

            // 1. DB 조회 결과를 저장할 변수를 final로 선언합니다.
            final List<SurveyResult> resultList;

            // 2. if/else 로직을 통해 변수에 단 한 번만 값을 할당합니다.
            if (currentProjectId != -1) {
                // 특정 프로젝트 ID로 조회하여 resultList에 할당 (단 한 번 할당)
                resultList = surveyDiameterDao.getResultsByProjectId(projectIdToFilter);
                binding.mcExportButton.setVisibility(View.VISIBLE);
                binding.tvProjectEmpty.setVisibility(View.GONE);
            } else {
                // ID가 없으면 모든 프로젝트 조회 (단 한 번 할당)
                binding.mcExportButton.setVisibility(View.GONE);
                binding.tvProjectEmpty.setVisibility(View.VISIBLE);
                resultList = java.util.Collections.emptyList();
//                resultList = surveyDiameterDao.getAllResults();
            }

            if (isAdded()) {
                requireActivity().runOnUiThread(() -> {
                    adapter.setResults(resultList);
                    binding.recyclerViewResults.scrollToPosition(0);
                });
            }
        });
    }

    // 관경 수정 다이얼로그
    @Override
    public void editSceneryFirstListener(SurveyResult resultToEdit, int position) {
        showEditSceneryDialog(resultToEdit, position, FIELD_1);
    }

    // 2번 관경 수정 버튼 리스너 처리
    @Override
    public void editScenerySecondListener(SurveyResult resultToEdit, int position) {
        showEditSceneryDialog(resultToEdit, position, FIELD_2);
    }

    // 3번 관경 수정 버튼 리스너 처리
    @Override
    public void editSceneryThirdListener(SurveyResult resultToEdit, int position) {
        showEditSceneryDialog(resultToEdit, position, FIELD_3);
    }

    // 4번 관경 수정 버튼 리스너 처리
    @Override
    public void editSceneryFourthListener(SurveyResult resultToEdit, int position) {
        showEditSceneryDialog(resultToEdit, position, FIELD_4);
    }

    // 5번 관경 수정 버튼 리스너 처리
    @Override
    public void editSceneryFifthListener(SurveyResult resultToEdit, int position) {
        showEditSceneryDialog(resultToEdit, position, FIELD_5);
    }

    // 6번 관경 수정 버튼 리스너 처리
    @Override
    public void editScenerySixthListener(SurveyResult resultToEdit, int position) {
        showEditSceneryDialog(resultToEdit, position, FIELD_6);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

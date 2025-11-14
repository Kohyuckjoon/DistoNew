package com.terra.terradisto.ui.survey_diameter.adapter;

import android.annotation.SuppressLint;
import android.security.identity.ResultData;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.terra.terradisto.R;
import com.terra.terradisto.distosdkapp.data.SurveyDiameterData;
import com.terra.terradisto.distosdkapp.data.SurveyDiameterEntity;
import com.terra.terradisto.ui.survey_diameter.model.SurveyResult;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class ResultListAdapter extends RecyclerView.Adapter<ResultListAdapter.ResultViewHolder> {

    // 1번 삭제 버튼 클릭 이벤트를 위한 인터페이스 정의
    public interface OnItemDeleteListener {
        void onDeleteClick(SurveyResult resultToDelete, int position);
    }

    private OnItemDeleteListener deleteListener;

    // 1번 관로 수정 리스너
    public interface OnItemEditSceneryFirstListener {
        void editSceneryFirstListener(SurveyResult resultToEdit, int position);
    }

    // 2번 관로 수정 리스너
    public interface OnItemEditScenerySecondListener {
        void editScenerySecondListener(SurveyResult resultToEdit, int position);
    }

    // 3번 관로 수정 리스너
    public interface OnItemEditSceneryThirdListener {
        void editSceneryThirdListener(SurveyResult resultToEdit, int position);
    }

    // 4번 관로 수정 리스너
    public interface OnItemEditSceneryFourthListener {
        void editSceneryFourthListener(SurveyResult resultToEdit, int position);
    }

    // 5번 관로 수정 리스너
    public interface OnItemEditSceneryFifthListener {
        void editSceneryFifthListener(SurveyResult resultToEdit, int position);
    }

    // 6번 관로 수정 리스너
    public interface OnItemEditScenerySixthListener {
        void editScenerySixthListener(SurveyResult resultToEdit, int position);
    }

    // 1번 높이 수정 리스너
    public interface OnItemEditFirstListener {
        void onEditClick(SurveyResult resultToEdit, int position);
    }

    // 2번 높이 수정 리스너
    public interface OnItemEditSecondListener {
        void onEditClickSecond(SurveyResult resultToEdit, int position);
    }

    // 3번 높이 수정 리스너
    public interface OnItemEditThirdListener {
        void onEditClickThird(SurveyResult resultToEdit, int position);
    }

    // 4번 높이 수정 리스너
    public interface OnItemEditFourthListener {
        void onEditClickFourth(SurveyResult resultToEdit, int position);
    }

    // 5번 높이 수정 리스너
    public interface OnItemEditFifthListener {
        void onEditClickFifth(SurveyResult resultToEdit, int position);
    }

    // 6번 높이 수정 리스너
    public interface OnItemEditSixthListener {
        void onEditClickSixth(SurveyResult resultToEdit, int position);
    }

    // 1번 재질 수정 리스너
    public interface OnItemPipFirstListener {
        void onEditClickPipFirst(SurveyResult resultToEdit, int position);
    }

    // 2번 재질 수정 리스너
    public interface OnItemPipSecondListener {
        void onEditClickPipSecond(SurveyResult resultToEdit, int position);
    }

    // 3번 재질 수정 리스너
    public interface OnItemPipThirdListener {
        void onEditClickPipThird(SurveyResult resultToEdit, int position);
    }

    // 4번 재질 수정 리스너
    public interface OnItemPipFourthListener {
        void onEditClickPipFourth(SurveyResult resultToEdit, int position);
    }

    // 5번 재질 수정 리스너
    public interface OnItemPipFifthListener {
        void onEditClickPipFifth(SurveyResult resultToEdit, int position);
    }

    // 6번 재질 수정 리스너
    public interface OnItemPipSixthListener {
        void onEditClickPipSixth(SurveyResult resultToEdit, int position);
    }

    // 1번 두께 수정 리스너
    public interface OnItemThicknessFirstListener {
        void onEditClickThicknessFirst(SurveyResult resultToEdit, int position);
    }

    // 2번 두께 수정 리스너
    public interface OnItemThicknessSecondListener {
        void onEditClickThicknessSecond(SurveyResult resultToEdit, int position);
    }

    // 3번 두께 수정 리스너
    public interface OnItemThicknessThirdListener {
        void onEditClickThicknessThird(SurveyResult resultToEdit, int position);
    }

    // 4번 두께 수정 리스너
    public interface OnItemThicknessFourthListener {
        void onEditClickThicknessFourth(SurveyResult resultToEdit, int position);
    }

    // 5번 두께 수정 리스너
    public interface OnItemThicknessFifthListener {
        void onEditClickThicknessFifth(SurveyResult resultToEdit, int position);
    }

    // 6번 두께 수정 리스너
    public interface OnItemThicknessSixthListener {
        void onEditClickThicknessSixth(SurveyResult resultToEdit, int position);
    }


    // 수정 버튼 클릭 이벤트를 위한 인터페이스 정의
    private OnItemEditFirstListener editFirstListener;
    private OnItemEditSecondListener editSecondListener;
    private OnItemEditThirdListener editThirdListener;
    private OnItemEditFourthListener editFourthListener;
    private OnItemEditFifthListener editFifthListener;
    private OnItemEditSixthListener editSixthListener;

    // tvSceneryFirst
    private OnItemEditSceneryFirstListener editSceneryFirstListener;
    private OnItemEditScenerySecondListener editScenerySecondListener;
    private OnItemEditSceneryThirdListener editSceneryThirdListener;
    private OnItemEditSceneryFourthListener editSceneryFourthListener;
    private OnItemEditSceneryFifthListener editSceneryFifthListener;
    private OnItemEditScenerySixthListener editScenerySixthListener;

    // pip
    private OnItemPipFirstListener editPipFirstListener;
    private OnItemPipSecondListener editPipSecondListener;
    private OnItemPipThirdListener editPipThirdListener;
    private OnItemPipFourthListener editPipFourthListener;
    private OnItemPipFifthListener editPipFifthListener;
    private OnItemPipSixthListener editPipSixthListener;

    // thickness
    private OnItemThicknessFirstListener editthicknessFirstListener;
    private OnItemThicknessSecondListener editthicknessSecondListener;
    private OnItemThicknessThirdListener editthicknessThirdListener;
    private OnItemThicknessFourthListener editthicknessFourthListener;
    private OnItemThicknessFifthListener editthicknessFifthListener;
    private OnItemThicknessSixthListener editthicknessSixthListener;

    private List<SurveyResult> results;
    private List<SurveyDiameterData> resultData;

    public void setOnItemDeleteListener(OnItemDeleteListener listener) {
        this.deleteListener = listener;
    }

    public void setOnItemEditListener(OnItemEditFirstListener listener) { this.editFirstListener = listener; }
    public void setOnItemEditSecondListener(OnItemEditSecondListener listener) { this.editSecondListener = listener; }
    public void setOnItemEditThirdListener(OnItemEditThirdListener listener) { this.editThirdListener = listener; }
    public void setOnItemEditFourthListener(OnItemEditFourthListener listener) { this.editFourthListener = listener; }
    public void setOnItemEditFifthListener(OnItemEditFifthListener listener) { this.editFifthListener = listener; }
    public void setOnItemEditSixthListener(OnItemEditSixthListener listener) { this.editSixthListener = listener; }

    public void setOnItemEditSceneryFirstListener(OnItemEditSceneryFirstListener listener) { this.editSceneryFirstListener = listener; }
    public void setOnItemEditScenerySecondListener(OnItemEditScenerySecondListener listener) { this.editScenerySecondListener = listener; }
    public void setOnItemEditSceneryThirdListener(OnItemEditSceneryThirdListener listener) { this.editSceneryThirdListener = listener; }
    public void setOnItemEditSceneryFourthListener(OnItemEditSceneryFourthListener listener) { this.editSceneryFourthListener = listener; }
    public void setOnItemEditSceneryFifthListener(OnItemEditSceneryFifthListener listener) { this.editSceneryFifthListener = listener; }
    public void setOnItemEditScenerySixthListener(OnItemEditScenerySixthListener listener) { this.editScenerySixthListener = listener; }

    public void setOnItemEditPipFirstListener(OnItemPipFirstListener listener) { this.editPipFirstListener = listener; }
    public void setOnItemEditPipSecondListener(OnItemPipSecondListener listener) { this.editPipSecondListener = listener; }
    public void setOnItemEditPipThirdListener(OnItemPipThirdListener listener) { this.editPipThirdListener = listener; }
    public void setOnItemEditPipFourthListener(OnItemPipFourthListener listener) { this.editPipFourthListener = listener; }
    public void setOnItemEditPipFifthListener(OnItemPipFifthListener listener) { this.editPipFifthListener = listener; }
    public void setOnItemEditPipSixthListener(OnItemPipSixthListener listener) { this.editPipSixthListener = listener; }

    public void setOnItemEditThicknessFirstListener(OnItemThicknessFirstListener listener) { this.editthicknessFirstListener = listener; }
    public void setOnItemEditThicknessSecondListener(OnItemThicknessSecondListener listener) { this.editthicknessSecondListener = listener; }
    public void setOnItemEditThicknessThirdListener(OnItemThicknessThirdListener listener) { this.editthicknessThirdListener = listener; }
    public void setOnItemEditThicknessFourthListener(OnItemThicknessFourthListener listener) { this.editthicknessFourthListener = listener; }
    public void setOnItemEditThicknessFifthListener(OnItemThicknessFifthListener listener) { this.editthicknessFifthListener = listener; }
    public void setOnItemEditThicknessSixthListener(OnItemThicknessSixthListener listener) { this.editthicknessSixthListener = listener; }

    public void setResults(List<SurveyResult> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    public List<SurveyResult> getResults() {
        return results;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 주석: 항목 하나를 위한 레이아웃으로 변경 (R.layout.list_item_survey_result는 새로 만든 레이아웃 ID라고 가정)
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_result, parent, false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        SurveyResult item = results.get(position);
        holder.bind(item, deleteListener, editFirstListener, editSecondListener, editThirdListener, editFourthListener, editFifthListener, editSixthListener,
                editSceneryFirstListener, editScenerySecondListener, editSceneryThirdListener, editSceneryFourthListener, editSceneryFifthListener, editScenerySixthListener,
                editPipFirstListener, editPipSecondListener, editPipThirdListener, editPipFourthListener, editPipFifthListener, editPipSixthListener,
                editthicknessFirstListener, editthicknessSecondListener, editthicknessThirdListener, editthicknessFourthListener, editthicknessFifthListener, editthicknessSixthListener);
    }

    @Override
    public int getItemCount() {
        return results != null ? results.size() : 0;
    }

    static class ResultViewHolder extends RecyclerView.ViewHolder {

        private final DecimalFormat df;
        private final MaterialCardView mcDeleteButton;

        public TextView mapNumber;        // 도엽 번호
        public TextView manholType;       // 맨홀 타입 (1개, 2개, 3개, 4개)

        // === 관경 (Scenery) 데이터 필드 (6개) ===
        private TextView tvSceneryFirst;  // 1번 관경
        private TextView tvScenerySecond; // 2번 관경
        private TextView tvSceneryThird;  // 3번 관경
        private TextView tvSceneryFourth; // 4번 관경
        private TextView tvSceneryFifth; // 5번 관경
        private TextView tvScenerySixth; // 6번 관경

        // === 높이 (Pipe Material) 데이터 필드 (6개) ===
        private TextView tvInputFirst; // 1번 수기 입력 데이터
        private TextView tvInputsecond; // 2번 수기 입력 데이터
        private TextView tvInputthird;  // 3번 수기 입력 데이터
        private TextView tvInputfourth; // 4번 수기 입력 데이터
        private TextView tvInputFifth; // 4번 수기 입력 데이터
        private TextView tvInputSixth; // 4번 수기 입력 데이터

        // === 재질 (Pipe Material) 데이터 필드 (6개) ===
        private TextView etPipMaterialFirst; // 1번 재질
        private TextView etPipMaterialSecond; // 2번 재질
        private TextView etPipMaterialThird;  // 3번 재질
        private TextView etPipMaterialFourth; // 4번 재질
        private TextView etPipMaterialFifth; // 4번 재질
        private TextView etPipMaterialSixth; // 4번 재질

        // === 두께 ((height Material) / variable : mc_note_first)
        private final MaterialCardView mcThicknessFirst;
        private final MaterialCardView mcThicknessSecond;
        private final MaterialCardView mcThicknessThird;
        private final MaterialCardView mcThicknessFourth;
        private final MaterialCardView mcThicknessFifth;
        private final MaterialCardView mcThicknessSixth;

        // === 두께 (note) 데이터 필드 (6개) ===
        private TextView tvThicknessFirst; // 1번 두께 값
        private TextView tvThicknessSecond; // 2번 두께 값
        private TextView tvThicknessThird; // 3번 두께 값
        private TextView tvThicknessFourth; // 4번 두께 값
        private TextView tvThicknessFifth; // 5번 두께 값
        private TextView etThicknessSixth; // 6번 두께 값

        // 높이 Material
        private final MaterialCardView mcInputFirst;
        private final MaterialCardView mcInputSecond;
        private final MaterialCardView mcInputThird;
        private final MaterialCardView mcInputFourth;
        private final MaterialCardView mcInputFifth;
        private final MaterialCardView mcInputSixth;

        // 재질 Material
        private final MaterialCardView mcPipFirst;
        private final MaterialCardView mcPipSecond;
        private final MaterialCardView mcPipThird;
        private final MaterialCardView mcPipFourth;
        private final MaterialCardView mcPipFifth;
        private final MaterialCardView mcPipSixth;

        private final MaterialCardView mcSceneryFirst;
        private final MaterialCardView mcScenerySecond;
        private final MaterialCardView mcSceneryThird;
        private final MaterialCardView mcSceneryFourth;
        private final MaterialCardView mcSceneryFifth;
        private final MaterialCardView mcScenerySixth;


        private final View llMeasurement01;
        private final View llMeasurement02;
        private final View llMeasurement03;
        private final View llMeasurement04;
        private final View llMeasurement05;
        private final View llMeasurement06;

        @SuppressLint("WrongViewCast")
        public ResultViewHolder(View itemView) {
            super(itemView);

            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
            this.df = new DecimalFormat("0.000", symbols);

            mapNumber = itemView.findViewById(R.id.tv_sheet_number);
            manholType = itemView.findViewById(R.id.tv_manhol_type);
            mcDeleteButton = itemView.findViewById(R.id.mc_delete_button);

            // 관경 (Scenery)
            tvSceneryFirst = itemView.findViewById(R.id.tv_scenery_first);
            tvScenerySecond = itemView.findViewById(R.id.tv_scenery_second);
            tvSceneryThird = itemView.findViewById(R.id.tv_scenery_third);
            tvSceneryFourth = itemView.findViewById(R.id.tv_scenery_fourth);
            tvSceneryFifth = itemView.findViewById(R.id.tv_scenery_fifth);
            tvScenerySixth = itemView.findViewById(R.id.tv_scenery_sixth);

            // 높이 TextView
            tvInputFirst = itemView.findViewById(R.id.tv_input_first);
            tvInputsecond = itemView.findViewById(R.id.tv_input_second);
            tvInputthird = itemView.findViewById(R.id.tv_input_third);
            tvInputfourth = itemView.findViewById(R.id.tv_input_fourth);
            tvInputFifth = itemView.findViewById(R.id.tv_input_fifth);
            tvInputSixth = itemView.findViewById(R.id.tv_input_sixth);

            // 높이 Material
            mcInputFirst = itemView.findViewById(R.id.mc_input_first);
            mcInputSecond = itemView.findViewById(R.id.mc_input_second);
            mcInputThird = itemView.findViewById(R.id.mc_input_third);
            mcInputFourth = itemView.findViewById(R.id.mc_input_fourth);
            mcInputFifth = itemView.findViewById(R.id.mc_input_fifth);
            mcInputSixth = itemView.findViewById(R.id.mc_input_sixth);

            // 재질 Material
            mcPipFirst = itemView.findViewById(R.id.mc_pip_first);
            mcPipSecond = itemView.findViewById(R.id.mc_pip_second);
            mcPipThird = itemView.findViewById(R.id.mc_pip_third);
            mcPipFourth = itemView.findViewById(R.id.mc_pip_fourth);
            mcPipFifth = itemView.findViewById(R.id.mc_pip_fifth);
            mcPipSixth = itemView.findViewById(R.id.mc_pip_sixth);

            // 관경 Material
            mcSceneryFirst = itemView.findViewById(R.id.mc_scenery_first);
            mcScenerySecond = itemView.findViewById(R.id.mc_scenery_second);
            mcSceneryThird = itemView.findViewById(R.id.mc_scenery_third);
            mcSceneryFourth = itemView.findViewById(R.id.mc_scenery_fourth);
            mcSceneryFifth = itemView.findViewById(R.id.mc_scenery_fifth);
            mcScenerySixth = itemView.findViewById(R.id.mc_scenery_sixth);

            // 재질 (Pipe TextView)
            etPipMaterialFirst = itemView.findViewById(R.id.et_pip_material_first);
            etPipMaterialSecond = itemView.findViewById(R.id.et_pip_material_second);
            etPipMaterialThird = itemView.findViewById(R.id.et_pip_material_third);
            etPipMaterialFourth = itemView.findViewById(R.id.et_pip_material_fourth);
            etPipMaterialFifth = itemView.findViewById(R.id.et_pip_material_fifth);
            etPipMaterialSixth = itemView.findViewById(R.id.et_pip_material_sixth);

            // 재질 (Pipe Material)
            mcThicknessFirst = itemView.findViewById(R.id.mc_thickness_first);
            mcThicknessSecond = itemView.findViewById(R.id.mc_thickness_second);
            mcThicknessThird = itemView.findViewById(R.id.mc_thickness_third);
            mcThicknessFourth = itemView.findViewById(R.id.mc_thickness_fourth);
            mcThicknessFifth = itemView.findViewById(R.id.mc_thickness_fifth);
            mcThicknessSixth = itemView.findViewById(R.id.mc_thickness_sixth);

            // 비고 (note)
            tvThicknessFirst = itemView.findViewById(R.id.tv_thickness_first);
            tvThicknessSecond = itemView.findViewById(R.id.tv_thickness_second);
            tvThicknessThird = itemView.findViewById(R.id.tv_thickness_third);
            tvThicknessFourth = itemView.findViewById(R.id.tv_thickness_fourth);
            tvThicknessFifth = itemView.findViewById(R.id.tv_thickness_fifth);
            etThicknessSixth = itemView.findViewById(R.id.et_thickness_sixth);

            llMeasurement01 = itemView.findViewById(R.id.ll_measurement_01);
            llMeasurement02 = itemView.findViewById(R.id.ll_measurement_02);
            llMeasurement03 = itemView.findViewById(R.id.ll_measurement_03);
            llMeasurement04 = itemView.findViewById(R.id.ll_measurement_04);
            llMeasurement05 = itemView.findViewById(R.id.ll_measurement_05);
            llMeasurement06 = itemView.findViewById(R.id.ll_measurement_06);
        }

        private String formatValue(String value) {
            if (value == null || value.trim().isEmpty()) {
                Log.e("khj", "test00001");
                return "0.000"; // 값이 비어있다면 기본 0.000 표기
//                return ""; // 값이 비어있다면 기본 0.000 표기
            }

            String cleanedValue = value.trim().replace("m", "");


            try {
                Double.parseDouble(cleanedValue);
//                return cleanedValue;
                double doubleValue = Double.parseDouble(cleanedValue);
                return df.format(doubleValue);
            } catch (NumberFormatException e) {
                Log.w("ResultListAdapter", "NumberFormatException for value : " + value);
                return value; //원본 값 그대로 표시
            }
        }

        private String formatValueInt(String value) {
            if (value == null || value.trim().isEmpty()) {
                return "0"; // 값이 비어있다면 기본 0.000 표기
            }

            String cleanedValue = value.trim().replace("m", "");

            try {
                Double.parseDouble(cleanedValue);
                return cleanedValue;
            } catch (NumberFormatException e) {
                Log.w("ResultListAdapter", "NumberFormatException for value : " + value);
                return value; //원본 값 그대로 표시
            }
        }

        private int extractManholeCount(String manholTypeString) {
            if(manholTypeString == null || manholTypeString.trim().isEmpty()) return 1;

            try {
                // manholTypeString 사용
                String numberString = manholTypeString.replaceAll("[^\\d]", "");

                if (numberString.isEmpty()) {
                    return 1;
                }

                int count = Integer.parseInt(numberString);
                return Math.min(count, 4);
            } catch (NumberFormatException e) {
                // manholTypeString 사용
                Log.e("ResultListAdapter", "맨홀 갯수 파싱 오류 : " + manholTypeString, e);
                return 1;
            }
        }

        public void bind(final SurveyResult item,
                         final OnItemDeleteListener listener,
                         final OnItemEditFirstListener editListener,
                         final OnItemEditSecondListener editSecondListener,
                         final OnItemEditThirdListener editThirdListener,
                         final OnItemEditFourthListener editFourthListener,
                         final OnItemEditFifthListener editFifthListener,
                         final OnItemEditSixthListener editSixthListener,

                         final OnItemEditSceneryFirstListener editSceneryFirstListener,
                         final OnItemEditScenerySecondListener editScenerySecondListener,
                         final OnItemEditSceneryThirdListener editSceneryThirdListener,
                         final OnItemEditSceneryFourthListener editSceneryFourthListener,
                         final OnItemEditSceneryFifthListener editSceneryFifthListener,
                         final OnItemEditScenerySixthListener editScenerySixthListener,

                         final OnItemPipFirstListener editPipFirsthListener,
                         final OnItemPipSecondListener editPipSecondListener,
                         final OnItemPipThirdListener editPipThirdListener,
                         final OnItemPipFourthListener editPipFourthListener,
                         final OnItemPipFifthListener editPipFifthListener,
                         final OnItemPipSixthListener editPipSixthListener,

                         final OnItemThicknessFirstListener editThicknessFirstListener,
                         final OnItemThicknessSecondListener editThicknessSecondListener,
                         final OnItemThicknessThirdListener editThicknessThirdListener,
                         final OnItemThicknessFourthListener editThicknessFourthListener,
                         final OnItemThicknessFifthListener editThicknessFifthListener,
                         final OnItemThicknessSixthListener editThicknessSixthListener) {
//            mapNumber.setText("도엽 번호 : " + item.getMapNumber());
//            manholType.setText("맨홀 타입 : " + item.getManholType());

            int selectedCount = extractManholeCount(item.getManholType());



            mapNumber.setText("맨홀 번호 : " + item.getMapNumber());
            manholType.setText("배관 수 : " + item.getManholType());

//            double distanceDouble = Double.parseDouble(item.getTvSceneryFirst());
//            double distanceMM = distanceDouble * 1000; // 밀리미터(mm)로 변환

            // 관경
            tvSceneryFirst.setText(formatValueInt(item.getTvSceneryFirst()));
            tvScenerySecond.setText(formatValueInt(item.getTvScenerySecond()));
            tvSceneryThird.setText(formatValueInt(item.getTvSceneryThird()));
            tvSceneryFourth.setText(formatValueInt(item.getTvSceneryFourth()));
            tvSceneryFifth.setText(formatValueInt(item.getTvSceneryFifth()));
            tvScenerySixth.setText(formatValueInt(item.getTvScenerySixth()));

            // 높이
            tvInputFirst.setText(formatValue(item.getTvInputFirst()));
            tvInputsecond.setText(formatValue(item.getTvInputSecond()));
            tvInputthird.setText(formatValue(item.getTvInputThird()));
            tvInputfourth.setText(formatValue(item.getTvInputFourth()));
            tvInputFifth.setText(formatValue(item.getTvInputFifth()));
            tvInputSixth.setText(formatValue(item.getTvInputSixth()));

            // 재질
            etPipMaterialFirst.setText(item.getEtPipMaterialFirst());
            etPipMaterialSecond.setText(item.getEtPipMaterialSecond());
            etPipMaterialThird.setText(item.getEtPipMaterialThird());
            etPipMaterialFourth.setText(item.getEtPipMaterialFourth());
            etPipMaterialFifth.setText(item.getEtPipMaterialFifth());
            etPipMaterialSixth.setText(item.getEtPipMaterialSixth());

            // 두께
            tvThicknessFirst.setText(formatValue(item.getThicknessFirst()));
            tvThicknessSecond.setText(formatValue(item.getThicknessSecond()));
            tvThicknessThird.setText(formatValue(item.getThicknessThird()));
            tvThicknessFourth.setText(formatValue(item.getThicknessFourth()));
            tvThicknessFifth.setText(formatValue(item.getThicknessFifth()));
            etThicknessSixth.setText(formatValue(item.getThicknessSixth()));

            // 스피너 갯수에 따라 Visible-Gone
//            llMeasurement01.setVisibility(View.VISIBLE);
//            llMeasurement02.setVisibility(selectedCount >= 2 ? View.VISIBLE : View.GONE);
//            llMeasurement03.setVisibility(selectedCount >= 3 ? View.VISIBLE : View.GONE);
//            llMeasurement04.setVisibility(selectedCount >= 4 ? View.VISIBLE : View.GONE);
//            llMeasurement05.setVisibility(selectedCount >= 5 ? View.VISIBLE : View.GONE);
//            llMeasurement06.setVisibility(selectedCount >= 6 ? View.VISIBLE : View.GONE);


            Log.e("Disto_도엽 번호", "item.getMapNumber() : " + item.getTvSceneryFirst());
            Log.e("Disto_도엽 번호", "item.getManholType() : " + item.getManholType());

            Log.e("Disto_측정 관경", "item.getTvSceneryFirst() : " + item.getTvSceneryFirst()
                    + " item.getTvScenerySecond() : " + item.getTvScenerySecond()
                    + " item.getTvSceneryThird() : " + item.getTvSceneryThird()
                    + " item.getTvSceneryFourth() : " + item.getTvSceneryFourth());

            Log.e("Disto_입력", "item.getTvInputFirst() : " + item.getTvInputFirst()
                    + " item.getTvInputsecond() : " + item.getTvInputSecond()
                    + " item.getTvInputthird() : " + item.getTvInputThird()
                    + " item.getTvInputfourth() : " + item.getTvInputFourth());

            Log.e("Disto_재질", "item.getEtPipMaterialFirst() : " + item.getEtPipMaterialFirst()
                    + " item.getEtPipMaterialSecond() : " + item.getEtPipMaterialSecond()
                    + " item.getEtPipMaterialThird() : " + item.getEtPipMaterialThird()
                    + " item.getEtPipMaterialFourth() : " + item.getEtPipMaterialFourth());

            Log.e("Disto_두께", "item.getEtThicknessFirst() : " + item.getThicknessFirst()
                    + " item.getEtThicknessSecond() : " + item.getThicknessSecond()
                    + " item.getEtThicknessThird() : " + item.getThicknessThird()
                    + " item.getEtThicknessFourth() : " + item.getThicknessFourth());

            mcDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onDeleteClick(item, getAdapterPosition());
                    }
                }
            });

            // 관경 클릭
            mcSceneryFirst.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (editSceneryFirstListener != null) {
                        editSceneryFirstListener.editSceneryFirstListener(item, getAdapterPosition());
                    }
                }
            });

            mcScenerySecond.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (editScenerySecondListener != null) {
                        editScenerySecondListener.editScenerySecondListener(item, getAdapterPosition());
                    }
                }
            });

            mcSceneryThird.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (editSceneryThirdListener != null) {
                        editSceneryThirdListener.editSceneryThirdListener(item, getAdapterPosition());
                    }
                }
            });

            mcSceneryFourth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (editSceneryFourthListener != null) {
                        editSceneryFourthListener.editSceneryFourthListener(item, getAdapterPosition());
                    }
                }
            });

            mcSceneryFifth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (editSceneryFifthListener != null) {
                        editSceneryFifthListener.editSceneryFifthListener(item, getAdapterPosition());
                    }
                }
            });

            mcScenerySixth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (editScenerySixthListener != null) {
                        editScenerySixthListener.editScenerySixthListener(item, getAdapterPosition());
                    }
                }
            });

            // 높이 클릭
            mcInputFirst.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editListener != null) {
                        editListener.onEditClick(item, getAdapterPosition());
                    }
                }
            });

            mcInputSecond.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editSecondListener != null) {
//                        editListener.onEditClick(item, getAdapterPosition());
                        editSecondListener.onEditClickSecond(item, getAdapterPosition());
                    }
                }
            });

            mcInputThird.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editThirdListener != null) {
//                        editListener.onEditClick(item, getAdapterPosition());
                        editThirdListener.onEditClickThird(item, getAdapterPosition());
                    }
                }
            });

            mcInputFourth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editFourthListener != null) {
//                        editListener.onEditClick(item, getAdapterPosition());
                        editFourthListener.onEditClickFourth(item, getAdapterPosition());
                    }
                }
            });

            mcInputFifth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editFifthListener != null) {
//                        editListener.onEditClick(item, getAdapterPosition());
                        editFifthListener.onEditClickFifth(item, getAdapterPosition());
                    }
                }
            });

            mcInputSixth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editSixthListener != null) {
//                        editListener.onEditClick(item, getAdapterPosition());
                        editSixthListener.onEditClickSixth(item, getAdapterPosition());
                    }
                }
            });

            // 재질 클릭
            mcPipFirst.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editPipFirsthListener != null) {
                        editPipFirsthListener.onEditClickPipFirst(item, getAdapterPosition());
                    }
                }
            });

            mcPipSecond.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editPipSecondListener != null) {
                        editPipSecondListener.onEditClickPipSecond(item, getAdapterPosition());
                    }
                }
            });

            mcPipThird.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editPipThirdListener != null) {
                        editPipThirdListener.onEditClickPipThird(item, getAdapterPosition());
                    }
                }
            });

            mcPipFourth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editPipFourthListener != null) {
                        editPipFourthListener.onEditClickPipFourth(item, getAdapterPosition());
                    }
                }
            });

            mcPipFifth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editPipFifthListener != null) {
                        editPipFifthListener.onEditClickPipFifth(item, getAdapterPosition());
                    }
                }
            });

            mcPipSixth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editPipSixthListener != null) {
                        editPipSixthListener.onEditClickPipSixth(item, getAdapterPosition());
                    }
                }
            });

            mcThicknessFirst.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editThicknessFirstListener != null) {
                        editThicknessFirstListener.onEditClickThicknessFirst(item, getAdapterPosition());
                    }
                }
            });

            mcThicknessSecond.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editThicknessSecondListener != null) {
                        editThicknessSecondListener.onEditClickThicknessSecond(item, getAdapterPosition());
                    }
                }
            });

            mcThicknessThird.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editThicknessThirdListener != null) {
                        editThicknessThirdListener.onEditClickThicknessThird(item, getAdapterPosition());
                    }
                }
            });

            mcThicknessFourth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editThicknessFourthListener != null) {
                        editThicknessFourthListener.onEditClickThicknessFourth(item, getAdapterPosition());
                    }
                }
            });

            mcThicknessFifth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editThicknessFifthListener != null) {
                        editThicknessFifthListener.onEditClickThicknessFifth(item, getAdapterPosition());
                    }
                }
            });

            mcThicknessSixth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editThicknessSixthListener != null) {
                        editThicknessSixthListener.onEditClickThicknessSixth(item, getAdapterPosition());
                    }
                }
            });
        }
    }
}
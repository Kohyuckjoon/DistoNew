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

    public interface OnItemEditListener {
        void onEditClick(SurveyResult resultToEdit, int position);
    }

    // 2번 관로 수정 리스너
    public interface OnItemEditSecondListener {
        void onEditClickSecond(SurveyResult resultToEdit, int position);
    }

    // 3번 관로 수정 리스너
    public interface OnItemEditThirdListener {
        void onEditClickThird(SurveyResult resultToEdit, int position);
    }

    // 4번 관로 수정 리스너
    public interface OnItemEditFourthListener {
        void onEditClickFourth(SurveyResult resultToEdit, int position);
    }

    // 수정 버튼 클릭 이벤트를 위한 인터페이스 정의
    private OnItemEditListener editListener;
    private OnItemEditSecondListener editSecondListener;
    private OnItemEditThirdListener editThirdListener;
    private OnItemEditFourthListener editFourthListener;


    private List<SurveyResult> results;
    private List<SurveyDiameterData> resultData;

    public void setOnItemDeleteListener(OnItemDeleteListener listener) {
        this.deleteListener = listener;
    }

    public void setOnItemEditListener(OnItemEditListener listener) {
        this.editListener = listener;
    }
    public void setOnItemEditSecondListener(OnItemEditSecondListener listener) { this.editSecondListener = listener; }
    public void setOnItemEditThirdListener(OnItemEditThirdListener listener) { this.editThirdListener = listener; }
    public void setOnItemEditFourthListener(OnItemEditFourthListener listener) { this.editFourthListener = listener; }

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
        holder.bind(item, deleteListener, editListener, editSecondListener, editThirdListener, editFourthListener);
    }

    @Override
    public int getItemCount() {
        return results != null ? results.size() : 0;
    }

    static class ResultViewHolder extends RecyclerView.ViewHolder {

        private final DecimalFormat df;
        private final MaterialCardView mcDeleteButton;
        private final MaterialCardView mcInputFirst;
        private final MaterialCardView mcInputSecond;
        private final MaterialCardView mcInputThird;
        private final MaterialCardView mcInputFourth;

        public TextView mapNumber;        // 도엽 번호
        public TextView manholType;       // 맨홀 타입 (1개, 2개, 3개, 4개)

        // === 관경 (Scenery) 데이터 필드 (4개) ===
        private TextView tvSceneryFirst;  // 1번 관경
        private TextView tvScenerySecond; // 2번 관경
        private TextView tvSceneryThird;  // 3번 관경
        private TextView tvSceneryFourth; // 4번 관경

        // === 재질 (Pipe Material) 데이터 필드 (4개) ===
        private TextView tvInputFirst; // 1번 수기 입력 데이터
        private TextView tvInputsecond; // 2번 수기 입력 데이터
        private TextView tvInputthird;  // 3번 수기 입력 데이터
        private TextView tvInputfourth; // 4번 수기 입력 데이터

        // === 재질 (Pipe Material) 데이터 필드 (4개) ===
        private TextView etPipMaterialFirst; // 1번 재질
        private TextView etPipMaterialSecond; // 2번 재질
        private TextView etPipMaterialThird;  // 3번 재질
        private TextView etPipMaterialFourth; // 4번 재질

        private final View llMeasurement01;
        private final View llMeasurement02;
        private final View llMeasurement03;
        private final View llMeasurement04;

        @SuppressLint("WrongViewCast")
        public ResultViewHolder(View itemView) {
            super(itemView);

            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
            this.df = new DecimalFormat("0.000", symbols);

            mapNumber = itemView.findViewById(R.id.tv_sheet_number);
            manholType = itemView.findViewById(R.id.tv_manhol_type);
            tvSceneryFirst = itemView.findViewById(R.id.tv_scenery_first);
            tvScenerySecond = itemView.findViewById(R.id.tv_scenery_second);
            tvSceneryThird = itemView.findViewById(R.id.tv_scenery_third);
            tvSceneryFourth = itemView.findViewById(R.id.tv_scenery_fourth);
            mcDeleteButton = itemView.findViewById(R.id.mc_delete_button);

            tvInputFirst = itemView.findViewById(R.id.tv_input_first);
            tvInputsecond = itemView.findViewById(R.id.tv_input_second);
            tvInputthird = itemView.findViewById(R.id.tv_input_third);
            tvInputfourth = itemView.findViewById(R.id.tv_input_fourth);

            mcInputFirst = itemView.findViewById(R.id.mc_input_01);
            mcInputSecond = itemView.findViewById(R.id.mc_input_02);
            mcInputThird = itemView.findViewById(R.id.mc_input_03);
            mcInputFourth = itemView.findViewById(R.id.mc_input_04);

            etPipMaterialFirst = itemView.findViewById(R.id.et_pip_material_first);
            etPipMaterialSecond = itemView.findViewById(R.id.et_pip_material_second);
            etPipMaterialThird = itemView.findViewById(R.id.et_pip_material_third);
            etPipMaterialFourth = itemView.findViewById(R.id.et_pip_material_fourth);

            llMeasurement01 = itemView.findViewById(R.id.ll_measurement_01);
            llMeasurement02 = itemView.findViewById(R.id.ll_measurement_02);
            llMeasurement03 = itemView.findViewById(R.id.ll_measurement_03);
            llMeasurement04 = itemView.findViewById(R.id.ll_measurement_04);
        }

        private String formatValue(String value) {
            if (value == null || value.trim().isEmpty()) {
                return "0.000"; // 값이 비어있다면 기본 0.000 표기
//                return ""; // 값이 비어있다면 기본 0.000 표기
            }

            String cleanedValue = value.trim().replace("m", "");

            try {
                double number = Double.parseDouble(cleanedValue);
                return df.format(number);
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
                         final OnItemEditListener editListener,
                         final OnItemEditSecondListener editSecondListener,
                         final OnItemEditThirdListener editThirdListener,
                         final OnItemEditFourthListener editFourthListener) {
//            mapNumber.setText("도엽 번호 : " + item.getMapNumber());
//            manholType.setText("맨홀 타입 : " + item.getManholType());

            int selectedCount = extractManholeCount(item.getManholType());

            mapNumber.setText("맨홀 번호 : " + item.getMapNumber());
            manholType.setText("배관 수 : " + item.getManholType());

            tvSceneryFirst.setText(formatValue(item.getTvSceneryFirst()));
            tvScenerySecond.setText(formatValue(item.getTvScenerySecond()));
            tvSceneryThird.setText(formatValue(item.getTvSceneryThird()));
            tvSceneryFourth.setText(formatValue(item.getTvSceneryFourth()));

            tvInputFirst.setText(formatValue(item.getEtInputFirst()));
            tvInputsecond.setText(formatValue(item.getEtInputSecond()));
            tvInputthird.setText(formatValue(item.getEtInputThird()));
            tvInputfourth.setText(formatValue(item.getEtInputFourth()));

            etPipMaterialFirst.setText(item.getEtPipMaterialFirst());
            etPipMaterialSecond.setText(item.getEtPipMaterialSecond());
            etPipMaterialThird.setText(item.getEtPipMaterialThird());
            etPipMaterialFourth.setText(item.getEtPipMaterialFourth());

            llMeasurement01.setVisibility(View.VISIBLE);
            llMeasurement02.setVisibility(selectedCount >= 2 ? View.VISIBLE : View.GONE);
            llMeasurement03.setVisibility(selectedCount >= 3 ? View.VISIBLE : View.GONE);
            llMeasurement04.setVisibility(selectedCount >= 4 ? View.VISIBLE : View.GONE);


            Log.e("Disto_도엽 번호", "item.getMapNumber() : " + item.getTvSceneryFirst());
            Log.e("Disto_도엽 번호", "item.getManholType() : " + item.getManholType());

            Log.e("Disto_측정 관경", "item.getTvSceneryFirst() : " + item.getTvSceneryFirst()
                    + " item.getTvScenerySecond() : " + item.getTvScenerySecond()
                    + " item.getTvSceneryThird() : " + item.getTvSceneryThird()
                    + " item.getTvSceneryFourth() : " + item.getTvSceneryFourth());

            Log.e("Disto_입력", "item.getTvInputFirst() : " + item.getEtInputFirst()
                    + " item.getTvInputsecond() : " + item.getEtInputSecond()
                    + " item.getTvInputthird() : " + item.getEtInputThird()
                    + " item.getTvInputfourth() : " + item.getEtInputFourth());

            Log.e("Disto_재질", "item.getEtPipMaterialFirst() : " + item.getEtPipMaterialFirst()
                    + " item.getEtPipMaterialSecond() : " + item.getEtPipMaterialSecond()
                    + " item.getEtPipMaterialThird() : " + item.getEtPipMaterialThird()
                    + " item.getEtPipMaterialFourth() : " + item.getEtPipMaterialFourth());

            mcDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onDeleteClick(item, getAdapterPosition());
                    }
                }
            });

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
        }
    }
}
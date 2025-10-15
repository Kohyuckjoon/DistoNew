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

import java.util.List;

public class ResultListAdapter extends RecyclerView.Adapter<ResultListAdapter.ResultViewHolder> {

    // 1. 삭제 버튼 클릭 이벤트를 위한 인터페이스 정의
    public interface OnItemDeleteListener {
        void onDeleteClick(SurveyResult resultToDelete, int position);
    }
    private OnItemDeleteListener deleteListener;

    private List<SurveyResult> results;
    private List<SurveyDiameterData> resultData;

    public void setOnItemDeleteListener(OnItemDeleteListener listener) {
        this.deleteListener = listener;
    }

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
        holder.bind(item, deleteListener);
    }

    @Override
    public int getItemCount() {
        return results != null ? results.size() : 0;
    }

    static class ResultViewHolder extends RecyclerView.ViewHolder {

        private final MaterialCardView mcDeleteButton;

        public TextView mapNumber;        // 도엽 번호
        public String manholType;       // 맨홀 타입 (1개, 2개, 3개, 4개)

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

        @SuppressLint("WrongViewCast")
        public ResultViewHolder(View itemView) {
            super(itemView);
            mapNumber = itemView.findViewById(R.id.tv_sheet_number);
            tvSceneryFirst = itemView.findViewById(R.id.tv_scenery_first);
            tvScenerySecond = itemView.findViewById(R.id.tv_scenery_second);
            tvSceneryThird = itemView.findViewById(R.id.tv_scenery_third);
            tvSceneryFourth = itemView.findViewById(R.id.tv_scenery_fourth);
            mcDeleteButton = itemView.findViewById(R.id.mc_delete_button);

            tvInputFirst = itemView.findViewById(R.id.tv_input_first);
            tvInputsecond = itemView.findViewById(R.id.tv_input_second);
            tvInputthird = itemView.findViewById(R.id.tv_input_third);
            tvInputfourth = itemView.findViewById(R.id.tv_input_fourth);

            etPipMaterialFirst = itemView.findViewById(R.id.et_pip_material_first);
            etPipMaterialSecond = itemView.findViewById(R.id.et_pip_material_second);
            etPipMaterialThird = itemView.findViewById(R.id.et_pip_material_third);
            etPipMaterialFourth = itemView.findViewById(R.id.et_pip_material_fourth);
        }

        public void bind(final SurveyResult item, final OnItemDeleteListener listener) {
            mapNumber.setText("도엽 번호 : " + item.getMapNumber());
            tvSceneryFirst.setText(item.getTvSceneryFirst());
            tvScenerySecond.setText(item.getTvScenerySecond());
            tvSceneryThird.setText(item.getTvSceneryThird());
            tvSceneryFourth.setText(item.getTvSceneryFourth());

            tvInputFirst.setText(item.getEtInputFirst());
            tvInputsecond.setText(item.getEtInputSecond());
            tvInputthird.setText(item.getEtInputThird());
            tvInputfourth.setText(item.getEtInputFourth());

            etPipMaterialFirst.setText(item.getEtPipMaterialFirst());
            etPipMaterialSecond.setText(item.getEtPipMaterialSecond());
            etPipMaterialThird.setText(item.getEtPipMaterialThird());
            etPipMaterialFourth.setText(item.getEtPipMaterialFourth());

            Log.e("Disto_도엽 번호", "item.getMapNumber() : " + item.getTvSceneryFirst());

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
        }
    }
}
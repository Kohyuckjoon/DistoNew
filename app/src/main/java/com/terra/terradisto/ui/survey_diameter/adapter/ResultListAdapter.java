package com.terra.terradisto.ui.survey_diameter.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.terra.terradisto.R;
import com.terra.terradisto.ui.survey_diameter.model.SurveyResult;

import java.util.List;

public class ResultListAdapter extends RecyclerView.Adapter<ResultListAdapter.ResultViewHolder> {

    // 1. 삭제 버튼 클릭 이벤트를 위한 인터페이스 정의
    public interface OnItemDeleteListener {
        void onDeleteClick(SurveyResult resultToDelete, int position);
    }
    private OnItemDeleteListener deleteListener;

    private List<SurveyResult> results;

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
        private final MaterialTextView mtMeasurementName;
        private final MaterialTextView mtManholType;
        private final MaterialTextView mtMeasurementDate; // DB에 일자 필드가 있다면 사용
        private final MaterialTextView mtDistance;
        private final MaterialTextView mtPipMaterial;
        private final MaterialCardView mcDeleteButton;
        private final MaterialCardView mcDownloadButton;

        public ResultViewHolder(View itemView) {
            super(itemView);
            mtMeasurementName = itemView.findViewById(R.id.mt_measurement_name);
            mtManholType = itemView.findViewById(R.id.mt_manhol_type);
            mtMeasurementDate = itemView.findViewById(R.id.mt_measurement_date);
            mtDistance = itemView.findViewById(R.id.mt_distance);
            mtPipMaterial = itemView.findViewById(R.id.mt_pip_material);
            mcDeleteButton = itemView.findViewById(R.id.mc_delete_button);
            mcDownloadButton = itemView.findViewById(R.id.mc_download_button);
        }

//        public void bind(SurveyResult item) {
        public void bind(final SurveyResult item, final OnItemDeleteListener listener) {
            mtMeasurementName.setText("" + item.id + "번 측정 값");
            mtManholType.setText("맨홀 타입 : " + item.manholType);
            mtDistance.setText("관 경 : " + item.distance + " m");
            mtPipMaterial.setText("관 재질 : " + item.pipMaterial);

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
package com.terra.terradisto.ui.survey_diameter.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.terra.terradisto.R;
import com.terra.terradisto.ui.survey_diameter.model.SurveyResult;

import java.util.List;

public class ResultListAdapter extends RecyclerView.Adapter<ResultListAdapter.ResultViewHolder> {

    private List<SurveyResult> results;

    // 주석: 데이터 목록을 어댑터에 설정합니다.
    public void setResults(List<SurveyResult> results) {
        this.results = results;
        notifyDataSetChanged();
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
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return results != null ? results.size() : 0;
    }

    static class ResultViewHolder extends RecyclerView.ViewHolder {
        // 임시 뷰 변수 (실제 레이아웃 ID로 변경 필요)
        // private final MaterialTextView tvId, tvType, tvDistance, tvMaterial;

        public ResultViewHolder(View itemView) {
            super(itemView);
            // 주석: 여기서 list_item_result.xml의 뷰 ID를 찾아 초기화해야 합니다.
            // tvId = itemView.findViewById(R.id.tv_result_id);
            // ...
        }

        public void bind(SurveyResult item) {
            // 주석: 데이터 객체의 값을 뷰에 설정합니다.
            // tvId.setText(String.valueOf(item.id));
            // tvDistance.setText(String.format("%.3f %s", item.distance, "m")); // 단위는 적절히 적용
            // tvType.setText(item.manholeType);
            // tvMaterial.setText(item.pipMaterial);
        }
    }
}
package com.terra.terradisto.ui.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.terra.terradisto.R;
import com.terra.terradisto.distosdkapp.data.ProjectCreate;
import java.util.List;

public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ViewHolder> {

    private List<ProjectCreate> projectList;
    private OnProjectSelectListener listener;

    public ProjectListAdapter(List<ProjectCreate> projectList, OnProjectSelectListener listener) {
        this.projectList = projectList;
        this.listener = listener;
    }

    public interface OnProjectSelectListener {
        void onProjectSelected(ProjectCreate project);  // 프로젝트 선택
        void onProjectDeleted(ProjectCreate project);   // 프로젝트 삭제
    }

    public ProjectListAdapter(List<ProjectCreate> projectList) {
        this.projectList = projectList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_project, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProjectCreate project = projectList.get(position);
        holder.tvProjectName.setText(project.name);
        holder.tvProjectLocation.setText(project.location);

        // 아이템을 눌렀을때에는 상세 페이지로 갈 수 있도록
//        holder.itemView.setOnClickListener(v -> {
//            if (listener != null) {
//                listener.onProjectSelected(project);
//            }
//        });

        // 프로젝트 선택
        holder.mcSelectButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onProjectSelected(project);
            }
        });

        // 프로젝트 삭제
        holder.mcDeleteButton.setOnClickListener(v -> {
            if(listener != null) {
                listener.onProjectDeleted(project);
            }
        });
    }

    @Override
    public int getItemCount() {
        return projectList == null ? 0 : projectList.size();
    }

    public void setProjects(List<ProjectCreate> projects) {
        this.projectList = projects;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProjectName, tvProjectLocation;
        MaterialCardView mcSelectButton, mcDeleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProjectName = itemView.findViewById(R.id.mc_project_name);
            tvProjectLocation = itemView.findViewById(R.id.mc_number);
            mcSelectButton = itemView.findViewById(R.id.mc_select_button);
            mcDeleteButton = itemView.findViewById(R.id.mc_delete_button);
        }
    }
}

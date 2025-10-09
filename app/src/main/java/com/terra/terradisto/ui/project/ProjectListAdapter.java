package com.terra.terradisto.ui.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.terra.terradisto.R;
import com.terra.terradisto.distosdkapp.data.ProjectCreate;
import java.util.List;

public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ViewHolder> {

    private List<ProjectCreate> projectList;

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProjectName = itemView.findViewById(R.id.mc_project_name);
            tvProjectLocation = itemView.findViewById(R.id.mc_number);
        }
    }
}

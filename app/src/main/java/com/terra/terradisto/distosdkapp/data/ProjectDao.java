package com.terra.terradisto.distosdkapp.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface ProjectDao {

    @Insert
    void insertProject(ProjectCreate projectCreate);

    @Query("SELECT * FROM PROJECTS")
    List<ProjectCreate> getAllProjects();
}

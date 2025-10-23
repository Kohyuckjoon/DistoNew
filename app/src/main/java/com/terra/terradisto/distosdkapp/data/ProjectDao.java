package com.terra.terradisto.distosdkapp.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface ProjectDao {

    @Insert
    void insertProject(ProjectCreate projectCreate);

    @Query("SELECT * FROM PROJECTS")
    List<ProjectCreate> getAllProjects();

    @Delete
    void delete(ProjectCreate project);

    @Query("SELECT COUNT(name) FROM projects WHERE name = :projectName")
    int countExistingProjectName(String projectName);
}

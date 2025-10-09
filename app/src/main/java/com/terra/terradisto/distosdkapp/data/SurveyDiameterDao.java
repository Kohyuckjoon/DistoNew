package com.terra.terradisto.distosdkapp.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SurveyDiameterDao {

    @Insert
    void insert(SurveyDiameterEntity entity);

    @Query("SELECT * FROM survey_diameter ORDER BY id DESC")
    List<SurveyDiameterEntity> getAll();
}

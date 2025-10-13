package com.terra.terradisto.distosdkapp.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.terra.terradisto.ui.survey_diameter.model.SurveyResult;

import java.util.List;

@Dao
public interface SurveyDiameterDao {

    @Insert
    void insert(SurveyDiameterEntity entity);

    @Query("SELECT * FROM survey_diameter ORDER BY id DESC")
    List<SurveyDiameterEntity> getAll();

    @Query("SELECT id, manholType, distance, pipMaterial FROM survey_diameter ORDER BY id DESC")
    List<SurveyResult> getAllResults();

    @Query("DELETE FROM survey_diameter WHERE id = :itemId")
    void deleteById(int itemId);
}

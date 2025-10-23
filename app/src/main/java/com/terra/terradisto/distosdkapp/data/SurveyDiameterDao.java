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

    // 특정 프로젝트의 모든 결과를 조회하는 메서드 추가
    @Query("SELECT * FROM survey_diameter WHERE projectId = :projectId ORDER BY id DESC")
    List<SurveyResult> getResultsByProjectId(int projectId);

    // 특정 프로젝트의 간략 정보를 조회하는 메서드 추가 (필요시)
    @Query("SELECT id, mapNumber, manholType, tvSceneryFirst, tvScenerySecond, tvSceneryThird, tvSceneryFourth FROM survey_diameter WHERE projectId = :projectId ORDER BY id DESC")
    List<SurveyResult> getResultDataByProjectId(int projectId);

//    @Query("SELECT id, manholType, tvSceneryFirst, etPipMaterialFirst FROM survey_diameter ORDER BY id DESC")
//    List<SurveyResult> getAllResults();

    @Query("SELECT * FROM survey_diameter ORDER BY id DESC")
    List<SurveyResult> getAllResults();

    @Query("SELECT id, mapNumber, manholType, tvSceneryFirst, tvScenerySecond, tvSceneryThird, tvSceneryFourth FROM survey_diameter ORDER BY id DESC")
    List<SurveyResult> getAllResultData();

    @Query("DELETE FROM survey_diameter WHERE id = :itemId")
    void deleteById(int itemId);

    @Query("SELECT * FROM survey_diameter WHERE projectId = :projectId AND mapNumber = :mapNumber")
    int countExistingMapNumber(int projectId, String mapNumber);

    @Query("UPDATE survey_diameter SET etInputFirst = :newValue WHERE id = :id")
    void updateInputFirst(int id, String newValue);
}

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

    @Query("SELECT id, mapNumber, manholType, tvSceneryFirst, tvScenerySecond, tvSceneryThird, tvSceneryFourth FROM survey_diameter ORDER BY id DESC")
    List<SurveyResult> getAllResultData();

    @Query("SELECT * FROM survey_diameter WHERE projectId = :projectId AND mapNumber = :mapNumber")
    int countExistingMapNumber(int projectId, String mapNumber);



    // Select Query & DESC
    @Query("SELECT * FROM survey_diameter ORDER BY id DESC")
    List<SurveyResult> getAllResults();

    // Delete Query
    @Query("DELETE FROM survey_diameter WHERE id = :itemId")
    void deleteById(int itemId);




    // Update Query 높이
    @Query("UPDATE survey_diameter SET tvInputFirst = :newValue WHERE id = :id")
    void updateInputFirst(int id, String newValue);

    @Query("UPDATE survey_diameter SET tvInputSecond = :newValue WHERE id = :id")
    void updateInputSecond(int id, String newValue);

    @Query("UPDATE survey_diameter SET tvInputThird = :newValue WHERE id = :id")
    void updateInputThird(int id, String newValue);

    @Query("UPDATE survey_diameter SET tvInputFourth = :newValue WHERE id = :id")
    void updateInputFourth(int id, String newValue);

    @Query("UPDATE survey_diameter SET tvInputFifth = :newValue WHERE id = :id")
    void updateInputFifth(int id, String newValue);

    @Query("UPDATE survey_diameter SET tvInputSixth = :newValue WHERE id = :id")
    void updateInputSixth(int id, String newValue);







    // update query 관경

    @Query("UPDATE survey_diameter SET tvSceneryFirst = :newValue WHERE id = :id")
    void updateSceneryFirst(int id, String newValue);

    @Query("UPDATE survey_diameter SET tvScenerySecond = :newValue WHERE id = :id")
    void updateScenerySecond(int id, String newValue);

    @Query("UPDATE survey_diameter SET tvSceneryThird = :newValue WHERE id = :id")
    void updateSceneryThird(int id, String newValue);

    @Query("UPDATE survey_diameter SET tvSceneryFourth = :newValue WHERE id = :id")
    void updateSceneryFourth(int id, String newValue);

    @Query("UPDATE survey_diameter SET tvSceneryFifth = :newValue WHERE id = :id")
    void updateSceneryFifth(int id, String newValue);

    @Query("UPDATE survey_diameter SET tvScenerySixth = :newValue WHERE id = :id")
    void updateScenerySixth(int id, String newValue);




    // update query 재질
    @Query("UPDATE survey_diameter SET etPipMaterialFirst = :newValue WHERE id = :id")
    void updatePipFirst(int id, String newValue);

    @Query("UPDATE survey_diameter SET etPipMaterialSecond = :newValue WHERE id = :id")
    void updatePipSecond(int id, String newValue);

    @Query("UPDATE survey_diameter SET etPipMaterialThird = :newValue WHERE id = :id")
    void updatePipThird(int id, String newValue);

    @Query("UPDATE survey_diameter SET etPipMaterialFourth = :newValue WHERE id = :id")
    void updatePipFourth(int id, String newValue);

    @Query("UPDATE survey_diameter SET etPipMaterialFifth = :newValue WHERE id = :id")
    void updatePipFifth(int id, String newValue);

    @Query("UPDATE survey_diameter SET etPipMaterialSixth = :newValue WHERE id = :id")
    void updatePipSixth(int id, String newValue);




    // update query 두께
    @Query("UPDATE survey_diameter SET thicknessFirst = :newValue WHERE id = :id")
    void updateThicknessFirst(int id, String newValue);

    @Query("UPDATE survey_diameter SET thicknessSecond = :newValue WHERE id = :id")
    void updateThicknessSecond(int id, String newValue);

    @Query("UPDATE survey_diameter SET thicknessThird = :newValue WHERE id = :id")
    void updateThicknessThird(int id, String newValue);

    @Query("UPDATE survey_diameter SET thicknessFourth = :newValue WHERE id = :id")
    void updateThicknessFourth(int id, String newValue);

    @Query("UPDATE survey_diameter SET thicknessFifth = :newValue WHERE id = :id")
    void updateThicknessFifth(int id, String newValue);

    @Query("UPDATE survey_diameter SET thicknessSixth = :newValue WHERE id = :id")
    void updateThicknessSixth(int id, String newValue);

}

package com.terra.terradisto.ui.survey_diameter.model;

public class SurveyResult {
    public int id;
    public String mapNumber;        // 도엽 번호
    public String manholType;       // 맨홀 타입 (1개, 2개, 3개, 4개)

    // === 관경 (Scenery) 데이터 필드 (4개) ===
    private String tvSceneryFirst;  // 1번 관경
    private String tvScenerySecond; // 2번 관경
    private String tvSceneryThird;  // 3번 관경
    private String tvSceneryFourth; // 4번 관경

    // === 재질 (Pipe Material) 데이터 필드 (4개) ===
    private String etPipMaterialFirst; // 1번 재질
    private String etPipMaterialSecond; // 2번 재질
    private String etPipMaterialThird;  // 3번 재질
    private String etPipMaterialFourth; // 4번 재질


    public SurveyResult(String mapNumber, String manholType, String tvSceneryFirst, String tvScenerySecond, String tvSceneryThird, String tvSceneryFourth, String etPipMaterialFirst, String etPipMaterialSecond, String etPipMaterialThird, String etPipMaterialFourth) {
        this.mapNumber = mapNumber;
        this.manholType = manholType;
        this.tvSceneryFirst = tvSceneryFirst;
        this.tvScenerySecond = tvScenerySecond;
        this.tvSceneryThird = tvSceneryThird;
        this.tvSceneryFourth = tvSceneryFourth;
        this.etPipMaterialFirst = etPipMaterialFirst;
        this.etPipMaterialSecond = etPipMaterialSecond;
        this.etPipMaterialThird = etPipMaterialThird;
        this.etPipMaterialFourth = etPipMaterialFourth;
    }
}

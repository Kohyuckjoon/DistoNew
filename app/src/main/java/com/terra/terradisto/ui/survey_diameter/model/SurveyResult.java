package com.terra.terradisto.ui.survey_diameter.model;

import androidx.annotation.NonNull;

public class SurveyResult {
    public int id;
    public String mapNumber;        // 도엽 번호
    public String manholType;       // 맨홀 타입 (1개, 2개, 3개, 4개)

    // === 관경 측정치 (Scenery) 데이터 필드 (4개) ===
    private String tvSceneryFirst;  // 1번 관경
    private String tvScenerySecond; // 2번 관경
    private String tvSceneryThird;  // 3번 관경
    private String tvSceneryFourth; // 4번 관경

    // === 재질 선택 (Pipe Material) 데이터 필드 (4개) ===
    private String etPipMaterialFirst; // 1번 재질
    private String etPipMaterialSecond; // 2번 재질
    private String etPipMaterialThird;  // 3번 재질
    private String etPipMaterialFourth; // 4번 재질

    // === 수기 입력치 (Input) 데이터 필드 (4개) ===
    private String etInputFirst; // 1번 수기 입력
    private String etInputSecond; // 2번 수기 입력
    private String etInputThird;  // 3번 수기 입력
    private String etInputFourth; // 4번 수기 입력


    public SurveyResult(String mapNumber, String manholType, String tvSceneryFirst, String tvScenerySecond, String tvSceneryThird, String tvSceneryFourth, String etPipMaterialFirst, String etPipMaterialSecond, String etPipMaterialThird, String etPipMaterialFourth, String etInputFirst, String etInputSecond, String etInputThird, String etInputFourth) {
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
        this.etInputFirst = etInputFirst;
        this.etInputSecond = etInputSecond;
        this.etInputThird = etInputThird;
        this.etInputFourth = etInputFourth;
    }

    // Room 또는 Deserialization을 위해 기본 생성자 추가 권장
    public SurveyResult() {
    }

    // -----------------------------------------------------------------
    // Getter/Setter (Null-Safe 로직 적용)
    // -----------------------------------------------------------------

    @NonNull
    public String getMapNumber() {
        return mapNumber != null ? mapNumber : "";
    }

    public void setMapNumber(String mapNumber) {
        this.mapNumber = mapNumber;
    }

    @NonNull
    public String getManholType() {
        return manholType != null ? manholType : "";
    }

    public void setManholType(String manholType) {
        this.manholType = manholType;
    }

    @NonNull
    public String getTvSceneryFirst() {
        return tvSceneryFirst != null ? tvSceneryFirst : "";
    }

    public void setTvSceneryFirst(String tvSceneryFirst) {
        this.tvSceneryFirst = tvSceneryFirst;
    }

    @NonNull
    public String getTvScenerySecond() {
        return tvScenerySecond != null ? tvScenerySecond : "";
    }

    public void setTvScenerySecond(String tvScenerySecond) {
        this.tvScenerySecond = tvScenerySecond;
    }

    @NonNull
    public String getTvSceneryThird() {
        return tvSceneryThird != null ? tvSceneryThird : "";
    }

    public void setTvSceneryThird(String tvSceneryThird) {
        this.tvSceneryThird = tvSceneryThird;
    }

    @NonNull
    public String getTvSceneryFourth() {
        return tvSceneryFourth != null ? tvSceneryFourth : "";
    }

    public void setTvSceneryFourth(String tvSceneryFourth) {
        this.tvSceneryFourth = tvSceneryFourth;
    }

    @NonNull
    public String getEtPipMaterialFirst() {
        return etPipMaterialFirst != null ? etPipMaterialFirst : "";
    }

    public void setEtPipMaterialFirst(String etPipMaterialFirst) {
        this.etPipMaterialFirst = etPipMaterialFirst;
    }

    @NonNull
    public String getEtPipMaterialSecond() {
        return etPipMaterialSecond != null ? etPipMaterialSecond : "";
    }

    public void setEtPipMaterialSecond(String etPipMaterialSecond) {
        this.etPipMaterialSecond = etPipMaterialSecond;
    }

    @NonNull
    public String getEtPipMaterialThird() {
        return etPipMaterialThird != null ? etPipMaterialThird : "";
    }

    public void setEtPipMaterialThird(String etPipMaterialThird) {
        this.etPipMaterialThird = etPipMaterialThird;
    }

    @NonNull
    public String getEtPipMaterialFourth() {
        return etPipMaterialFourth != null ? etPipMaterialFourth : "";
    }

    public void setEtPipMaterialFourth(String etPipMaterialFourth) {
        this.etPipMaterialFourth = etPipMaterialFourth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEtInputFirst() {
        return etInputFirst;
    }

    public void setEtInputFirst(String etInputFirst) {
        this.etInputFirst = etInputFirst;
    }

    public String getEtInputSecond() {
        return etInputSecond;
    }

    public void setEtInputSecond(String etInputSecond) {
        this.etInputSecond = etInputSecond;
    }

    public String getEtInputThird() {
        return etInputThird;
    }

    public void setEtInputThird(String etInputThird) {
        this.etInputThird = etInputThird;
    }

    public String getEtInputFourth() {
        return etInputFourth;
    }

    public void setEtInputFourth(String etInputFourth) {
        this.etInputFourth = etInputFourth;
    }
}
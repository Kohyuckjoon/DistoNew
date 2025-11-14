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
    private String tvSceneryFifth; // 5번 관경
    private String tvScenerySixth; // 6번 관경

    // === 재질 선택 (Pipe Material) 데이터 필드 (4개) ===
    private String etPipMaterialFirst; // 1번 재질
    private String etPipMaterialSecond; // 2번 재질
    private String etPipMaterialThird;  // 3번 재질
    private String etPipMaterialFourth; // 4번 재질
    private String etPipMaterialFifth; // 5번 재질
    private String etPipMaterialSixth; // 6번 재질

    // === 수기 입력치 (Input) 데이터 필드 (4개) ===
    private String tvInputFirst; // 1번 수기 입력
    private String tvInputSecond; // 2번 수기 입력
    private String tvInputThird;  // 3번 수기 입력
    private String tvInputFourth; // 4번 수기 입력
    private String tvInputFifth; // 5번 수기 입력
    private String tvInputSixth; // 6번 수기 입력

    // === 비고 입력치 (Note) 데이터 필드 (4개) ===
    private String thicknessFirst; // 1번 비고 입력
    private String thicknessSecond; // 2번 비고 입력
    private String thicknessThird; // 3번 비고 입력
    private String thicknessFourth; // 4번 비고 입력
    private String thicknessFifth; // 5번 비고 입력
    private String thicknessSixth; // 6번 비고 입력

    public SurveyResult(int id, String mapNumber, String manholType, String tvSceneryFirst, String tvScenerySecond, String tvSceneryThird, String tvSceneryFourth, String tvSceneryFifth, String tvScenerySixth, String etPipMaterialFirst, String etPipMaterialSecond, String etPipMaterialThird, String etPipMaterialFourth, String etPipMaterialFifth, String etPipMaterialSixth, String tvInputFirst, String tvInputSecond, String tvInputThird, String tvInputFourth, String tvInputFifth, String tvInputSixth, String thicknessFirst, String thicknessSecond, String thicknessThird, String thicknessFourth, String thicknessFifth, String thicknessSixth) {
        this.id = id;
        this.mapNumber = mapNumber;
        this.manholType = manholType;
        this.tvSceneryFirst = tvSceneryFirst;
        this.tvScenerySecond = tvScenerySecond;
        this.tvSceneryThird = tvSceneryThird;
        this.tvSceneryFourth = tvSceneryFourth;
        this.tvSceneryFifth = tvSceneryFifth;
        this.tvScenerySixth = tvScenerySixth;
        this.etPipMaterialFirst = etPipMaterialFirst;
        this.etPipMaterialSecond = etPipMaterialSecond;
        this.etPipMaterialThird = etPipMaterialThird;
        this.etPipMaterialFourth = etPipMaterialFourth;
        this.etPipMaterialFifth = etPipMaterialFifth;
        this.etPipMaterialSixth = etPipMaterialSixth;
        this.tvInputFirst = tvInputFirst;
        this.tvInputSecond = tvInputSecond;
        this.tvInputThird = tvInputThird;
        this.tvInputFourth = tvInputFourth;
        this.tvInputFifth = tvInputFifth;
        this.tvInputSixth = tvInputSixth;
        this.thicknessFirst = thicknessFirst;
        this.thicknessSecond = thicknessSecond;
        this.thicknessThird = thicknessThird;
        this.thicknessFourth = thicknessFourth;
        this.thicknessFifth = thicknessFifth;
        this.thicknessSixth = thicknessSixth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMapNumber() {
        return mapNumber;
    }

    public void setMapNumber(String mapNumber) {
        this.mapNumber = mapNumber;
    }

    public String getManholType() {
        return manholType;
    }

    public void setManholType(String manholType) {
        this.manholType = manholType;
    }

    public String getTvSceneryFirst() {
        return tvSceneryFirst;
    }

    public void setTvSceneryFirst(String tvSceneryFirst) {
        this.tvSceneryFirst = tvSceneryFirst;
    }

    public String getTvScenerySecond() {
        return tvScenerySecond;
    }

    public void setTvScenerySecond(String tvScenerySecond) {
        this.tvScenerySecond = tvScenerySecond;
    }

    public String getTvSceneryThird() {
        return tvSceneryThird;
    }

    public void setTvSceneryThird(String tvSceneryThird) {
        this.tvSceneryThird = tvSceneryThird;
    }

    public String getTvSceneryFourth() {
        return tvSceneryFourth;
    }

    public void setTvSceneryFourth(String tvSceneryFourth) {
        this.tvSceneryFourth = tvSceneryFourth;
    }

    public String getTvSceneryFifth() {
        return tvSceneryFifth;
    }

    public void setTvSceneryFifth(String tvSceneryFifth) {
        this.tvSceneryFifth = tvSceneryFifth;
    }

    public String getTvScenerySixth() {
        return tvScenerySixth;
    }

    public void setTvScenerySixth(String tvScenerySixth) {
        this.tvScenerySixth = tvScenerySixth;
    }

    public String getEtPipMaterialFirst() {
        return etPipMaterialFirst;
    }

    public void setEtPipMaterialFirst(String etPipMaterialFirst) {
        this.etPipMaterialFirst = etPipMaterialFirst;
    }

    public String getEtPipMaterialSecond() {
        return etPipMaterialSecond;
    }

    public void setEtPipMaterialSecond(String etPipMaterialSecond) {
        this.etPipMaterialSecond = etPipMaterialSecond;
    }

    public String getEtPipMaterialThird() {
        return etPipMaterialThird;
    }

    public void setEtPipMaterialThird(String etPipMaterialThird) {
        this.etPipMaterialThird = etPipMaterialThird;
    }

    public String getEtPipMaterialFourth() {
        return etPipMaterialFourth;
    }

    public void setEtPipMaterialFourth(String etPipMaterialFourth) {
        this.etPipMaterialFourth = etPipMaterialFourth;
    }

    public String getEtPipMaterialFifth() {
        return etPipMaterialFifth;
    }

    public void setEtPipMaterialFifth(String etPipMaterialFifth) {
        this.etPipMaterialFifth = etPipMaterialFifth;
    }

    public String getEtPipMaterialSixth() {
        return etPipMaterialSixth;
    }

    public void setEtPipMaterialSixth(String etPipMaterialSixth) {
        this.etPipMaterialSixth = etPipMaterialSixth;
    }

    public String getTvInputFirst() {
        return tvInputFirst;
    }

    public void setTvInputFirst(String tvInputFirst) {
        this.tvInputFirst = tvInputFirst;
    }

    public String getTvInputSecond() {
        return tvInputSecond;
    }

    public void setTvInputSecond(String tvInputSecond) {
        this.tvInputSecond = tvInputSecond;
    }

    public String getTvInputThird() {
        return tvInputThird;
    }

    public void setTvInputThird(String tvInputThird) {
        this.tvInputThird = tvInputThird;
    }

    public String getTvInputFourth() {
        return tvInputFourth;
    }

    public void setTvInputFourth(String tvInputFourth) {
        this.tvInputFourth = tvInputFourth;
    }

    public String getTvInputFifth() {
        return tvInputFifth;
    }

    public void setTvInputFifth(String tvInputFifth) {
        this.tvInputFifth = tvInputFifth;
    }

    public String getTvInputSixth() {
        return tvInputSixth;
    }

    public void setTvInputSixth(String tvInputSixth) {
        this.tvInputSixth = tvInputSixth;
    }

    public String getThicknessFirst() {
        return thicknessFirst;
    }

    public void setThicknessFirst(String thicknessFirst) {
        this.thicknessFirst = thicknessFirst;
    }

    public String getThicknessSecond() {
        return thicknessSecond;
    }

    public void setThicknessSecond(String thicknessSecond) {
        this.thicknessSecond = thicknessSecond;
    }

    public String getThicknessThird() {
        return thicknessThird;
    }

    public void setThicknessThird(String thicknessThird) {
        this.thicknessThird = thicknessThird;
    }

    public String getThicknessFourth() {
        return thicknessFourth;
    }

    public void setThicknessFourth(String thicknessFourth) {
        this.thicknessFourth = thicknessFourth;
    }

    public String getThicknessFifth() {
        return thicknessFifth;
    }

    public void setThicknessFifth(String thicknessFifth) {
        this.thicknessFifth = thicknessFifth;
    }

    public String getThicknessSixth() {
        return thicknessSixth;
    }

    public void setThicknessSixth(String thicknessSixth) {
        this.thicknessSixth = thicknessSixth;
    }

    @Override
    public String toString() {
        return "SurveyResult{" +
                "id=" + id +
                ", mapNumber='" + mapNumber + '\'' +
                ", manholType='" + manholType + '\'' +
                ", tvSceneryFirst='" + tvSceneryFirst + '\'' +
                ", tvScenerySecond='" + tvScenerySecond + '\'' +
                ", tvSceneryThird='" + tvSceneryThird + '\'' +
                ", tvSceneryFourth='" + tvSceneryFourth + '\'' +
                ", tvSceneryFifth='" + tvSceneryFifth + '\'' +
                ", tvScenerySixth='" + tvScenerySixth + '\'' +
                ", etPipMaterialFirst='" + etPipMaterialFirst + '\'' +
                ", etPipMaterialSecond='" + etPipMaterialSecond + '\'' +
                ", etPipMaterialThird='" + etPipMaterialThird + '\'' +
                ", etPipMaterialFourth='" + etPipMaterialFourth + '\'' +
                ", etPipMaterialFifth='" + etPipMaterialFifth + '\'' +
                ", etPipMaterialSixth='" + etPipMaterialSixth + '\'' +
                ", tvInputFirst='" + tvInputFirst + '\'' +
                ", tvInputSecond='" + tvInputSecond + '\'' +
                ", tvInputThird='" + tvInputThird + '\'' +
                ", tvInputFourth='" + tvInputFourth + '\'' +
                ", tvInputFifth='" + tvInputFifth + '\'' +
                ", tvInputSixth='" + tvInputSixth + '\'' +
                ", thicknessFirst='" + thicknessFirst + '\'' +
                ", thicknessSecond='" + thicknessSecond + '\'' +
                ", thicknessThird='" + thicknessThird + '\'' +
                ", thicknessFourth='" + thicknessFourth + '\'' +
                ", thicknessFifth='" + thicknessFifth + '\'' +
                ", thicknessSixth='" + thicknessSixth + '\'' +
                '}';
    }
}
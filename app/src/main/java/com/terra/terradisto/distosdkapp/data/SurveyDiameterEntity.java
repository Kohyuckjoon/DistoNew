package com.terra.terradisto.distosdkapp.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "survey_diameter")
public class SurveyDiameterEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    public int projectId;

    // === 상위 데이터 필드 ===
    private String mapNumber;       // 도엽 번호
    private String manholType;      // 맨홀 타입 (1개, 2개, 3개, 4개)

    // === 관경 (Scenery) 데이터 필드 (4개) ===
    private String tvSceneryFirst;  // 1번 관경
    private String tvScenerySecond; // 2번 관경
    private String tvSceneryThird;  // 3번 관경
    private String tvSceneryFourth; // 4번 관경

    // === 수기 입력값 (Scenery) 데이터 필드 (4개) ===
    private String etInputFirst;  // 1번 관경
    private String etInputSecond; // 2번 관경
    private String etInputThird;  // 3번 관경
    private String etInputFourth; // 4번 관경

    // === 재질 (Pipe Material) 데이터 필드 (4개) ===
    private String etPipMaterialFirst; // 1번 재질
    private String etPipMaterialSecond; // 2번 재질
    private String etPipMaterialThird;  // 3번 재질
    private String etPipMaterialFourth; // 4번 재질

    public SurveyDiameterEntity(int projectId, String mapNumber, String manholType, String tvSceneryFirst, String tvScenerySecond, String tvSceneryThird, String tvSceneryFourth, String etInputFirst, String etInputSecond, String etInputThird, String etInputFourth, String etPipMaterialFirst, String etPipMaterialSecond, String etPipMaterialThird, String etPipMaterialFourth) {
        this.projectId = projectId;
        this.mapNumber = mapNumber;
        this.manholType = manholType;
        this.tvSceneryFirst = tvSceneryFirst;
        this.tvScenerySecond = tvScenerySecond;
        this.tvSceneryThird = tvSceneryThird;
        this.tvSceneryFourth = tvSceneryFourth;
        this.etInputFirst = etInputFirst;
        this.etInputSecond = etInputSecond;
        this.etInputThird = etInputThird;
        this.etInputFourth = etInputFourth;
        this.etPipMaterialFirst = etPipMaterialFirst;
        this.etPipMaterialSecond = etPipMaterialSecond;
        this.etPipMaterialThird = etPipMaterialThird;
        this.etPipMaterialFourth = etPipMaterialFourth;
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

    @Override
    public String toString() {
        return "SurveyDiameterEntity{" +
                "mapNumber='" + mapNumber + '\'' +
                ", manholType='" + manholType + '\'' +
                ", tvSceneryFirst='" + tvSceneryFirst + '\'' +
                ", tvScenerySecond='" + tvScenerySecond + '\'' +
                ", tvSceneryThird='" + tvSceneryThird + '\'' +
                ", tvSceneryFourth='" + tvSceneryFourth + '\'' +
                ", etInputFirst='" + etInputFirst + '\'' +
                ", etInputSecond='" + etInputSecond + '\'' +
                ", etInputThird='" + etInputThird + '\'' +
                ", etInputFourth='" + etInputFourth + '\'' +
                ", etPipMaterialFirst='" + etPipMaterialFirst + '\'' +
                ", etPipMaterialSecond='" + etPipMaterialSecond + '\'' +
                ", etPipMaterialThird='" + etPipMaterialThird + '\'' +
                ", etPipMaterialFourth='" + etPipMaterialFourth + '\'' +
                '}';
    }
}

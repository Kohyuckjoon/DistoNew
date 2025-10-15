package com.terra.terradisto.distosdkapp.data;

import java.io.Serializable;

public class SurveyDiameterData implements Serializable {
    // @PrimaryKey(autoGenerate = true)
    // private int id;

    // === 상위 데이터 필드 ===
    private String mapNumber;       // 도엽 번호
    private String manholType;      // 맨홀 타입 (1개, 2개, 3개, 4개)

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

    // === 평면 및 심도 필드 (4개씩 필요하다면 배열로 처리하거나, 1개씩 필요하다면 그대로 유지) ===
    private String planeFirst;
    private String planeSecond;
    private String planeThird;
    private String planeFourth;

    private String depthFirst;
    private String depthSecond;
    private String depthThird;
    private String depthFourth;

    /**
     *
     * @param mapNumber
     * @param manholType
     * @param tvSceneryFirst
     * @param tvScenerySecond
     * @param tvSceneryThird
     * @param tvSceneryFourth
     * @param etPipMaterialFirst
     * @param etPipMaterialSecond
     * @param etPipMaterialThird
     * @param etPipMaterialFourth
     *
     * String planeFirst, String planeSecond, String planeThird, String planeFourth,
     *                               String depthFirst, String depthSecond, String depthThird, String depthFourth
     */

    // 생성자 (필수 데이터만 포함)
    public SurveyDiameterData(String mapNumber, String manholType,
                              String tvSceneryFirst, String tvScenerySecond, String tvSceneryThird, String tvSceneryFourth,
                              String etPipMaterialFirst, String etPipMaterialSecond, String etPipMaterialThird, String etPipMaterialFourth
                              ) {
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

//        this.planeFirst = planeFirst;
//        this.planeSecond = planeSecond;
//        this.planeThird = planeThird;
//        this.planeFourth = planeFourth;
//
//        this.depthFirst = depthFirst;
//        this.depthSecond = depthSecond;
//        this.depthThird = depthThird;
//        this.depthFourth = depthFourth;
    }

    // === Getters and Setters ===

    // 도엽번호
    public String getMapNumber() { return mapNumber; }
    public void setMapNumber(String mapNumber) { this.mapNumber = mapNumber; }

    // 맨홀 타입
    public String getManholType() { return manholType; }
    public void setManholType(String manholType) { this.manholType = manholType; }

    // 관경 (1~4)
    public String getTvSceneryFirst() { return tvSceneryFirst; }
    public void setTvSceneryFirst(String tvSceneryFirst) { this.tvSceneryFirst = tvSceneryFirst; }

    public String getTvScenerySecond() { return tvScenerySecond; }
    public void setTvScenerySecond(String tvScenerySecond) { this.tvScenerySecond = tvScenerySecond; }

    public String getTvSceneryThird() { return tvSceneryThird; }

    public void setTvSceneryThird(String tvSceneryThird) { this.tvSceneryThird = tvSceneryThird; }

    public String getTvSceneryFourth() { return tvSceneryFourth; }

    public void setTvSceneryFourth(String tvSceneryFourth) { this.tvSceneryFourth = tvSceneryFourth; }

    // 재질 (1~4)
    public String getEtPipMaterialFirst() { return etPipMaterialFirst; }
    public void setEtPipMaterialFirst(String etPipMaterialFirst) { this.etPipMaterialFirst = etPipMaterialFirst; }
    // (이하 Second, Third, Fourth 게터/세터 생략)

    // 평면 (1~4)
    public String getPlaneFirst() { return planeFirst; }
    public void setPlaneFirst(String planeFirst) { this.planeFirst = planeFirst; }
    // (이하 Second, Third, Fourth 게터/세터 생략)

    // 심도 (1~4)
    public String getDepthFirst() { return depthFirst; }
    public void setDepthFirst(String depthFirst) { this.depthFirst = depthFirst; }
    // (이하 Second, Third, Fourth 게터/세터 생략)

    @Override
    public String toString() {
        return "SurveyDiameterData{" +
                "mapNumber='" + mapNumber + '\'' +
                ", manholType='" + manholType + '\'' +
                ", tvSceneryFirst='" + tvSceneryFirst + '\'' +
                ", tvScenerySecond='" + tvScenerySecond + '\'' +
                ", tvSceneryThird='" + tvSceneryThird + '\'' +
                ", tvSceneryFourth='" + tvSceneryFourth + '\'' +
                ", etPipMaterialFirst='" + etPipMaterialFirst + '\'' +
                ", etPipMaterialSecond='" + etPipMaterialSecond + '\'' +
                ", etPipMaterialThird='" + etPipMaterialThird + '\'' +
                ", etPipMaterialFourth='" + etPipMaterialFourth + '\'' +
                '}';
    }

    //    public SurveyDiameterData(String manholType, String distance, String pipeMaterial) {
//        this.manholType = manholType;
//        this.distance = distance;
//        this.pipeMaterial = pipeMaterial;
//    }
//
//    public String getManholType() {
//        return manholType;
//    }
//
//    public String getDistance() {
//        return distance;
//    }
//
//    public String getPipeMaterial() {
//        return pipeMaterial;
//    }
//
//    @Override
//    public String toString() {
//        return "SurveyDiameterData{" +
//                "manholType='" + manholType + '\'' +
//                ", distance='" + distance + '\'' +
//                ", pipeMaterial='" + pipeMaterial + '\'' +
//                '}';
//    }
}

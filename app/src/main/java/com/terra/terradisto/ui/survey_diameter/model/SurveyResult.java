package com.terra.terradisto.ui.survey_diameter.model;

public class SurveyResult {
    public int id;
    public String manholType;
    public double distance;
    public String pipMaterial;

    public SurveyResult(int id, String manholType, double distance, String pipMaterial) {
        this.id = id;
        this.manholType = manholType;
        this.distance = distance;
        this.pipMaterial = pipMaterial;
    }
}

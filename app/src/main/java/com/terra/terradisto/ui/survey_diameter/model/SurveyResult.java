package com.terra.terradisto.ui.survey_diameter.model;

public class SurveyResult {
    public long id;
    public String manholType;
    public double distance;
    public String pipMaterial;

    public SurveyResult(long id, String manholType, double distance, String pipMaterial) {
        this.id = id;
        this.manholType = manholType;
        this.distance = distance;
        this.pipMaterial = pipMaterial;
    }
}

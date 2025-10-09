package com.terra.terradisto.distosdkapp.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "survey_diameter")
public class SurveyDiameterEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String manholType;
    private String distance;
    private String pipMaterial;

    public SurveyDiameterEntity(String manholType, String distance, String pipMaterial) {
        this.manholType = manholType;
        this.distance = distance;
        this.pipMaterial = pipMaterial;
    }

    public String getManholType() {
        return manholType;
    }

    public void setManholType(String manholType) {
        this.manholType = manholType;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getPipMaterial() {
        return pipMaterial;
    }

    public void setPipMaterial(String pipMaterial) {
        this.pipMaterial = pipMaterial;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

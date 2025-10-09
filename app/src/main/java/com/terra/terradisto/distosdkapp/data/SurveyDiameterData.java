package com.terra.terradisto.distosdkapp.data;

import java.io.Serializable;

public class SurveyDiameterData  implements Serializable {
    private String manholType;
    private String distance;
    private String pipeMaterial;

    public SurveyDiameterData(String manholType, String distance, String pipeMaterial) {
        this.manholType = manholType;
        this.distance = distance;
        this.pipeMaterial = pipeMaterial;
    }

    public String getManholType() {
        return manholType;
    }

    public String getDistance() {
        return distance;
    }

    public String getPipeMaterial() {
        return pipeMaterial;
    }

    @Override
    public String toString() {
        return "SurveyDiameterData{" +
                "manholType='" + manholType + '\'' +
                ", distance='" + distance + '\'' +
                ", pipeMaterial='" + pipeMaterial + '\'' +
                '}';
    }
}

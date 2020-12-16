package com.example.smartsafari;

import com.google.gson.annotations.SerializedName;

public class Directions {
    @SerializedName("y1")
    private Double yLongitude;
    @SerializedName("x1")
    private Double xLatitude;

    public Double getyLongitude() {
        return yLongitude;
    }

    public void setyLongitude(Double yLongitude) {
        this.yLongitude = yLongitude;
    }

    public Double getxLatitude() {
        return xLatitude;
    }

    public void setxLatitude(Double xLatitude) {
        this.xLatitude = xLatitude;
    }

    public Directions(Double yLongitude, Double xLatitude) {
        this.yLongitude = yLongitude;
        this.xLatitude = xLatitude;
    }
}

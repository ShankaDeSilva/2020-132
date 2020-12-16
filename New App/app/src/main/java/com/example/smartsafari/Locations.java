package com.example.smartsafari;

import com.google.gson.annotations.SerializedName;

public class Locations {
    @SerializedName("start longitude")
    private Double sLatitude;
    @SerializedName("start latitude")
    private Double sLongitude;
    @SerializedName("destination longitude")
    private Double dLatitude;
    @SerializedName("destination latitude")
    private Double dLongitude;

    public Locations(Double sLatitude, Double sLongitude, Double dLatitude, Double dLongitude) {
        this.sLatitude = sLatitude;
        this.sLongitude = sLongitude;
        this.dLatitude = dLatitude;
        this.dLongitude = dLongitude;
    }

    public Double getsLatitude() {
        return sLatitude;
    }

    public void setsLatitude(Double sLatitude) {
        this.sLatitude = sLatitude;
    }

    public Double getsLongitude() {
        return sLongitude;
    }

    public void setsLongitude(Double sLongitude) {
        this.sLongitude = sLongitude;
    }

    public Double getdLatitude() {
        return dLatitude;
    }

    public void setdLatitude(Double dLatitude) {
        this.dLatitude = dLatitude;
    }

    public Double getdLongitude() {
        return dLongitude;
    }

    public void setdLongitude(Double dLongitude) {
        this.dLongitude = dLongitude;
    }
}

package com.isprid.smartsafari.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LatLong {
    @SerializedName("x1")
    @Expose
    private Double longitude;
    @SerializedName("y1")
    @Expose
    private Double latitude;
    @SerializedName("x2")
    @Expose
    private Double lastLongitude;
    @SerializedName("y2")
    @Expose
    private Double lastLatitude;
    @SerializedName("cost")
    @Expose
    private Double distance;

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLastLongitude() {
        return lastLongitude;
    }

    public void setLastLongitude(Double lastLongitude) {
        this.lastLongitude = lastLongitude;
    }

    public Double getLastLatitude() {
        return lastLatitude;
    }

    public void setLastLatitude(Double lastLatitude) {
        this.lastLatitude = lastLatitude;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "LatLong{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", lastLongitude=" + lastLongitude +
                ", lastLatitude=" + lastLatitude +
                ", distance=" + distance +
                '}';
    }
}

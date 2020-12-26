package com.isprid.smartsafaridashboard.interfaces;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LatLong {
    @SerializedName("x1")
    @Expose
    private Double longitude;
    @SerializedName("y1")
    @Expose
    private Double latitude;

//    public Double getX1() {
//        return x1;
//    }
//
//    public void setX1(Double x1) {
//        this.x1 = x1;
//    }
//
//    public Double getY1() {
//        return y1;
//    }
//
//    public void setY1(Double y1) {
//        this.y1 = y1;
//    }
//
//    @Override
//    public String toString() {
//        return "LatLong{" +
//                "x1=" + x1 +
//                ", y1=" + y1 +
//                '}';
//    }

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

    @Override
    public String toString() {
        return "LatLong{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}

package com.isprid.smartsafari.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LocDir {

    @SerializedName("start latitude")
    private Double sLatitude;
    @SerializedName("start longitude")
    private Double sLongitude;
    @SerializedName("destination latitude")
    private Double dLatitude;
    @SerializedName("destination longitude")
    private Double dLongitude;

    @SerializedName("LatLong")
    @Expose
    private ArrayList<LatLong> latLong;


    public LocDir(Double sLatitude, Double sLongitude, Double dLatitude, Double dLongitude, ArrayList<LatLong> latLong) {
        this.sLatitude = sLatitude;
        this.sLongitude = sLongitude;
        this.dLatitude = dLatitude;
        this.dLongitude = dLongitude;
        this.latLong = latLong;
    }

    public LocDir(Double sLatitude, Double sLongitude, Double dLatitude, Double dLongitude) {
        this.sLatitude = sLatitude;
        this.sLongitude = sLongitude;
        this.dLatitude = dLatitude;
        this.dLongitude = dLongitude;
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

    public ArrayList<LatLong> getLatLong() {
        return latLong;
    }

    public void setLatLong(ArrayList<LatLong> latLong) {
        this.latLong = latLong;
    }

    @Override
    public String toString() {
        return "LocDir{" +
                "sLatitude=" + sLatitude +
                ", sLongitude=" + sLongitude +
                ", dLatitude=" + dLatitude +
                ", dLongitude=" + dLongitude +
                ", latLong=" + latLong +
                '}';
    }
}

package com.example.tsetmap.animalsJSON;

public class bLatLng {
    private Double Latitude;
    private Double Longitude;
    private String name;
    private String description;

    public bLatLng(Double latitude, Double longitude) {
        Latitude = latitude;
        Longitude = longitude;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    @Override
    public String toString() {
        return "bLatLng{" +
                "Latitude=" + Latitude +
                ", Longitude=" + Longitude +
                '}';
    }
}

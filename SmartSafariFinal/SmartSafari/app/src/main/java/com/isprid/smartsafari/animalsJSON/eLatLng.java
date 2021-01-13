package com.isprid.smartsafari.animalsJSON;

public class eLatLng {
    private Double Latitude;
    private Double Longitude;
    private String name;
    private String description;

    public eLatLng(Double latitude, Double longitude) {
        Latitude = latitude;
        Longitude = longitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    @Override
    public String toString() {
        return "eLatLng{" +
                "Latitude=" + Latitude +
                ", Longitude=" + Longitude +
                '}';
    }
}

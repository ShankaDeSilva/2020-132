package com.isprid.smartsafaridashboard.animalsJSON;

public class leLatLng {
    private Double Latitude;
    private Double Longitude;

    public leLatLng(Double latitude, Double longitude) {
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
        return "leLatLng{" +
                "Latitude=" + Latitude +
                ", Longitude=" + Longitude +
                '}';
    }
}

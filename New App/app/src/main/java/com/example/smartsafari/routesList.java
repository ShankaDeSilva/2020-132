package com.example.smartsafari;

public class routesList {
    private Double laitude;
    private Double longitude;

    public routesList(Double laitude, Double longitude) {
        this.laitude = laitude;
        this.longitude = longitude;
    }

    public Double getLaitude() {
        return laitude;
    }

    public void setLaitude(Double laitude) {
        this.laitude = laitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}

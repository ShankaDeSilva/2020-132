package com.isprid.smartsafari.activity;

public class Data {

    String rid;
    String uuid;
    String latitude;
    String longitude;
    String animalName;
    String animalActivity;


    public Data() {
    }

    public Data(String rid, String uuid, String latitude, String longitude, String animalName, String animalActivity) {
        this.rid = rid;
        this.uuid = uuid;
        this.latitude = latitude;
        this.longitude = longitude;
        this.animalName = animalName;
        this.animalActivity = animalActivity;
    }

    public Data(String uuid, String latitude, String longitude, String animalName, String animalActivity) {
        this.uuid = uuid;
        this.latitude = latitude;
        this.longitude = longitude;
        this.animalName = animalName;
        this.animalActivity = animalActivity;
    }

    public String getRid() {
        return rid;
    }

    public String getUuid() {
        return uuid;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getAnimalName() {
        return animalName;
    }

    public String getAnimalActivity() {
        return animalActivity;
    }
}


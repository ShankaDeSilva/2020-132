package com.example.smartsafari;

import com.google.gson.annotations.SerializedName;

public class Routing {
    @SerializedName("animalId")
    private Integer id;

    @SerializedName("startLat")
    private Float startLat;

    @SerializedName("StartLon")
    private Float StartLon;

    @SerializedName("EndLat")
    private Float endLat;

    @SerializedName("EndLon")
    private Float endLon;


    public Routing(Integer id, Float startLat, Float startLon, Float endLat, Float endLon) {
        this.id = id;
        this.startLat = startLat;
        this.StartLon = startLon;
        this.endLat = endLat;
        this.endLon = endLon;
    }

    public Integer getId() {
        return id;
    }

    public Float getStartLat() {
        return startLat;
    }

    public Float getStartLon() {
        return StartLon;
    }

    public Float getEndLat() {
        return endLat;
    }

    public Float getEndLon() {
        return endLon;
    }
}

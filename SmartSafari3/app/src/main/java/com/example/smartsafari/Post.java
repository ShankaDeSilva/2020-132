package com.example.smartsafari;

import com.google.gson.annotations.SerializedName;

public class Post {

    private int userId;

    private int id;

    private String title;

    @SerializedName("body")
    private String text;

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
//    @SerializedName("body")
//    private float lat;
//
//    @SerializedName("Body")
//    private float lon;
//
//    @SerializedName("Body")
//    private float destinationLat;
//
//    @SerializedName("Body")
//    private float destinationLon;

//    public int getId() {
//        return id;
//    }
//
//    public float getLat() {
//        return lat;
//    }
//
//    public float getLon() {
//        return lon;
//    }
//
//    public float getDestinationLat() {
//        return destinationLat;
//    }
//
//    public float getDestinationLon() {
//        return destinationLon;
//    }
}

package com.example.smartsafari;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LatLong {
    @SerializedName("LatLong")
    private List<Directions> directionsList;

    public List<Directions> getDirectionsList() {
        return directionsList;
    }

    public void setDirectionsList(List<Directions> directionsList) {
        this.directionsList = directionsList;
    }

    public LatLong(List<Directions> directionsList) {
        this.directionsList = directionsList;
    }
}

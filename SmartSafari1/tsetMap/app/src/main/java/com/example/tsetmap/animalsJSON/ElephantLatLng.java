package com.example.tsetmap.animalsJSON;

import java.util.ArrayList;

public class ElephantLatLng {
    private ArrayList<eLatLng> eLatLng;

    public ArrayList<com.example.tsetmap.animalsJSON.eLatLng> geteLatLng() {
        return eLatLng;
    }

    public void seteLatLng(ArrayList<com.example.tsetmap.animalsJSON.eLatLng> eLatLng) {
        this.eLatLng = eLatLng;
    }

    @Override
    public String toString() {
        return "ElephantLatLng{" +
                "eLatLng=" + eLatLng +
                '}';
    }
}

package com.isprid.smartsafari.animalsJSON;

import java.util.ArrayList;

public class ElephantLatLng {
    private ArrayList<eLatLng> eLatLng;

    public ArrayList<com.isprid.smartsafari.animalsJSON.eLatLng> geteLatLng() {
        return eLatLng;
    }

    public void seteLatLng(ArrayList<com.isprid.smartsafari.animalsJSON.eLatLng> eLatLng) {
        this.eLatLng = eLatLng;
    }

    @Override
    public String toString() {
        return "ElephantLatLng{" +
                "eLatLng=" + eLatLng +
                '}';
    }
}

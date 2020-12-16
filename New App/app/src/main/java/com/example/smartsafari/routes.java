package com.example.smartsafari;

import java.util.List;

public class routes {

//    private int count;
    private List<routesList> routesLists;

    public routes(List<routesList> routesLists) {
        this.routesLists = routesLists;
    }

    public List<routesList> getRoutesLists() {
        return routesLists;
    }

    public void setRoutesLists(List<routesList> routesLists) {
        this.routesLists = routesLists;
    }

    @Override
    public String toString() {
        return "routes{" +
                "routesLists=" + routesLists +
                '}';
    }
}

package com.isprid.smartsafari.model;

public class SliderItem {
    private String description;
    private int image;

    public SliderItem(int image, String description) {
        this.description = description;
        this.image = image;
    }

    public String getDescription() {
        return description;
    }


    public int getImage() {
        return image;
    }

}

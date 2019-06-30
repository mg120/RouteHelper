package com.routehelperr.model;

public class HelpItemModel {

    private int center_image;
    private String center_name;
    private String center_number;

    public HelpItemModel(int center_image, String center_name, String center_number) {
        this.center_image = center_image;
        this.center_name = center_name;
        this.center_number = center_number;
    }


    public int getCenter_image() {
        return center_image;
    }

    public void setCenter_image(int center_image) {
        this.center_image = center_image;
    }

    public String getCenter_name() {
        return center_name;
    }

    public void setCenter_name(String center_name) {
        this.center_name = center_name;
    }

    public String getCenter_number() {
        return center_number;
    }

    public void setCenter_number(String center_number) {
        this.center_number = center_number;
    }
}

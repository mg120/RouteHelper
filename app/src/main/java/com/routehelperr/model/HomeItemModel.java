package com.routehelperr.model;

public class HomeItemModel {

    private String item_image;
    private String item_title;

    public HomeItemModel(String item_image, String item_title) {
        this.item_image = item_image;
        this.item_title = item_title;
    }

    public String getItem_image() {
        return item_image;
    }

    public void setItem_image(String item_image) {
        this.item_image = item_image;
    }

    public String getItem_title() {
        return item_title;
    }

    public void setItem_title(String item_title) {
        this.item_title = item_title;
    }
}

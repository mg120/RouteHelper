package com.routehelperr.model;

public class NavItemModel {

    private int title;
    private int image;

    public NavItemModel(int title, int image) {
        this.title = title;
        this.image = image;
    }


    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}

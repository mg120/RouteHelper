package com.routehelperr.model;

import com.google.gson.annotations.SerializedName;

public class MakeOrderModel {
    @SerializedName("success")
    private Boolean success;
    @SerializedName("data")
    private String data;

    public Boolean getSuccess() {
        return success;
    }

    public String getData() {
        return data;
    }
}
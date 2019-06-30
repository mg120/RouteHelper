package com.routehelperr.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpdateLocationModel {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("message")
    @Expose
    private List<updateData> message;

    // Getter
    public Boolean getSuccess() {
        return success;
    }

    public String getData() {
        return data;
    }


    public List<updateData> getMessage() {
        return message;
    }

    // ---------- userData ----------
    public static class updateData {

        @SerializedName("id")
        private int id;
        @SerializedName("lat")
        private String lat;
        @SerializedName("lng")
        private String lng;

        public int getId() {
            return id;
        }

        public String getLat() {
            return lat;
        }

        public String getLng() {
            return lng;
        }
    }
}

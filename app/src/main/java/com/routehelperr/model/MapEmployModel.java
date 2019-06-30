package com.routehelperr.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MapEmployModel {

    @SerializedName("success")
    private Boolean success;
    @SerializedName("data")
    private String data;
    @SerializedName("message")
    private List<Messages> message;

    //Getter
    public Boolean getSuccess() {
        return success;
    }

    public String getData() {
        return data;
    }

    public List<Messages> getMessage() {
        return message;
    }


    // Messages Class
    public static class Messages {
        @SerializedName("id")
        private int id;
        @SerializedName("username")
        private String username;
        @SerializedName("email")
        private String email;
        @SerializedName("phone")
        private String phone;
        @SerializedName("user_type")
        private int user_type;
        @SerializedName("idnumber")
        private String idnumber;
        @SerializedName("startdate")
        private String startdate;
        @SerializedName("enddate")
        private String enddate;
        @SerializedName("paid")
        private int paid;
        @SerializedName("carnumber")
        private String carnumber;
        @SerializedName("carmodal")
        private String carmodal;
        @SerializedName("cartype")
        private String cartype;
        @SerializedName("suspensed")
        private int suspensed;
        @SerializedName("user_hash")
        private String user_hash;
        @SerializedName("lat")
        private String lat;
        @SerializedName("lng")
        private String lng;


        // Getter
        public int getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone() {
            return phone;
        }

        public int getUser_type() {
            return user_type;
        }

        public String getIdnumber() {
            return idnumber;
        }

        public String getStartdate() {
            return startdate;
        }

        public String getEnddate() {
            return enddate;
        }

        public int getPaid() {
            return paid;
        }

        public String getCarnumber() {
            return carnumber;
        }

        public String getCarmodal() {
            return carmodal;
        }

        public String getCartype() {
            return cartype;
        }

        public int getSuspensed() {
            return suspensed;
        }

        public String getUser_hash() {
            return user_hash;
        }

        public String getLat() {
            return lat;
        }

        public String getLng() {
            return lng;
        }
    }
}

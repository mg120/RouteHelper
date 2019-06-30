package com.routehelperr.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LogInResponseModel {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("message")
    @Expose
    private userData message;

    // Getter
    public Boolean getSuccess() {
        return success;
    }

    public String getData() {
        return data;
    }

    public userData getMessage() {
        return message;
    }


    // ---------- userData ----------
    public static class userData {

        @SerializedName("id")
        private int id;
        @SerializedName("username")
        private String username;
        @SerializedName("email")
        private String email;
        @SerializedName("phone")
        private String phone;
        @SerializedName("idnumber")
        private String idnumber;
        @SerializedName("carnumber")
        private String carnumber;
        @SerializedName("carmodal")
        private String carmodal;
        @SerializedName("cartype")
        private String cartype;
        @SerializedName("user_type")
        private String user_type;
        @SerializedName("firebase_token")
        private String firebase_token;
        @SerializedName("user_hash")
        private String user_hash;
        @SerializedName("lat")
        private String lat;
        @SerializedName("lng")
        private String lng;


        // Getter ....
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

        public String getIdnumber() {
            return idnumber;
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

        public String getUser_type() {
            return user_type;
        }

        public String getFirebase_token() {
            return firebase_token;
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

package com.routehelperr.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateProfileModel {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("message")
    @Expose
    private updateData message;

    // Getter
    public Boolean getSuccess() {
        return success;
    }

    public String getData() {
        return data;
    }

    public updateData getMessage() {
        return message;
    }


    // ---------- userData ----------
    public static class updateData {

        @SerializedName("id")
        private int id;
        @SerializedName("username")
        private String username;
        @SerializedName("email")
        private String email;
        @SerializedName("phone")
        private String phone;
        @SerializedName("package")
        private String packages;
        @SerializedName("idnumber")
        private String idnumber;
        @SerializedName("policynumber")
        private String policynumber;
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
        @SerializedName("user_type")
        private String user_type;
        @SerializedName("firebase_token")
        private String firebase_token;
        @SerializedName("forgetcode")
        private String forgetcode;
        @SerializedName("suspensed")
        private int suspensed;
        @SerializedName("user_hash")
        private String user_hash;


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

        public String getPackages() {
            return packages;
        }

        public String getIdnumber() {
            return idnumber;
        }

        public String getPolicynumber() {
            return policynumber;
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

        public String getUser_type() {
            return user_type;
        }

        public String getFirebase_token() {
            return firebase_token;
        }

        public String getForgetcode() {
            return forgetcode;
        }

        public int getSuspensed() {
            return suspensed;
        }

        public String getUser_hash() {
            return user_hash;
        }
    }
}

package com.routehelperr.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable{

    private int id;
    private String username;
    private String email;
    private String phone;
    private String idnumber;
    private String carnumber;
    private String carmodal;
    private String cartype;
    private String user_type;
    private String firebase_token;
    private String user_hash;
    private double lat;
    private double lng;

    public User() {
    }

    public User(int id, String username, String email, String phone, String idnumber, String carnumber, String carmodal, String cartype, String user_type, String firebase_token, String user_hash, double lat, double lng) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.idnumber = idnumber;
        this.carnumber = carnumber;
        this.carmodal = carmodal;
        this.cartype = cartype;
        this.user_type = user_type;
        this.firebase_token = firebase_token;
        this.user_hash = user_hash;
        this.lat = lat;
        this.lng = lng;
    }


    protected User(Parcel in) {
        id = in.readInt();
        username = in.readString();
        email = in.readString();
        phone = in.readString();
        idnumber = in.readString();
        carnumber = in.readString();
        carmodal = in.readString();
        cartype = in.readString();
        user_type = in.readString();
        firebase_token = in.readString();
        user_hash = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getCarnumber() {
        return carnumber;
    }

    public void setCarnumber(String carnumber) {
        this.carnumber = carnumber;
    }

    public String getCarmodal() {
        return carmodal;
    }

    public void setCarmodal(String carmodal) {
        this.carmodal = carmodal;
    }

    public String getCartype() {
        return cartype;
    }

    public void setCartype(String cartype) {
        this.cartype = cartype;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getFirebase_token() {
        return firebase_token;
    }

    public void setFirebase_token(String firebase_token) {
        this.firebase_token = firebase_token;
    }

    public String getUser_hash() {
        return user_hash;
    }

    public void setUser_hash(String user_hash) {
        this.user_hash = user_hash;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public static Creator<User> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(username);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeString(idnumber);
        dest.writeString(carnumber);
        dest.writeString(carmodal);
        dest.writeString(cartype);
        dest.writeString(user_type);
        dest.writeString(firebase_token);
        dest.writeString(user_hash);
        dest.writeDouble(lat);
        dest.writeDouble(lng);
    }
}

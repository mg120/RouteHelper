package com.routehelperr.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InfoModalModel {

    @SerializedName("success")
    private Boolean success;
    @SerializedName("data")
    private String data;
    @SerializedName("message")
    private ModalData message;

    // Getters
    public Boolean getSuccess() {
        return success;
    }

    public String getData() {
        return data;
    }

    public ModalData getMessage() {
        return message;
    }


    // Message Class ...
    public static class ModalData {
        @SerializedName("modals")
        private List<ModalYear> modals;

        //Getters
        public List<ModalYear> getInfo() {
            return modals;
        }
    }

    // INFO Class ...
    public static class ModalYear {

        @SerializedName("modalyear")
        @Expose
        private String modalyear;


        // Getters
        public String getModalyear() {
            return modalyear;
        }
    }
}

package com.routehelperr.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PackagesModel {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("message")
    @Expose
    private MessagePackages messagePackages;

    // Getters
    public Boolean getSuccess() {
        return success;
    }

    public String getData() {
        return data;
    }

    public MessagePackages getMessagePackages() {
        return messagePackages;
    }


    // Message Class ...
    public static class MessagePackages {

        @SerializedName("packages")
        @Expose
        private List<Packages> packages;


        public List<Packages> getPackages() {
            return packages;
        }

    }


    // Packages Class ...
    public static class Packages implements Parcelable{
        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("desc")
        @Expose
        private String desc;

        protected Packages(Parcel in) {
            id = in.readInt();
            title = in.readString();
            desc = in.readString();
        }

        public static final Creator<SettingInfoModel.Packages> CREATOR = new Creator<SettingInfoModel.Packages>() {
            @Override
            public SettingInfoModel.Packages createFromParcel(Parcel in) {
                return new SettingInfoModel.Packages(in);
            }

            @Override
            public SettingInfoModel.Packages[] newArray(int size) {
                return new SettingInfoModel.Packages[size];
            }
        };

        // Getters
        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getDesc() {
            return desc;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(title);
            dest.writeString(desc);
        }
    }

}

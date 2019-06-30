package com.routehelperr.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServicesModel {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("message")
    @Expose
    private MessageData message;

    // Getters
    public Boolean getSuccess() {
        return success;
    }

    public String getData() {
        return data;
    }

    public MessageData getMessage() {
        return message;
    }


    // Message Class ...
    public static class MessageData {
        @SerializedName("services")
        private List<ServiceDataModel> services;

        // Getter
        public List<ServiceDataModel> getServices() {
            return services;
        }
    }


    // ServiceDataModel Class ...
    public static class ServiceDataModel implements Parcelable{
        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("desc")
        @Expose
        private String desc;
        @SerializedName("image")
        @Expose
        private String image;

        protected ServiceDataModel(Parcel in) {
            id = in.readInt();
            title = in.readString();
            desc = in.readString();
            image = in.readString();
        }

        public static final Creator<SettingInfoModel.ServiceModel> CREATOR = new Creator<SettingInfoModel.ServiceModel>() {
            @Override
            public SettingInfoModel.ServiceModel createFromParcel(Parcel in) {
                return new SettingInfoModel.ServiceModel(in);
            }

            @Override
            public SettingInfoModel.ServiceModel[] newArray(int size) {
                return new SettingInfoModel.ServiceModel[size];
            }
        };

        //Getters
        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getDesc() {
            return desc;
        }

        public String getImage() {
            return image;
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
            dest.writeString(image);
        }
    }
}

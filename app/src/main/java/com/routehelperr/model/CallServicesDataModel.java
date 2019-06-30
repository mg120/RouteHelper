package com.routehelperr.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CallServicesDataModel {

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
        @SerializedName("info")
        @Expose
        private List<Info> info;
        @SerializedName("callservices")
        @Expose
        private List<CallServices> callservices;

        //Getters
        public List<Info> getInfo() {
            return info;
        }

        public List<CallServices> getCallservices() {
            return callservices;
        }
    }

    // INFO Class ...
    public static class Info implements Parcelable {

        @SerializedName("about")
        @Expose
        private String about;
        @SerializedName("conditions")
        @Expose
        private String conditions;
        @SerializedName("logo")
        @Expose
        private String logo;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("email2")
        @Expose
        private String email2;
        @SerializedName("facebook")
        @Expose
        private String facebook;
        @SerializedName("instagram")
        @Expose
        private String instagram;
        @SerializedName("twitter")
        @Expose
        private String twitter;
        @SerializedName("google")
        @Expose
        private String google;

        protected Info(Parcel in) {
            about = in.readString();
            conditions = in.readString();
            logo = in.readString();
            email = in.readString();
            email2 = in.readString();
            facebook = in.readString();
            instagram = in.readString();
            twitter = in.readString();
            google = in.readString();
        }

        public static final Creator<SettingInfoModel.Info> CREATOR = new Creator<SettingInfoModel.Info>() {
            @Override
            public SettingInfoModel.Info createFromParcel(Parcel in) {
                return new SettingInfoModel.Info(in);
            }

            @Override
            public SettingInfoModel.Info[] newArray(int size) {
                return new SettingInfoModel.Info[size];
            }
        };

        // Getters
        public String getAbout() {
            return about;
        }

        public String getConditions() {
            return conditions;
        }

        public String getLogo() {
            return logo;
        }

        public String getEmail() {
            return email;
        }

        public String getEmail2() {
            return email2;
        }

        public String getFacebook() {
            return facebook;
        }

        public String getInstagram() {
            return instagram;
        }

        public String getTwitter() {
            return twitter;
        }

        public String getGoogle() {
            return google;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(about);
            dest.writeString(conditions);
            dest.writeString(logo);
            dest.writeString(email);
            dest.writeString(email2);
            dest.writeString(facebook);
            dest.writeString(instagram);
            dest.writeString(twitter);
            dest.writeString(google);
        }
    }

    // Call ServiceDataModel Class ...
    public static class CallServices  implements Parcelable{
        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("phonenumbers")
        @Expose
        private String phonenumbers;


        protected CallServices(Parcel in) {
            id = in.readInt();
            country = in.readString();
            title = in.readString();
            phonenumbers = in.readString();
        }

        public static final Creator<CallServices> CREATOR = new Creator<CallServices>() {
            @Override
            public CallServices createFromParcel(Parcel in) {
                return new CallServices(in);
            }

            @Override
            public CallServices[] newArray(int size) {
                return new CallServices[size];
            }
        };

        //Getters
        public int getId() {
            return id;
        }

        public String getCountry() {
            return country;
        }

        public String getPhonenumbers() {
            return phonenumbers;
        }

        public String getTitle() {
            return title;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(country);
            dest.writeString(title);
            dest.writeString(phonenumbers);
        }
    }
}

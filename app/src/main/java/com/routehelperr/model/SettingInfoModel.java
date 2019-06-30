package com.routehelperr.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SettingInfoModel {

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


    // ----------- Classes ------------------------
    // Message Class ...
    public static class MessageData implements Parcelable{
        @SerializedName("info")
        @Expose
        private List<Info> info;
        @SerializedName("modals")
        @Expose
        private List<Modals> modals;
        @SerializedName("banks")
        @Expose
        private List<Banks> banks;
        @SerializedName("callservices")
        @Expose
        private ArrayList<CallServices> callservices;
        @SerializedName("packages")
        @Expose
        private List<Packages> packages;
        @SerializedName("services")
        @Expose
        private ArrayList<ServiceModel> services;


        protected MessageData(Parcel in) {
            callservices = in.createTypedArrayList(CallServices.CREATOR);
            packages = in.createTypedArrayList(Packages.CREATOR);
            services = in.createTypedArrayList(ServiceModel.CREATOR);
        }

        public static final Creator<MessageData> CREATOR = new Creator<MessageData>() {
            @Override
            public MessageData createFromParcel(Parcel in) {
                return new MessageData(in);
            }

            @Override
            public MessageData[] newArray(int size) {
                return new MessageData[size];
            }
        };

        //Getters
        public List<Info> getInfo() {
            return info;
        }

        public List<Modals> getModals() {
            return modals;
        }

        public List<Banks> getBanks() {
            return banks;
        }

        public ArrayList<CallServices> getCallservices() {
            return callservices;
        }

        public List<Packages> getPackages() {
            return packages;
        }

        public ArrayList<ServiceModel> getServices() {
            return services;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeTypedList(callservices);
            dest.writeTypedList(packages);
            dest.writeTypedList(services);
        }
    }

    // INFO Class ...
    public static class Info implements Parcelable{

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

        public static final Creator<Info> CREATOR = new Creator<Info>() {
            @Override
            public Info createFromParcel(Parcel in) {
                return new Info(in);
            }

            @Override
            public Info[] newArray(int size) {
                return new Info[size];
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

    // Modals Class ...
    public static class Modals {

        @SerializedName("modalyear")
        @Expose
        private String modalyear;

        // Getters
        public String getModalyear() {
            return modalyear;
        }
    }

    // Bank Class ...
    public static class Banks {

        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("accountnumber")
        @Expose
        private String accountnumber;
        @SerializedName("image")
        @Expose
        private String image;

        //Getters
        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getAccountnumber() {
            return accountnumber;
        }

        public String getImage() {
            return image;
        }
    }

    // Call ServiceDataModel Class ...
    public static class CallServices implements Parcelable {
        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("phonenumbers")
        @Expose
        private String phonenumbers;

        protected CallServices(Parcel in) {
            id = in.readInt();
            country = in.readString();
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(country);
            dest.writeString(phonenumbers);
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

        public static final Creator<Packages> CREATOR = new Creator<Packages>() {
            @Override
            public Packages createFromParcel(Parcel in) {
                return new Packages(in);
            }

            @Override
            public Packages[] newArray(int size) {
                return new Packages[size];
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

    // ServiceDataModel Class ...
    public static class ServiceModel implements Parcelable{
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

        protected ServiceModel(Parcel in) {
            id = in.readInt();
            title = in.readString();
            desc = in.readString();
            image = in.readString();
        }

        public static final Creator<ServiceModel> CREATOR = new Creator<ServiceModel>() {
            @Override
            public ServiceModel createFromParcel(Parcel in) {
                return new ServiceModel(in);
            }

            @Override
            public ServiceModel[] newArray(int size) {
                return new ServiceModel[size];
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

package com.routehelperr.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatMessageResponseModel {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("message")
    @Expose
    private List<Message> message = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<Message> getMessage() {
        return message;
    }

    public void setMessage(List<Message> message) {
        this.message = message;
    }

    // ------------- Message ---------------------
    public static class Message implements Parcelable {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("sender_id")
        @Expose
        private String senderId;
        @SerializedName("receiver_id")
        @Expose
        private String receiverId;
        @SerializedName("msg_date")
        @Expose
        private String msgDate;


        public Message() {
        }

        public Message(String message, String senderId, String receiverId, String msgDate) {
            this.message = message;
            this.senderId = senderId;
            this.receiverId = receiverId;
            this.msgDate = msgDate;
        }

        protected Message(Parcel in) {
            if (in.readByte() == 0) {
                id = null;
            } else {
                id = in.readInt();
            }
            message = in.readString();
            senderId = in.readString();
            receiverId = in.readString();
            msgDate = in.readString();
        }

        public static final Creator<Message> CREATOR = new Creator<Message>() {
            @Override
            public Message createFromParcel(Parcel in) {
                return new Message(in);
            }

            @Override
            public Message[] newArray(int size) {
                return new Message[size];
            }
        };

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getSenderId() {
            return senderId;
        }

        public void setSenderId(String senderId) {
            this.senderId = senderId;
        }

        public String getReceiverId() {
            return receiverId;
        }

        public void setReceiverId(String receiverId) {
            this.receiverId = receiverId;
        }

        public String getMsgDate() {
            return msgDate;
        }

        public void setMsgDate(String msgDate) {
            this.msgDate = msgDate;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            if (id == null) {
                parcel.writeByte((byte) 0);
            } else {
                parcel.writeByte((byte) 1);
                parcel.writeInt(id);
            }
            parcel.writeString(message);
            parcel.writeString(senderId);
            parcel.writeString(receiverId);
            parcel.writeString(msgDate);
        }
    }
}

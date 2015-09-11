package com.vec.android.apptaxi.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vuduc on 9/5/15.
 */
public class ResponseDataString {
    @Expose
    @SerializedName("Success")
    private Boolean mSuccess;
    @Expose
    @SerializedName("Message")
    private String mMessage;
    @Expose
    @SerializedName("Data")
    private String mData;

    public ResponseDataString(Boolean success, String message, String data) {
        mSuccess = success;
        mMessage = message;
        mData = data;
    }

    public Boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Boolean success) {
        mSuccess = success;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getData() {
        return mData;
    }

    public void setData(String data) {
        mData = data;
    }
}

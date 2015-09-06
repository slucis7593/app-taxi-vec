package com.vec.android.apptaxi.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vuduc on 9/5/15.
 */
public class ResponseData {
    @Expose
    @SerializedName("Success")
    private Boolean mSuccess;
    @Expose
    @SerializedName("Message")
    private String mMessage;
    @Expose
    @SerializedName("Data")
    private JsonObject mData;

    public ResponseData(Boolean success, String message, JsonObject data) {
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

    public JsonObject getData() {
        return mData;
    }

    public void setData(JsonObject data) {
        mData = data;
    }
}

package com.vec.android.apptaxi.model;

/**
 * Created by vuduc on 9/5/15.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {

    @Expose
    @SerializedName("ID")
    private String mId;

    @Expose
    @SerializedName("EMAIL")
    private String mEmail;

    @SerializedName("FIRST_NAME")
    @Expose
    private String mFirstName;

    @SerializedName("LAST_NAME")
    @Expose
    private String mLastName;

    @Expose
    @SerializedName("BIRTHDAY")
    private String mBirthday;

    @SerializedName("SERVICE_TYPE")
    @Expose
    private String mServiceType;

    @SerializedName("PERSONAL_PHOTO")
    @Expose
    private String mPersonalPhoto;

    @SerializedName("CONTACT_NUMBER")
    @Expose
    private String mContactNumber;

    @SerializedName("MACHINE_CODE")
    @Expose
    private String mMachineCode;

    @SerializedName("OS_TYPE")
    @Expose
    private String mOSType;

    @SerializedName("FACEBOOK_ID")
    @Expose
    private String mFacebookId;

    @SerializedName("IDENTIFY_CARD")
    @Expose
    private String mIdentifyCard;

    public Login(String id, String email, String firstName, String lastName, String birthday, String serviceType, String personalPhoto, String contactNumber, String machineCode, String OSType, String facebookId, String identifyCard) {
        mId = id;
        mEmail = email;
        mFirstName = firstName;
        mLastName = lastName;
        mBirthday = birthday;
        mServiceType = serviceType;
        mPersonalPhoto = personalPhoto;
        mContactNumber = contactNumber;
        mMachineCode = machineCode;
        mOSType = OSType;
        mFacebookId = facebookId;
        mIdentifyCard = identifyCard;
    }

    public Login(String id, String email, String firstName, String lastName, String birthday, String serviceType, String identifyCard, String contactNumber) {
        mId = id;
        mEmail = email;
        mFirstName = firstName;
        mLastName = lastName;
        mBirthday = birthday;
        mServiceType = serviceType;
        mIdentifyCard = identifyCard;
        mContactNumber = contactNumber;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getBirthday() {
        return mBirthday;
    }

    public void setBirthday(String birthday) {
        mBirthday = birthday;
    }

    public String getServiceType() {
        return mServiceType;
    }

    public void setServiceType(String serviceType) {
        mServiceType = serviceType;
    }

    public String getPersonalPhoto() {
        return mPersonalPhoto;
    }

    public void setPersonalPhoto(String personalPhoto) {
        mPersonalPhoto = personalPhoto;
    }

    public String getContactNumber() {
        return mContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        mContactNumber = contactNumber;
    }

    public String getMachineCode() {
        return mMachineCode;
    }

    public void setMachineCode(String machineCode) {
        mMachineCode = machineCode;
    }

    public String getOSType() {
        return mOSType;
    }

    public void setOSType(String OSType) {
        mOSType = OSType;
    }

    public String getFacebookId() {
        return mFacebookId;
    }

    public void setFacebookId(String facebookId) {
        mFacebookId = facebookId;
    }

    public String getIdentifyCard() {
        return mIdentifyCard;
    }

    public void setIdentifyCard(String identifyCard) {
        mIdentifyCard = identifyCard;
    }

    @Override
    public String toString() {
        return mId + " " + mEmail + " " + mFirstName + " " + mLastName + " " + mBirthday;
    }
}

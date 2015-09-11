package com.vec.android.apptaxi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vuduc on 9/9/15.
 */
public class RegisterData {
    @Expose
    @SerializedName("email")
    private String mEmail;
    @Expose
    @SerializedName("password")
    private String mPassword;
    @Expose
    @SerializedName("firstName")
    private String mFirstName;
    @Expose
    @SerializedName("lastName")
    private String mLastName;
    @Expose
    @SerializedName("birthday")
    private String mBirthday;
    @Expose
    @SerializedName("serviceType")
    private Integer mServiceType;
    @Expose
    @SerializedName("country")
    private String mCountry;
    @Expose
    @SerializedName("personalPhoto")
    private String mPersonalPhoto;
    @Expose
    @SerializedName("vehiclePhoto")
    private String mVehiclePhoto;
    @Expose
    @SerializedName("identifyCard")
    private String mIdentifyCard;
    @Expose
    @SerializedName("vehicleType")
    private Integer mVehicleType;
    @Expose
    @SerializedName("carryingBrand")
    private Integer mCarryingBrand;
    @Expose
    @SerializedName("seatNumber")
    private Integer mSeatNumber;
    @Expose
    @SerializedName("convenience")
    private String mConvenience;
    @Expose
    @SerializedName("licensePlate")
    private String mLicensePlate;
    @Expose
    @SerializedName("volume")
    private String mVolume;
    @Expose
    @SerializedName("boxsize")
    private String mBoxSize;
    @Expose
    @SerializedName("vehicleWeight")
    private String mVehicleWeight;
    @Expose
    @SerializedName("contactNumber")
    private String mContactNumber;

    public RegisterData() {
    }

    public RegisterData(String email, String password, String firstName, String lastName, String birthday, Integer serviceType, String country, String personalPhoto, String vehiclePhoto, String identifyCard, Integer vehicleType, Integer carryingBrand, Integer seatNumber, String convenience, String licensePlate, String volume, String boxSize, String vehicleWeight) {
        mEmail = email;
        mPassword = password;
        mFirstName = firstName;
        mLastName = lastName;
        mBirthday = birthday;
        mServiceType = serviceType;
        mCountry = country;
        mPersonalPhoto = personalPhoto;
        mVehiclePhoto = vehiclePhoto;
        mIdentifyCard = identifyCard;
        mVehicleType = vehicleType;
        mCarryingBrand = carryingBrand;
        mSeatNumber = seatNumber;
        mConvenience = convenience;
        mLicensePlate = licensePlate;
        mVolume = volume;
        mBoxSize = boxSize;
        mVehicleWeight = vehicleWeight;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
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

    public Integer getServiceType() {
        return mServiceType;
    }

    public void setServiceType(Integer serviceType) {
        mServiceType = serviceType;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

    public String getPersonalPhoto() {
        return mPersonalPhoto;
    }

    public void setPersonalPhoto(String personalPhoto) {
        mPersonalPhoto = personalPhoto;
    }

    public String getVehiclePhoto() {
        return mVehiclePhoto;
    }

    public void setVehiclePhoto(String vehiclePhoto) {
        mVehiclePhoto = vehiclePhoto;
    }

    public String getIdentifyCard() {
        return mIdentifyCard;
    }

    public void setIdentifyCard(String identifyCard) {
        mIdentifyCard = identifyCard;
    }

    public Integer getVehicleType() {
        return mVehicleType;
    }

    public void setVehicleType(Integer vehicleType) {
        mVehicleType = vehicleType;
    }

    public Integer getCarryingBrand() {
        return mCarryingBrand;
    }

    public void setCarryingBrand(Integer carryingBrand) {
        mCarryingBrand = carryingBrand;
    }

    public Integer getSeatNumber() {
        return mSeatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        mSeatNumber = seatNumber;
    }

    public String getConvenience() {
        return mConvenience;
    }

    public void setConvenience(String convenience) {
        mConvenience = convenience;
    }

    public String getLicensePlate() {
        return mLicensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        mLicensePlate = licensePlate;
    }
}

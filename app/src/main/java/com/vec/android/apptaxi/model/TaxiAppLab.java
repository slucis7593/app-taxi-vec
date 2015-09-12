package com.vec.android.apptaxi.model;

import android.content.Context;

import java.io.File;

/**
 * Created by vuduc on 9/9/15.
 */
public class TaxiAppLab {
    private static TaxiAppLab sTaxiAppLab;

    private Context mContext;

    private RegisterData mRegisterData;
    private File mPersonalPhotoFile;
    private File mVehiclePhotoFile;

    private TaxiAppLab(Context context) {
        mContext = context;
        mRegisterData = new RegisterData();
    }

    public static TaxiAppLab get(Context context) {
        if (sTaxiAppLab == null)
            sTaxiAppLab = new TaxiAppLab(context);

        return sTaxiAppLab;
    }

    public RegisterData getRegisterData() {
        return mRegisterData;
    }

    public void setRegisterData(RegisterData registerData) {
        mRegisterData = registerData;
    }

    public File getPersonalPhotoFile() {
        return mPersonalPhotoFile;
    }

    public void setPersonalPhotoFile(File personalPhotoFile) {
        mPersonalPhotoFile = personalPhotoFile;
    }

    public File getVehiclePhotoFile() {
        return mVehiclePhotoFile;
    }

    public void setVehiclePhotoFile(File vechilePhotoFile) {
        mVehiclePhotoFile = vechilePhotoFile;
    }
}

package com.vec.android.apptaxi.controller;

import android.support.v4.app.Fragment;

import com.vec.android.apptaxi.R;
import com.vec.android.apptaxi.model.RegisterData;

import java.io.File;

/**
 * Created by vuduc on 8/24/15.
 */
public class RegisterActivity extends CalligraphyFragmentActivity implements MultipleFragmentsActivity {
    private RegisterData mRegisterData;
    private File mPersonalPhotoFile;
    private File mVehiclePhotoFile;

    public RegisterActivity() {
        mRegisterData = new RegisterData();
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

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_register;
    }

    @Override
    protected Fragment createFragment() {
        return new RegisterFragment01();
    }

    @Override
    public void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}

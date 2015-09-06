package com.vec.android.apptaxi.controller;

import android.support.v4.app.Fragment;

public class LoginActivity extends CalligraphyFragmentActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected Fragment createFragment() {
        return new LoginFragment();
    }
}

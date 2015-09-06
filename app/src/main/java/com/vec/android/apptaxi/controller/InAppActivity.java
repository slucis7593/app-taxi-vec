package com.vec.android.apptaxi.controller;

import android.support.v4.app.Fragment;

public class InAppActivity extends CalligraphyFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new InAppFragment();
    }
}

package com.vec.android.apptaxi.controller;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.vec.android.apptaxi.R;
import com.vec.android.apptaxi.utils.FontUtils;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by vuduc on 8/24/15.
 */
public class TaxiApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FacebookSdk.sdkInitialize(getApplicationContext());

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath(FontUtils.FONT_VL_AMPLE_REGULAR)
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }
}

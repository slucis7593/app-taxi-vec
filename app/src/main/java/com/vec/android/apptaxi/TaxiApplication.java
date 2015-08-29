package com.vec.android.apptaxi;

import android.app.Application;

import com.facebook.FacebookSdk;

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

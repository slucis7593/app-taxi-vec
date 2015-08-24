package com.vec.android.apptaxi;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by vuduc on 8/24/15.
 */
public class CalligraphyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath(FontUtils.FONT_VL_AMPLE_REGULAR)
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }
}

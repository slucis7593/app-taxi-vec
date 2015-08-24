package com.vec.android.apptaxi;

import android.content.Context;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by vuduc on 8/24/15.
 */
public abstract class CalligraphyFragmentActivity extends SingleFragmentActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

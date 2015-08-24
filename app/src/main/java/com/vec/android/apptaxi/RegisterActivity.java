package com.vec.android.apptaxi;

import android.support.v4.app.Fragment;

/**
 * Created by vuduc on 8/24/15.
 */
public class RegisterActivity extends CalligraphyFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new RegisterFragment();
    }
}

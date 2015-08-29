package com.vec.android.apptaxi;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends CalligraphyFragmentActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected Fragment createFragment() {
        return new LoginFragment();
    }
}

package com.vec.android.apptaxi.controller;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.vec.android.apptaxi.R;

/**
 * Created by vuduc on 8/24/15.
 */
public class RegisterActivity extends CalligraphyFragmentActivity implements MultipleFragmentsActivity {
    private static final String TAG = RegisterActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_register;
    }

    @Override
    protected Fragment createFragment() {
        return new RegisterBirthdayFragment();
    }

    @Override
    public void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    public void setUpUi(Class fragment) {
        Resources r = getResources();

        ImageButton prevFragmentButton = (ImageButton) findViewById(R.id.register_button_prev_fragment);
        Button nextFragmentButton = (Button) findViewById(R.id.register_button_next_fragment);

        Fragment prevFragment = null;
        Fragment nextFragment = null;

        int button_one_drawable = R.drawable.button_focus_shape;
        int button_two_drawable = R.drawable.button_focus_shape;
        int button_three_drawable = R.drawable.button_focus_shape;

        boolean needUpdateIndexButtons = true;

        if (fragment == RegisterBirthdayFragment.class) {
            button_one_drawable = R.drawable.button_pressed_shape;
            prevFragmentButton.setVisibility(View.INVISIBLE);
            nextFragment = new RegisterPersonalInfoFragment();

        } else if (fragment == RegisterPersonalInfoFragment.class) {
            button_two_drawable = R.drawable.button_pressed_shape;
            prevFragmentButton.setVisibility(View.VISIBLE);
            prevFragment = new RegisterBirthdayFragment();
            nextFragment = new RegisterUserFragment();

        } else if (fragment == RegisterUserFragment.class) {
            button_three_drawable = R.drawable.button_pressed_shape;
            prevFragment = new RegisterPersonalInfoFragment();

        } else {
            needUpdateIndexButtons = false;
        }

        // Update index buttons
        Button numberOneButton = (Button) findViewById(R.id.register_button_number_one);
        Button numberTwoButton = (Button) findViewById(R.id.register_button_number_two);
        Button numberThreeButton = (Button) findViewById(R.id.register_button_number_three);

        if (needUpdateIndexButtons) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                numberOneButton.setBackground(r.getDrawable(button_one_drawable, getTheme()));
                numberTwoButton.setBackground(r.getDrawable(button_two_drawable, getTheme()));
                numberThreeButton.setBackground(r.getDrawable(button_three_drawable, getTheme()));
            } else {
                numberOneButton.setBackground(r.getDrawable(button_one_drawable));
                numberTwoButton.setBackground(r.getDrawable(button_two_drawable));
                numberThreeButton.setBackground(r.getDrawable(button_three_drawable));
            }
        }

        // Update next, prev fragment button
        final Fragment finalPrevFragment = prevFragment;
        prevFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalPrevFragment != null)
                    changeFragment(finalPrevFragment);
            }
        });

        final Fragment finalNextFragment = nextFragment;
        nextFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalNextFragment != null)
                    changeFragment(finalNextFragment);
            }
        });
    }
}

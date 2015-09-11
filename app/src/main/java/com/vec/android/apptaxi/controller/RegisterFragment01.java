package com.vec.android.apptaxi.controller;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vec.android.apptaxi.R;

/**
 * Created by vuduc on 9/9/15.
 */
public class RegisterFragment01 extends Fragment {
    private MultipleFragmentsActivity mMultipleFragmentsActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mMultipleFragmentsActivity = (MultipleFragmentsActivity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_01, container, false);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Resources r = getResources();

        Button numberOneButton = (Button) getActivity().findViewById(R.id.register_button_number_one);
        Button numberTwoButton = (Button) getActivity().findViewById(R.id.register_button_number_two);
        Button numberThreeButton = (Button) getActivity().findViewById(R.id.register_button_number_three);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            numberOneButton.setBackground(r.getDrawable(R.drawable.button_focus_shape, getActivity().getTheme()));
            numberTwoButton.setBackground(r.getDrawable(R.drawable.button_normal_shape, getActivity().getTheme()));
            numberThreeButton.setBackground(r.getDrawable(R.drawable.button_normal_shape, getActivity().getTheme()));
        } else {
            numberOneButton.setBackground(r.getDrawable(R.drawable.button_focus_shape));
            numberTwoButton.setBackground(r.getDrawable(R.drawable.button_normal_shape));
            numberThreeButton.setBackground(r.getDrawable(R.drawable.button_normal_shape));
        }

        Button prevFragmentButton = (Button) getActivity().findViewById(R.id.register_button_prev_fragment);
        prevFragmentButton.setVisibility(View.INVISIBLE);

        Button nextFragmentButton = (Button) getActivity().findViewById(R.id.register_button_next_fragment);
        nextFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMultipleFragmentsActivity.changeFragment(new RegisterFragment02());
            }
        });
    }
}

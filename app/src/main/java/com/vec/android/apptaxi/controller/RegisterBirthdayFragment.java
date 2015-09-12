package com.vec.android.apptaxi.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vec.android.apptaxi.R;
import com.vec.android.apptaxi.model.RegisterData;
import com.vec.android.apptaxi.model.TaxiAppLab;
import com.vec.android.apptaxi.utils.DateUtils;

import java.util.Date;

/**
 * Created by vuduc on 9/9/15.
 */
public class RegisterBirthdayFragment extends Fragment {
    private static final int REQUEST_DATE = 0;
    private static final String DIALOG_DATE = "DialogDate";
    private RegisterActivity mRegisterActivity;
    private TextView mTvDate;
    private RegisterData mRegisterData;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mRegisterActivity = (RegisterActivity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRegisterData = TaxiAppLab.get(getActivity()).getRegisterData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_birthday, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set up Activity UI
        mRegisterActivity.setUpUi(RegisterBirthdayFragment.class);

        // Date text view
        mTvDate = (TextView) view.findViewById(R.id.login_tv_date);
        String currentDateString = DateUtils.date2StringForDisplay(DateUtils.stringApi2Date(mRegisterData.getBirthday()));
        if (currentDateString != "") {
            mTvDate.setText(currentDateString);
        }
        mTvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvDate.setClickable(false);

                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(DateUtils.stringUi2Date(mTvDate.getText().toString()));
                dialog.setTargetFragment(RegisterBirthdayFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK)
            return;

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            // Update for Upload
            mRegisterData.setBirthday(DateUtils.date2StringForUpload(date));

            // Update for Display
            mTvDate.setText(DateUtils.date2StringForDisplay(date));

            mTvDate.setClickable(true);
        }
    }
}

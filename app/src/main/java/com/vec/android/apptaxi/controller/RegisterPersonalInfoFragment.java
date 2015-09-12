package com.vec.android.apptaxi.controller;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.vec.android.apptaxi.R;
import com.vec.android.apptaxi.model.TaxiAppLab;
import com.vec.android.apptaxi.utils.DateUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by vuduc on 8/24/15.
 */
public class RegisterPersonalInfoFragment extends Fragment {
    private static final String TAG = RegisterPersonalInfoFragment.class.getSimpleName();

    private Spinner mDateSpinner;
    private Spinner mMonthSpinner;
    private Spinner mYearSpinner;

    private RegisterActivity mRegisterActivity;

    private TaxiAppLab mTaxiAppLab;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mRegisterActivity = (RegisterActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaxiAppLab = TaxiAppLab.get(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_personal_info, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Resources r = getResources();

        // Set focus
        LinearLayout mainLayout = (LinearLayout) view.findViewById(R.id.register_layout_main);
        mainLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });

        // Set up Service Register Spinner
        Spinner serviceRegisterSpinner = (Spinner) view.findViewById(R.id.register_spinner_service);
        ArrayList<String> items = new ArrayList<String>(Arrays.asList(r.getStringArray(R.array.service_register_array)));
        items.add(r.getString(R.string.service_hint));

        ServiceRegisterAdapter adapter = new ServiceRegisterAdapter(getActivity(), R.layout.spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serviceRegisterSpinner.setAdapter(adapter);
        serviceRegisterSpinner.setSelection(items.size() - 1);

        // Setup Date Picker
        mDateSpinner = (Spinner) view.findViewById(R.id.register_spinner_day);
        mMonthSpinner = (Spinner) view.findViewById(R.id.register_spinner_month);
        mYearSpinner = (Spinner) view.findViewById(R.id.register_spinner_year);

        DateUtils.initDateTimeSpinners(getActivity(), mDateSpinner, mMonthSpinner, mYearSpinner);

        mDateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (DateUtils.isValidDate(mDateSpinner, mMonthSpinner, mYearSpinner) == DateUtils.VALIDATE_DATE.FALSE)
                    DateUtils.decreaseSpinnerValue(mDateSpinner);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mMonthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (DateUtils.isValidDate(mDateSpinner, mMonthSpinner, mYearSpinner) == DateUtils.VALIDATE_DATE.FALSE)
                    DateUtils.decreaseSpinnerValue(mDateSpinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mYearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (DateUtils.isValidDate(mDateSpinner, mMonthSpinner, mYearSpinner) == DateUtils.VALIDATE_DATE.FALSE)
                    DateUtils.decreaseSpinnerValue(mDateSpinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Activity UI Initialize
        mRegisterActivity.setUpUi(RegisterPersonalInfoFragment.class);
    }

    class ServiceRegisterAdapter extends ArrayAdapter<String> {
        private static final String TAG = "ServiceRegisterAdapter";

        public ServiceRegisterAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = super.getView(position, convertView, parent);

            if (position == getCount()) {
                ((TextView) v).setTextColor(Color.argb(100, 0, 0, 0));
            }

            return v;
        }

        @Override
        public int getCount() {
            return super.getCount() - 1;
        }
    }
}

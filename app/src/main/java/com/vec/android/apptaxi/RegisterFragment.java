package com.vec.android.apptaxi;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by vuduc on 8/24/15.
 */
public class RegisterFragment extends Fragment {
    private Spinner mDateSpinner;
    private Spinner mMonthSpinner;
    private Spinner mYearSpinner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        Resources r = getResources();

        // Set focus
        LinearLayout mainLayout = (LinearLayout) v.findViewById(R.id.mainLayout_register);
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
        Spinner serviceRegisterSpinner = (Spinner) v.findViewById(R.id.spinnerServiceRegister_register);
        ArrayList<String> items = new ArrayList<String>(Arrays.asList(r.getStringArray(R.array.service_register_array)));
        items.add(r.getString(R.string.service_hint));

        ServiceRegisterAdapter adapter = new ServiceRegisterAdapter(getActivity(), R.layout.spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serviceRegisterSpinner.setAdapter(adapter);
        serviceRegisterSpinner.setSelection(items.size() - 1);

        // Setup Date Picker
        mDateSpinner = (Spinner) v.findViewById(R.id.spinDate_register);
        mMonthSpinner = (Spinner) v.findViewById(R.id.spinMonth_register);
        mYearSpinner = (Spinner) v.findViewById(R.id.spinYear_register);

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

        // Coloring policy string
        TextView tvTermPolicy = (TextView) v.findViewById(R.id.tvTermPolicy_register);

        String termPolicyText = r.getString(R.string.term_policy);
        String termText = r.getString(R.string.term);
        String policyText = r.getString(R.string.policy);

        SpannableStringBuilder coloredTermPolicyText = new SpannableStringBuilder(termPolicyText);
        coloredTermPolicyText.setSpan(new ForegroundColorSpan(Color.RED),
                termPolicyText.indexOf(termText),
                termPolicyText.indexOf(termText) + termText.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        coloredTermPolicyText.setSpan(new ForegroundColorSpan(Color.RED),
                termPolicyText.indexOf(policyText),
                termPolicyText.indexOf(policyText) + policyText.length(),
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        tvTermPolicy.setText(coloredTermPolicyText);

        return v;
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

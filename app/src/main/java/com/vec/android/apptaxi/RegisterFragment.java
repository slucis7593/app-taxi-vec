package com.vec.android.apptaxi;

import android.app.ActionBar;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by vuduc on 8/24/15.
 */
public class RegisterFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        Resources r = getResources();

        // Setup Action bar

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
}

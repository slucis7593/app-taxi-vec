package com.vec.android.apptaxi;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by vuduc on 8/21/15.
 */
public class LoginFragment extends Fragment {

    private static final String TAG = LoginFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        Context c = getActivity().getApplicationContext();
        Resources r = getResources();

        TextView tvTitleLogin = (TextView) v.findViewById(R.id.tvTitleLogin_login);
        CustomFontHelper.setCustomFont(tvTitleLogin, CustomFontHelper.FONT_VL_AMPLE_MEDIUM, c);

        EditText etAccount = (EditText) v.findViewById(R.id.etAccount_login);
        CustomFontHelper.setCustomFont(etAccount, CustomFontHelper.FONT_VL_AMPLE_REGULAR, c);

        EditText etPassword = (EditText) v.findViewById(R.id.etPassword_login);
        CustomFontHelper.setCustomFont(etPassword, CustomFontHelper.FONT_VL_AMPLE_REGULAR, c);

        Button btnSignIn = (Button) v.findViewById(R.id.btnSignIn_login);
        CustomFontHelper.setCustomFont(btnSignIn, CustomFontHelper.FONT_VL_AMPLE_MEDIUM, c);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Sign In Button Clicked");
            }
        });

        TextView tvOr = (TextView) v.findViewById(R.id.tvOr_login);
        CustomFontHelper.setCustomFont(tvOr, CustomFontHelper.FONT_VL_AMPLE_REGULAR, c);

        ImageButton btnFacebook = (ImageButton) v.findViewById(R.id.btnFacebook_login);
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Facebook Button Clicked");
            }
        });

        TextView tvNoAccount = (TextView) v.findViewById(R.id.tvNoAccount_login);
        CustomFontHelper.setCustomFont(tvNoAccount, CustomFontHelper.FONT_HELVETICA_NEUE, c);

        TextView tvSignUp = (TextView) v.findViewById(R.id.tvSignUp_login);
        CustomFontHelper.setCustomFont(tvSignUp, CustomFontHelper.FONT_MYRIAD_PRO_REGULAR, c);
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Sign Up Text View Clicked");
            }
        });

//        SpannableString signUpString = new SpannableString(r.getString(R.string.dang_ky));
//        signUpString.setSpan(new ClickableSpan() {
//            @Override
//            public void onClick(View widget) {
//                Log.d(TAG, "Click Sign Up");
//            }
//        }, 0, signUpString.length(), 0);
//        tvSignUp.setMovementMethod(LinkMovementMethod.getInstance());
//        tvSignUp.setText(signUpString, TextView.BufferType.SPANNABLE);

        return v;
    }
}

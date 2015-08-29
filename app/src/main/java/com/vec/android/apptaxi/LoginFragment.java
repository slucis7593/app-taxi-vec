package com.vec.android.apptaxi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

/**
 * Created by vuduc on 8/21/15.
 */
public class LoginFragment extends Fragment {

    private static final String TAG = LoginFragment.class.getSimpleName();

    private AccessTokenTracker mTokenTracker;
    private ProfileTracker mProfileTracker;
    private CallbackManager mCallbackManager;
    private FacebookCallback<LoginResult> mFacebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();

            Profile profile = Profile.getCurrentProfile();

            Log.d(TAG, "onSuccess: " + displayMessage(profile) +
                    "\nUser Id: " + accessToken.getUserId() +
                    "\nApp Id: " + accessToken.getApplicationId() +
                    "\nToken: " + accessToken.getToken() +
                    "\nPermissions: " + accessToken.getPermissions());
        }

        @Override
        public void onCancel() {
            Log.d(TAG, "Facebook onCancel");
        }

        @Override
        public void onError(FacebookException e) {
            Log.e(TAG, "Facebook error: ", e);
        }
    };


    private String displayMessage(Profile profile) {
        String message = "";
        if (profile != null) {
            message = "Logged In " + profile.getFirstName() + " " + profile.getMiddleName() + " " + profile.getLastName();
        } else {
            message = "You are not logged in";
        }
        return message;
    }

    private String displayMessage(AccessToken accessToken) {
        String message = "";
        if (accessToken != null) {
            message = "Logged In " + accessToken.getUserId();
        } else {
            message = "You are not logged in";
        }
        return message;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCallbackManager = CallbackManager.Factory.create();
        mTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken accessToken, AccessToken accessToken1) {
                Log.d(TAG, "onCurrentAccessTokenChanged: " + displayMessage(accessToken) + " to " + displayMessage(accessToken1));
            }
        };
        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile profile, Profile profile1) {
                Log.d(TAG, "onCurrentProfileChanged: " + displayMessage(profile) + " to " + displayMessage(profile1));
            }
        };

        mTokenTracker.startTracking();
        mProfileTracker.startTracking();
        Log.d(TAG, "onCreate: Start Tracking");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        RelativeLayout mainLayout = (RelativeLayout) v.findViewById(R.id.mainLayout_login);
        mainLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });

        TextView tvSignUp = (TextView) v.findViewById(R.id.tvSignUp_login);
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Sign Up Text View Clicked");

                Intent i = new Intent(getActivity(), RegisterActivity.class);
                startActivity(i);
            }
        });

        final LoginButton loginButton = (LoginButton) v.findViewById(R.id.btnFacebookLogin_login);
        loginButton.setReadPermissions("user_friends", "email");
        loginButton.setFragment(this);
        loginButton.registerCallback(mCallbackManager, mFacebookCallback);

        ImageButton loginImageButton = (ImageButton) v.findViewById(R.id.btnFacebook_login);
        loginImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.performClick();
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        Log.d(TAG, "onResume: " + displayMessage(profile));
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
        mProfileTracker.stopTracking();
        mTokenTracker.stopTracking();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}

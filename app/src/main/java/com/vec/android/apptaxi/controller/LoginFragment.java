package com.vec.android.apptaxi.controller;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.vec.android.apptaxi.R;
import com.vec.android.apptaxi.api.TaxiAppService;
import com.vec.android.apptaxi.api.TaxiAppServiceManager;
import com.vec.android.apptaxi.model.ResponseData;

import retrofit.Callback;
import retrofit.Response;

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

            /*Log.d(TAG, "onSuccess: " + displayMessage(profile) +
                    "\nUser Id: " + accessToken.getUserId() +
                    "\nApp Id: " + accessToken.getApplicationId() +
                    "\nToken: " + accessToken.getToken() +
                    "\nPermissions: " + accessToken.getPermissions());*/
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
    private LoginButton mBtnFacebookLogin;
    private EditText mETPassword;
    private EditText mETAccount;

    private void requestLogin() {
        String acc = mETAccount.getText().toString();
        String pwd = mETPassword.getText().toString();

        TaxiAppService service = TaxiAppServiceManager.get(getActivity()).getService();

        service.login(acc, pwd, TaxiAppServiceManager.PARAM_MACHINE_CODE, TaxiAppServiceManager.PARAM_OS_TYPE).enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Response<ResponseData> response) {
                ResponseData responseData = response.body();
                if (responseData != null) {
                    Log.d(TAG, "Success Login");
                    boolean isSuccess = responseData.getSuccess();
                    if (isSuccess) {
                        Intent i = new Intent(getActivity(), InAppActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(getActivity(), responseData.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG, "Failed: " + t.getMessage());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

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
                Intent i = new Intent(getActivity(), RegisterActivity.class);
                startActivity(i);
            }
        });

        mBtnFacebookLogin = (LoginButton) v.findViewById(R.id.btnFacebookLogin_login);
        mBtnFacebookLogin.setReadPermissions("user_friends", "email");
        mBtnFacebookLogin.setFragment(this);
        mBtnFacebookLogin.registerCallback(mCallbackManager, mFacebookCallback);

        ImageButton IBtnLogin = (ImageButton) v.findViewById(R.id.btnFacebook_login);
        IBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtnFacebookLogin.performClick();
            }
        });

        Button btnSignIn = (Button) v.findViewById(R.id.btnSignIn_login);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLogin();
            }
        });

        mETAccount = (EditText) v.findViewById(R.id.etAccount_login);

        mETPassword = (EditText) v.findViewById(R.id.etPassword_login);

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStop() {
        super.onStop();
        mProfileTracker.stopTracking();
        mTokenTracker.stopTracking();
    }
}

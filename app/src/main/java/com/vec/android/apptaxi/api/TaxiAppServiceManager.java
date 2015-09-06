package com.vec.android.apptaxi.api;

import android.content.Context;
import android.util.Log;

import com.vec.android.apptaxi.model.ResponseData;

import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by vuduc on 9/5/15.
 */
public class TaxiAppServiceManager {
    private static final String END_POINT = "http://app.websomot.com:6969";
    //    private static final String END_POINT = "http://api.openweathermap.org";
    public static final String PARAM_MACHINE_CODE = "Abc1223";
    public static final String PARAM_OS_TYPE = "ANDROID";
    private static final String TAG = TaxiAppServiceManager.class.getSimpleName();

    private static TaxiAppServiceManager sManager;
    private Context mAppContext;

    public TaxiAppService getService() {
        return mService;
    }

    private TaxiAppService mService;
    private ResponseData mResponseData;

    private TaxiAppServiceManager(Context appContext) {
        mAppContext = appContext;

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(END_POINT)
                .build();

        mService = retrofit.create(TaxiAppService.class);
    }

    public static TaxiAppServiceManager get(Context context) {
        if (sManager == null) {
            sManager = new TaxiAppServiceManager(context.getApplicationContext());
        }
        return sManager;
    }
}

package com.vec.android.apptaxi.model;

import android.content.Context;

/**
 * Created by vuduc on 9/9/15.
 */
public class TaxiAppLab {
    private static TaxiAppLab sTaxiAppLab;

    private Context mContext;

    public RegisterData getNewRegister() {
        return getNewRegister(false);
    }

    public RegisterData getNewRegister(boolean forceCreateNew) {
        if (mNewRegister == null) {
            mNewRegister = new RegisterData();
        } else {
            if (forceCreateNew) {
                mNewRegister = new RegisterData();
            }
        }

        return mNewRegister;
    }

    private RegisterData mNewRegister;

    private TaxiAppLab(Context context) {
        mContext = context;
    }

    public static TaxiAppLab get(Context context) {
        if (sTaxiAppLab == null)
            sTaxiAppLab = new TaxiAppLab(context);

        return sTaxiAppLab;
    }
}

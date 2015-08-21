package com.vec.android.apptaxi;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.util.Hashtable;

/**
 * Created by vuduc on 8/21/15.
 */
public class FontCache {

    private static final String TAG = FontCache.class.getSimpleName();
    private static Hashtable<String, Typeface> fontCache = new Hashtable<String, Typeface>();

    public static Typeface get(String name, Context context) {
        Typeface tf = fontCache.get(name);
        if (tf == null) {
            try {
                tf = Typeface.createFromAsset(context.getAssets(), name);
            } catch (Exception e) {
                Log.d(TAG, "Faile to create type facec: ", e);
            }
            fontCache.put(name, tf);
        }
        return tf;
    }
}

package com.vec.android.apptaxi;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by vuduc on 8/21/15.
 */
public class FontUtils {
    public static final String FONT_HELVETICA_NEUE = "fonts/HelveticaNeue-Regular.otf";
    public static final String FONT_MYRIAD_PRO_REGULAR = "fonts/MyriadPro-Regular.otf";
    public static final String FONT_VL_AMPLE_MEDIUM = "fonts/VL_Ample-Bold.otf";
    public static final String FONT_VL_AMPLE_REGULAR = "fonts/VL_Ample-Regular.otf";

    /**
     * Sets a font on a textView
     * @param view
     * @param font
     * @param context
     */
    public static void setCustomFont(TextView view, String font, Context context) {
        if(font == null) {
            return;
        }
        Typeface tf = FontCache.get(font, context);
        if(tf != null) {
            view.setTypeface(tf);
        }
    }
}

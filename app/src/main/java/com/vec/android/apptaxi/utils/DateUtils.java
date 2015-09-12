package com.vec.android.apptaxi.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.vec.android.apptaxi.R;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by vuduc on 8/26/15.
 */
public class DateUtils {

    private static final String TAG = DateUtils.class.getSimpleName();

    private static boolean isValidDate(int d, int m, int y) {
        switch (m) {
            case 4:
            case 6:
            case 9:
            case 11:
                return d <= 30;
            case 2:
                Calendar c = Calendar.getInstance();
                c.clear();
                c.set(y, m - 1, 1);
                int maxDate = c.getActualMaximum(Calendar.DAY_OF_MONTH);

                Log.d(TAG, d + " <= " + maxDate);

                return d <= maxDate;

            default:
                return true;
        }
    }

    private static void initSpinner(Context context, Spinner spinner, int min, int max, String hint) {
        ArrayList<String> items = new ArrayList<>();
        for (int i = min; i <= max; i++)
            items.add(Integer.toString(i));
        items.add(hint);
        StringSpinnerAdapter adapter = new StringSpinnerAdapter(context, R.layout.spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(items.size() - 1);
    }

    public static void initDateTimeSpinners(Context context, Spinner dateSpinner, Spinner monthSpinner, Spinner yearSpinner) {
        Resources r = context.getResources();

        // Date
        initSpinner(context, dateSpinner, 1, 31, r.getString(R.string.date_hint));

        // Month
        initSpinner(context, monthSpinner, 1, 12, r.getString(R.string.month_hint));

        // Year
        initSpinner(context, yearSpinner, 1900, Calendar.getInstance().get(Calendar.YEAR), r.getString(R.string.year_hint));
    }

    public static VALIDATE_DATE isValidDate(Spinner dateSpinner, Spinner monthSpinner, Spinner yearSpinner) {

        try {
            // Check length to sure user chose value for all spinner
            int d = Integer.parseInt((String) dateSpinner.getSelectedItem());
            int m = Integer.parseInt((String) monthSpinner.getSelectedItem());
            int y = Integer.parseInt((String) yearSpinner.getSelectedItem());

            if (isValidDate(d, m, y))
                return VALIDATE_DATE.TRUE;
            else
                return VALIDATE_DATE.FALSE;
        } catch (Exception e) {
            return VALIDATE_DATE.NONE;
        }
    }

    public static void setSpinnerSelection(Spinner spinner, String value) {
        StringSpinnerAdapter adapter = (StringSpinnerAdapter) spinner.getAdapter();
        int pos = adapter.getPosition(value);
        spinner.setSelection(pos);
    }

    public static void decreaseSpinnerValue(Spinner spinner) {
        try {
            int d = Integer.parseInt((String) spinner.getSelectedItem());
            setSpinnerSelection(spinner, String.valueOf(d - 1));
        } catch (Exception ignored) {

        }
    }

    public enum VALIDATE_DATE {TRUE, FALSE, NONE}

    static class StringSpinnerAdapter extends ArrayAdapter<String> {

        public StringSpinnerAdapter(Context context, int resource, ArrayList<String> items) {
            super(context, resource, items);
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

        @Override
        public int getPosition(String item) {
            return super.getPosition(item);
        }

        @Override
        public String getItem(int position) {
            return super.getItem(position);
        }
    }

    public static String date2StringForDisplay(Date date) {
        return date2String(date, "dd/MM/yyyy");
    }

    public static String date2StringForUpload(Date date) {
        return date2String(date, "yyyy-MM-dd");
    }

    public static String date2String(Date date, String formatString) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatString, Locale.US);
        return formatter.format(date);
    }

    public static Date stringApi2Date(String source) {
        return string2Date(source, "yyyy-MM-dd");
    }

    public static Date stringUi2Date(String source) {
        return string2Date(source, "dd/MM/yyyy");
    }

    public static Date string2Date(String source, String formatString) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatString);
        Date date = null;
        try {
            date = formatter.parse(source);
        } catch (NullPointerException | ParseException e) {
            date = new Date();
        }
        return date;
    }
}

package com.vec.android.apptaxi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by vuduc on 8/24/15.
 */
public class RegisterFragment extends Fragment {
    private static final String TAG = RegisterFragment.class.getSimpleName();
    private static final int REQUEST_CAMERA = 0;
    private static final int SELECT_FILE = 1;
    String mCurrentPhotoPath;
    private Spinner mDateSpinner;
    private Spinner mMonthSpinner;
    private Spinner mYearSpinner;
    private CircleImageView mImgAddAvatar;

    private String mCurrentImagePath;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        Resources r = getResources();

        // Circle Image View
        mImgAddAvatar = (CircleImageView) v.findViewById(R.id.imgAddImage_register);
        mImgAddAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Click Add Avatar");
                selectImage();
            }
        });

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                handleImageFromCamera(data);
            } else if (requestCode == SELECT_FILE) {
                handleImageFromGallery(data);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private void handleImageFromCamera(Intent data) {
        // Get image
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        assert thumbnail != null;
        mImgAddAvatar.setImageBitmap(thumbnail);

        // Compress Image to Byte array
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        if (FileUtils.isExternalStorageWritable() && FileUtils.isExternalStorageReadable()) {
            // Save image to external storage
            String dir = Environment.getExternalStorageDirectory() + "/TaxiApp/Camera";
            Log.d(TAG, "Path: " + dir);
            File checkDir = new File(dir);

            if (!checkDir.isDirectory() && !checkDir.exists()) {
                if (!checkDir.mkdirs())
                    return;
            }

            File destination = new File(Environment.getExternalStorageDirectory(),
                    System.currentTimeMillis() + ".jpg");

            FileOutputStream fo;
            try {
                if (destination.createNewFile()) {
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();


                }
            } catch (IOException e) {
                Log.e(TAG, "Error write image to file: ", e);
            }
        } else {
            FileOutputStream fo;
            try {
                String fileName = System.currentTimeMillis() + ".jpg";
                fo = getActivity().openFileOutput(fileName, Context.MODE_PRIVATE);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (IOException e) {
                Log.e(TAG, "Error write image to file: ", e);
            }
        }
    }

    private void handleImageFromGallery(Intent data) {
        Uri uri = data.getData();
        String[] projection = {MediaStore.Images.Media.DATA};

        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(projection[0]);
            String picturePath = cursor.getString(columnIndex); // returns null
            cursor.close();

            Log.d(TAG, "picturePath: " + picturePath);
            Picasso.with(getActivity()).load("file://" + picturePath).into(mImgAddAvatar);
        } else {
            Toast.makeText(getActivity(), "Could not load image", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectImage() {
        final Resources r = getResources();

        final CharSequence[] items = r.getTextArray(R.array.add_image_items_array);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(r.getString(R.string.add_image_title));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                // Take picture by camera
                if (item == 0) {

                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        Log.e(TAG, "Error creat file: ", ex);
                    }

                    if (photoFile != null) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                            startActivityForResult(intent, REQUEST_CAMERA);
                        }
                    }
                }
                // Choose from Gallery
                else if (item == 1) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, r.getString(R.string.choose_file)),
                            SELECT_FILE);
                }
                // Cancel
                else if (item == 2) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
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

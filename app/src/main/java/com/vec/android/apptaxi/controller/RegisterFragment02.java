package com.vec.android.apptaxi.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.vec.android.apptaxi.R;
import com.vec.android.apptaxi.api.TaxiAppServiceManager;
import com.vec.android.apptaxi.model.ResponseDataString;
import com.vec.android.apptaxi.utils.DateUtils;
import com.vec.android.apptaxi.utils.PictureUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Callback;
import retrofit.Response;

/**
 * Created by vuduc on 8/24/15.
 */
public class RegisterFragment02 extends Fragment {
    private static final String TAG = RegisterFragment02.class.getSimpleName();
    private static final int REQUEST_CAMERA = 0;
    private static final int REQUEST_SELECT_FILE = 1;
    private Spinner mDateSpinner;
    private Spinner mMonthSpinner;
    private Spinner mYearSpinner;
    private CircleImageView mImgAddAvatar;

    private RegisterActivity mRegisterActivity;

    private Intent mCaptureImage;
    private boolean mCanTakePhoto;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mRegisterActivity = (RegisterActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_02, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PackageManager packageManager = getActivity().getPackageManager();
        Resources r = getResources();

        // Circle Image View
        File photoFile = mRegisterActivity.getPersonalPhotoFile();
        if (photoFile == null) {
            photoFile = PictureUtils.getPhotoFile(getActivity());
            mRegisterActivity.setPersonalPhotoFile(photoFile);
        }

        mCaptureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mCanTakePhoto = photoFile != null &&
                mCaptureImage.resolveActivity(packageManager) != null;
        if (mCanTakePhoto) {
            Uri uri = Uri.fromFile(photoFile);
            mCaptureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }

        mImgAddAvatar = (CircleImageView) view.findViewById(R.id.imgAddImage_register);
        mImgAddAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        updatePhotoViewFromCamera(0.3f);

        // Set focus
        LinearLayout mainLayout = (LinearLayout) view.findViewById(R.id.mainLayout_register);
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
        Spinner serviceRegisterSpinner = (Spinner) view.findViewById(R.id.spinnerServiceRegister_register);
        ArrayList<String> items = new ArrayList<String>(Arrays.asList(r.getStringArray(R.array.service_register_array)));
        items.add(r.getString(R.string.service_hint));

        ServiceRegisterAdapter adapter = new ServiceRegisterAdapter(getActivity(), R.layout.spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serviceRegisterSpinner.setAdapter(adapter);
        serviceRegisterSpinner.setSelection(items.size() - 1);

        // Setup Date Picker
        mDateSpinner = (Spinner) view.findViewById(R.id.spinDate_register);
        mMonthSpinner = (Spinner) view.findViewById(R.id.spinMonth_register);
        mYearSpinner = (Spinner) view.findViewById(R.id.spinYear_register);

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
        TextView tvTermPolicy = (TextView) view.findViewById(R.id.tvTermPolicy_register);

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


        // Activity UI Initialize
        Button numberOneButton = (Button) getActivity().findViewById(R.id.register_button_number_one);
        Button numberTwoButton = (Button) getActivity().findViewById(R.id.register_button_number_two);
        Button numberThreeButton = (Button) getActivity().findViewById(R.id.register_button_number_three);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            numberOneButton.setBackground(r.getDrawable(R.drawable.button_normal_shape, getActivity().getTheme()));
            numberTwoButton.setBackground(r.getDrawable(R.drawable.button_focus_shape, getActivity().getTheme()));
            numberThreeButton.setBackground(r.getDrawable(R.drawable.button_normal_shape, getActivity().getTheme()));
        } else {
            numberOneButton.setBackground(r.getDrawable(R.drawable.button_normal_shape));
            numberTwoButton.setBackground(r.getDrawable(R.drawable.button_focus_shape));
            numberThreeButton.setBackground(r.getDrawable(R.drawable.button_normal_shape));
        }

        Button prevFragmentButton = (Button) getActivity().findViewById(R.id.register_button_prev_fragment);
        prevFragmentButton.setVisibility(View.VISIBLE);
        prevFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRegisterActivity.changeFragment(new RegisterFragment01());
            }
        });

        Button nextFragmentButton = (Button) getActivity().findViewById(R.id.register_button_next_fragment);
        nextFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                updatePhotoViewFromCamera(0.3f);
                //uploadPhotoView();
            } else if (requestCode == REQUEST_SELECT_FILE) {
                handleImageFileFromStorage(data);
                updatePhotoViewFromCamera(0.3f);
                //uploadPhotoView();
            }
        }
    }

    private void handleImageFileFromStorage(Intent data) {
        Uri uri = data.getData();
        String picturePath;

        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(projection[0]);
            picturePath = cursor.getString(columnIndex); // returns null
            cursor.close();
        } else {
            picturePath = uri.getPath();
        }

        mRegisterActivity.setPersonalPhotoFile(new File(picturePath));
    }

    private void uploadPhotoView() {
        Bitmap bitmap;
        if (mImgAddAvatar.getDrawable() instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) mImgAddAvatar.getDrawable()).getBitmap();
        } else {
            Drawable d = mImgAddAvatar.getDrawable();
            bitmap = Bitmap.createBitmap(d.getIntrinsicWidth(), d.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            d.draw(canvas);
        }

        if (bitmap != null) {
            String base64Bitmap = PictureUtils.bitmapToBase64(bitmap);

            TaxiAppServiceManager.get(getActivity()).getService()
                    .uploadImage(base64Bitmap).enqueue(new Callback<ResponseDataString>() {
                @Override
                public void onResponse(Response<ResponseDataString> response) {
                    ResponseDataString data = response.body();
                    Log.d(TAG, "Image Url: " + data.getData());

                    // Update

                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e(TAG, "Failed upload image: ", t);
                }
            });
        }
    }

    private void updatePhotoViewFromCamera(float scale) {
        File photoFile = mRegisterActivity.getPersonalPhotoFile();
        if (photoFile == null && !photoFile.exists()) {
            mImgAddAvatar.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(photoFile.getPath(), getActivity(), scale);
            mImgAddAvatar.setImageBitmap(bitmap);
        }
    }

    /**
     * Thumbnail Photo
     * @param data
     */
    private void updatePhotoViewFromCamera(Intent data) {
        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");
        mImgAddAvatar.setImageBitmap(imageBitmap);
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
                    if (mCanTakePhoto) {


                        startActivityForResult(mCaptureImage, REQUEST_CAMERA);
                    } else {
                        Toast.makeText(getActivity(), "Camera isn't avaiable", Toast.LENGTH_LONG).show();
                    }
                }
                // Choose from Gallery
                else if (item == 1) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, r.getString(R.string.choose_file)),
                            REQUEST_SELECT_FILE);
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

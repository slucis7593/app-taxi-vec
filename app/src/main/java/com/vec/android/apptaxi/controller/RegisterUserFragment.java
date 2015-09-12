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
import android.widget.TextView;
import android.widget.Toast;

import com.vec.android.apptaxi.R;
import com.vec.android.apptaxi.api.TaxiAppServiceManager;
import com.vec.android.apptaxi.model.ResponseDataString;
import com.vec.android.apptaxi.model.TaxiAppLab;
import com.vec.android.apptaxi.utils.PictureUtils;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Callback;
import retrofit.Response;

/**
 * Created by vuduc on 9/12/15.
 */
public class RegisterUserFragment extends Fragment {
    private static final int REQUEST_CAMERA = 0;
    private static final int REQUEST_SELECT_FILE = 1;
    private static final String TAG = RegisterUserFragment.class.getSimpleName();

    private CircleImageView mImgAddAvatar;

    private Intent mCaptureImage;
    private boolean mCanTakePhoto;

    private TaxiAppLab mTaxiAppLab;

    private RegisterActivity mRegisterActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mRegisterActivity = (RegisterActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaxiAppLab = TaxiAppLab.get(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_user, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PackageManager packageManager = getActivity().getPackageManager();
        Resources r = getResources();

        // Set up Activity UI
        mRegisterActivity.setUpUi(RegisterUserFragment.class);

        // Circle Image View
        mCaptureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mCanTakePhoto = mCaptureImage.resolveActivity(packageManager) != null;

        mImgAddAvatar = (CircleImageView) view.findViewById(R.id.register_img_add_avatar);
        mImgAddAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        updatePhotoViewFromCamera(0.3f);

        // Coloring policy string
        TextView tvTermPolicy = (TextView) view.findViewById(R.id.register_tv_term_policy);

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

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                updatePhotoViewFromCamera(0.3f);
                //TODO: uploadPhotoView();
            } else if (requestCode == REQUEST_SELECT_FILE) {
                handleImageFileFromStorage(data);
                updatePhotoViewFromCamera(0.3f);
                //TODO: uploadPhotoView();
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

        mTaxiAppLab.setPersonalPhotoFile(new File(picturePath));
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

                    // Update
                    mTaxiAppLab.getRegisterData().setPersonalPhoto(data.getData());
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e(TAG, "Failed upload image: ", t);
                }
            });
        }
    }

    private void updatePhotoViewFromCamera(float scale) {
        File photoFile = mTaxiAppLab.getPersonalPhotoFile();
        if (photoFile == null)
            return;

        if (photoFile.exists()) {
            Bitmap bitmap = PictureUtils.getScaledBitmap(photoFile.getPath(), getActivity(), scale);
            mImgAddAvatar.setImageBitmap(bitmap);
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
                    File photoFile = PictureUtils.getPhotoFile(getActivity());
                    mTaxiAppLab.setPersonalPhotoFile(photoFile);
                    boolean canSavePhoto = mCanTakePhoto && photoFile != null;
                    if (canSavePhoto) {
                        Uri uri = Uri.fromFile(photoFile);
                        mCaptureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);

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
}

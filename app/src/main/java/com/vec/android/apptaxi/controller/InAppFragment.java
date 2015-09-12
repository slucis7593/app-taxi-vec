package com.vec.android.apptaxi.controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vec.android.apptaxi.R;

/**
 * Created by vuduc on 9/5/15.
 */
public class InAppFragment extends Fragment {

    private static final String TAG = InAppFragment.class.getSimpleName();
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setUpMapIfNeeded();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_in_app, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnGetMyLocation = (Button) view.findViewById(R.id.btnGetMyLocation_in_app);
        btnGetMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "My Location: " + mMap.getMyLocation().getLatitude() + ", " + mMap.getMyLocation().getLongitude());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            // This is nested fragment, so you must use getChildFragmentManager instead of getActivity().getSupportFragmentManager()
            SupportMapFragment smf = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.in_app_map);

            if (smf != null) {
                mMap = smf.getMap();
                // Check if we were successful in obtaining the map.
                if (mMap != null) {
                    setUpMap();
                }
            } else {
                Log.d(TAG, "Cannot get SupportMapFragment");
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        LatLng VuTrungDucHouse = new LatLng(20.995625, 105.819559);

        mMap.addMarker(new MarkerOptions()
                        .position(VuTrungDucHouse)
                        .title("VEC Team")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_account)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(VuTrungDucHouse));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        mMap.setMyLocationEnabled(true);
    }
}

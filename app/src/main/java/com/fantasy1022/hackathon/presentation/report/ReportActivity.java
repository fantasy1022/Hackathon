package com.fantasy1022.hackathon.presentation.report;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.blankj.utilcode.utils.TimeUtils;
import com.fantasy1022.hackathon.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReportActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener ,OnMapReadyCallback ,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    private final String TAG = ReportActivity.class.getSimpleName();
    private final static String KEY_TITLE = "KEY_TITLE";
    private final static String KEY_SUB = "KEY_SUB";
    private final static String KEY_SUB_OPT = "KEY_SUB_OPT";
    private final static String KEY_SUB_TWO = "KEY_SUB_TWO";
    private final static String KEY_SUB_TWO_OPT = "KEY_SUB_TWO_OPT";
    private GoogleApiClient googleApiClient;
    private GoogleMap googleMap;
    private boolean locationPermissionGranted;
    private Location lastKnownLocation;
    private final int DEFAULT_ZOOM = 15;
    private final LatLng DEFAULT_LOCATION = new LatLng(-33.8523341, 151.2106085); //Use taipei
    private final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private CameraPosition cameraPosition;


    @BindView(R.id.dateTxt)
    TextView dateTxt;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.questionCatTxt)
    TextView questionCatTxt;
    @BindView(R.id.questionCatTxt2)
    TextView questionCatTxt2;
    @BindViews({R.id.itemOne, R.id.itemTwo, R.id.itemThree})
    CheckBox[] item;
    @BindViews({R.id.itemOne2, R.id.itemTwo2, R.id.itemThree2})
    CheckBox[] item2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);
        dateTxt.setText(TimeUtils.getCurTimeString(new SimpleDateFormat("yyyy/MM/dd")));
        String title = getIntent().getStringExtra(KEY_TITLE);
        toolbar.setTitle(title);
        String sub = getIntent().getStringExtra(KEY_SUB);
        questionCatTxt.setText(sub);
        String subTwo = getIntent().getStringExtra(KEY_SUB_TWO);
        questionCatTxt2.setText(subTwo);

        String[] subOpt = getIntent().getStringArrayExtra(KEY_SUB_OPT);
        for(int i =0;i<subOpt.length;i++){
            item[i].setText(subOpt[i]);
        }

        String[] subOpt2 = getIntent().getStringArrayExtra(KEY_SUB_TWO_OPT);

        for(int i =0;i<subOpt2.length;i++){
            item2[i].setText(subOpt2[i]);
        }
        initGoogleApiClient();
    }


    public static void newIntent(Activity activity, String title, String sub, String[] subOpt, String subTwo, String[] subTwoOpt) {
        Intent intent = new Intent(activity, ReportActivity.class);
        intent.putExtra(KEY_TITLE, title);
        intent.putExtra(KEY_SUB, sub);
        intent.putExtra(KEY_SUB_OPT, subOpt);
        intent.putExtra(KEY_SUB_TWO, subTwo);
        intent.putExtra(KEY_SUB_TWO_OPT, subTwoOpt);
        activity.startActivity(intent);
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        dateTxt.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
    }


    @OnClick(R.id.dateTxt)
    public void onViewClicked() {

        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                ReportActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @OnClick({R.id.itemOne, R.id.itemTwo, R.id.itemThree, R.id.itemOne2, R.id.itemTwo2, R.id.itemThree2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.itemOne:
                break;
            case R.id.itemTwo:
                break;
            case R.id.itemThree:
                break;
            case R.id.itemOne2:
                break;
            case R.id.itemTwo2:
                break;
            case R.id.itemThree2:
                break;
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        getDeviceLocation();
        updateLocationUI();

    }

    private void initGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                        this /* OnConnectionFailedListener */)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        googleApiClient.connect();
    }

    private void updateLocationUI() {

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

        if (locationPermissionGranted) {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            googleMap.setMyLocationEnabled(false);
            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
            lastKnownLocation = null;
        }
    }

    private void getDeviceLocation() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        if (locationPermissionGranted) {
            lastKnownLocation = LocationServices.FusedLocationApi
                    .getLastLocation(googleApiClient);
        }

        // Set the map's camera position to the current location of the device.
        if (cameraPosition != null) {
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        } else if (lastKnownLocation != null) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(lastKnownLocation.getLatitude(),
                            lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
            Log.d(TAG, "lat:" + lastKnownLocation.getLatitude() + " lon:" + lastKnownLocation.getLongitude());
        } else {
            Log.d(TAG, "Current location is null. Using defaults.");
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, DEFAULT_ZOOM));
            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }
}

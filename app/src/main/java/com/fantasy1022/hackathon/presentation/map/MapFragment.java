package com.fantasy1022.hackathon.presentation.map;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fantasy1022.hackathon.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MapFragment extends Fragment implements OnMapReadyCallback, MapContract.View,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private final String TAG = getClass().getSimpleName();
    private MapPresenter mapPresenter;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mapPresenter == null) {
            Log.d(TAG, "mapPresenter");
            mapPresenter = new MapPresenter(getActivity());
            mapPresenter.initGoogleApiClient(this,this);
        }else{
            mapPresenter.updateLocationUI();
            mapPresenter.getDeviceLocation();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG,"onMapReady");
        mapPresenter.setGoogleMap(googleMap);
        mapPresenter.updateLocationUI();
        mapPresenter.getDeviceLocation();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "Play services connection onConnected");
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Play services connection suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "Play services connection failed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mapPresenter.handlePermission(requestCode, grantResults);
    }

//    @Override
//    public void onDestroyView() {
//        Log.d(TAG,"onDestroyView");
//        mapPresenter.disconnet();
//        super.onDestroyView();
//
//    }
}

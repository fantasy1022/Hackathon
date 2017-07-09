package com.fantasy1022.hackathon.presentation.map;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.IntDef;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.fantasy1022.hackathon.R;
import com.fantasy1022.hackathon.entity.PlaceDetailEntity;
import com.fantasy1022.hackathon.entity.PlaceEntity;
import com.fantasy1022.hackathon.presentation.base.BasePresenter;
import com.fantasy1022.hackathon.presentation.main.MainContract;
import com.fantasy1022.hackathon.repository.FirebaseRepository;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Created by fantasy_apple on 2017/7/1.
 */

public class MapsPresenter extends BasePresenter<MainContract.View> implements MapsContract.Presenter,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener {

    private static final String TAG = MapsFragment.class.getSimpleName();
    private final int DEFAULT_ZOOM = 15;
    private final LatLng DEFAULT_LOCATION = new LatLng(-33.8523341, 151.2106085); //Use taipei
    private final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    // The entry point to Google Play services, used by the Places API and Fused Location Provider.
    private GoogleApiClient googleApiClient;
    private GoogleMap googleMap;
    private FragmentActivity fragmentActivity;
    private boolean locationPermissionGranted;
    private Location lastKnownLocation;
    private CameraPosition cameraPosition;


    public MapsPresenter(FragmentActivity fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
    }

    @Override
    public void initGoogleApiClient(OnConnectionFailedListener listener, ConnectionCallbacks connectionCallbacks) {
        googleApiClient = new GoogleApiClient.Builder(fragmentActivity)
                .enableAutoManage(fragmentActivity /* FragmentActivity */,
                        listener /* OnConnectionFailedListener */)
                .addConnectionCallbacks(connectionCallbacks)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        googleApiClient.connect();
    }

    @Override
    public void getDateFromFirebase(String key) {
        FirebaseRepository.getInstance().getDateFromFirebase(key);
    }

    @Override
    public void setGoogleMap(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnInfoWindowClickListener(this);
    }


    @Override
    public void updateLocationUI() {

        if (ContextCompat.checkSelfPermission(fragmentActivity.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(fragmentActivity,
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

    @Override
    public void getDeviceLocation() {
        if (ContextCompat.checkSelfPermission(fragmentActivity.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(fragmentActivity,
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

    @Override
    public void handlePermission(int requestCode, int[] grantResults) {
        locationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    @Override
    public void updateMapMaker(@MapTypeMode int index, int weekValue) {
        ArrayList<PlaceDetailEntity> placeDetailEntities = new ArrayList<>();
        PlaceEntity placeEntity = FirebaseRepository.getInstance().getPlaceEntity();
        if (placeEntity != null) {
            switch (index) {
                case TYPE_ROAD:
                    placeDetailEntities = placeEntity.getRoad();

                    break;
                case TYPE_ENVIRONMENT:
                    placeDetailEntities = placeEntity.getEnvironment();
                    break;
                case TYPE_TREE:
                    placeDetailEntities = placeEntity.getTree();
                    break;
                case TYPE_PARK:
                    placeDetailEntities = placeEntity.getTree();
                    break;
                case TYPE_OTHER:
                    placeDetailEntities = placeEntity.getOther();
                    break;
            }
        }
        if (googleMap != null) {
            googleMap.clear();
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            for (int i = 0; i < placeDetailEntities.size(); i++) {
                if (placeDetailEntities.get(i).getTime() <= weekValue) {
                    LatLng latLng = new LatLng(placeDetailEntities.get(i).getLat(), placeDetailEntities.get(i).getLon());

//                    int px = fragmentActivity.getResources().getDimensionPixelSize(R.dimen.map_dot_marker_size);
//                    Bitmap mDotMarkerBitmap = Bitmap.createBitmap(px, px, Bitmap.Config.ARGB_8888);
//                    Canvas canvas = new Canvas(mDotMarkerBitmap);
//                    Drawable shape;
                    BitmapDescriptor bitmapDescriptor;
                    switch (index) {
                        case TYPE_ROAD:
                            bitmapDescriptor = getMarkerIcon(ContextCompat.getColor(fragmentActivity, R.color.colorRoad));
                            break;
                        case TYPE_ENVIRONMENT:
                            bitmapDescriptor = getMarkerIcon(ContextCompat.getColor(fragmentActivity, R.color.colorEnvironment));
                            break;
                        case TYPE_TREE:
                            bitmapDescriptor = getMarkerIcon(ContextCompat.getColor(fragmentActivity, R.color.colorTree));
                            break;
                        case TYPE_PARK:
                            bitmapDescriptor = getMarkerIcon(ContextCompat.getColor(fragmentActivity, R.color.colorPark));
                            break;
                        case TYPE_OTHER:
                        default:
                            bitmapDescriptor = getMarkerIcon(ContextCompat.getColor(fragmentActivity, R.color.colorOther));
                            break;
                    }
                    String  content = String.format("立案編號:%s\n陳情主旨:%s\n類別:%s\n立案時間:%s\n陳情地址:%s\n按讚人數：%s",
                            placeDetailEntities.get(i).getNum(),
                            placeDetailEntities.get(i).getQuestion(),
                            placeDetailEntities.get(i).getCategory(),
                            placeDetailEntities.get(i).getFilingTime(),
                            placeDetailEntities.get(i).getAddress(),
                            placeDetailEntities.get(i).getThumb());

                    MarkerOptions markerOptions = new MarkerOptions()
                            .icon(bitmapDescriptor)
                            .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                            .position(latLng)
                            .title(content)
                            .snippet(placeDetailEntities.get(i).getPictureUrl());//Bring picture url

                    MapInfoWindowAdapter adapter = new MapInfoWindowAdapter(fragmentActivity);
                    googleMap.setInfoWindowAdapter(adapter);

                    googleMap.addMarker(markerOptions);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, googleMap.getCameraPosition().zoom));
                }
            }
        }

    }


    public BitmapDescriptor getMarkerIcon(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }

    @Override
    public void disconnet() {
//        googleApiClient.disconnect();
//        googleApiClient = null;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d(TAG, "marker:" + marker.getTitle());
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Log.d(TAG, "marker:" + marker.getTitle());
    }

    @IntDef({TYPE_ROAD, TYPE_ENVIRONMENT, TYPE_TREE, TYPE_PARK, TYPE_OTHER})
    public @interface MapTypeMode {
    }

    public static final int TYPE_ROAD = 0;
    public static final int TYPE_ENVIRONMENT = 1;
    public static final int TYPE_TREE = 2;
    public static final int TYPE_PARK = 3;
    public static final int TYPE_OTHER = 4;
}

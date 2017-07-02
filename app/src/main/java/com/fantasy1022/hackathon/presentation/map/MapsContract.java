package com.fantasy1022.hackathon.presentation.map;

import com.fantasy1022.hackathon.presentation.base.MvpPresenter;
import com.fantasy1022.hackathon.presentation.base.MvpView;
import com.fantasy1022.hackathon.presentation.main.MainContract;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.maps.GoogleMap;

/**
 * Created by fantasy_apple on 2017/7/1.
 */

public interface MapsContract {

    interface View extends MvpView {


    }

    interface Presenter extends MvpPresenter<MainContract.View> {

        void initGoogleApiClient(OnConnectionFailedListener listener, ConnectionCallbacks connectionCallbacks);

        void setGoogleMap(GoogleMap googleMap);

        void updateLocationUI();

        void getDeviceLocation();

        void handlePermission(int requestCode, int[] grantResults);

        void disconnet();
    }

}

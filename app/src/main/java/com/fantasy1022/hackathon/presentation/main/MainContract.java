package com.fantasy1022.hackathon.presentation.main;

import android.support.v4.app.Fragment;

import com.fantasy1022.hackathon.presentation.base.MvpPresenter;
import com.fantasy1022.hackathon.presentation.base.MvpView;

/**
 * Created by fantasy_apple on 2017/6/29.
 */

public interface MainContract {

    interface View extends MvpView {
        void showSignInInfo(String name, String email);

        void goToSignInPage();

        void showLoading();//Used for getRealDataBaseFromFirebase

        void hideLoading();

        void setToolBarName(String name);
    }


    interface Presenter extends MvpPresenter<View> {
        void checkSignInStatus();

        void onStart();

        void onStop();

        void signOut();

        void parseNotificationData(String data);

        void removeListener();

    }
}

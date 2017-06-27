package com.fantasy1022.hackathon.presentation.signin;

import com.fantasy1022.hackathon.presentation.base.MvpPresenter;
import com.fantasy1022.hackathon.presentation.base.MvpView;

/**
 * Created by fantasy_apple on 2017/6/27.
 */

public interface SignInContract {

    interface View extends MvpView {
        void goToNextPage();

        void showError();

        void disableEmailPasswordEdit();

        void enableEmailPasswordEdit();

        void clearPasswordEdit();

    }

    interface Presenter extends MvpPresenter<View> {

        void signInToFirebase(String email, String password);
    }
}
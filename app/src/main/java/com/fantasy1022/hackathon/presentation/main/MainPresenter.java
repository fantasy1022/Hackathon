package com.fantasy1022.hackathon.presentation.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.blankj.utilcode.utils.SPUtils;
import com.fantasy1022.hackathon.R;
import com.fantasy1022.hackathon.common.Constant;
import com.fantasy1022.hackathon.presentation.base.BasePresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by fantasy_apple on 2017/6/29.
 */

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    private final String TAG = "MainActivity";
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    private Context context;
    private SPUtils sp;

    public MainPresenter(Context context) {
        this.context = context;
        sp = new SPUtils(context, Constant.KEY_SP_DATA);
        auth = FirebaseAuth.getInstance();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid() + " topic name:" + sp.getString(Constant.KEY_SP_TOPIC_NAME));
                    checkSignInStatus();
                    //getRealDataFromFirebase(sp.getString(Constant.KEY_SP_TOPIC_NAME));
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    getView().goToSignInPage();
                }
            }
        };
    }

    @Override
    public void checkSignInStatus() {
        String email = sp.getString(Constant.KEY_SP_EMAIL);
        String name = sp.getString(Constant.KEY_SP_TOPIC_NAME);
        Log.d(TAG, "eamil:" + email + " name:" + name);
        getView().showSignInInfo("UXDDD", "");
    }

    @Override
    public void onStart() {
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

    @Override
    public void signOut() {
        //FirebaseMessaging.getInstance().unsubscribeFromTopic(sp.getString(Constant.KEY_SP_TOPIC_NAME));
        sp.clear();
        auth.signOut();
    }

    @Override
    public void parseNotificationData(String data) {

    }

    @Override
    public void removeListener() {
        //FirebaseManager.getInstance().removeListener();
    }
}

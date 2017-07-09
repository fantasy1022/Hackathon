package com.fantasy1022.hackathon.presentation.signin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.blankj.utilcode.utils.SPUtils;
import com.fantasy1022.hackathon.common.Constant;
import com.fantasy1022.hackathon.presentation.base.BasePresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by fantasy_apple on 2017/6/27.
 */

public class SignInPresenter extends BasePresenter<SignInContract.View> implements SignInContract.Presenter {

    private final String TAG = SignInActivity.class.getSimpleName();
    private FirebaseAuth auth;
    private Context context;
    private SPUtils sp;

    public SignInPresenter(Context context) {
        this.context = context;
        auth = FirebaseAuth.getInstance();
        sp = new SPUtils(context, Constant.KEY_SP_DATA);
    }

    @Override
    public void signInToFirebase(@NonNull final String email, @NonNull final String password) {
        getView().disableEmailPasswordEdit();
        Log.d(TAG, "email:" + email + " password:" + password);

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Result:" + task.getResult().toString());
//                            String topicName = UiUtils.extractTopicFromEmail(email);
//                            FirebaseMessaging.getInstance().subscribeToTopic(topicName);
                            sp.putString(Constant.KEY_SP_EMAIL, email);
                            sp.putString(Constant.KEY_SP_PASSWORD, password);
                            // sp.putString(Constant.KEY_SP_TOPIC_NAME, topicName);
                            //  Log.d(TAG, "topic name:" + topicName);
                            getView().goToNextPage();
                        } else {
                            Log.d(TAG, "Exception:" + task.getException());
                            getView().showError();
                            getView().clearPasswordEdit();
                            getView().enableEmailPasswordEdit();
                        }

                    }
                });

    }
}

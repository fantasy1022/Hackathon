package com.fantasy1022.hackathon.presentation.signin;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.blankj.utilcode.utils.NetworkUtils;
import com.dd.processbutton.iml.ActionProcessButton;
import com.fantasy1022.hackathon.R;
import com.fantasy1022.hackathon.common.Dialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;

public class SignInActivity extends AppCompatActivity implements SignInContract.View {

    public final String TAG = getClass().getSimpleName();

    private SignInContract.Presenter signInPresenter;
    private String email = "", password = "";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.emailEditText)
    EditText emailEditText;
    @BindView(R.id.emailInputLay)
    TextInputLayout emailInputLay;
    @BindView(R.id.passwordEditText)
    EditText passwordEditText;
    @BindView(R.id.passwordInputLay)
    TextInputLayout passwordInputLay;
    @BindView(R.id.signInBtn)
    ActionProcessButton signInBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        initSignInBtn();
        signInPresenter = new SignInPresenter(this);
        signInPresenter.attachView(this);
        toolbar.setTitle(R.string.title);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        signInPresenter.detachView();
    }

    private void initSignInBtn() {
        signInBtn.setEnabled(false);
        signInBtn.setTextColor(Color.GRAY);
        signInBtn.setMode(ActionProcessButton.Mode.ENDLESS);
    }

    @Override
    public void goToNextPage() {
        Log.d(TAG, "goToNextPage");
        signInBtn.setProgress(100);
        finish();
        //TODO: add trasiction
    }

    @Override
    public void showError() {
        signInBtn.setEnabled(true);
        signInBtn.setProgress(0);
        Dialog.showInfoDialog(this, getString(R.string.dialog_error_title), getString(R.string.dialog_sign_in_error_message));
    }

    @Override
    public void clearPasswordEdit() {
        passwordEditText.setText("");
    }


    @Override
    public void enableEmailPasswordEdit() {
        emailEditText.setEnabled(true);
        passwordEditText.setEnabled(true);
    }

    @Override
    public void disableEmailPasswordEdit() {
        emailEditText.setEnabled(false);
        passwordEditText.setEnabled(false);
    }


    @OnClick(R.id.signInBtn)
    public void onClick() {
        if (NetworkUtils.isAvailable(this)) {
            signInBtn.setEnabled(false);
            signInBtn.setProgress(10);
            signInPresenter.signInToFirebase(emailEditText.getText().toString().concat("@gmail.com"), passwordEditText.getText().toString());
        } else {
            Dialog.showInfoDialog(this, getString(R.string.dialog_error_title), getString(R.string.dialog_network_error_message));
        }
    }


    @OnTextChanged(R.id.emailEditText)
    public void onEmailEdit(Editable editable) {
        email = editable.toString();
        checkSignInBtnEnable();
    }

    @OnTextChanged(R.id.passwordEditText)
    public void onPasswordEdit(Editable editable) {
        password = editable.toString();
//        if (!RegexUtils.isEmail(email)) {
//            emailInputLay.setError(getString(R.string.emaila_format_error));
//        } else {
//            emailInputLay.setError(null);
//        }
        checkSignInBtnEnable();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCompat.finishAffinity(this);//Clear all activity before SignInActivity
    }

    @OnEditorAction(value = R.id.passwordEditText)
    public boolean OnEdit(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_GO) {
            if (NetworkUtils.isAvailable(this)) {
                signInBtn.setEnabled(false);
                signInBtn.setProgress(10);
                signInPresenter.signInToFirebase(emailEditText.getText().toString().concat("@gmail.com"), passwordEditText.getText().toString());
            } else {
                Dialog.showInfoDialog(this, getString(R.string.dialog_error_title), getString(R.string.dialog_network_error_message));
            }
            return true;
        }
        return false;
    }


    private void checkSignInBtnEnable() {
        if ("".equals(email) || "".equals(password)) {
            signInBtn.setEnabled(false);
            signInBtn.setTextColor(Color.GRAY);
        } else {
            signInBtn.setEnabled(true);
            signInBtn.setTextColor(Color.WHITE);
        }
    }


    @OnClick({R.id.phoneLay, R.id.questionLay, R.id.aboutLay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.phoneLay:
                Log.d(TAG, "phoneLay onclick");
                break;
            case R.id.questionLay:
                break;
            case R.id.aboutLay:
                break;
        }
    }
}


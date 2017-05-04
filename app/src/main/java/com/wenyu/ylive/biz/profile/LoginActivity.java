package com.wenyu.ylive.biz.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.wenyu.ylive.R;
import com.wenyu.ylive.base.YLiveActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends YLiveActivity {

    @Bind(R.id.username)
    EditText mPasswordView;

    @Bind(R.id.password)
    EditText mProgressView;

    @OnClick(R.id.email_sign_in_button)
    void onLoginClicked() {

    }

    @OnClick(R.id.register)
    void onRegisterClicked() {

    }

    @Override
    protected int contentId() {
        return R.layout.activity_login;
    }


    public static Intent createIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }
}


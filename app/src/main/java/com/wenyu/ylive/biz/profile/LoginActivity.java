package com.wenyu.ylive.biz.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.trello.rxlifecycle.ActivityEvent;
import com.wenyu.ylive.R;
import com.wenyu.ylive.base.YLiveActivity;
import com.wenyu.ylive.biz.account.AccountConfig;
import com.wenyu.ylive.common.api.service.AccountApiService;
import com.wenyu.ylive.common.bean.User;
import com.wenyu.ylive.common.event.UserInfoUpdateEvent;
import com.wenyu.ylive.common.rx.YLiveSubscriber;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends YLiveActivity {

    @Bind(R.id.username)
    EditText mEtUsername;

    @Bind(R.id.password)
    EditText mEtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.email_sign_in_button)
    void onLoginClicked() {
        String username = mEtUsername.getText().toString();
        String password = mEtPassword.getText().toString();

        if (TextUtils.isEmpty(username)) {
            showToast("用户名不能为空");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            showToast("密码不能为空");
            return;
        }

        showProgressDialog();
        AccountApiService.getAccountApiService(getApplicationContext())
                .login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<User>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new YLiveSubscriber<User>() {
                    @Override
                    public void onResponseFailed(Throwable e) {
                        dismissProgressDialog();
                        showToast("登录失败");
                    }

                    @Override
                    public void onResponseSuccess(User data) {
                        showToast("登录成功");
                        AccountConfig.getIntance(LoginActivity.this).setCurrentUser(data);
                        EventBus.getDefault().post(new UserInfoUpdateEvent());
                        finish();
                    }
                });
    }

    @OnClick(R.id.register)
    void onRegisterClicked() {
        startActivity(RegisterActivity.newIntent(this));
    }

    @Override
    protected int contentId() {
        return R.layout.activity_login;
    }


    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }
}


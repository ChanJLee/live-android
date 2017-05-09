package com.wenyu.ylive.biz.home.nav.view;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.wenyu.mvp.view.BaseMvpView;
import com.wenyu.ylive.R;
import com.wenyu.ylive.biz.home.nav.presenter.HomeNavEventListener;
import com.wenyu.ylive.biz.profile.LoginActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chan on 17/4/4.
 */

public class HomeNavViewImpl extends BaseMvpView<HomeNavEventListener> implements IHomeNavView {

    @Bind(R.id.navigation_login_and_register)
    View mBtnLoginAndRegister;

    @Bind(R.id.navigation_avatar)
    ImageView mIvAvatar;

    @Bind(R.id.navigation_username)
    TextView mTvUsername;

    private RequestManager mRequestManager;

    @Inject
    public HomeNavViewImpl(@NonNull Activity activity) {
        super(activity);

        NavigationView navigationView = (NavigationView) activity.findViewById(R.id.nav_view);
        for (int i = 0; i < navigationView.getHeaderCount(); ++i) {
            ButterKnife.bind(this, navigationView.getHeaderView(i));
        }

        mRequestManager = Glide.with(activity);
    }

    @OnClick(R.id.navigation_login_and_register)
    void onLoginAndRegisterClicked() {
        if (getEventListener() != null) {
            getEventListener().onLoginAndRegisterClicked();
        }
    }

    @OnClick(R.id.navigation_username)
    void onUsernameClicked() {
        //TODO
    }

    @Override
    public void goToLoginAndRegister() {
        getActivity().startActivity(LoginActivity.newIntent(getActivity()));
    }

    @Override
    public void render(final Data data) {
        mIvAvatar.post(new Runnable() {
            @Override
            public void run() {
                mBtnLoginAndRegister.setVisibility(View.GONE);
                mTvUsername.setVisibility(View.VISIBLE);
                mTvUsername.setText(data.username);
                mRequestManager.load(Uri.parse(data.avatarUrl)).into(mIvAvatar);
            }
        });
    }
}

package com.wenyu.ylive.biz.home.thiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.wenyu.apt.MvpInjector;
import com.wenyu.apt.annotations.MvpInject;
import com.wenyu.apt.annotations.MvpPresenter;
import com.wenyu.apt.annotations.MvpView;
import com.wenyu.ylive.R;
import com.wenyu.ylive.base.YLiveActivity;
import com.wenyu.ylive.biz.home.thiz.inject.HomeComponent;
import com.wenyu.ylive.biz.home.thiz.inject.HomeModule;
import com.wenyu.ylive.biz.home.thiz.presenter.HomePresenterImpl;
import com.wenyu.ylive.biz.home.thiz.view.HomeViewImpl;

import javax.inject.Inject;

@MvpInject(module = HomeModule.class, component = HomeComponent.class)
public class HomeActivity extends YLiveActivity {
    private static final long MIN_DURATION = 2000;

    private long mLastClickedTimePoint = 0;

    @MvpPresenter
    HomePresenterImpl mHomePresenter;

    @MvpView
    @Inject
    HomeViewImpl mHomeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MvpInjector.inject(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }

        mHomePresenter.attach();
        mHomePresenter.init();
    }

    @Override
    protected void onDestroy() {
        mHomePresenter.detach();
        super.onDestroy();
    }

    @Override
    protected int contentId() {
        return R.layout.activity_home;
    }

    @Override
    public void onBackPressed() {

        if (mHomePresenter.onBackPressed()) {
            return;
        }

        long currentTag = System.currentTimeMillis();
        if (currentTag - mLastClickedTimePoint < MIN_DURATION) {
            finish();
            return;
        }

        mLastClickedTimePoint = currentTag;
        showToast("再按一次将退出");
    }

    @Override
    protected Toolbar findToolbarById() {
        return (Toolbar) findViewById(R.id.home_toolbar);
    }

    @Override
    protected boolean displayHomeAsUpEnabled() {
        return false;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, HomeActivity.class);
    }
}

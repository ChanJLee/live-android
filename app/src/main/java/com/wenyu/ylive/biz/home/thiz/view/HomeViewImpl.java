package com.wenyu.ylive.biz.home.thiz.view;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import com.wenyu.mvp.view.BaseMvpView;
import com.wenyu.mvp.presenter.MvpEventListener;
import com.wenyu.ylive.R;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chan on 17/3/29.
 */

public class HomeViewImpl extends BaseMvpView<MvpEventListener> implements IHomeView {

    @Bind(R.id.home_toolbar)
    Toolbar mToolbar;

    @Bind(R.id.home_drawer_layout)
    DrawerLayout mDrawerLayout;

    @Inject
    public HomeViewImpl(@NonNull Activity activity) {
        super(activity);

        ButterKnife.bind(this, activity.findViewById(R.id.home_drawer_layout));
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(activity, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void goToBroadcast() {
        //TODO
    }

    @Override
    public void goToRegisterAnchor() {
        //TODO
    }

    @Override
    public boolean dismissDrawerLayout() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }

        return false;
    }
}

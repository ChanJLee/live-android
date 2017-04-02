package com.wenyu.ylive.biz.home.main.inject;

import android.app.Activity;
import android.content.Context;

import com.wenyu.mvp.annotation.UserScope;
import com.wenyu.ylive.biz.home.thiz.presenter.HomePresenterImpl;

import dagger.Component;

/**
 * Created by chan on 17/4/2.
 */
@UserScope
@Component(modules = HomeMainModule.class)
public interface HomeMainComponent {

    Activity getActivity();

    Context getApplicationContext();

    void inject(HomePresenterImpl presenter);
}

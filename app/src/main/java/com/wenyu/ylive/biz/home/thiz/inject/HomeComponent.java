package com.wenyu.ylive.biz.home.thiz.inject;

import android.app.Activity;

import com.wenyu.mvp.annotation.ActivityScope;
import com.wenyu.ylive.biz.home.thiz.HomeActivity;

import dagger.Component;

/**
 * Created by chan on 17/3/29.
 */
@ActivityScope
@Component(modules = HomeModule.class)
public interface HomeComponent {

    Activity getActivity();

    void inject(HomeActivity activity);
}

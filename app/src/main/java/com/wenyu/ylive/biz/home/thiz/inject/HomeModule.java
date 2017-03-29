package com.wenyu.ylive.biz.home.thiz.inject;

import android.app.Activity;

import com.wenyu.mvp.annotation.ActivityScope;
import com.wenyu.ylive.biz.home.thiz.HomeActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by chan on 17/3/29.
 */
@Module
public class HomeModule {
    private HomeActivity mHomeActivity;

    public HomeModule(HomeActivity homeActivity) {
        mHomeActivity = homeActivity;
    }

    @ActivityScope
    @Provides
    public Activity provideActivity() {
        return mHomeActivity;
    }
}

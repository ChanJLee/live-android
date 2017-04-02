package com.wenyu.ylive.biz.home.main.inject;

import android.app.Activity;
import android.content.Context;

import com.wenyu.mvp.annotation.UserScope;
import com.wenyu.ylive.biz.home.main.presenter.HomeMainPresenter;
import com.wenyu.ylive.biz.home.thiz.presenter.HomePresenterImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by chan on 17/4/2.
 */

@Module
public class HomeMainModule {
    private HomePresenterImpl mHomePresenter;

    public HomeMainModule(HomePresenterImpl homePresenter) {
        mHomePresenter = homePresenter;
    }

    @UserScope
    @Provides
    public Activity provideActivity() {
        return mHomePresenter.getView().getActivity();
    }

    @UserScope
    @Provides
    public Context provideApplicationContext() {
        return provideActivity().getApplicationContext();
    }
}

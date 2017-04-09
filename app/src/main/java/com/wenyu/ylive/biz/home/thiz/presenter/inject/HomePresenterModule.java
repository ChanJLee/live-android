package com.wenyu.ylive.biz.home.thiz.presenter.inject;

import android.app.Activity;
import android.content.Context;

import com.wenyu.mvp.annotation.UserScope;
import com.wenyu.ylive.biz.home.thiz.presenter.HomePresenterImpl;
import com.wenyu.ylive.common.api.service.AccountApiService;
import com.wenyu.ylive.common.api.service.YLiveApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by chan on 17/4/6.
 */
@Module
public class HomePresenterModule {
    private HomePresenterImpl mHomePresenter;

    public HomePresenterModule(HomePresenterImpl homePresenter) {
        mHomePresenter = homePresenter;
    }

    @UserScope
    @Provides
    public Activity provideActivity() {
        return mHomePresenter.getView().getActivity();
    }

    @Provides
    @UserScope
    public AccountApiService provideAccountApiService() {
        return AccountApiService.getAccountApiService(getApplicationContext());
    }

    @Provides
    @UserScope
    public YLiveApiService provideYLiveApiService() {
        return YLiveApiService.getYLiveApiService(getApplicationContext());
    }

    private Context getApplicationContext() {
        return provideActivity().getApplication();
    }
}

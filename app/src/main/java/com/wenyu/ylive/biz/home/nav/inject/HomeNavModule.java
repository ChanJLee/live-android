package com.wenyu.ylive.biz.home.nav.inject;

import android.app.Activity;

import com.wenyu.mvp.annotation.UserScope;
import com.wenyu.ylive.biz.home.thiz.presenter.HomePresenterImpl;
import com.wenyu.ylive.common.api.service.AccountApiService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by chan on 17/4/4.
 */
@Module
public class HomeNavModule {
    private HomePresenterImpl mHomePresenter;

    public HomeNavModule(HomePresenterImpl homePresenter) {
        mHomePresenter = homePresenter;
    }

    @Provides
    @UserScope
    public Activity provideActivity() {
        return mHomePresenter.getView().getActivity();
    }

    @Provides
    @UserScope
    public AccountApiService provideAccountApiService() {
        return AccountApiService.getAccountApiService(provideActivity().getApplicationContext());
    }
}

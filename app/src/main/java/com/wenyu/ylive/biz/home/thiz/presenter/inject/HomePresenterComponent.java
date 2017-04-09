package com.wenyu.ylive.biz.home.thiz.presenter.inject;

import android.app.Activity;

import com.wenyu.mvp.annotation.UserScope;
import com.wenyu.ylive.biz.home.thiz.presenter.HomePresenterImpl;
import com.wenyu.ylive.common.api.service.AccountApiService;
import com.wenyu.ylive.common.api.service.YLiveApiService;

import dagger.Component;

/**
 * Created by chan on 17/4/6.
 */

@UserScope
@Component(modules = HomePresenterModule.class)
public interface HomePresenterComponent {

    Activity getActivity();

    AccountApiService getAccountApiService();

    YLiveApiService getYLiveApiService();

    void inject(HomePresenterImpl homePresenter);
}

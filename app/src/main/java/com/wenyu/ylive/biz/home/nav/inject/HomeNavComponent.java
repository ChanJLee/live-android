package com.wenyu.ylive.biz.home.nav.inject;

import android.app.Activity;

import com.wenyu.mvp.annotation.UserScope;
import com.wenyu.ylive.biz.home.thiz.presenter.HomePresenterImpl;
import com.wenyu.ylive.common.api.service.AccountApiService;

import dagger.Component;

/**
 * Created by chan on 17/4/4.
 */
@UserScope
@Component(modules = HomeNavModule.class)
public interface HomeNavComponent {
    Activity getActivity();

    AccountApiService getAccountApiService();

    void inject(HomePresenterImpl presenter);
}

package com.wenyu.ylive.biz.home.nav.model;

import com.wenyu.mvp.annotation.Tag;
import com.wenyu.mvp.model.BaseModel;
import com.wenyu.ylive.common.api.service.AccountApiService;
import com.wenyu.ylive.common.bean.User;

import javax.inject.Inject;

/**
 * Created by chan on 17/4/4.
 */

public class HomeNavModelImpl extends BaseModel implements IHomeNavModel {
    private AccountApiService mAccountApiService;

    @Inject
    public HomeNavModelImpl(AccountApiService accountApiService) {
        mAccountApiService = accountApiService;
    }

    @Override
    public User getCurrentUser() {
        return mAccountApiService.getCurrentUser();
    }
}

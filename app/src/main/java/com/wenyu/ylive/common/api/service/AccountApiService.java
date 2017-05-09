package com.wenyu.ylive.common.api.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.JsonElement;
import com.wenyu.network.BaseApiService;
import com.wenyu.ylive.BuildConfig;
import com.wenyu.ylive.biz.account.AccountConfig;
import com.wenyu.ylive.common.api.AccountApi;
import com.wenyu.ylive.common.bean.User;

import rx.Observable;

/**
 * Created by jiacheng.li on 17/1/15.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public class AccountApiService extends BaseApiService<AccountApi> {

    @SuppressLint("StaticFieldLeak")
    private static AccountApiService sAccountApiService;

    private AccountApiService(Context context, String domain, @NonNull Class<AccountApi> accountApiClass) {
        super(context, domain, AccountApi.class);
    }

    public static AccountApiService getAccountApiService(Context appContext) {
        if (sAccountApiService == null) {
            synchronized (AccountApiService.class) {
                if (sAccountApiService == null) {
                    sAccountApiService = new AccountApiService(appContext, BuildConfig.HTTP_BASE_URI, AccountApi.class);
                }
            }
        }

        return sAccountApiService;
    }

    public Observable<User> login(String username, String password) {
        return convert(getAPI().login(username, password));
    }

    public Observable<JsonElement> register(String username, String password, String email) {
        return convert(getAPI().register(username, password, email));
    }

    public Observable<JsonElement> logout() {
        return convert(getAPI().logout());
    }

    private static final String KEY_USER = "AccountServiceUser";

    public User getCurrentUser() {
        return AccountConfig.getIntance(getContext()).getCurrentUser();
    }
}

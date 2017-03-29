package com.wenyu.ylive.common.api.service;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.JsonElement;
import com.wenyu.network.BaseApiService;
import com.wenyu.ylive.BuildConfig;
import com.wenyu.ylive.common.api.AccountApi;
import com.wenyu.ylive.common.bean.User;
import com.wenyu.ylive.utils.AppClient;

import rx.Observable;

/**
 * Created by jiacheng.li on 17/1/15.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public class AccountApiService extends BaseApiService<AccountApi> {

	private static AccountApiService sAccountApiService;

	public AccountApiService(Context context, String domain, @NonNull Class<AccountApi> accountApiClass) {
		super(context, domain, accountApiClass);
	}

	public static AccountApiService getAccountApiService() {
		if (sAccountApiService == null) {
			synchronized (AccountApiService.class) {
				if (sAccountApiService == null) {
					sAccountApiService = new AccountApiService(AppClient.getAppContext(), BuildConfig.HTTP_BASE_URI, AccountApi.class);
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
}

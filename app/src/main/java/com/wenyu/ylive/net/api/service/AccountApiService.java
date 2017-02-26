package com.wenyu.ylive.net.api.service;

import android.content.Context;

import com.google.gson.JsonElement;
import com.wenyu.http.core.BaseApiService;
import com.wenyu.ylive.net.api.AccountApi;
import com.wenyu.ylive.net.model.User;

import rx.Observable;

/**
 * Created by jiacheng.li on 17/1/15.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public class AccountApiService extends BaseApiService<AccountApi> {

	private static AccountApiService sAccountApiService;

	private AccountApiService(Context context) {
		super(context, AccountApi.class);
	}

	public static AccountApiService getAccountApiService(Context context) {
		if (sAccountApiService == null) {
			synchronized (AccountApiService.class) {
				if (sAccountApiService == null) {
					sAccountApiService = new AccountApiService(context);
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

package com.wenyu.ylive.net.api.service;

import android.support.annotation.NonNull;

import com.wenyu.ylive.net.api.AccountApi;
import com.wenyu.ylive.net.core.BaseApiService;

/**
 * Created by jiacheng.li on 17/1/15.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public class AccountApiService extends BaseApiService<AccountApi> {

	private static AccountApiService sAccountApiService;

	private AccountApiService() {
		super(AccountApi.class);
	}

	public static AccountApiService getAccountApiService() {
		if (sAccountApiService == null) {
			synchronized (AccountApiService.class) {
				if (sAccountApiService == null) {
					sAccountApiService = new AccountApiService();
				}
			}
		}

		return sAccountApiService;
	}
}

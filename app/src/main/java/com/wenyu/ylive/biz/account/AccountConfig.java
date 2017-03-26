package com.wenyu.ylive.biz.account;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.wenyu.ylive.net.model.User;

/**
 * Created by jiacheng.li on 17/3/18.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public class AccountConfig {
	private static AccountConfig sAccountConfig;
	private static final String CONFIG_NAME = "account_config";
	private static final String KEY_USER = "user";
	private SharedPreferences mSharedPreferences;

	private AccountConfig(Context appContext) {
		mSharedPreferences = appContext.getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
	}

	public User getCurrentUser() {
		String userJson = mSharedPreferences.getString(KEY_USER, null);
		if (TextUtils.isEmpty(userJson)) {
			return User.anonymous;
		}

		return new Gson().fromJson(userJson, User.class);
	}

	public void setCurrentUser(User user) {
		String userJson = new Gson().toJson(user);
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putString(KEY_USER, userJson);
		editor.commit();
	}

	public static AccountConfig getIntance(Context appContext) {
		if (sAccountConfig == null) {
			synchronized (AccountConfig.class) {
				if (sAccountConfig == null) {
					sAccountConfig = new AccountConfig(appContext);
				}
			}
		}
		return sAccountConfig;
	}
}

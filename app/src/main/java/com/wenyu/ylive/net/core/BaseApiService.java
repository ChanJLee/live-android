package com.wenyu.ylive.net.core;

import android.support.annotation.NonNull;

import com.wenyu.ylive.utils.AppClient;

/**
 * Created by jiacheng.li on 17/1/15.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public class BaseApiService <API> {

	public static final int CODE_EXCEPTION = 0x300;
	public static final int CODE_NO_AUTHENTICATION = 0x400;

	private API mAPI;

	public BaseApiService(@NonNull Class<API> apiClass) {
		mAPI = YLiveClient.getYLiveClient(AppClient.getAppContext()).getCore().create(apiClass);
	}

	public API getAPI() {
		return mAPI;
	}

	protected <T> T convert (@NonNull BaseResponse<T> response) {

		if (response.statusCode == CODE_EXCEPTION || response.statusCode == CODE_NO_AUTHENTICATION) {
			throw new YLiveException(response.statusCode, response.message);
		}

		return response.data;
	}
}

package com.wenyu.ylive.utils;


import com.wenyu.ylive.base.YLiveApplication;

/**
 * Created by jiacheng.li on 17/1/15.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public class AppClient {
	public static YLiveApplication getAppContext() {
		return YLiveApplication.getYLiveApplication();
	}
}

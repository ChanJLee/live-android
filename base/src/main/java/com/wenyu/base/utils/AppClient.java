package com.wenyu.base.utils;


import com.wenyu.base.YLiveApplication;

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

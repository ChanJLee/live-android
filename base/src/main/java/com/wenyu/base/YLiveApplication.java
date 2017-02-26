package com.wenyu.base;

import android.app.Application;

/**
 * Created by jiacheng.li on 17/1/15.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public class YLiveApplication extends Application {
	private static YLiveApplication sYLiveApplication;

	@Override
	public void onCreate() {
		super.onCreate();
		sYLiveApplication = this;
	}

	public static YLiveApplication getYLiveApplication() {
		return sYLiveApplication;
	}
}

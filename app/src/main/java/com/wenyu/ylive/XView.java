package com.wenyu.ylive;

import android.app.Application;

import com.wenyu.ylive.biz.home.MainActivity;

import javax.inject.Inject;

/**
 * Created by jiacheng.li on 17/3/2.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public class XView {
	private MainActivity mMainActivity;
	private Application mApplication;

	@Inject
	public XView(MainActivity mainActivity, Application application) {
		mMainActivity = mainActivity;
		mApplication = application;
	}
}

package com.wenyu.ylive.base;

import android.app.Application;

import com.wenyu.ylive.DaggerMainDependencyComponent;
import com.wenyu.ylive.MainDependencyComponent;
import com.wenyu.ylive.MainDependencyModule;

/**
 * Created by jiacheng.li on 17/1/15.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public class YLiveApplication extends Application {
	private static YLiveApplication sYLiveApplication;
	private static MainDependencyComponent mMainDependencyComponent;

	@Override
	public void onCreate() {
		super.onCreate();
		sYLiveApplication = this;
		mMainDependencyComponent = DaggerMainDependencyComponent.builder().mainDependencyModule(new MainDependencyModule(this)).build();
	}

	public static YLiveApplication getYLiveApplication() {
		return sYLiveApplication;
	}

	public static MainDependencyComponent getmMainDependencyComponent() {
		return mMainDependencyComponent;
	}
}

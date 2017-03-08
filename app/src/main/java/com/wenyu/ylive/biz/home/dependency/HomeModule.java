package com.wenyu.ylive.biz.home.dependency;

import android.app.Activity;
import android.util.Log;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jiacheng.li on 17/3/8.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
@Module
public class HomeModule {
	Activity mActivity;

	public HomeModule(Activity activity) {
		mActivity = activity;
	}

	@Provides
	@Singleton
	public int provideId() {
		Log.d("chan_debug", "provide id");
		return 0;
	}
}

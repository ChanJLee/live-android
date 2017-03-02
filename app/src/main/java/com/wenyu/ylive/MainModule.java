package com.wenyu.ylive;

import android.app.Activity;

import com.wenyu.mvp.annotation.PerActivity;
import com.wenyu.ylive.biz.home.MainActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jiacheng.li on 17/3/2.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
@Module
public class MainModule {
	public MainActivity mMainActivity;

	public MainModule(MainActivity mainActivity) {
		mMainActivity = mainActivity;
	}

	@PerActivity
	@Provides
	public MainActivity provideActivity() {
		return mMainActivity;
	}
}

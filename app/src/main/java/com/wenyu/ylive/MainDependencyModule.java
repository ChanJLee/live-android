package com.wenyu.ylive;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jiacheng.li on 17/3/7.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
@Module
public class MainDependencyModule {
	Application mApplication;

	public MainDependencyModule(Application application) {
		mApplication = application;
	}

	@Singleton
	@Provides
	public Application provideApplication() {
		return mApplication;
	}
}

package com.wenyu.ylive;

import android.app.Application;

import com.wenyu.ylive.base.YLiveApplication;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jiacheng.li on 17/3/7.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
@Singleton
@Component(modules = MainDependencyModule.class)
public interface MainDependencyComponent {
	Application getApplication();

	void inject(YLiveApplication application);
}

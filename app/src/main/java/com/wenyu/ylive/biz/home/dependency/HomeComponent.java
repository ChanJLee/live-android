package com.wenyu.ylive.biz.home.dependency;

import android.app.Activity;

import com.wenyu.ylive.biz.splash.SplashActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jiacheng.li on 17/3/8.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
@Singleton
@Component(modules = HomeModule.class)
public interface HomeComponent {

	int getId();

	void inject(Activity activity);

	void inject(SplashActivity activity);
}

package com.wenyu.ylive;

import com.wenyu.mvp.annotation.PerActivity;
import com.wenyu.ylive.biz.home.MainActivity;

import dagger.Component;

/**
 * Created by jiacheng.li on 17/3/2.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
@PerActivity
@Component(modules = MainModule.class, dependencies = {MainDependencyComponent.class})
public interface MainComponent {

	MainActivity getActivity();

	void inject(MainActivity mainActivity);
}

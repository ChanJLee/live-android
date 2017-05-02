package com.wenyu.ylive.base;

import android.os.Bundle;

import com.wenyu.mvp.presenter.BaseMvpPresenter;


/**
 * Created by jiacheng.li on 17/1/11.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public abstract class YLiveActivity extends BizActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(contentId());
	}
	
	abstract protected int contentId();
}

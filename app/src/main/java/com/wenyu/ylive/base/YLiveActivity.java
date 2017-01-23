package com.wenyu.ylive.base;

import android.os.Bundle;

import com.wenyu.ylive.mvp.presenter.BaseMvpPresenter;

/**
 * Created by jiacheng.li on 17/1/11.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public abstract class YLiveActivity extends BizActivity {

	private BaseMvpPresenter<?, ?> mBaseMvpPresenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(contentId());

		if (mBaseMvpPresenter != null) {
			mBaseMvpPresenter.attach();
		}
	}

	@Override
	protected void onDestroy() {

		if (mBaseMvpPresenter != null) {
			mBaseMvpPresenter.detach();
		}

		super.onDestroy();
	}

	public void bindPresenter(BaseMvpPresenter<?, ?> mvpPresenter) {
		mBaseMvpPresenter = mvpPresenter;
	}

	abstract protected int contentId();
}

package com.wenyu.mvp.view;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.wenyu.mvp.presenter.MvpEventListener;

import javax.inject.Inject;

/**
 * Created by jiacheng.li on 17/3/7.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public abstract class BaseMvpView<E extends MvpEventListener> implements IMvpView<E> {
	protected Activity mActivity;
	private E mEventListener;

	public BaseMvpView(@NonNull Activity activity) {
		mActivity = activity;
	}

	@Override
	public void getEventListener(E eventListener) {
		mEventListener = eventListener;
	}

	@Override
	public Activity getActivity() {
		return mActivity;
	}

	@Override
	public void showToast(String message) {
		Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void setVisibility(boolean visible) {

	}
}

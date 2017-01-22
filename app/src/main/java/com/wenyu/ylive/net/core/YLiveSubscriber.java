package com.wenyu.ylive.net.core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.wenyu.ylive.biz.profile.LoginActivity;

import java.lang.ref.SoftReference;

import rx.Subscriber;

/**
 * Created by jiacheng.li on 17/1/22.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public class YLiveSubscriber<T> extends Subscriber<T> {
	private SoftReference<Context> mContextSoftReference;

	public YLiveSubscriber(Context context) {
		mContextSoftReference = new SoftReference<Context>(context);
	}

	@Override
	public void onCompleted() {

	}

	@Override
	public void onError(Throwable e) {
		if (e instanceof YLiveException && ((YLiveException) e).isNoAuthentication()) {
			onAuthFailed();
		}
	}

	@Override
	public void onNext(T t) {

	}

	public void onAuthFailed() {
		if (mContextSoftReference.get() == null) {
			return;
		}

		Context context = mContextSoftReference.get();
		Intent intent = LoginActivity.createIntent(context);
		if (!(context instanceof Activity)) {
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		}
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
	}
}

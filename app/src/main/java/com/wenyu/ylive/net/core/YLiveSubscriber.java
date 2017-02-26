package com.wenyu.ylive.net.core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;

import com.wenyu.http.YLiveBaseSubscriber;
import com.wenyu.ylive.biz.profile.LoginActivity;

/**
 * Created by jiacheng.li on 17/2/26.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public class YLiveSubscriber<T> extends YLiveBaseSubscriber<T> {

	public YLiveSubscriber(Context context) {
		super(context);
	}

	@Override
	public void onAuthFailed() {
		if (mContextSoftReference.get() == null) {
			return;
		}

		Context context = mContextSoftReference.get();
		Intent intent = LoginActivity.createIntent(context);
		context.startActivity(intent);
		if (context instanceof Activity) {
			ActivityCompat.finishAffinity((Activity) context);
		}
	}
}

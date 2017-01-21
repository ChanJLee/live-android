package com.wenyu.ylive.net.core;

import android.support.annotation.NonNull;

import com.wenyu.ylive.utils.AppClient;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by jiacheng.li on 17/1/15.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public class BaseApiService<API> {

	public static final int CODE_EXCEPTION = 0x300;
	public static final int CODE_NO_AUTHENTICATION = 0x400;

	private API mAPI;

	public BaseApiService(@NonNull Class<API> apiClass) {
		mAPI = YLiveClient.getYLiveClient(AppClient.getAppContext()).getCore().create(apiClass);
	}

	protected API getAPI() {
		return mAPI;
	}

	protected <T> Observable<T> convert(@NonNull Observable<YResponse<T>> response) {
		return response.onErrorResumeNext(
				new Func1<Throwable, Observable<? extends YResponse<T>>>() {
					@Override
					public Observable<? extends YResponse<T>> call(Throwable throwable) {
						return Observable.error(throwable);
					}
				})
				.flatMap(new Func1<YResponse<T>, Observable<T>>() {
					@Override
					public Observable<T> call(YResponse<T> response) {
						return Observable.just(response.data);
					}
				});
	}
}

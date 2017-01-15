package com.wenyu.ylive.net;


import android.content.Context;

import com.google.gson.Gson;
import com.wenyu.ylive.BuildConfig;


import java.io.IOException;
import java.net.CookieHandler;
import java.net.URI;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by jiacheng.li on 17/1/15.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public class YLiveClient {

	private static YLiveClient sYLiveClient;
	private Retrofit mRetrofit;

	private YLiveClient(Context context) {
		OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
				.cookieJar(new YLiveCookieJar(PersistentCookieStore.getIntance(context)))
				.addInterceptor(new CsrfTokenInterceptor());


		mRetrofit = new Retrofit.Builder()
				.baseUrl(BuildConfig.BASE_URI)
				.client(clientBuilder.build())
				.addConverterFactory(GsonConverterFactory.create(new Gson()))
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.build();
	}

	public static YLiveClient getYLiveClient(Context context) {

		if (sYLiveClient == null) {
			synchronized (YLiveClient.class) {
				if (sYLiveClient == null) {
					sYLiveClient = new YLiveClient(context);
				}
			}
		}

		return sYLiveClient;
	}

	public Retrofit getClient() {
		return mRetrofit;
	}
}

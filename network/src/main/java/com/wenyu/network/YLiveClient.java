package com.wenyu.network;


import android.content.Context;

import com.google.gson.Gson;

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

	private YLiveClient(Context context, String domain) {
		OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
				.cookieJar(new YLiveCookieJar(PersistentCookieStore.getIntance(context)))
				.addInterceptor(new CsrfTokenInterceptor(context, domain));

		mRetrofit = new Retrofit.Builder()
				.baseUrl(domain)
				.client(clientBuilder.build())
				.addConverterFactory(GsonConverterFactory.create(new Gson()))
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.build();
	}

	public static YLiveClient getYLiveClient(Context context, String domain) {

		if (sYLiveClient == null) {
			synchronized (YLiveClient.class) {
				if (sYLiveClient == null) {
					sYLiveClient = new YLiveClient(context, domain);
				}
			}
		}

		return sYLiveClient;
	}

	public Retrofit getCore() {
		return mRetrofit;
	}
}

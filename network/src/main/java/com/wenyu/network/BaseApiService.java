package com.wenyu.network;

import android.content.Context;

import java.net.ConnectException;

import retrofit2.adapter.rxjava.HttpException;
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
    public static final int CODE_OK = 0x200;

    private API mAPI;
    private Context mContext;

    public BaseApiService(Context context, String domain, Class<API> apiClass) {
        mContext = context;
        mAPI = YLiveClient.getYLiveClient(context, domain).getCore().create(apiClass);
    }

    protected API getAPI() {
        return mAPI;
    }

    protected Context getContext() {
        return mContext;
    }

    protected <T> Observable<T> convert(Observable<YResponse<T>> response) {
        return response.onErrorResumeNext(
                new Func1<Throwable, Observable<? extends YResponse<T>>>() {
                    @Override
                    public Observable<? extends YResponse<T>> call(Throwable throwable) {

                        if (throwable instanceof ConnectException) {
                            return Observable.error(new YLiveException(CODE_EXCEPTION, "网络异常"));
                        }

                        if (throwable instanceof HttpException) {
                            return Observable.error(new YLiveException(CODE_EXCEPTION, "Bad Request"));
                        }

                        return Observable.error(new YLiveException(CODE_EXCEPTION, "未知错误"));
                    }
                })
                .flatMap(new Func1<YResponse<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(YResponse<T> response) {

                        if (response.code != CODE_OK) {
                            return Observable.error(new YLiveException(response.code, response.message));
                        }

                        return Observable.just(response.data);
                    }
                });
    }
}

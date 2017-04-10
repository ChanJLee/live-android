package com.wenyu.network;

import rx.Subscriber;

/**
 * Created by jiacheng.li on 17/1/22.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public abstract class YLiveBaseSubscriber<T> extends Subscriber<T> {
    private T mData;

    @Override
    public void onError(Throwable e) {
        if (e instanceof YLiveException && ((YLiveException) e).isNoAuthentication()) {
            onAuthFailed();
            return;
        }

        onResponseFailed(e);
    }

    @Override
    public void onCompleted() {
        onResponseSuccess(mData);
    }

    @Override
    public void onNext(T t) {
        mData = t;
    }

    public abstract void onAuthFailed();

    public abstract void onResponseFailed(Throwable e);

    public abstract void onResponseSuccess(T data);
}

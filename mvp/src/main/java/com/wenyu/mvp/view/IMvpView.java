package com.wenyu.mvp.view;

import android.app.Activity;

import com.wenyu.mvp.presenter.MvpEventListener;


/**
 * Created by jiacheng.li on 17/1/22.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public interface IMvpView<E extends MvpEventListener> {
    E getEventListener();

    void setVisibility(boolean visible);

    void setEventListener(E listener);

    Activity getActivity();

    void showToast(String message);
}

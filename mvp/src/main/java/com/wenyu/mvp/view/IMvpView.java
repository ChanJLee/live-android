package com.wenyu.mvp.view;

import android.app.Activity;

import java.util.EventListener;

/**
 * Created by jiacheng.li on 17/1/22.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public interface IMvpView<E extends EventListener> {
	void getEventListener(E e);

	void onAttach();

	void onDetach();

	Activity getActivity();

	void showToast(String message);
}

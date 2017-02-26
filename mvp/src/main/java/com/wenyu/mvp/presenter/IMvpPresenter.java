package com.wenyu.mvp.presenter;

import com.wenyu.mvp.model.IMvpModel;
import com.wenyu.mvp.view.IMvpView;

/**
 * Created by jiacheng.li on 17/1/22.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public interface IMvpPresenter<V extends IMvpView, M extends IMvpModel> {
	void setView(V view);

	V getView();

	M getModel();

	void setModel(M model);

	void attach();

	void detach();
}

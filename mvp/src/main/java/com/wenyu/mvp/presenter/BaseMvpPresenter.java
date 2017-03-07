package com.wenyu.mvp.presenter;

import com.wenyu.mvp.model.IMvpModel;
import com.wenyu.mvp.view.IMvpView;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jiacheng.li on 17/1/22.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public abstract class BaseMvpPresenter<V extends IMvpView, M extends IMvpModel> implements IMvpPresenter<V, M> {
	private V mView;
	private M mModel;
	private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

	@Override
	final public void setView(V view) {
		mView = view;
	}

	@Override
	final public void setModel(M model) {
		mModel = model;
	}

	@Override
	public void attach() {
		mView.onAttach();
	}

	@Override
	public void detach() {
		if (!mCompositeSubscription.isUnsubscribed()) {
			mCompositeSubscription.unsubscribe();
		}
		mView.onDetach();
	}

	@Override
	public M getModel() {
		return mModel;
	}

	@Override
	public V getView() {
		return mView;
	}

	protected void add(Subscription subscription) {
		if (subscription != null) {
			mCompositeSubscription.add(subscription);
		}
	}
}

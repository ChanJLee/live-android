package com.wenyu.mvp.presenter;

import com.wenyu.mvp.model.IMvpModel;
import com.wenyu.mvp.view.IMvpView;

import rx.Subscription;
import rx.internal.util.SubscriptionList;

/**
 * Created by jiacheng.li on 17/1/22.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public abstract class BaseMvpPresenter<V extends IMvpView, M extends IMvpModel> implements IMvpPresenter<V, M> {
	private V mView;
	private M mModel;
	private SubscriptionList mSubscriptionList = new SubscriptionList();

	public BaseMvpPresenter(V view, M model) {
		mView = view;
		mModel = model;
	}

	@Override
	public void attach() {
		onAttach();
	}

	@Override
	public void detach() {
        onDetach();
        if (mSubscriptionList.hasSubscriptions() && !mSubscriptionList.isUnsubscribed()) {
            mSubscriptionList.unsubscribe();
        }
	}

	protected abstract void onAttach();

    protected abstract void onDetach();

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
            mSubscriptionList.add(subscription);
		}
	}
}

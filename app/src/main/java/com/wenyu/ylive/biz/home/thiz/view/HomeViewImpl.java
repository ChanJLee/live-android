package com.wenyu.ylive.biz.home.thiz.view;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.wenyu.mvp.view.BaseMvpView;
import com.wenyu.mvp.presenter.MvpEventListener;

import javax.inject.Inject;

/**
 * Created by chan on 17/3/29.
 */

public class HomeViewImpl extends BaseMvpView<MvpEventListener> implements IHomeView {

    @Inject
    public HomeViewImpl(@NonNull Activity activity) {
        super(activity);
    }

    @Override
    public void goToBroadcast() {
        //TODO
    }

    @Override
    public void goToRegisterAnchor() {
        //TODO
    }
}

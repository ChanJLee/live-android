package com.wenyu.ylive.biz.home.main.view;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.wenyu.mvp.view.BaseMvpView;
import com.wenyu.ylive.biz.home.main.presenter.HomeMainEventListener;

import javax.inject.Inject;

/**
 * Created by chan on 17/4/2.
 */

public class HomeMainViewImpl extends BaseMvpView<HomeMainEventListener> implements IHomeMainView {

    @Inject
    public HomeMainViewImpl(@NonNull Activity activity) {
        super(activity);
    }
}

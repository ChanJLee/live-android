package com.wenyu.ylive.biz.home.nav.view;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.wenyu.mvp.view.BaseMvpView;
import com.wenyu.ylive.biz.home.nav.presenter.HomeNavEventListener;

import javax.inject.Inject;

/**
 * Created by chan on 17/4/4.
 */

public class HomeNavViewImpl extends BaseMvpView<HomeNavEventListener> implements IHomeNavView {

    @Inject
    public HomeNavViewImpl(@NonNull Activity activity) {
        super(activity);
    }
}

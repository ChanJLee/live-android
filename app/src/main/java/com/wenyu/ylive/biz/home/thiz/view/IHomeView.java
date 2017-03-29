package com.wenyu.ylive.biz.home.thiz.view;

import com.wenyu.mvp.presenter.MvpEventListener;
import com.wenyu.mvp.view.IMvpView;

/**
 * Created by chan on 17/3/29.
 */

public interface IHomeView extends IMvpView {
    void goToBroadcast();

    void goToRegisterAnchor();
}

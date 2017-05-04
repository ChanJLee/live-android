package com.wenyu.ylive.biz.home.nav.view;

import com.wenyu.mvp.view.IMvpView;
import com.wenyu.ylive.biz.home.nav.presenter.HomeNavEventListener;

/**
 * Created by chan on 17/4/4.
 */

public interface IHomeNavView extends IMvpView<HomeNavEventListener> {
    void goToLoginAndRegister();
}

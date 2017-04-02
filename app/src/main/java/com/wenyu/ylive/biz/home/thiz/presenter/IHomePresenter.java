package com.wenyu.ylive.biz.home.thiz.presenter;

import com.wenyu.mvp.model.IMvpModel;
import com.wenyu.mvp.presenter.IMvpPresenter;
import com.wenyu.ylive.biz.home.thiz.view.IHomeView;

/**
 * Created by chan on 17/3/29.
 */

public interface IHomePresenter extends IMvpPresenter<IHomeView, IMvpModel> {
    void init();
}

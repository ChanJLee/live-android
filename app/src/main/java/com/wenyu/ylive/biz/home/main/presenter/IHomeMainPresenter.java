package com.wenyu.ylive.biz.home.main.presenter;

import com.wenyu.mvp.presenter.IMvpPresenter;
import com.wenyu.ylive.biz.home.main.model.IHomeMainModel;
import com.wenyu.ylive.biz.home.main.view.IHomeMainView;

/**
 * Created by chan on 17/4/2.
 */

public interface IHomeMainPresenter extends IMvpPresenter<IHomeMainView, IHomeMainModel> {
    void init();
}

package com.wenyu.ylive.biz.home.thiz.presenter;

import com.wenyu.mvp.model.IMvpModel;
import com.wenyu.mvp.presenter.BaseMvpPresenter;
import com.wenyu.ylive.biz.home.thiz.view.IHomeView;

/**
 * Created by chan on 17/3/29.
 */

public class HomePresenterImpl extends BaseMvpPresenter<IHomeView, IMvpModel> implements IHomePresenter {

    public HomePresenterImpl(IHomeView view, IMvpModel model) {
        super(view, model);
    }

    @Override
    protected void onAttach() {

    }

    @Override
    public void onDetach() {

    }
}

package com.wenyu.ylive.biz.home.main.presenter;

import com.wenyu.apt.MvpInjector;
import com.wenyu.mvp.presenter.BaseMvpPresenter;
import com.wenyu.ylive.biz.home.main.model.IHomeMainModel;
import com.wenyu.ylive.biz.home.main.view.IHomeMainView;

/**
 * Created by chan on 17/4/2.
 */

public class HomeMainPresenter extends BaseMvpPresenter<IHomeMainView, IHomeMainModel> implements IHomeMainPresenter {


    public HomeMainPresenter(IHomeMainView view, IHomeMainModel model) {
        super(view, model);
    }

    @Override
    protected void onAttach() {
        MvpInjector.inject(this);
    }

    @Override
    protected void onDetach() {

    }
}

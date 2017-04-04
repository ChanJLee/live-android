package com.wenyu.ylive.biz.home.thiz.presenter;

import com.wenyu.apt.MvpInjector;
import com.wenyu.apt.annotations.MvpModel;
import com.wenyu.apt.annotations.MvpPresenter;
import com.wenyu.apt.annotations.MvpView;
import com.wenyu.mvp.model.IMvpModel;
import com.wenyu.mvp.presenter.BaseMvpPresenter;
import com.wenyu.ylive.biz.home.main.inject.HomeMainComponent;
import com.wenyu.ylive.biz.home.main.inject.HomeMainModule;
import com.wenyu.ylive.biz.home.main.model.HomeMainModelImpl;
import com.wenyu.ylive.biz.home.main.presenter.HomeMainPresenter;
import com.wenyu.ylive.biz.home.main.view.HomeMainViewImpl;
import com.wenyu.ylive.biz.home.nav.inject.HomeNavComponent;
import com.wenyu.ylive.biz.home.nav.inject.HomeNavModule;
import com.wenyu.ylive.biz.home.nav.model.HomeNavModelImpl;
import com.wenyu.ylive.biz.home.nav.presenter.HomeNavPresenterImpl;
import com.wenyu.ylive.biz.home.nav.view.HomeNavViewImpl;
import com.wenyu.ylive.biz.home.thiz.view.IHomeView;

import javax.inject.Inject;

/**
 * Created by chan on 17/3/29.
 */

public class HomePresenterImpl extends BaseMvpPresenter<IHomeView, IMvpModel> implements IHomePresenter {

    @MvpModel(tag = "main")
    @Inject
    HomeMainModelImpl mHomeMainModel;

    @MvpPresenter(module = HomeMainModule.class, component = HomeMainComponent.class, tag = "main")
    HomeMainPresenter mHomeMainPresenter;

    @MvpView(tag = "main")
    @Inject
    HomeMainViewImpl mHomeMainView;

    @Inject
    @MvpModel(tag = "nav")
    HomeNavModelImpl mHomeNavModel;

    @MvpPresenter(module = HomeNavModule.class, component = HomeNavComponent.class, tag = "nav")
    HomeNavPresenterImpl mHomeNavPresenter;

    @MvpView(tag = "nav")
    @Inject
    HomeNavViewImpl mHomeNavView;

    public HomePresenterImpl(IHomeView view, IMvpModel model) {
        super(view, model);
    }

    @Override
    protected void onAttach() {
        MvpInjector.inject(this);
    }

    @Override
    public void onDetach() {
//        mHomeMainPresenter = null;
//        mHomeMainView = null;
//        mHomeMainModel = null;
    }

    @Override
    public void init() {

    }
}

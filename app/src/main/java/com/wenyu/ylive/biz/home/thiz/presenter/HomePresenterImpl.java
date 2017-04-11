package com.wenyu.ylive.biz.home.thiz.presenter;

import com.wenyu.apt.MvpInjector;
import com.wenyu.apt.annotations.MvpInject;
import com.wenyu.apt.annotations.MvpModel;
import com.wenyu.apt.annotations.MvpPresenter;
import com.wenyu.apt.annotations.MvpView;
import com.wenyu.mvp.model.IMvpModel;
import com.wenyu.mvp.presenter.BaseMvpPresenter;
import com.wenyu.ylive.biz.home.main.model.HomeMainModelImpl;
import com.wenyu.ylive.biz.home.main.presenter.HomeMainPresenter;
import com.wenyu.ylive.biz.home.main.view.HomeMainViewImpl;
import com.wenyu.ylive.biz.home.nav.model.HomeNavModelImpl;
import com.wenyu.ylive.biz.home.nav.presenter.HomeNavPresenterImpl;
import com.wenyu.ylive.biz.home.nav.view.HomeNavViewImpl;
import com.wenyu.ylive.biz.home.thiz.presenter.inject.HomePresenterComponent;
import com.wenyu.ylive.biz.home.thiz.presenter.inject.HomePresenterModule;
import com.wenyu.ylive.biz.home.thiz.view.IHomeView;

import javax.inject.Inject;

/**
 * Created by chan on 17/3/29.
 */
@MvpInject(module = HomePresenterModule.class, component = HomePresenterComponent.class)
public class HomePresenterImpl extends BaseMvpPresenter<IHomeView, IMvpModel> implements IHomePresenter {

    private IHomeView mHomeView;

    @MvpModel(tag = "main")
    @Inject
    HomeMainModelImpl mHomeMainModel;

    @MvpPresenter(tag = "main")
    HomeMainPresenter mHomeMainPresenter;

    @MvpView(tag = "main")
    @Inject
    HomeMainViewImpl mHomeMainView;

    @Inject
    @MvpModel(tag = "nav")
    HomeNavModelImpl mHomeNavModel;

    @MvpPresenter(tag = "nav")
    HomeNavPresenterImpl mHomeNavPresenter;

    @MvpView(tag = "nav")
    @Inject
    HomeNavViewImpl mHomeNavView;

    public HomePresenterImpl(IHomeView view, IMvpModel model) {
        super(view, model);
    }

    @Override
    protected void onAttach() {
        mHomeView = getView();
        MvpInjector.inject(this);
        mHomeNavPresenter.attach();
        mHomeMainPresenter.attach();
    }

    @Override
    public void onDetach() {
        mHomeMainPresenter.detach();
        mHomeNavPresenter.detach();

        mHomeView = null;

        mHomeMainPresenter = null;
        mHomeMainView = null;
        mHomeMainModel = null;

        mHomeNavPresenter = null;
        mHomeNavView = null;
        mHomeNavModel = null;
    }

    @Override
    public void init() {
        mHomeMainPresenter.init();
    }

    @Override
    public boolean onBackPressed() {
        return mHomeView != null && mHomeView.dismissDrawerLayout();
    }
}

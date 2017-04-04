package com.wenyu.ylive.biz.home.nav.presenter;

import com.wenyu.mvp.presenter.BaseMvpPresenter;
import com.wenyu.ylive.biz.home.nav.model.IHomeNavModel;
import com.wenyu.ylive.biz.home.nav.view.IHomeNavView;

/**
 * Created by chan on 17/4/4.
 */

public class HomeNavPresenterImpl extends BaseMvpPresenter<IHomeNavView, IHomeNavModel> implements IHomeNavPresenter {

    public HomeNavPresenterImpl(IHomeNavView view, IHomeNavModel model) {
        super(view, model);
    }

    @Override
    protected void onAttach() {

    }

    @Override
    protected void onDetach() {

    }
}

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
        mView.setEventListener(new HomeNavEventListener() {
            @Override
            public void onLoginAndRegisterClicked() {
                mView.goToLoginAndRegister();
            }
        });
    }

    @Override
    protected void onDetach() {

    }
}

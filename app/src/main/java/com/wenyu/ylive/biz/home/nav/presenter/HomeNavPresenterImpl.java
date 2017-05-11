package com.wenyu.ylive.biz.home.nav.presenter;

import com.wenyu.mvp.presenter.BaseMvpPresenter;
import com.wenyu.ylive.biz.home.nav.model.IHomeNavModel;
import com.wenyu.ylive.biz.home.nav.view.IHomeNavView;
import com.wenyu.ylive.common.bean.User;
import com.wenyu.ylive.common.event.UserInfoUpdateEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDetach() {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateUserInfo(UserInfoUpdateEvent event) {
        render();
    }

    @Override
    public void init() {
        //render();
    }

    private void render() {
        User user = mModel.getCurrentUser();
        if (user == User.anonymous) {
            return;
        }

        IHomeNavView.Data data = new IHomeNavView.Data();
        data.avatarUrl = user.avatar;
        data.username = user.username;
        mView.render(data);
    }
}

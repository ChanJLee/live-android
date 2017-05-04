package com.wenyu.ylive.biz.live;

import android.os.Bundle;

import com.wenyu.apt.MvpInjector;
import com.wenyu.apt.annotations.MvpInject;
import com.wenyu.apt.annotations.MvpModel;
import com.wenyu.apt.annotations.MvpPresenter;
import com.wenyu.apt.annotations.MvpView;
import com.wenyu.ylive.R;
import com.wenyu.ylive.base.YLiveActivity;
import com.wenyu.ylive.biz.live.inject.LiveComponent;
import com.wenyu.ylive.biz.live.inject.LiveModule;
import com.wenyu.ylive.biz.live.model.LiveModelImpl;
import com.wenyu.ylive.biz.live.presenter.LivePresenterImpl;
import com.wenyu.ylive.biz.live.view.LiveViewImpl;

import javax.inject.Inject;

@MvpInject(module = LiveModule.class, component = LiveComponent.class)
public class LiveActivity extends YLiveActivity {

    @MvpModel
    @Inject
    LiveModelImpl mLiveModel;

    @Inject
    @MvpView
    LiveViewImpl mLiveView;

    @MvpPresenter
    LivePresenterImpl mLivePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MvpInjector.inject(this);

        mLivePresenter.attach();
    }

    @Override
    protected int contentId() {
        return R.layout.activity_live;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLivePresenter.onActivityStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLivePresenter.onActivityStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLivePresenter.detach();
    }
}

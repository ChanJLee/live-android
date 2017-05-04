package com.wenyu.ylive.biz.live.presenter;

import com.wenyu.mvp.presenter.BaseMvpPresenter;
import com.wenyu.ylive.biz.live.model.ILiveModel;
import com.wenyu.ylive.biz.live.view.ILiveView;

/**
 * Created by chan on 17/5/2.
 */

public class LivePresenterImpl extends BaseMvpPresenter<ILiveView, ILiveModel> implements ILivePresenter {

    public LivePresenterImpl(ILiveView view, ILiveModel model) {
        super(view, model);
    }

    @Override
    protected void onAttach() {

    }

    @Override
    protected void onDetach() {
        mView.release();
    }

    @Override
    public void onActivityStop() {
        mView.pause();
    }

    @Override
    public void onActivityStart() {
        mView.resume();
    }
}

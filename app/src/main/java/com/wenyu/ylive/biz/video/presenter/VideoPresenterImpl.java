package com.wenyu.ylive.biz.video.presenter;

import com.wenyu.mvp.presenter.BaseMvpPresenter;
import com.wenyu.ylive.biz.video.model.IVideoModel;
import com.wenyu.ylive.biz.video.video.IVideoView;

/**
 * Created by chan on 17/4/24.
 */

public class VideoPresenterImpl extends BaseMvpPresenter<IVideoView, IVideoModel> implements IVideoPresenter {

    public VideoPresenterImpl(IVideoView view, IVideoModel model) {
        super(view, model);
    }

    @Override
    protected void onAttach() {

    }

    @Override
    protected void onDetach() {

    }
}

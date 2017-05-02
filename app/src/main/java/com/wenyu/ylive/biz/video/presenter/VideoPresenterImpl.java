package com.wenyu.ylive.biz.video.presenter;

import com.wenyu.mvp.presenter.BaseMvpPresenter;
import com.wenyu.ylive.biz.video.model.IVideoModel;
import com.wenyu.ylive.biz.video.video.IVideoView;
import com.wenyu.ylive.common.bean.Room;

/**
 * Created by chan on 17/4/24.
 */

public class VideoPresenterImpl extends BaseMvpPresenter<IVideoView, IVideoModel> implements IVideoPresenter {

    public VideoPresenterImpl(IVideoView view, IVideoModel model) {
        super(view, model);
    }

    @Override
    protected void onAttach() {
        mView.setEventListener(new VideoEventListener() {

        });
    }

    @Override
    protected void onDetach() {
        mView = null;
    }

    @Override
    public void init(Room room) {
        IVideoView.Data data = new IVideoView.Data();
        data.title = room.title;
        data.anchor = room.anchor;
        data.cover = room.snapshot;
        data.url = room.liveUrl;
        mView.render(data);
    }
}

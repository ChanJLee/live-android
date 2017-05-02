package com.wenyu.ylive.biz.video.presenter;

import com.wenyu.mvp.presenter.IMvpPresenter;
import com.wenyu.ylive.biz.video.model.IVideoModel;
import com.wenyu.ylive.biz.video.video.IVideoView;
import com.wenyu.ylive.common.bean.Room;

/**
 * Created by chan on 17/4/24.
 */

public interface IVideoPresenter extends IMvpPresenter<IVideoView, IVideoModel> {
    void init(Room room);
}

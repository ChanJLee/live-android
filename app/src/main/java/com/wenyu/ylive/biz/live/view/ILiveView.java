package com.wenyu.ylive.biz.live.view;

import com.wenyu.mvp.view.IMvpView;
import com.wenyu.ylive.biz.live.presenter.LiveEventListener;

/**
 * Created by chan on 17/5/2.
 */

public interface ILiveView extends IMvpView<LiveEventListener> {

    void release();

    void pause();

    void resume();

    void render(Data data);

    int fetchCategory();

    String fetchTitle();

    void showRoomConfigDialog();

    class Data {
        public String liveUrl;
        public String chatUrl;
    }
}

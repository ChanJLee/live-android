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

    void render(String liveUrl);

    int fetchCategory();

    String fetchTitle();

    void showRoomConfigDialog();

    void renderDanma(String message);

    void closeOpenBroadcast();

    void switchCamera(boolean front);

    void enableMagic(boolean enable);
}

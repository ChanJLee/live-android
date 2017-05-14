package com.wenyu.ylive.biz.video.video;

import com.wenyu.mvp.view.IMvpView;
import com.wenyu.ylive.biz.video.presenter.VideoEventListener;

/**
 * Created by chan on 17/4/24.
 */

public interface IVideoView extends IMvpView<VideoEventListener> {

    void render(Data data);

    String getInput();

    void clearInput();

    void renderDanma(String message);

    class Data {
        public String title;
        public String cover;
        public String anchor;
        public String url;
    }
}

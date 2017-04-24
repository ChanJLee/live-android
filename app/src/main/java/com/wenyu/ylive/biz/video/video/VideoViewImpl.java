package com.wenyu.ylive.biz.video.video;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.wenyu.mvp.view.BaseMvpView;
import com.wenyu.ylive.biz.video.presenter.VideoEventListener;

import javax.inject.Inject;

/**
 * Created by chan on 17/4/24.
 */

public class VideoViewImpl extends BaseMvpView<VideoEventListener> implements IVideoView {

    @Inject
    public VideoViewImpl(@NonNull Activity activity) {
        super(activity);
    }
}

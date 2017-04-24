package com.wenyu.ylive.biz.video.inject;

import android.app.Activity;

import com.wenyu.mvp.annotation.ActivityScope;
import com.wenyu.ylive.biz.video.VideoActivity;

import dagger.Component;

/**
 * Created by chan on 17/4/24.
 */
@ActivityScope
@Component(modules = VideoModule.class)
public interface VideoComponent {
    Activity getActivity();

    void inject(VideoActivity activity);
}

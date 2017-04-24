package com.wenyu.ylive.biz.video.inject;

import android.app.Activity;

import com.wenyu.mvp.annotation.ActivityScope;
import com.wenyu.ylive.biz.video.VideoActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by chan on 17/4/24.
 */
@Module
public class VideoModule {
    private VideoActivity mVideoActivity;

    public VideoModule(VideoActivity videoActivity) {
        mVideoActivity = videoActivity;
    }

    @Provides
    @ActivityScope
    public Activity provideActivity() {
        return mVideoActivity;
    }
}

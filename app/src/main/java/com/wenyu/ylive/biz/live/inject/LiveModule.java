package com.wenyu.ylive.biz.live.inject;

import android.app.Activity;

import com.wenyu.mvp.annotation.ActivityScope;
import com.wenyu.ylive.biz.live.LiveActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by chan on 17/5/3.
 */

@Module
public class LiveModule {
    LiveActivity mLiveActivity;

    public LiveModule(LiveActivity liveActivity) {
        mLiveActivity = liveActivity;
    }

    @ActivityScope
    @Provides
    Activity provideActivity() {
        return mLiveActivity;
    }
}

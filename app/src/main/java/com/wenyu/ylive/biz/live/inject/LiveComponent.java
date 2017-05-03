package com.wenyu.ylive.biz.live.inject;

import android.app.Activity;

import com.wenyu.mvp.annotation.ActivityScope;
import com.wenyu.ylive.biz.live.LiveActivity;

import dagger.Component;

/**
 * Created by chan on 17/5/3.
 */
@ActivityScope
@Component(modules = LiveModule.class)
public interface LiveComponent {
    Activity getActivity();

    void inject(LiveActivity activity);
}

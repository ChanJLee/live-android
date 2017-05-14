package com.wenyu.ylive.biz.video.inject;

import android.app.Activity;

import com.wenyu.mvp.annotation.ActivityScope;
import com.wenyu.xmpp.XmppClient;
import com.wenyu.ylive.biz.video.VideoActivity;
import com.wenyu.ylive.common.api.service.YLiveApiService;

import dagger.Component;

/**
 * Created by chan on 17/4/24.
 */
@ActivityScope
@Component(modules = VideoModule.class)
public interface VideoComponent {
    Activity getActivity();

    XmppClient getXmppClient();

    YLiveApiService getYLiveApiService();

    void inject(VideoActivity activity);
}

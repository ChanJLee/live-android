package com.wenyu.ylive.biz.live.inject;

import android.app.Activity;

import com.wenyu.mvp.annotation.ActivityScope;
import com.wenyu.xmpp.XmppClient;
import com.wenyu.ylive.biz.live.LiveActivity;
import com.wenyu.ylive.common.api.service.YLiveApiService;

import dagger.Component;

/**
 * Created by chan on 17/5/3.
 */
@ActivityScope
@Component(modules = LiveModule.class)
public interface LiveComponent {
    Activity getActivity();

    YLiveApiService getYLiveApiService();

    XmppClient getXmppClient();

    void inject(LiveActivity activity);
}

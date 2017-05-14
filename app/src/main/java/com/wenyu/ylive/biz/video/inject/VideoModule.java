package com.wenyu.ylive.biz.video.inject;

import android.app.Activity;
import android.content.Context;

import com.wenyu.mvp.annotation.ActivityScope;
import com.wenyu.xmpp.XmppClient;
import com.wenyu.ylive.biz.account.AccountConfig;
import com.wenyu.ylive.biz.video.VideoActivity;
import com.wenyu.ylive.common.api.service.YLiveApiService;
import com.wenyu.ylive.common.bean.User;

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

    @ActivityScope
    @Provides
    XmppClient provideXmppClient() {
        Context appContext = mVideoActivity.getApplicationContext();
        AccountConfig accountConfig = AccountConfig.getIntance(appContext);
        User user = accountConfig.getCurrentUser();
        if (user == User.anonymous) {
            return null;
        }
        return XmppClient.getXmppClient(appContext, user.username, user.username);
    }

    @ActivityScope
    @Provides
    YLiveApiService provideYliveApiService() {
        return YLiveApiService.getYLiveApiService(mVideoActivity.getApplicationContext());
    }
}

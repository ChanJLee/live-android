package com.wenyu.ylive.biz.live.inject;

import android.app.Activity;
import android.content.Context;

import com.wenyu.mvp.annotation.ActivityScope;
import com.wenyu.xmpp.XmppClient;
import com.wenyu.ylive.biz.account.AccountConfig;
import com.wenyu.ylive.biz.live.LiveActivity;
import com.wenyu.ylive.common.api.service.YLiveApiService;
import com.wenyu.ylive.common.bean.User;

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

    @ActivityScope
    @Provides
    YLiveApiService provideYliveApiService() {
        return YLiveApiService.getYLiveApiService(mLiveActivity.getApplicationContext());
    }

    @ActivityScope
    @Provides
    XmppClient provideXmppClient() {
        Context appContext = mLiveActivity.getApplicationContext();
        AccountConfig accountConfig = AccountConfig.getIntance(appContext);
        User user = accountConfig.getCurrentUser();
        if (user == User.anonymous) {
            return null;
        }

        return XmppClient.getXmppClient(appContext, user.username, user.username);
    }
}

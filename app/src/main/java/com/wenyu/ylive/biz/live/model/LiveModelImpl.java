package com.wenyu.ylive.biz.live.model;

import com.google.gson.JsonElement;
import com.wenyu.mvp.model.BaseModel;
import com.wenyu.xmpp.XmppClient;
import com.wenyu.ylive.common.api.service.YLiveApiService;
import com.wenyu.ylive.common.bean.Broadcast;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by chan on 17/5/2.
 */

public class LiveModelImpl extends BaseModel implements ILiveModel {

    private YLiveApiService mYLiveApiService;
    private XmppClient mXmppClient;

    @Inject
    public LiveModelImpl(YLiveApiService liveApiService, XmppClient xmppClient) {
        mYLiveApiService = liveApiService;
        mXmppClient = xmppClient;
    }

    @Override
    public Observable<Broadcast> openBroadcast(String title, int category) {
        return mYLiveApiService.openBroadcast(title, category);
    }

    @Override
    public Observable<JsonElement> closeBroadcast() {
        return mYLiveApiService.claseBroadcast();
    }

    @Override
    public XmppClient getXmppClient() {
        return mXmppClient;
    }
}

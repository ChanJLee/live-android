package com.wenyu.ylive.biz.video.model;

import com.wenyu.mvp.model.BaseModel;
import com.wenyu.xmpp.XmppClient;
import com.wenyu.ylive.common.api.service.YLiveApiService;

import javax.inject.Inject;

/**
 * Created by chan on 17/4/24.
 */

public class VideoModelImpl extends BaseModel implements IVideoModel {

    private XmppClient mXmppClient;
    private YLiveApiService mYLiveApiService;

    @Inject
    public VideoModelImpl(XmppClient xmppClient, YLiveApiService YLiveApiService) {
        mXmppClient = xmppClient;
        mYLiveApiService = YLiveApiService;
    }

    @Override
    public XmppClient fetchXmppClient() {
        return mXmppClient;
    }
}

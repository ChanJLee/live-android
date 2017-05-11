package com.wenyu.ylive.biz.live.model;

import com.google.gson.JsonElement;
import com.wenyu.mvp.model.BaseModel;
import com.wenyu.ylive.common.api.service.YLiveApiService;
import com.wenyu.ylive.common.bean.Broadcast;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by chan on 17/5/2.
 */

public class LiveModelImpl extends BaseModel implements ILiveModel {

    private YLiveApiService mYLiveApiService;

    @Inject
    public LiveModelImpl(YLiveApiService liveApiService) {
        mYLiveApiService = liveApiService;
    }

    @Override
    public Observable<Broadcast> openBroadcast(String title, int category) {
        return mYLiveApiService.openBroadcast(title, category);
    }

    @Override
    public Observable<JsonElement> closeBroadcast() {
        return mYLiveApiService.claseBroadcast();
    }
}

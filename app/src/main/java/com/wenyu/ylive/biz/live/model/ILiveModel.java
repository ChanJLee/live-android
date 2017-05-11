package com.wenyu.ylive.biz.live.model;

import com.google.gson.JsonElement;
import com.wenyu.mvp.model.IMvpModel;
import com.wenyu.ylive.common.bean.Broadcast;

import rx.Observable;

/**
 * Created by chan on 17/5/2.
 */

public interface ILiveModel extends IMvpModel {
    Observable<Broadcast> openBroadcast(String title, int category);

    Observable<JsonElement> closeBroadcast();
}

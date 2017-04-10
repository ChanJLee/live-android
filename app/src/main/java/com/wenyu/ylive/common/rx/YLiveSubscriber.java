package com.wenyu.ylive.common.rx;

import com.wenyu.network.YLiveBaseSubscriber;

/**
 * Created by chan on 17/4/9.
 */

public abstract class YLiveSubscriber<T> extends YLiveBaseSubscriber<T> {

    @Override
    public void onAuthFailed() {
        //TODO
    }
}

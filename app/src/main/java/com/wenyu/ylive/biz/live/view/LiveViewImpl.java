package com.wenyu.ylive.biz.live.view;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.wenyu.mvp.view.BaseMvpView;
import com.wenyu.ylive.biz.live.presenter.LiveEventListener;

import javax.inject.Inject;

/**
 * Created by chan on 17/5/2.
 */

public class LiveViewImpl extends BaseMvpView<LiveEventListener> implements ILiveView {

    @Inject
    public LiveViewImpl(@NonNull Activity activity) {
        super(activity);
    }
}

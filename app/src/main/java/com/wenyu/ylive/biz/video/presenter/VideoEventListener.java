package com.wenyu.ylive.biz.video.presenter;

import com.wenyu.mvp.presenter.MvpEventListener;

/**
 * Created by chan on 17/4/24.
 */

public interface VideoEventListener extends MvpEventListener {
    void onSendClicked();
}

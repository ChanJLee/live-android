package com.wenyu.ylive.biz.home.main.presenter;

import com.wenyu.mvp.presenter.MvpEventListener;

/**
 * Created by chan on 17/4/2.
 */

public interface HomeMainEventListener extends MvpEventListener {
    void onTabSelected(int position);

    void onTabReselected(int position);

    void onRoomClicked(int position);

    void onOpenBroadcastClicked();
}

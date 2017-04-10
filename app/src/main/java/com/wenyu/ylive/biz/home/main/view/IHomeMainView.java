package com.wenyu.ylive.biz.home.main.view;

import com.wenyu.mvp.view.IMvpView;
import com.wenyu.ylive.biz.home.main.presenter.HomeMainEventListener;
import com.wenyu.ylive.common.bean.Room;
import com.wenyu.ylive.common.listener.LoadingListenerCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chan on 17/4/2.
 */

public interface IHomeMainView extends IMvpView<HomeMainEventListener> {

    void refresh(List<Data> data);

    void loadMore(List<Data> data);

    void setLoadingListener(LoadingListenerCompat<?> dataLoadingListenerCompat);

    void renderLoading();

    class Data {
        public String title;
        public String snapshot;
        public String anchor;
        public int audienceCount;
    }
}

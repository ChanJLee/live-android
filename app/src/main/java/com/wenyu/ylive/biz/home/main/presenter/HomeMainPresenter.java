package com.wenyu.ylive.biz.home.main.presenter;

import com.wenyu.apt.MvpInjector;
import com.wenyu.mvp.presenter.BaseMvpPresenter;
import com.wenyu.ylive.biz.home.main.model.IHomeMainModel;
import com.wenyu.ylive.biz.home.main.view.IHomeMainView;
import com.wenyu.ylive.common.bean.Room;
import com.wenyu.ylive.common.listener.LoadingListenerCompat;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;

/**
 * Created by chan on 17/4/2.
 */

public class HomeMainPresenter extends BaseMvpPresenter<IHomeMainView, IHomeMainModel> implements IHomeMainPresenter {
    private static int CATEGORY_CODE[] = {0x0521, 0x0522, 0x0523, 0x0524, 0x0525};

    private int mCurrentCategory = CATEGORY_CODE[0];
    private IHomeMainView mHomeMainView;
    private LoadingListenerCompat<List<Room>> mDataLoadingListenerCompat = new LoadingListenerCompat<List<Room>>() {
        @Override
        public void addSubscription(Subscription subscription) {
            add(subscription);
        }

        @Override
        public Observable<List<Room>> requestData(int page) {
            return getModel().fetchRoomList(mCurrentCategory, page);
        }

        @Override
        public void handleRefreshSuccess(List<Room> data) {
            List<IHomeMainView.Data> viewDataList = new ArrayList<>();
            for (Room room : data) {
                IHomeMainView.Data viewData = new IHomeMainView.Data();
                viewData.anchor = room.anchor;
                viewData.audienceCount = room.audienceCount;
                viewData.snapshot = room.snapshot;
                viewData.title = room.title;
                viewDataList.add(viewData);
            }
            mHomeMainView.refresh(viewDataList);
        }

        @Override
        public void handleLoadMoreSuccess(List<Room> data) {
            List<IHomeMainView.Data> viewDataList = new ArrayList<>();
            for (Room room : data) {
                IHomeMainView.Data viewData = new IHomeMainView.Data();
                viewData.anchor = room.anchor;
                viewData.audienceCount = room.audienceCount;
                viewData.snapshot = room.snapshot;
                viewData.title = room.title;
                viewDataList.add(viewData);
            }
            mHomeMainView.loadMore(viewDataList);
        }

        @Override
        public int getDataCount(List<Room> data) {
            return data.size();
        }
    };

    public HomeMainPresenter(IHomeMainView view, IHomeMainModel model) {
        super(view, model);
    }

    @Override
    protected void onAttach() {
        mHomeMainView = getView();
        mHomeMainView.setEventListener(new HomeMainEventListener() {
            @Override
            public void onTabSelected(int position) {
                reload(position);
            }

            @Override
            public void onTabReselected(int position) {
                reload(position);
            }

            @Override
            public void onRoomClicked(int position) {
                if (mHomeMainView != null) {
                    mHomeMainView.gotoRoom();
                }
            }

            private void reload(int position) {
                if (position < 0 || position >= CATEGORY_CODE.length || mHomeMainView == null) {
                    return;
                }
                mCurrentCategory = CATEGORY_CODE[position];
                mHomeMainView.renderLoading();
            }
        });
        mHomeMainView.setLoadingListener(mDataLoadingListenerCompat);
    }

    @Override
    protected void onDetach() {
        mHomeMainView = null;
    }

    @Override
    public void init() {
        mHomeMainView.renderLoading();
    }
}

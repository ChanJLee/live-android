package com.wenyu.ylive.biz.home.main.presenter;

import com.google.gson.JsonElement;
import com.wenyu.apt.MvpInjector;
import com.wenyu.mvp.presenter.BaseMvpPresenter;
import com.wenyu.ylive.biz.home.main.model.IHomeMainModel;
import com.wenyu.ylive.biz.home.main.view.IHomeMainView;
import com.wenyu.ylive.common.bean.Room;
import com.wenyu.ylive.common.listener.LoadingListenerCompat;
import com.wenyu.ylive.common.rx.YLiveSubscriber;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.wenyu.ylive.common.api.service.YLiveApiService.CATEGORY_CODE;

/**
 * Created by chan on 17/4/2.
 */

public class HomeMainPresenter extends BaseMvpPresenter<IHomeMainView, IHomeMainModel> implements IHomeMainPresenter {

    private int mCurrentCategory = CATEGORY_CODE[0];
    private IHomeMainView mHomeMainView;
    private List<Room> mRooms = new ArrayList<>();

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
            mRooms.clear();
            mRooms.addAll(data);
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
            mRooms.addAll(data);
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
                if (position < 0 || position >= mRooms.size()) {
                    return;
                }

                if (mHomeMainView != null) {
                    mHomeMainView.gotoRoom(mRooms.get(position));
                }
            }

            @Override
            public void onOpenBroadcastClicked() {
                add(getModel().fetchLivePermission()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new YLiveSubscriber<JsonElement>() {
                    @Override
                    public void onResponseFailed(Throwable e) {
                        mView.showLiveErrorDialog();
                    }

                    @Override
                    public void onResponseSuccess(JsonElement data) {
                        if (mView != null) {
                            mView.goToLive();
                        }
                    }
                }));
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

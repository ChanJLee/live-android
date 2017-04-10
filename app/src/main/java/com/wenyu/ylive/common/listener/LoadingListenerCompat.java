package com.wenyu.ylive.common.listener;

import com.wenyu.loadingrecyclerview.LoadMoreLoadingEvent;
import com.wenyu.loadingrecyclerview.LoadingListener;
import com.wenyu.loadingrecyclerview.RefreshLoadingEvent;
import com.wenyu.loadingrecyclerview.ReloadLoadingEvent;
import com.wenyu.ylive.common.rx.YLiveSubscriber;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public abstract class LoadingListenerCompat<T> implements LoadingListener {
    private int mPage;
    private int mCurrentTotal;

    public LoadingListenerCompat() {
        mPage = 1;
        mCurrentTotal = 0;
    }

    @Override
    public void onRefresh(final RefreshLoadingEvent event) {
        mPage = 1;
        mCurrentTotal = 0;
        event.onEventStart();
        addSubscription(requestData(mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new YLiveSubscriber<T>() {


                    @Override
                    public void onResponseFailed(Throwable e) {
                        event.onEventFailure();
                    }

                    @Override
                    public void onResponseSuccess(T data) {
                        mPage++;
                        mCurrentTotal = getDataCount(data);
                        handleRefreshSuccess(data);
                        event.onEventSuccess();
                    }
                }));

    }

    @Override
    public void onLoadMore(final LoadMoreLoadingEvent event) {

        event.onEventStart();
        addSubscription(requestData(mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new YLiveSubscriber<T>() {
                    @Override
                    public void onResponseFailed(Throwable e) {
                        event.onEventFailure();
                    }

                    @Override
                    public void onResponseSuccess(T data) {
                        mPage++;
                        mCurrentTotal += getDataCount(data);
                        handleLoadMoreSuccess(data);
                        event.onEventSuccess();
                    }
                }));
    }

    @Override
    public void onReload(ReloadLoadingEvent event) {
        event.sameAsRefresh();
    }

    public abstract void addSubscription(Subscription subscription);

    public abstract Observable<T> requestData(int page);

    public abstract void handleRefreshSuccess(T data);

    public abstract void handleLoadMoreSuccess(T data);

    public abstract int getDataCount(T data);

    /**
     * 可以通过重写该方法，来实现：当读取完所有内容之后，执行一些操作
     */
    public void handleAllLoadCompleted() {

    }

    /**
     * 当我们没有依赖onRefresh，而是手动请求了第一页的数据时
     * 通过调用该方法，手动将第一页的数据刷进LoadingRecycleView中
     */
    public void onManuallyRefresh(T data) {
        mPage = 2;
        mCurrentTotal = getDataCount(data);
        handleRefreshSuccess(data);
    }
}
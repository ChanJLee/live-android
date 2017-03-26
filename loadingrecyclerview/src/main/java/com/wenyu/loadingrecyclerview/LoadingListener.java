package com.wenyu.loadingrecyclerview;

public interface LoadingListener {
	void onRefresh(RefreshLoadingEvent event);

	void onLoadMore(LoadMoreLoadingEvent event);

	void onReload(ReloadLoadingEvent event);
}

package com.wenyu.loadingrecyclerview;

public interface LoadingEvent {
	void onEventStart();

	void onEventSuccess();

	void onEventFailure();

	void onEventNull();
}

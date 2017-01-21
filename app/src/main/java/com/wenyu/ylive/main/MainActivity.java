package com.wenyu.ylive.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.JsonElement;
import com.trello.rxlifecycle.ActivityEvent;
import com.wenyu.ylive.R;
import com.wenyu.ylive.base.YLiveActivity;
import com.wenyu.ylive.net.api.service.AccountApiService;
import com.wenyu.ylive.net.model.User;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class MainActivity extends YLiveActivity {
	private long mLastClickedTag = 0;
	private static final long MIN_DURATION = 2000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		AccountApiService.getAccountApiService().login("chan_debug", "chan_debug_password")
				.subscribeOn(Schedulers.io())
				.observeOn(Schedulers.io())
				.compose(this.<User>bindUntilEvent(ActivityEvent.DESTROY))
				.subscribe(new Subscriber<User>() {
					@Override
					public void onCompleted() {
						Log.d("chan_debug", "complete");
					}

					@Override
					public void onError(Throwable e) {
						Log.d("chan_debug", "error");
					}

					@Override
					public void onNext(User jsonElement) {
						Log.d("chan_debug", "next");
					}
				});
	}

	@Override
	public void onBackPressed() {
		long currentTag = System.currentTimeMillis();
		if (currentTag - mLastClickedTag < MIN_DURATION) {
			finish();
			return;
		}

		mLastClickedTag = currentTag;
		showToast("再按一次将退出");
	}

	public static Intent newIntent(Context context) {
		return new Intent(context, MainActivity.class);
	}
}

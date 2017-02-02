package com.wenyu.ylive.biz.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;

import com.wenyu.ylive.R;
import com.wenyu.ylive.base.YLiveActivity;

public class MainActivity extends YLiveActivity {
	private long mLastClickedTag = 0;
	private static final long MIN_DURATION = 2000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		AccountApiService.getAccountApiService().login("chan_debug", "chan_debug_password")
//				.subscribeOn(Schedulers.io())
//				.observeOn(Schedulers.io())
//				.compose(this.<User>bindUntilEvent(ActivityEvent.DESTROY))
//				.subscribe(new Subscriber<User>() {
//					@Override
//					public void onCompleted() {
//						Log.d("chan_debug", "complete");
//					}
//
//					@Override
//					public void onError(Throwable e) {
//						Log.d("chan_debug", "error");
//					}
//
//					@Override
//					public void onNext(User jsonElement) {
//						Log.d("chan_debug", "next");
//					}
//				});

		findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				View cube = findViewById(R.id.cube);
				TransitionManager.beginDelayedTransition((ViewGroup) findViewById(R.id.root), new Slide());
				toggle(cube);
			}
		});
	}

	private void toggle(View... views) {
		for (View view : views) {
			view.setVisibility(view.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
		}
	}

	@Override
	protected int contentId() {
		return R.layout.activity_main;
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

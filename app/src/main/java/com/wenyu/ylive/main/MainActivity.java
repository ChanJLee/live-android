package com.wenyu.ylive.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.wenyu.ylive.R;
import com.wenyu.ylive.base.YLiveActivity;

public class MainActivity extends YLiveActivity {
	private long mLastClickedTag = 0;
	private static final long MIN_DURATION = 2000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public void onBackPressed() {
		long currentTag = System.currentTimeMillis();
		if (currentTag - mLastClickedTag > MIN_DURATION) {
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

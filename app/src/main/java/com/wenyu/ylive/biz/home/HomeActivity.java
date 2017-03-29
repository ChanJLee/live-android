package com.wenyu.ylive.biz.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.wenyu.ylive.R;
import com.wenyu.ylive.base.YLiveActivity;

public class HomeActivity extends YLiveActivity {
	private static final long MIN_DURATION = 2000;

	private long mLastClickedTimePoint = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(false);
		}
	}

	@Override
	protected int contentId() {
		return R.layout.activity_home;
	}

	@Override
	public void onBackPressed() {
		long currentTag = System.currentTimeMillis();
		if (currentTag - mLastClickedTimePoint < MIN_DURATION) {
			finish();
			return;
		}

		mLastClickedTimePoint = currentTag;
		showToast("再按一次将退出");
	}

	@Override
	protected Toolbar findToolbarById() {
		return (Toolbar) findViewById(R.id.toolbar);
	}

	public static Intent newIntent(Context context) {
		return new Intent(context, HomeActivity.class);
	}
}

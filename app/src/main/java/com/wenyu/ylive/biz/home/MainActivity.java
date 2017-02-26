package com.wenyu.ylive.biz.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import com.wenyu.ylive.R;
import com.wenyu.ylive.base.YLiveActivity;
import com.wenyu.ylive.biz.home.adapter.HomeAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends YLiveActivity {
	private long mLastClickedTag = 0;
	private static final long MIN_DURATION = 2000;

	RecyclerView mRecyclerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRecyclerView = (RecyclerView) findViewById(R.id.main_content_recycler_view);
		mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
		List<String> list = new ArrayList<>();
		for (int i = 0; i < 20; ++i) {
			list.add(i + "");
		}
		mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
			@Override
			public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
				outRect.set(10, 0, 10, 40);
			}
		});
		HomeAdapter adapter = new HomeAdapter(list, LayoutInflater.from(this));
		mRecyclerView.setAdapter(adapter);
	}

	private void toggle(View... views) {
		for (View view : views) {
			view.setBackgroundColor(Color.RED);
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

	@Override
	protected Toolbar findToolbarById() {
		return (Toolbar) findViewById(R.id.toolbar);
	}
}

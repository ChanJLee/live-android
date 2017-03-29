package com.wenyu.ylive.biz.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wenyu.ylive.R;

import java.util.List;

/**
 * Created by jiacheng.li on 17/2/5.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

	private List<String> mStrings;
	private LayoutInflater mLayoutInflater;

	public HomeAdapter(List<String> strings, LayoutInflater layoutInflater) {
		mStrings = strings;
		mLayoutInflater = layoutInflater;
	}

	@Override
	public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new HomeViewHolder(mLayoutInflater.inflate(R.layout.item_home_recycler_view, parent, false));
	}

	@Override
	public void onBindViewHolder(HomeViewHolder holder, int position) {

	}

	@Override
	public int getItemCount() {
		return mStrings.size();
	}

	public static class HomeViewHolder extends RecyclerView.ViewHolder {

		public HomeViewHolder(View itemView) {
			super(itemView);
		}
	}
}

package com.wenyu.ylive.biz.home.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wenyu.loadingrecyclerview.BaseRVAdapter;
import com.wenyu.ylive.R;
import com.wenyu.ylive.biz.home.main.view.IHomeMainView;
import com.wenyu.ylive.utils.GlideImageUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jiacheng.li on 17/2/5.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public class HomeMainAdapter extends BaseRVAdapter<HomeMainAdapter.HomeMainViewHolder, BaseRVAdapter.Listener, IHomeMainView.Data> {

    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public HomeMainAdapter(Context context) {
        super(context);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public HomeMainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HomeMainViewHolder(mLayoutInflater.inflate(R.layout.item_home_recycler_view, parent, false));
    }

    @Override
    public void onBindViewHolder(HomeMainViewHolder holder, int position) {
        IHomeMainView.Data data = getItem(position);
        GlideImageUtils.setImage(mContext, holder.ivCover, data.snapshot);
        holder.tvTitle.setText(data.title);
        holder.tvAnchor.setText(data.anchor);
        holder.tvCount.setText(String.valueOf(data.audienceCount));
    }

    class HomeMainViewHolder extends BaseRVAdapter.ViewHolder {

        @Bind(R.id.home_main_cover)
        ImageView ivCover;

        @Bind(R.id.home_main_title)
        TextView tvTitle;

        @Bind(R.id.home_main_anchor)
        TextView tvAnchor;

        @Bind(R.id.home_main_count)
        TextView tvCount;

        public HomeMainViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

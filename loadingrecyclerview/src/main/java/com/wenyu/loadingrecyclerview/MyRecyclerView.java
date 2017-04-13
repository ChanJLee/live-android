package com.wenyu.loadingrecyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by chan on 17/4/13.
 */

public abstract class MyRecyclerView extends RecyclerView {
    public MyRecyclerView(Context context) {
        super(context);
    }

    private OnScrollListener mOnScrollListener;

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
        if (mOnScrollListener != null) {
            removeOnScrollListener(mOnScrollListener);
        }

        layout.setItemPrefetchEnabled(false);
        if (layout instanceof LinearLayoutManager) {
            addOnScrollListener(mOnScrollListener = new EndlessRecyclerOnScrollListener((LinearLayoutManager) layout) {
                @Override
                public void onLoadMore(int page) {
                    onLoadMorePage(page);
                }
            });
        } else if (layout instanceof StaggeredGridLayoutManager) {
            addOnScrollListener(mOnScrollListener = new EndlessStateGridOnScrollListener((StaggeredGridLayoutManager) layout) {
                @Override
                public void onLoadMore(int page) {
                    onLoadMorePage(page);
                }
            });
        }
    }

    public abstract void onLoadMorePage(int page);
}

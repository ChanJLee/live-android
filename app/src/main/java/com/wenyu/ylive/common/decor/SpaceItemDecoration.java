package com.wenyu.ylive.common.decor;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.wenyu.ylive.R;

/**
 * Created by chan on 17/4/10.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int mVerticalSpace;
    public SpaceItemDecoration(Context context) {
        this((int) context.getResources().getDimension(R.dimen.padding4));
    }

    public SpaceItemDecoration(int verticalSpace) {
        mVerticalSpace = verticalSpace;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int position = parent.getChildLayoutPosition(view);

        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            if (linearLayoutManager.getOrientation() != LinearLayoutManager.VERTICAL) {
                return;
            }

            if (position != state.getItemCount() - 1) {
                outRect.set(0, 0, 0, mVerticalSpace);
            }
            return;
        }

        if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            if (staggeredGridLayoutManager.getOrientation() != StaggeredGridLayoutManager.VERTICAL) {
                return;
            }

            int spanCount = staggeredGridLayoutManager.getSpanCount();
            int count = state.getItemCount();
            if (count - position / spanCount * spanCount > spanCount) {
                outRect.set(0, 0, 0, mVerticalSpace);
            }
        }
    }
}

package com.wenyu.ylive.biz.home.main.view;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.wenyu.loadingrecyclerview.BaseRVAdapter;
import com.wenyu.loadingrecyclerview.LoadingRecyclerView;
import com.wenyu.mvp.view.BaseMvpView;
import com.wenyu.ylive.R;
import com.wenyu.ylive.biz.home.main.adapter.HomeMainAdapter;
import com.wenyu.ylive.biz.home.main.presenter.HomeMainEventListener;
import com.wenyu.ylive.biz.video.VideoActivity;
import com.wenyu.ylive.common.bean.Room;
import com.wenyu.ylive.common.decor.SpaceItemDecoration;
import com.wenyu.ylive.common.listener.LoadingListenerCompat;
import com.wenyu.ylive.test.RTMPActivity;
import com.wenyu.ylive.test.VideoTestActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chan on 17/4/2.
 */

public class HomeMainViewImpl extends BaseMvpView<HomeMainEventListener> implements IHomeMainView {
    private static final int SCROLL_THRESHOLD = 50;
    private final int mTranslationY;

    @Bind(R.id.home_main_tab_layout)
    TabLayout mTabLayout;

    @Bind(R.id.home_main_recycler_view)
    LoadingRecyclerView mLoadingRecyclerView;

    @Bind(R.id.home_main_open_broadcast)
    View mBtnAction;

    private View mCover;
    private HomeMainAdapter mHomeMainAdapter;

    private ObjectAnimator mObjectAnimator;


    private TabLayout.OnTabSelectedListener mOnTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            if (getEventListener() != null) {
                getEventListener().onTabSelected(tab.getPosition());
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
            if (getEventListener() != null) {
                getEventListener().onTabReselected(tab.getPosition());
            }
        }
    };

    @Inject
    public HomeMainViewImpl(@NonNull Activity activity) {
        super(activity);
        View rootView = activity.findViewById(R.id.item_home_root);
        ButterKnife.bind(this, rootView);

        mTranslationY = (int) activity.getResources().getDimension(R.dimen.height100);
        mTabLayout.addOnTabSelectedListener(mOnTabSelectedListener);
        mHomeMainAdapter = new HomeMainAdapter(activity);
        RecyclerView recyclerView = mLoadingRecyclerView.getView();
        recyclerView.addItemDecoration(new SpaceItemDecoration(activity) {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
            }
        });
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (Math.abs(dy) >= SCROLL_THRESHOLD) {
                    playAnimator(dy > 0);
                }
            }
        });
        mHomeMainAdapter.setListener(new BaseRVAdapter.Listener<HomeMainAdapter.HomeMainViewHolder>() {
            @Override
            public void onItemClicked(HomeMainAdapter.HomeMainViewHolder viewHolder, int position) {
                mCover = viewHolder.ivCover;
                if (getEventListener() != null) {
                    getEventListener().onRoomClicked(position);
                }
            }
        });
        mLoadingRecyclerView.setAdapter(mHomeMainAdapter);
    }

    @OnClick(R.id.home_main_open_broadcast)
    void onOpenBroadcastClicked() {
        Intent intent = new Intent(getActivity(), VideoTestActivity.class);
        getActivity().startActivity(intent);
    }

    @Override
    public void refresh(List<Data> data) {
        mHomeMainAdapter.setDataList(data);
    }

    @Override
    public void loadMore(List<Data> data) {
        mHomeMainAdapter.addDataList(data);
    }

    @Override
    public void setLoadingListener(LoadingListenerCompat<?> dataLoadingListenerCompat) {
        mLoadingRecyclerView.setListener(dataLoadingListenerCompat);
    }

    @Override
    public void renderLoading() {
        mLoadingRecyclerView.performRefresh();
    }

    @Override
    public void gotoRoom(Room room) {
        Activity activity = getActivity();
        Intent intent = VideoActivity.newIntent(getActivity(), room);
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(getActivity(), mCover, mCover.getTransitionName()).toBundle();
        activity.startActivity(intent, bundle);
    }

    private void playAnimator(boolean toBottom) {
        if (mObjectAnimator != null && mObjectAnimator.isRunning()) {
            mObjectAnimator.cancel();
        }

        if (toBottom) {
            mObjectAnimator = ObjectAnimator.ofFloat(mBtnAction, View.TRANSLATION_Y, 0, mTranslationY);
        } else {
            mObjectAnimator = ObjectAnimator.ofFloat(mBtnAction, View.TRANSLATION_Y, mBtnAction.getTranslationY(), 0);
        }
        mObjectAnimator.setDuration(500);
        mObjectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mObjectAnimator.start();
    }
}
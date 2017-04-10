package com.wenyu.ylive.biz.home.main.view;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.wenyu.loadingrecyclerview.LoadingRecyclerView;
import com.wenyu.mvp.view.BaseMvpView;
import com.wenyu.ylive.R;
import com.wenyu.ylive.biz.home.main.adapter.HomeMainAdapter;
import com.wenyu.ylive.biz.home.main.presenter.HomeMainEventListener;
import com.wenyu.ylive.common.decor.SpaceItemDecoration;
import com.wenyu.ylive.common.listener.LoadingListenerCompat;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chan on 17/4/2.
 */

public class HomeMainViewImpl extends BaseMvpView<HomeMainEventListener> implements IHomeMainView {

    @Bind(R.id.home_main_tab_layout)
    TabLayout mTabLayout;

    @Bind(R.id.home_main_recycler_view)
    LoadingRecyclerView mLoadingRecyclerView;

    private HomeMainAdapter mHomeMainAdapter;

    @Inject
    public HomeMainViewImpl(@NonNull Activity activity) {
        super(activity);
        View rootView = activity.findViewById(R.id.item_home_root);
        ButterKnife.bind(this, rootView);

        mHomeMainAdapter = new HomeMainAdapter(activity);
        mLoadingRecyclerView.getView().addItemDecoration(new SpaceItemDecoration(activity));
        mLoadingRecyclerView.getView().setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mLoadingRecyclerView.setAdapter(mHomeMainAdapter);
    }

    @OnClick(R.id.home_main_open_broadcast)
    void onOpenBroadcastClicked() {

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
}

package com.wenyu.ylive.biz.video;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.wenyu.apt.MvpInjector;
import com.wenyu.apt.annotations.MvpInject;
import com.wenyu.apt.annotations.MvpModel;
import com.wenyu.apt.annotations.MvpPresenter;
import com.wenyu.apt.annotations.MvpView;
import com.wenyu.ylive.R;
import com.wenyu.ylive.base.YLiveActivity;
import com.wenyu.ylive.biz.video.inject.VideoComponent;
import com.wenyu.ylive.biz.video.inject.VideoModule;
import com.wenyu.ylive.biz.video.model.VideoModelImpl;
import com.wenyu.ylive.biz.video.presenter.VideoPresenterImpl;
import com.wenyu.ylive.biz.video.video.VideoViewImpl;

import javax.inject.Inject;

@MvpInject(module = VideoModule.class, component = VideoComponent.class)
public class VideoActivity extends YLiveActivity {

    @MvpModel
    @Inject
    VideoModelImpl mVideoModel;

    @MvpView
    @Inject
    VideoViewImpl mVideoView;

    @MvpPresenter
    VideoPresenterImpl mVideoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MvpInjector.inject(this);
    }

    @Override
    protected int contentId() {
        return R.layout.activity_video;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu_video, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        switch (id) {
            case R.id.video_subscribe:
                return true;
            case R.id.video_un_subscribe:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Toolbar findToolbarById() {
        return (Toolbar) findViewById(R.id.video_toolbar);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, VideoActivity.class);
    }
}

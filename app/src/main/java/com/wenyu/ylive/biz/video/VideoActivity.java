package com.wenyu.ylive.biz.video;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

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
import com.wenyu.ylive.common.bean.Room;

import javax.inject.Inject;

@MvpInject(module = VideoModule.class, component = VideoComponent.class)
public class VideoActivity extends YLiveActivity {

    private static final String KEY_ROOM = "room";

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

        Room room = getIntent().getParcelableExtra(KEY_ROOM);
        mVideoPresenter.attach();

        mVideoPresenter.init(room);
    }

    @Override
    protected void onDestroy() {
        mVideoPresenter.detach();
        super.onDestroy();
    }

    @Override
    protected int contentId() {
        return R.layout.activity_video;
    }

    @Override
    protected Toolbar findToolbarById() {
        return (Toolbar) findViewById(R.id.video_toolbar);
    }

    public static Intent newIntent(Context context, Room room) {
        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra(KEY_ROOM, room);
        return intent;
    }
}

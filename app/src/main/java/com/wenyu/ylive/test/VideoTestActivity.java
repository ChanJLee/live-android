package com.wenyu.ylive.test;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wenyu.media.cview.VideoView;
import com.wenyu.ylive.BuildConfig;
import com.wenyu.ylive.R;

public class VideoTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_test);
        VideoView videoView = (VideoView) findViewById(R.id.video);
        videoView.setVideoURI(Uri.parse("rtmp://192.168.1.104:1396/chan_live/rtmpstream"));
        videoView.start();
    }
}

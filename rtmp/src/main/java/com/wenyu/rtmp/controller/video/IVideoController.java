package com.wenyu.rtmp.controller.video;

import com.wenyu.rtmp.configuration.VideoConfiguration;
import com.wenyu.rtmp.video.OnVideoEncodeListener;

public interface IVideoController {
    void start();
    void stop();
    void pause();
    void resume();
    boolean setVideoBps(int bps);
    void setVideoEncoderListener(OnVideoEncodeListener listener);
    void setVideoConfiguration(VideoConfiguration configuration);
}

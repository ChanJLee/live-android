package com.wenyu.rtmp.controller.video;

import com.wenyu.rtmp.configuration.VideoConfiguration;
import com.wenyu.rtmp.video.OnVideoEncodeListener;

/**
 * @Title: IVideoController
 * @Package com.wenyu.rtmp.controller.video
 * @Description:
 * @Author Jim
 * @Date 2016/11/2
 * @Time 下午2:17
 * @Version
 */

public interface IVideoController {
    void start();
    void stop();
    void pause();
    void resume();
    boolean setVideoBps(int bps);
    void setVideoEncoderListener(OnVideoEncodeListener listener);
    void setVideoConfiguration(VideoConfiguration configuration);
}

package com.wenyu.rtmp.controller.audio;

import com.wenyu.rtmp.audio.OnAudioEncodeListener;
import com.wenyu.rtmp.configuration.AudioConfiguration;

/**
 * @Title: IAudioController
 * @Package com.wenyu.rtmp.controller.audio
 * @Description:
 * @Author Jim
 * @Date 2016/11/2
 * @Time 下午2:09
 * @Version
 */

public interface IAudioController {
    void start();
    void stop();
    void pause();
    void resume();
    void mute(boolean mute);
    int getSessionId();
    void setAudioConfiguration(AudioConfiguration audioConfiguration);
    void setAudioEncodeListener(OnAudioEncodeListener listener);
}

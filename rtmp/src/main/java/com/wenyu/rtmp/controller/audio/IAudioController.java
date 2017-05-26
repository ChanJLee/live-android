package com.wenyu.rtmp.controller.audio;

import com.wenyu.rtmp.audio.OnAudioEncodeListener;
import com.wenyu.rtmp.configuration.AudioConfiguration;

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

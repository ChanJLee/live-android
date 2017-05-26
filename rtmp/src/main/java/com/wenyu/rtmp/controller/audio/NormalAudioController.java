package com.wenyu.rtmp.controller.audio;

import android.annotation.TargetApi;
import android.media.AudioRecord;

import com.wenyu.rtmp.audio.OnAudioEncodeListener;
import com.wenyu.rtmp.configuration.AudioConfiguration;
import com.wenyu.rtmp.constant.SopCastConstant;
import com.wenyu.rtmp.audio.AudioProcessor;
import com.wenyu.rtmp.audio.AudioUtils;
import com.wenyu.rtmp.controller.audio.IAudioController;
import com.wenyu.rtmp.utils.SopCastLog;

public class NormalAudioController implements IAudioController {
    private OnAudioEncodeListener mListener;
    private AudioRecord mAudioRecord;
    private AudioProcessor mAudioProcessor;
    private boolean mMute;
    private AudioConfiguration mAudioConfiguration;

    public NormalAudioController() {
        mAudioConfiguration = AudioConfiguration.createDefault();
    }

    public void setAudioConfiguration(AudioConfiguration audioConfiguration) {
        mAudioConfiguration = audioConfiguration;
    }

    public void setAudioEncodeListener(OnAudioEncodeListener listener) {
        mListener = listener;
    }

    public void start() {
        SopCastLog.d(SopCastConstant.TAG, "Audio Recording start");
        mAudioRecord = AudioUtils.getAudioRecord(mAudioConfiguration);
        try {
            mAudioRecord.startRecording();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mAudioProcessor = new AudioProcessor(mAudioRecord, mAudioConfiguration);
        mAudioProcessor.setAudioHEncodeListener(mListener);
        mAudioProcessor.start();
        mAudioProcessor.setMute(mMute);
    }

    public void stop() {
        SopCastLog.d(SopCastConstant.TAG, "Audio Recording stop");
        if(mAudioProcessor != null) {
            mAudioProcessor.stopEncode();
        }
        if(mAudioRecord != null) {
            try {
                mAudioRecord.stop();
                mAudioRecord.release();
                mAudioRecord = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void pause() {
        SopCastLog.d(SopCastConstant.TAG, "Audio Recording pause");
        if(mAudioRecord != null) {
            mAudioRecord.stop();
        }
        if (mAudioProcessor != null) {
            mAudioProcessor.pauseEncode(true);
        }
    }

    public void resume() {
        SopCastLog.d(SopCastConstant.TAG, "Audio Recording resume");
        if(mAudioRecord != null) {
            mAudioRecord.startRecording();
        }
        if (mAudioProcessor != null) {
            mAudioProcessor.pauseEncode(false);
        }
    }

    public void mute(boolean mute) {
        SopCastLog.d(SopCastConstant.TAG, "Audio Recording mute: " + mute);
        mMute = mute;
        if(mAudioProcessor != null) {
            mAudioProcessor.setMute(mMute);
        }
    }

    @Override
    @TargetApi(16)
    public int getSessionId() {
        if(mAudioRecord != null) {
            return mAudioRecord.getAudioSessionId();
        } else {
            return -1;
        }
    }
}

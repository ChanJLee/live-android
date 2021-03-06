package com.wenyu.rtmp.mediacodec;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;

import com.wenyu.rtmp.audio.AudioUtils;
import com.wenyu.rtmp.configuration.AudioConfiguration;

import java.io.IOException;

@TargetApi(18)
public class AudioMediaCodec {

    public static MediaCodec getAudioMediaCodec(AudioConfiguration configuration){
        MediaFormat format = MediaFormat.createAudioFormat(configuration.mime, configuration.frequency, configuration.channelCount);
        if(configuration.mime.equals(AudioConfiguration.DEFAULT_MIME)) {
            format.setInteger(MediaFormat.KEY_AAC_PROFILE, configuration.aacProfile);
        }
        format.setInteger(MediaFormat.KEY_BIT_RATE, configuration.maxBps * 1024);
        format.setInteger(MediaFormat.KEY_SAMPLE_RATE, configuration.frequency);
        int maxInputSize = AudioUtils.getRecordBufferSize(configuration);
        if(configuration.aec && configuration.frequency == 48000) {
            maxInputSize = maxInputSize*3;
        }
        format.setInteger(MediaFormat.KEY_MAX_INPUT_SIZE, maxInputSize);
        format.setInteger(MediaFormat.KEY_CHANNEL_COUNT, configuration.channelCount);

        MediaCodec mediaCodec = null;
        try {
            mediaCodec = MediaCodec.createEncoderByType(configuration.mime);
            mediaCodec.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
        } catch (Exception e) {
            e.printStackTrace();
            if (mediaCodec != null) {
                mediaCodec.stop();
                mediaCodec.release();
                mediaCodec = null;
            }
        }
        return mediaCodec;
    }
}

package com.wenyu.media.media;

import android.content.Context;
import android.support.annotation.IntDef;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import tv.danmaku.ijk.media.exo.IjkExoMediaPlayer;
import tv.danmaku.ijk.media.player.AndroidMediaPlayer;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player.TextureMediaPlayer;

/**
 * Created by jiacheng.li on 16/12/18.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public class MediaBuilder {
    public static final int TYPE_EXO = 0x0521;
    public static final int TYPE_IJK = 0x0525;
    public static final int TYPE_ANDROID = 0x0522;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(value = {TYPE_EXO, TYPE_IJK, TYPE_ANDROID})
    public @interface PlayerType {

    }

    private Options mOptions = new Options();
    private Context mAppContext;

//	static {
//		IjkMediaPlayer.loadLibrariesOnce(null);
//	}

    public MediaBuilder(Context appContext) {
        mAppContext = appContext;
    }

    public MediaBuilder(Context appContext, Options options) {
        mAppContext = appContext;
        mOptions = options;
    }

    public MediaBuilder setType(int type) {
        mOptions.type = type;
        return this;
    }

    public MediaBuilder setUsingMediaCodec(boolean enable) {
        mOptions.usingMediaCodec = enable;
        return this;
    }

    public MediaBuilder setUsingMediaCodecAutoRotate(boolean enable) {
        mOptions.usingMediaCodecAutoRotate = enable;
        return this;
    }

    public MediaBuilder setMediaCodecHandleResolutionChange(boolean enable) {
        mOptions.mediaCodecHandleResolutionChange = enable;
        return this;
    }

    public MediaBuilder setUsingOpenSLES(boolean enable) {
        mOptions.usingOpenSLES = enable;
        return this;
    }

    public MediaBuilder setPixelFormat(int pixelFormat) {
        mOptions.pixelFormat = pixelFormat;
        return this;
    }

    public MediaBuilder setDetachedSurfaceTextureView(boolean enable) {
        mOptions.enableDetachedSurfaceTextureView = enable;
        return this;
    }

    public MediaBuilder setFrameDrop(boolean enable) {
        mOptions.frameDrop = enable;
        return this;
    }

    public MediaBuilder setStartOnPrepared(boolean enable) {
        mOptions.startOnPrepared = enable;
        return this;
    }

    public MediaBuilder setHttpDetectRangeSupport(boolean enable) {
        mOptions.httpDetectRangeSupport = enable;
        return this;
    }

    public MediaBuilder setSkipLoopFilter(int value) {
        mOptions.skipLoopFilter = value;
        return this;
    }

    public void setOptions(Options options) {
        mOptions = options;
    }

    public IMediaPlayer build() {
        IMediaPlayer mediaPlayer = newMediaPlayer();

        if (mOptions.type == TYPE_IJK) {
            configIjkMediaPlayer(mediaPlayer);
        }

        if (mOptions.enableDetachedSurfaceTextureView) {
            mediaPlayer = new TextureMediaPlayer(mediaPlayer);
        }

        return mediaPlayer;
    }

    private void configIjkMediaPlayer(IMediaPlayer mediaPlayer) {
        if (!(mediaPlayer instanceof IjkMediaPlayer)) {
            return;
        }

        IjkMediaPlayer ijkMediaPlayer = (IjkMediaPlayer) mediaPlayer;
        if (mOptions.usingMediaCodec) {
            ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1);
            ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-auto-rotate", mOptions.usingMediaCodecAutoRotate ? 1 : 0);
            ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-handle-resolution-change", mOptions.mediaCodecHandleResolutionChange ? 1 : 0);
        } else {
            ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 0);
        }

        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "opensles", mOptions.usingOpenSLES ? 1 : 0);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "overlay-format", mOptions.pixelFormat);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", mOptions.frameDrop ? 1 : 0);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "start-on-prepared", mOptions.startOnPrepared ? 1 : 0);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "http-detect-range-support", mOptions.httpDetectRangeSupport ? 1 : 0);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_CODEC, "skip_loop_filter", mOptions.skipLoopFilter);
    }

    private IMediaPlayer newMediaPlayer() {
        switch (mOptions.type) {
            case TYPE_EXO:
                return new IjkExoMediaPlayer(mAppContext);
            case TYPE_ANDROID:
                return new AndroidMediaPlayer();
            case TYPE_IJK:
                return new IjkMediaPlayer();
            default:
                throw new IllegalStateException("播放器类型错误");
        }
    }

    public static class Options {

        /**
         * 播放器类型
         */
        @PlayerType
        public int type = TYPE_IJK;

        /**
         * 是否使用媒体编解码器
         */
        public boolean usingMediaCodec = true;
        /**
         * 是否使用媒体编解码器自动转屏
         */
        public boolean usingMediaCodecAutoRotate = false;

        /**
         * 是否使用媒体编解码器自动处理分辨率变化
         */
        public boolean mediaCodecHandleResolutionChange = false;

        /**
         * 是否启用OpenSL ES 即音频加速
         */
        public boolean usingOpenSLES = false;

        /**
         * 像素类型
         * SDL_FCC_YV12 YV12
         * SDL_FCC_RV16 RGB565
         * SDL_FCC_RV32 RGBX8888
         */
        public int pixelFormat = IjkMediaPlayer.SDL_FCC_RV32;


        /**
         * 是否支持在surface texture中运行
         */
        public boolean enableDetachedSurfaceTextureView = false;

        /**
         * 下面的可能和音频延迟有关
         * https://github.com/Bilibili/ijkplayer/issues/1572
         */
        public boolean frameDrop = true;
        public boolean startOnPrepared = false;
        public boolean httpDetectRangeSupport = false;
        public int skipLoopFilter = 48;
    }
}

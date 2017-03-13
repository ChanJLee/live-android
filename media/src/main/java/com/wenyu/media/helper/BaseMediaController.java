package com.wenyu.media.helper;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.wenyu.media.cview.IRenderView;
import com.wenyu.media.cview.TextureRenderView;
import com.wenyu.media.cview.VideoView;
import com.wenyu.media.media.FileMediaDataSource;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player.misc.IMediaDataSource;
import tv.danmaku.ijk.media.player.misc.ITrackInfo;

/**
 * Created by jiacheng.li on 16/12/24.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public abstract class BaseMediaController {
	private static final int STATE_ERROR = -1;
	private static final int STATE_IDLE = 0;
	private static final int STATE_PREPARING = 1;
	private static final int STATE_PREPARED = 2;
	private static final int STATE_PLAYING = 3;
	private static final int STATE_PAUSED = 4;
	private static final int STATE_PLAYBACK_COMPLETED = 5;
	private static final int[] ALL_ASPECT_RATIO = {
			IRenderView.AR_ASPECT_FIT_PARENT,
			IRenderView.AR_ASPECT_FILL_PARENT,
			IRenderView.AR_ASPECT_WRAP_CONTENT,
			IRenderView.AR_16_9_FIT_PARENT,
			IRenderView.AR_4_3_FIT_PARENT
	};

	private Uri mUri;
	private Map<String, String> mHeaders;
	private int mCurrentState = STATE_IDLE;
	private int mTargetState = STATE_IDLE;
	private int mVideoWidth;
	private int mVideoHeight;
	private int mSurfaceWidth;
	private int mSurfaceHeight;
	private int mVideoRotationDegree = 0;
	private IMediaPlayer.OnInfoListener mOnInfoListener;
	private IMediaPlayer.OnCompletionListener mOnCompletionListener;
	private IMediaPlayer.OnPreparedListener mOnPreparedListener;
	private IMediaPlayer.OnErrorListener mOnErrorListener;
	private IMediaPlayer.OnSeekCompleteListener mOnSeekCompleteListener;

	private IMediaPlayer mMediaPlayer;
	private int mCurrentBufferPercentage;
	private Callback mCallback;
	private boolean mCanPause = true;
	private boolean mCanSeekBack = true;
	private boolean mCanSeekForward = true;
	private Context mAppContext;
	private int mVideoSarNum;
	private int mVideoSarDen;
	private int mSeekWhenPrepared;
	private IRenderView.ISurfaceHolder mSurfaceHolder;
	private VideoView mVideoView;
	private int mCurrentAspectRatioIndex = 0;
	private int mCurrentAspectRatio = ALL_ASPECT_RATIO[0];

	IMediaPlayer.OnPreparedListener mPreparedListener = new IMediaPlayer.OnPreparedListener() {
		public void onPrepared(IMediaPlayer mp) {

			mCurrentState = STATE_PREPARED;

			if (mOnPreparedListener != null) {
				mOnPreparedListener.onPrepared(mMediaPlayer);
			}

			mVideoWidth = mp.getVideoWidth();
			mVideoHeight = mp.getVideoHeight();

			int seekToPosition = mSeekWhenPrepared;
			if (seekToPosition != 0) {
				seekTo(seekToPosition);
			}
			if (mVideoWidth != 0 && mVideoHeight != 0) {

				if (mVideoView != null) {
					mVideoView.setVideoSize(mVideoWidth, mVideoHeight);
					mVideoView.setVideoSampleAspectRatio(mVideoSarNum, mVideoSarDen);
					if (!mVideoView.getRenderView().shouldWaitForResize() || mSurfaceWidth == mVideoWidth && mSurfaceHeight == mVideoHeight) {
						if (mTargetState == STATE_PLAYING) {
							start();
						}
					}
				}
			} else {
				if (mTargetState == STATE_PLAYING) {
					start();
				}
			}
		}
	};

	private IMediaPlayer.OnCompletionListener mCompletionListener = new IMediaPlayer.OnCompletionListener() {
		public void onCompletion(IMediaPlayer mp) {
			mCurrentState = STATE_PLAYBACK_COMPLETED;
			mTargetState = STATE_PLAYBACK_COMPLETED;
			if (mOnCompletionListener != null) {
				mOnCompletionListener.onCompletion(mMediaPlayer);
			}
		}
	};

	private IMediaPlayer.OnBufferingUpdateListener mBufferingUpdateListener = new IMediaPlayer.OnBufferingUpdateListener() {
		public void onBufferingUpdate(IMediaPlayer mp, int percent) {
			mCurrentBufferPercentage = percent;
		}
	};

	private IMediaPlayer.OnErrorListener mErrorListener = new IMediaPlayer.OnErrorListener() {
		public boolean onError(IMediaPlayer mp, int framework_err, int impl_err) {
			mCurrentState = STATE_ERROR;
			mTargetState = STATE_ERROR;

			if (mOnErrorListener != null) {
				if (mOnErrorListener.onError(mMediaPlayer, framework_err, impl_err)) {
					return true;
				}
			}
			return true;
		}
	};

	private IMediaPlayer.OnSeekCompleteListener mSeekCompleteListener = new IMediaPlayer.OnSeekCompleteListener() {

		@Override
		public void onSeekComplete(IMediaPlayer mp) {
			if (mOnSeekCompleteListener != null) {
				mOnSeekCompleteListener.onSeekComplete(mp);
			}
		}
	};

	IRenderView.IRenderCallback mSHCallback = new IRenderView.IRenderCallback() {
		@Override
		public void onSurfaceChanged(@NonNull IRenderView.ISurfaceHolder holder, int format, int w, int h) {

			mSurfaceWidth = w;
			mSurfaceHeight = h;

			boolean isValidState = (mTargetState == STATE_PLAYING);
			boolean hasValidSize = !mVideoView.getRenderView().shouldWaitForResize() || (mVideoWidth == w && mVideoHeight == h);
			if (mMediaPlayer != null && isValidState && hasValidSize) {
				if (mSeekWhenPrepared != 0) {
					seekTo(mSeekWhenPrepared);
				}
				start();
			}

			if (mCallback != null && mVideoWidth != w && mVideoHeight != h) {
				if (isValidState) {
					mCallback.onVideoViewChanged(w, h);
				}
			}
		}

		@Override
		public void onSurfaceCreated(@NonNull IRenderView.ISurfaceHolder holder, int width, int height) {
			mSurfaceHolder = holder;
			tryBindMediaAndSurfaceHolder();

			if (mCallback != null) {
				mCallback.onVideoViewCreated(width, height);
			}
		}

		@Override
		public void onSurfaceDestroyed(@NonNull IRenderView.ISurfaceHolder holder) {
			mSurfaceHolder = null;
			//stopPlayback();
			mMediaPlayer.stop();

			if (mCallback != null) {
				mCallback.onVideoViewDestroyed();
			}
		}
	};

	public BaseMediaController(Context appContext) {
		mAppContext = appContext;
		mCurrentState = STATE_IDLE;
		mTargetState = STATE_IDLE;
		mVideoWidth = 0;
		mVideoHeight = 0;
	}

	public void bindVideoView(VideoView videoView) {
		mVideoView = videoView;
		mVideoView.addRenderCallback(mSHCallback);
	}

	private void openVideo() {
		if (mUri == null || mSurfaceHolder == null || mMediaPlayer == null) {
			return;
		}

		AudioManager audioManager = (AudioManager) mAppContext.getSystemService(Context.AUDIO_SERVICE);
		audioManager.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
		mCurrentBufferPercentage = 0;

		try {
			String scheme = mUri.getScheme();
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && (TextUtils.isEmpty(scheme) || scheme.equalsIgnoreCase("file"))) {
				IMediaDataSource dataSource = new FileMediaDataSource(new File(mUri.toString()));
				mMediaPlayer.setDataSource(dataSource);
			} else {
				mMediaPlayer.setDataSource(mAppContext, mUri, mHeaders);
			}

			mMediaPlayer.prepareAsync();
			mCurrentState = STATE_PREPARING;
		} catch (IOException ex) {
			mCurrentState = STATE_ERROR;
			mTargetState = STATE_ERROR;
			mErrorListener.onError(mMediaPlayer, MediaPlayer.MEDIA_ERROR_UNKNOWN, 0);
		} catch (IllegalArgumentException ex) {
			mCurrentState = STATE_ERROR;
			mTargetState = STATE_ERROR;
			mErrorListener.onError(mMediaPlayer, MediaPlayer.MEDIA_ERROR_UNKNOWN, 0);
		}
	}

	public void bindMediaPlayer(IMediaPlayer mediaPlayer, Callback callback) {
		mMediaPlayer = mediaPlayer;
		mCallback = callback;
		mMediaPlayer.setOnPreparedListener(mPreparedListener);
		mMediaPlayer.setOnVideoSizeChangedListener(mSizeChangedListener);
		mMediaPlayer.setOnCompletionListener(mCompletionListener);
		mMediaPlayer.setOnErrorListener(mErrorListener);
		mMediaPlayer.setOnBufferingUpdateListener(mBufferingUpdateListener);
		mMediaPlayer.setOnSeekCompleteListener(mSeekCompleteListener);
		mMediaPlayer.setOnInfoListener(mOnInfoListener);
		mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mMediaPlayer.setScreenOnWhilePlaying(true);
		tryBindMediaAndSurfaceHolder();
	}

	private void tryBindMediaAndSurfaceHolder() {

		if (mSurfaceHolder == null || mMediaPlayer == null) {
			return;
		}

		if (mVideoView != null && mVideoView.getSurfaceType() == VideoView.TYPE_TEXTURE) {
			TextureRenderView renderView = (TextureRenderView) mVideoView.getRenderView();
			renderView.getSurfaceHolder().bindToMediaPlayer(mMediaPlayer);
			renderView.setVideoSize(mMediaPlayer.getVideoWidth(), mMediaPlayer.getVideoHeight());
			renderView.setVideoSampleAspectRatio(mMediaPlayer.getVideoSarNum(), mMediaPlayer.getVideoSarDen());
			renderView.setAspectRatio(mCurrentAspectRatio);
		}
		mSurfaceHolder.bindToMediaPlayer(mMediaPlayer);
	}

	IMediaPlayer.OnVideoSizeChangedListener mSizeChangedListener = new IMediaPlayer.OnVideoSizeChangedListener() {
		public void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sarNum, int sarDen) {
			mVideoWidth = mp.getVideoWidth();
			mVideoHeight = mp.getVideoHeight();
			mVideoSarNum = mp.getVideoSarNum();
			mVideoSarDen = mp.getVideoSarDen();
			if (mVideoWidth != 0 && mVideoHeight != 0) {
				if (mVideoView != null) {
					mVideoView.setVideoSize(mVideoWidth, mVideoHeight);
					mVideoView.setVideoSampleAspectRatio(mVideoSarNum, mVideoSarDen);
				}
			}
		}
	};

	public void setVideoPath(String path) {
		setVideoURI(Uri.parse(path));
	}

	public void setVideoURI(Uri uri) {
		setVideoURI(uri, null);
	}

	private void setVideoURI(Uri uri, Map<String, String> headers) {
		mUri = uri;
		mHeaders = headers;
		mSeekWhenPrepared = 0;
		openVideo();
		mVideoView.requestLayout();
		mVideoView.invalidate();
	}

	public ITrackInfo[] getTrackInfo() {
		if (mMediaPlayer == null)
			return null;

		return mMediaPlayer.getTrackInfo();
	}

	public void selectTrack(int stream) {
		if (mMediaPlayer instanceof IjkMediaPlayer) {
			((IjkMediaPlayer) mMediaPlayer).selectTrack(stream);
		}
	}

	public void deselectTrack(int stream) {
		if (mMediaPlayer instanceof IjkMediaPlayer) {
			((IjkMediaPlayer) mMediaPlayer).deselectTrack(stream);
		}
	}

	public int getSelectedTrack(int trackType) {
		if (mMediaPlayer instanceof IjkMediaPlayer) {
			return ((IjkMediaPlayer) mMediaPlayer).getSelectedTrack(trackType);
		}

		return -1;
	}

	public void start() {
		if (isInPlaybackState()) {
			mMediaPlayer.start();
			mCurrentState = STATE_PLAYING;
		}
		mTargetState = STATE_PLAYING;
	}

	public void pause() {
		if (isInPlaybackState()) {
			if (mMediaPlayer.isPlaying()) {
				mMediaPlayer.pause();
				mCurrentState = STATE_PAUSED;
			}
		}
		mTargetState = STATE_PAUSED;
	}

	public void suspend() {
		release(false);
	}

	public void resume() {
		openVideo();
	}

	public int getDuration() {
		if (isInPlaybackState()) {
			return (int) mMediaPlayer.getDuration();
		}

		return -1;
	}

	public int getCurrentPosition() {
		if (isInPlaybackState()) {
			return (int) mMediaPlayer.getCurrentPosition();
		}
		return 0;
	}

	public void seekTo(int msec) {
		if (isInPlaybackState()) {
			mMediaPlayer.seekTo(msec);
			mSeekWhenPrepared = 0;
		} else {
			mSeekWhenPrepared = msec;
		}
	}

	public boolean isPlaying() {
		return isInPlaybackState() && mMediaPlayer.isPlaying();
	}

	public int getBufferPercentage() {
		if (mMediaPlayer != null) {
			return mCurrentBufferPercentage;
		}
		return 0;
	}

	private boolean isInPlaybackState() {
		return (mMediaPlayer != null &&
				mCurrentState != STATE_ERROR &&
				mCurrentState != STATE_IDLE &&
				mCurrentState != STATE_PREPARING);
	}

	public boolean canPause() {
		return mCanPause;
	}

	public boolean canSeekBackward() {
		return mCanSeekBack;
	}

	public boolean canSeekForward() {
		return mCanSeekForward;
	}

	public int getAudioSessionId() {
		return 0;
	}

	public int toggleAspectRatio() {
		mCurrentAspectRatioIndex++;
		mCurrentAspectRatioIndex %= ALL_ASPECT_RATIO.length;

		mCurrentAspectRatio = ALL_ASPECT_RATIO[mCurrentAspectRatioIndex];
		if (mVideoView != null)
			mVideoView.setAspectRatio(mCurrentAspectRatio);
		return mCurrentAspectRatio;
	}

	public void togglePlayer() {
		if (mMediaPlayer != null) {
			mMediaPlayer.release();
		}

		if (mVideoView != null) {
			mVideoView.invalidate();
		}
		openVideo();
	}

	public void setOnPreparedListener(IMediaPlayer.OnPreparedListener l) {
		mOnPreparedListener = l;
	}

	public void setOnCompletionListener(IMediaPlayer.OnCompletionListener l) {
		mOnCompletionListener = l;
	}

	public void setOnErrorListener(IMediaPlayer.OnErrorListener l) {
		mOnErrorListener = l;
	}

	public void setOnInfoListener(IMediaPlayer.OnInfoListener l) {
		mOnInfoListener = l;
	}

	private void bindSurfaceHolder(IMediaPlayer mp, IRenderView.ISurfaceHolder holder) {
		if (mp == null)
			return;

		if (holder == null) {
			mp.setDisplay(null);
			return;
		}

		holder.bindToMediaPlayer(mp);
	}

	public void releaseWithoutStop() {
		if (mMediaPlayer != null)
			mMediaPlayer.setDisplay(null);
	}

	public void release(boolean clearTargetState) {
		if (mMediaPlayer != null) {
			mMediaPlayer.reset();
			mMediaPlayer.release();
			mMediaPlayer = null;
			mCurrentState = STATE_IDLE;
			if (clearTargetState) {
				mTargetState = STATE_IDLE;
			}
			AudioManager am = (AudioManager) mAppContext.getSystemService(Context.AUDIO_SERVICE);
			am.abandonAudioFocus(null);
		}
	}

	public void stopPlayback() {
		if (mMediaPlayer != null) {
			mMediaPlayer.stop();
			mMediaPlayer.release();
			mMediaPlayer = null;
			mCurrentState = STATE_IDLE;
			mTargetState = STATE_IDLE;
			AudioManager am = (AudioManager) mAppContext.getSystemService(Context.AUDIO_SERVICE);
			am.abandonAudioFocus(null);
		}
	}

	public void setVideoRotationDegree(int videoRotationDegree) {
		mVideoView.setVideoRotation(videoRotationDegree);
	}
	public void setOnSeekCompleteListener(IMediaPlayer.OnSeekCompleteListener listener) {
		mOnSeekCompleteListener = listener;
	}

	public interface Callback {
		void onVideoViewChanged(int width, int height);

		void onVideoViewCreated(int width, int height);

		void onVideoViewDestroyed();
	}

	private ViewGroup mParent;
	private ViewGroup.LayoutParams mLayoutParams;

	public void setFullScreen(Activity activity, boolean fullScreen) {
		Window window = activity.getWindow();
		ViewGroup winParent = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);

		if (fullScreen) {
			WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
					WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
			mParent = (ViewGroup) mVideoView.getParent();
			mLayoutParams = mVideoView.getLayoutParams();

			mParent.removeView(mVideoView);
			mVideoView.setLayoutParams(layoutParams);
			window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
			winParent.addView(mVideoView);
		}
	}
}

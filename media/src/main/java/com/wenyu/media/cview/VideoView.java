package com.wenyu.media.cview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.wenyu.media.R;


/**
 * Created by jiacheng.li on 16/12/18.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public class VideoView extends FrameLayout {

	public static final int TYPE_SURFACE = 1;
	public static final int TYPE_TEXTURE = 2;
	private int mSurfaceType = TYPE_SURFACE;

	private IRenderView.ISurfaceHolder mSurfaceHolder = null;
	private IRenderView mRenderView;
	private OnKeyDownListener mOnKeyDownListener;

	public VideoView(Context context) {
		this(context, null);
	}

	public VideoView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public VideoView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public VideoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {

		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.VideoView, 0, 0);
		mSurfaceType = typedArray.getInt(R.styleable.VideoView_renderer, TYPE_SURFACE);
		initRenders(mSurfaceType);

		setBackgroundColor(Color.BLACK);
		typedArray.recycle();

		setFocusable(true);
		setFocusableInTouchMode(true);
		requestFocus();
	}

	private void initRenders(int type) {
		switch (type) {
			case TYPE_TEXTURE:
				setupRenderView(new TextureRenderView(getContext()));
				break;

			case TYPE_SURFACE:
				setupRenderView(new SurfaceRenderView(getContext()));
				break;
			default:
				break;
		}
	}

	public IRenderView getRenderView() {
		return mRenderView;
	}




	private void setupRenderView(IRenderView renderView) {
		mRenderView = renderView;
		View renderUIView = mRenderView.getView();
		LayoutParams lp = new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT,
				Gravity.CENTER);
		renderUIView.setLayoutParams(lp);
		addView(renderUIView);
	}

	public void setAspectRatio(int ratio) {
		if (mRenderView != null) {
			mRenderView.setAspectRatio(ratio);
		}
	}

	public void setVideoRotation(int degree) {
		if (mRenderView != null) {
			mRenderView.setVideoRotation(degree);
		}
	}

	public void addRenderCallback(IRenderView.IRenderCallback callback) {
		if (mRenderView != null) {
			mRenderView.addRenderCallback(callback);
		}
	}

	public int getSurfaceType() {
		return mSurfaceType;
	}

	public void setOnKeyDownListener(OnKeyDownListener onKeyDownListener) {
		mOnKeyDownListener = onKeyDownListener;
	}

	public void setVideoSize(int width, int height) {
		if (mRenderView != null) {
			mRenderView.setVideoSize(width, height);
			requestLayout();
		}
	}

	public void setVideoSampleAspectRatio(int num, int den) {
		if (mRenderView != null) {
			mRenderView.setVideoSampleAspectRatio(num, den);
			requestLayout();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean isKeyCodeSupported = keyCode != KeyEvent.KEYCODE_BACK &&
				keyCode != KeyEvent.KEYCODE_VOLUME_UP &&
				keyCode != KeyEvent.KEYCODE_VOLUME_DOWN &&
				keyCode != KeyEvent.KEYCODE_VOLUME_MUTE &&
				keyCode != KeyEvent.KEYCODE_MENU &&
				keyCode != KeyEvent.KEYCODE_CALL &&
				keyCode != KeyEvent.KEYCODE_ENDCALL;
		if (isKeyCodeSupported) {
			if (keyCode == KeyEvent.KEYCODE_HEADSETHOOK ||
					keyCode == KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE) {
				if (mOnKeyDownListener != null) {
					mOnKeyDownListener.onKeyHeadSetHookOrMediaPlayPause();
				}
				return true;
			} else if (keyCode == KeyEvent.KEYCODE_MEDIA_PLAY) {
				if (mOnKeyDownListener != null) {
					mOnKeyDownListener.onKeyMediaPlay();
				}
				return true;
			} else if (keyCode == KeyEvent.KEYCODE_MEDIA_STOP
					|| keyCode == KeyEvent.KEYCODE_MEDIA_PAUSE) {
				if (mOnKeyDownListener != null) {
					mOnKeyDownListener.onKeyStopOrPause();
				}
				return true;
			}
		}

		return super.onKeyDown(keyCode, event);
	}

	public interface OnKeyDownListener {
		void onKeyHeadSetHookOrMediaPlayPause();
		void onKeyMediaPlay();
		void onKeyStopOrPause();
	}
}

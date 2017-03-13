package com.wenyu.danmuku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.wenyu.danmuku.interfaces.IDanMa;
import com.wenyu.danmuku.render.RenderManager;

/**
 * Created by jiacheng.li on 17/2/28.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public class DanMaView extends SurfaceView implements SurfaceHolder.Callback {
	private RenderManager mRenderManager;
	private boolean mIsCreated = false;
	private Thread mRenderThread;
	private Paint mBackground;
	private PorterDuffXfermode mClearPorterDuffXfermode;

	public DanMaView(Context context) {
		this(context, null);
	}

	public DanMaView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public DanMaView(Context context, AttributeSet attrs, int defStyleAttr) {
		this(context, attrs, defStyleAttr, 0);
	}

	public DanMaView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init(context, attrs, defStyleAttr, defStyleRes);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		setZOrderOnTop(true);
		getHolder().setFormat(PixelFormat.TRANSLUCENT);
		getHolder().addCallback(this);
		mBackground = new Paint();
		mBackground.setStyle(Paint.Style.FILL);
		mClearPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mRenderManager = new RenderManager();
	}

	@Override
	public void surfaceChanged(final SurfaceHolder holder, int format, final int width, final int height) {
		mIsCreated = true;

		mRenderThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (mIsCreated) {
					try {
						Thread.sleep(60);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					Canvas canvas = holder.lockCanvas();
					if (canvas == null) {
						continue;
					}

					mBackground.setXfermode(mClearPorterDuffXfermode);
					canvas.drawPaint(mBackground);

					mRenderManager.render(canvas, width, height);
					try {
						holder.unlockCanvasAndPost(canvas);
					} catch (Exception e) {
						//canvas object must be the same instance that was previously returned by lockCanvas
					}
				}
			}
		});
		mRenderThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mIsCreated = false;
		mRenderManager.release();
	}

	public void pushDanMa(IDanMa danMa) {
		mRenderManager.push(danMa);
	}
}

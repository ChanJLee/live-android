package com.wenyu.danmuku.render;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.wenyu.danmuku.interfaces.IDanMa;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by jiacheng.li on 17/3/12.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public class RenderManager {

	private static final int DEFAULT_MAX_DAN_MA = 500;
	private BlockingQueue<IDanMa> mDanMaBlockingQueue = null;
	private Rect mWindowBound = new Rect();

	public RenderManager() {
		this(DEFAULT_MAX_DAN_MA);
	}

	public RenderManager(int danMaSize) {
		mDanMaBlockingQueue = new LinkedBlockingDeque<>(danMaSize);

	}

	public void render(Canvas canvas, int width, int height) {
		mWindowBound.set(0, 0, width, height);

		Iterator<IDanMa> iterator = mDanMaBlockingQueue.iterator();
		while (iterator.hasNext()) {
			IDanMa danMa = iterator.next();
			if (mWindowBound.contains(danMa.getBound())) {
				danMa.render(canvas);
			} else {
				iterator.remove();
			}
		}
	}

	public void pushDanMa(IDanMa danMa) {
		try {
			mDanMaBlockingQueue.add(danMa);
		} catch (Exception e) {
			//方式数据溢出
		}
	}

	public void release() {
		mDanMaBlockingQueue = null;
	}
}

package com.wenyu.danmuku.base;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.wenyu.danmuku.interfaces.IDanMa;

/**
 * Created by jiacheng.li on 17/3/12.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public abstract class AbstractDanMa implements IDanMa {
	protected int mSpeed;
	protected int mOrientation;
	protected int mStartX;
	protected int mStartY;

	public AbstractDanMa(int x, int y, int speed, @OrientationType int orientation) {
		mStartX = x;
		mStartY = y;
		mSpeed = speed;
		mOrientation = orientation;
	}

	@Override
	public int getOrientation() {
		return mOrientation;
	}

	@Override
	public int getSpeed() {
		return mSpeed;
	}

	@Override
	public void render(Canvas canvas) {
		onRender(canvas);
		nextFrame();
	}

	abstract protected void onRender(Canvas canvas);

	protected void nextFrame() {
		Rect rect = getBound();
		if (mOrientation == LEFT_TO_RIGHT) {
			rect.offset(mSpeed, 0);
		} else if (mOrientation == RIGHT_TO_LEFT) {
			rect.offset(-mSpeed, 0);
		}
	}
}

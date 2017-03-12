package com.wenyu.danmuku.interfaces;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by jiacheng.li on 17/3/12.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public interface IDanMa {

	int LEFT_TO_RIGHT = 0x0521;
	int RIGHT_TO_LEFT = 0x0525;

	@IntDef({LEFT_TO_RIGHT, RIGHT_TO_LEFT})
	@Retention(RetentionPolicy.SOURCE)
	@interface OrientationType {

	}

	Rect getBound();

	void render(Canvas canvas);

	int getOrientation();

	int getSpeed();
}

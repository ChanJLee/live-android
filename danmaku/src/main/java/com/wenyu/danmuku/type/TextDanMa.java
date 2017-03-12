package com.wenyu.danmuku.type;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.text.TextPaint;

import com.wenyu.danmuku.base.AbstractDanMa;

/**
 * Created by jiacheng.li on 17/3/12.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public class TextDanMa extends AbstractDanMa {
	private static TextPaint sTextPaint;

	public String mContent;
	public Rect mRect;

	public TextDanMa(@NonNull String content, int x, int y, int speed, @OrientationType int orientation) {
		super(x, y, speed, orientation);
		mRect = new Rect();
		TextPaint textPaint = getTextPaint();
		textPaint.getTextBounds(mContent, 0, content.length(), mRect);

		int offsetX = x - mRect.left;
		int offsetY = y - mRect.top;

		mRect.offset(offsetX, offsetY);
	}

	@Override
	public Rect getBound() {
		return mRect;
	}

	@Override
	void onRender(Canvas canvas) {
		canvas.drawText(mContent, mRect.left, mRect.top, getTextPaint());
	}

	private static TextPaint getTextPaint() {
		if (sTextPaint == null) {
			sTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
			sTextPaint.setTextSize(20);
			sTextPaint.setColor(Color.WHITE);
		}
		return sTextPaint;
	}
}

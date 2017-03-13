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
	private static final int DEFAULT_TEST_SIZE = 60;
	private String mContent;
	private Rect mRect;
	private int mColor = Color.WHITE;

	public TextDanMa(@NonNull String content, int x, int y, int speed, @OrientationType int orientation) {
		super(x, y, speed, orientation);
		mRect = new Rect();
		mContent = content;
		TextPaint textPaint = getTextPaint();
		textPaint.getTextBounds(mContent, 0, mContent.length(), mRect);

		int offsetX = x - mRect.left;
		int offsetY = y - mRect.top;

		mRect.offset(offsetX, offsetY);
	}

	@Override
	public Rect getBound() {
		return mRect;
	}

	@Override
	protected void onRender(Canvas canvas) {
		TextPaint textPaint = getTextPaint();
		textPaint.setColor(mColor);
		canvas.drawText(mContent, mRect.left, mRect.top, textPaint);
	}

	public void setColor(int color) {
		mColor = color;
	}

	private static TextPaint getTextPaint() {
		if (sTextPaint == null) {
			sTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
			sTextPaint.setTextSize(DEFAULT_TEST_SIZE);
		}
		return sTextPaint;
	}
}

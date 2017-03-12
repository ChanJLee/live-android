package com.wenyu.danmuku;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * Created by jiacheng.li on 17/2/28.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public class DanMaView extends SurfaceView {
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
	}
}

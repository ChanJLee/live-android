package com.wenyu.ylive.biz.home;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by jiacheng.li on 17/2/2.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public class ColorTranslation extends Transition {

	private static final String KEY = "chan_debug";

	@Override
	public void captureStartValues(TransitionValues transitionValues) {
		if (transitionValues.view.getBackground() instanceof ColorDrawable) {
			captureValue(transitionValues);
		}
	}

	@Override
	public void captureEndValues(TransitionValues transitionValues) {
		if (transitionValues.view.getBackground() instanceof ColorDrawable) {
			captureValue(transitionValues);
		}
	}

	private void captureValue(TransitionValues values) {
		ColorDrawable drawable = (ColorDrawable) values.view.getBackground();
		values.values.put(KEY, drawable.getColor());
	}

	@Override
	public Animator createAnimator(ViewGroup sceneRoot, final TransitionValues startValues, TransitionValues endValues) {
		int startColor = (int) startValues.values.get(KEY);
		int endColor = (int) endValues.values.get(KEY);

		ValueAnimator valueAnimator = ValueAnimator.ofInt(startColor, endColor);
		valueAnimator.setDuration(10000);
		valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
		valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				startValues.view.setBackgroundColor((Integer) animation.getAnimatedValue());
			}
		});
		return valueAnimator;
	}

	@Override
	public String[] getTransitionProperties() {
		return new String[]{KEY};
	}
}

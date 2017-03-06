package com.wenyu.apt.element;

/**
 * Created by jiacheng.li on 17/3/4.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public class Clazz {
	private String mClazzName;

	public Clazz(String clazzName) {
		mClazzName = clazzName.replaceAll("\\.class", "");
	}

	public String getCanonicalName() {
		return mClazzName;
	}

	public String getSimpleName() {
		int index = mClazzName.lastIndexOf('.');
		if (index == -1) {
			index = 0;
		}

		return mClazzName.substring(index + 1);
	}

	public String getPackage() {
		int index = mClazzName.lastIndexOf('.');
		if (index == -1) {
			index = 0;
		}
		return mClazzName.substring(0, index);
	}
}

package com.wenyu.apt.utils;

/**
 * Created by jiacheng.li on 17/3/4.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public class ClazzMapUtils {
	public static String getClazzPackage(String className) {
		return className.replaceAll("\\.", "\\$");
	}
}

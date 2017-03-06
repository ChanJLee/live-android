package com.wenyu.apt;

import com.wenyu.apt.utils.ClazzMapUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by jiacheng.li on 17/3/6.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public class MvpInjector {

	public static void inject(Object o) {
		inject(null, o);
	}

	public static void inject(ClassLoader classLoader, Object o) {
		inject(classLoader, o, null);
	}

	public static void inject(ClassLoader classLoader, Object o, Object dependency) {
		try {
			final String clazzName = o.getClass().getPackage().getName().toString() + "." + ClazzMapUtils.getClazzName(o.getClass().getCanonicalName());
			Class<?> clazz = classLoader != null ? Class.forName(clazzName, false, classLoader) : Class.forName(clazzName);
			Method[] methods = clazz.getDeclaredMethods();
			methods[0].invoke(null, o, dependency);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

package com.wenyu.apt.element;

import java.lang.reflect.Method;

import javafx.util.Pair;

/**
 * Created by jiacheng.li on 17/3/1.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public class PresenterElement extends MvpElement {
	public Class<?> component;
	public Class<?> module;

	public PresenterElement(String fieldName, String enclosingClazzName, Class<?> component, Class<?> module) {
		super(fieldName, enclosingClazzName);
		this.component = component;
		this.module = module;
	}
}

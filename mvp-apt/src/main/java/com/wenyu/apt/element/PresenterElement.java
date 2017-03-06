package com.wenyu.apt.element;

/**
 * Created by jiacheng.li on 17/3/1.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public class PresenterElement extends MvpElement {
	public Clazz component;
	public Clazz module;
	public Clazz dependency;

	public PresenterElement(String fieldName, String enclosingClazzName, String type) {
		super(fieldName, enclosingClazzName, type);
	}
}

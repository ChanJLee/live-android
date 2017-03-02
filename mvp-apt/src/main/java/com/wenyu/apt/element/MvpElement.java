package com.wenyu.apt.element;

/**
 * Created by jiacheng.li on 17/3/1.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public class MvpElement {
	public String fieldName;
	public String enclosingClazzName;

	public MvpElement(String fieldName, String enclosingClazzName) {
		this.fieldName = fieldName;
		this.enclosingClazzName = enclosingClazzName;
	}
}

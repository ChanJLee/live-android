package com.wenyu.ylive.net.core;

/**
 * Created by jiacheng.li on 17/1/15.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public class BaseResponse<T> {
	public int statusCode;
	public String message;
	public T data;
}

package com.wenyu.ylive.net.core;

/**
 * Created by jiacheng.li on 17/1/15.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public class YLiveException extends RuntimeException {

	private int code;
	private String message;

	public YLiveException(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public boolean isNoAuthentication() {
		return code == BaseApiService.CODE_NO_AUTHENTICATION;
	}

	public boolean isError() {
		return code == BaseApiService.CODE_EXCEPTION;
	}

	@Override
	public String getMessage() {
		return message;
	}
}

package com.wenyu.ylive.net.model;

/**
 * Created by jiacheng.li on 17/1/16.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public class User {
	public static User anonymous = new User();

	public String userName;
	public String passWord;
	public String avatar;
	public String age;
	public String phoneNumber;

	public boolean isAnonmyous() {
		return this == anonymous;
	}
}

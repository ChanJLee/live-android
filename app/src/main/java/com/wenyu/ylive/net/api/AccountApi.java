package com.wenyu.ylive.net.api;

import com.google.gson.JsonElement;
import com.wenyu.http.YResponse;
import com.wenyu.ylive.net.model.User;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import rx.Observable;

/**
 * Created by jiacheng.li on 17/1/15.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public interface AccountApi {

	@PUT("/user/login/")
	@FormUrlEncoded
	Observable<YResponse<User>> login(@Field("username") String username, @Field("password") String password);

	@POST("/user/register/")
	@FormUrlEncoded
	Observable<YResponse<JsonElement>> register(@Field("username") String username, @Field("password") String password, @Field("email") String email);

	@PUT("/user/logout/")
	Observable<YResponse<JsonElement>> logout();
}

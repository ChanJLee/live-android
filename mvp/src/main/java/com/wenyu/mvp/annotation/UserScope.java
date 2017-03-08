package com.wenyu.mvp.annotation;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by jiacheng.li on 17/3/8.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
@Scope
@Retention(RUNTIME)
public @interface UserScope {
}

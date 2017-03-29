package com.wenyu.ylive.common.bean;

import android.support.annotation.Keep;

/**
 * Created by chan on 17/3/29.
 */
@Keep
public class User {
    public static User anonymous = new User();

    public String username;
    public String avatar;
    public String email;
}

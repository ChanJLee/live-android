package com.wenyu.ylive.common.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chan on 17/5/10.
 */

public class Broadcast {
    public String title;
    @SerializedName("live_url")
    public String liveUrl;
    @SerializedName("chat_url")
    public String chatUrl;
}

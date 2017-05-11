package com.wenyu.ylive.common.api.service;

import android.content.Context;

import com.google.gson.JsonElement;
import com.wenyu.network.BaseApiService;
import com.wenyu.ylive.BuildConfig;
import com.wenyu.ylive.common.api.YLiveApi;
import com.wenyu.ylive.common.bean.Broadcast;
import com.wenyu.ylive.common.bean.Room;

import java.util.List;

import rx.Observable;

/**
 * Created by chan on 17/4/5.
 */

public class YLiveApiService extends BaseApiService<YLiveApi> {
    public static int CATEGORY_CODE[] = {0x0521, 0x0522, 0x0523, 0x0524, 0x0525};

    private static YLiveApiService sYLiveApiService;

    private YLiveApiService(Context context, String domain, Class<YLiveApi> yLiveApiClass) {
        super(context, domain, yLiveApiClass);
    }

    public static YLiveApiService getYLiveApiService(Context appContext) {
        if (sYLiveApiService == null) {
            synchronized (YLiveApiService.class) {
                if (sYLiveApiService == null) {
                    sYLiveApiService = new YLiveApiService(appContext, BuildConfig.HTTP_BASE_URI, YLiveApi.class);
                }
            }
        }

        return sYLiveApiService;
    }

    public Observable<List<Room>> fetchRoomList(int category, int page) {
        return convert(getAPI().fetchRoomList(category, page));
    }

    public Observable<JsonElement> fetchLivePermission() {
        return convert(getAPI().fetchLivePermission());
    }

    public Observable<Broadcast> openBroadcast(String title, int category) {
        return convert(getAPI().openBroadcast(title, category));
    }

    public Observable<JsonElement> claseBroadcast() {
        return convert(getAPI().closeBroadcast());
    }
}

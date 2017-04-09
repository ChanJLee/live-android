package com.wenyu.ylive.common.api.service;

import android.content.Context;

import com.wenyu.network.BaseApiService;
import com.wenyu.ylive.BuildConfig;
import com.wenyu.ylive.common.api.YLiveApi;
import com.wenyu.ylive.common.bean.Room;

import java.util.List;

import rx.Observable;

/**
 * Created by chan on 17/4/5.
 */

public class YLiveApiService extends BaseApiService<YLiveApi> {

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
}

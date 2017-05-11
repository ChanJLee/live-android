package com.wenyu.ylive.biz.home.main.model;

import com.google.gson.JsonElement;
import com.wenyu.mvp.model.BaseModel;
import com.wenyu.ylive.common.api.service.YLiveApiService;
import com.wenyu.ylive.common.bean.Room;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by chan on 17/4/2.
 */

public class HomeMainModelImpl extends BaseModel implements IHomeMainModel {

    private YLiveApiService mYLiveApiService;

    @Inject
    public HomeMainModelImpl(YLiveApiService yLiveApiService) {
        mYLiveApiService = yLiveApiService;
    }

    @Override
    public Observable<List<Room>> fetchRoomList(int category, int page) {
        return mYLiveApiService.fetchRoomList(category, page);
    }

    @Override
    public Observable<JsonElement> fetchLivePermission() {
        return mYLiveApiService.fetchLivePermission();
    }
}

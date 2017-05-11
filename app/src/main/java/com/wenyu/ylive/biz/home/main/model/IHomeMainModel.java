package com.wenyu.ylive.biz.home.main.model;

import com.google.gson.JsonElement;
import com.wenyu.mvp.model.IMvpModel;
import com.wenyu.ylive.common.bean.Room;

import java.util.List;

import rx.Observable;

/**
 * Created by chan on 17/4/2.
 */

public interface IHomeMainModel extends IMvpModel {
    Observable<List<Room>> fetchRoomList(int category, int page);

    Observable<JsonElement> fetchLivePermission();
}

package com.wenyu.ylive.biz.home.main.model;

import com.wenyu.mvp.model.IMvpModel;
import com.wenyu.ylive.common.bean.Room;

import java.util.List;

import rx.Observable;

/**
 * Created by chan on 17/4/2.
 */

public interface IHomeMainModel extends IMvpModel {
    int CATEGORY_SHOW = 0x0521;
    int CATEGORY_SPORT = 0x0522;
    int CATEGORY_PET = 0x0523;
    int CATEGORY_MUSIC = 0x0524;
    int CATEGORY_DESKTOP_GAME = 0x0525;

    Observable<List<Room>> fetchRoomList(int category, int page);
}

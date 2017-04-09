package com.wenyu.ylive.common.api;

import com.wenyu.network.YResponse;
import com.wenyu.ylive.common.bean.Room;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by chan on 17/4/5.
 */

public interface YLiveApi {

    @GET("program/{id}/")
    Observable<YResponse<List<Room>>> fetchRoomList(@Path("id") int category, @Query("page") int page);
}

package com.wenyu.ylive.common.api;

import com.google.gson.JsonElement;
import com.wenyu.network.YResponse;
import com.wenyu.ylive.common.bean.Broadcast;
import com.wenyu.ylive.common.bean.Room;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by chan on 17/4/5.
 */

public interface YLiveApi {

    @GET("program/{id}/")
    Observable<YResponse<List<Room>>> fetchRoomList(@Path("id") int category, @Query("page") int page);

    @GET("anchor/broadcast/permission/")
    Observable<YResponse<JsonElement>> fetchLivePermission();

    @POST("anchor/broadcast/open/")
    @FormUrlEncoded
    Observable<YResponse<Broadcast>> openBroadcast(@Field("title") String title, @Field("category") int category);

    @PUT("anchor/broadcast/close/")
    Observable<YResponse<JsonElement>> closeBroadcast();
}

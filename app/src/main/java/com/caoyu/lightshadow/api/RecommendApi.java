package com.caoyu.lightshadow.api;

import com.caoyu.lightshadow.api.model.IdList;
import com.caoyu.lightshadow.api.model.One;
import com.caoyu.lightshadow.api.model.Recommend;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * 推荐
 * Created by caoyu on 2017/8/12.
 */

public interface RecommendApi {

    @GET("onelist/idlist")
    Call<IdList> getIdList();

    @GET("onelist/{id}/0")
    Call<One> getOneList(@Path("id") String shotId);
}

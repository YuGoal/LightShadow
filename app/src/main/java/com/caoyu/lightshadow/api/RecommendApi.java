package com.caoyu.lightshadow.api;

import com.caoyu.lightshadow.api.model.Recommend;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * 推荐
 * Created by caoyu on 2017/8/12.
 */

public interface RecommendApi {

    @GET("feed-app")
    Call<Recommend> getRecommend(@QueryMap() Map<String,String> map);

    @GET("feed-app")
    Call<Recommend> getRecommend();
}

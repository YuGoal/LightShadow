package com.caoyu.lightshadow.api;

import com.caoyu.lightshadow.api.model.One;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 推荐
 * Created by caoyu on 2017/8/12.
 */

public interface RecommendApi {

    @GET("api/data/福利/10/{id}")
    Call<One> getMeizi(@Path("id") String shotId);
}

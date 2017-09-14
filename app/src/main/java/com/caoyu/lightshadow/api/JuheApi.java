package com.caoyu.lightshadow.api;

import com.caoyu.lightshadow.api.model.Meizi;
import com.caoyu.lightshadow.api.model.Two;

import java.util.Map;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * 聚合数据
 * Created by yugoal on 2017/8/21.
 */

public interface JuheApi {
    @GET("819-1")
    Call<ResponseBody> getMeizi(@QueryMap Map<String,String> map);
}

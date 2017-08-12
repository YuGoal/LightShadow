package com.caoyu.lightshadow.api;

import android.util.Log;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 网络拦截器
 * Created by caoyu on 2017/8/12.
 */

class MyInterceptors implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //封装headers
        Request request = chain.request().newBuilder()
                .build();
        Headers headers = request.headers();
        //打印
        System.out.println("phoneModel is : " + headers.get("phoneModel"));
        String requestUrl = request.url().toString(); //获取请求url地址
        String methodStr = request.method(); //获取请求方式
        RequestBody body = request.body(); //获取请求body
        String bodyStr = (body==null?"":body.toString());
        //打印Request数据
        Log.d("request", "intercept: Request Url is :" + requestUrl + "\nMethod is : " + methodStr + "\nRequest Body is :" + bodyStr + "\n");
        Response response = chain.proceed(request);
        Log.d("response", "intercept: response  "+response.body().toString());
        return response;
    }
}

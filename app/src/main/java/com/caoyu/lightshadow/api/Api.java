package com.caoyu.lightshadow.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求类
 * Created by caoyu on 2017/8/12.
 */

public class Api {

    public final static int CONNECT_TIMEOUT =5;
    public final static int READ_TIMEOUT=5;
    public final static int WRITE_TIMEOUT=5;

    //服务器地址
    private static final String BASE_URL = "http://gank.io/";
    private static Retrofit mRetrofit;

    public static Retrofit getRetrofit() {
        if (mRetrofit == null) {
            //网络缓存路径文件
            // File httpCacheDirectory = new File(BaseApplication.getInstance().getExternalCacheDir(), "responses");
            //通过拦截器设置缓存，暂未实现
            //CacheInterceptor cacheInterceptor = new CacheInterceptor();

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(new MyInterceptors())
                    .build();

            mRetrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return mRetrofit;
    }
}

package com.caoyu.lightshadow.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by caoyu on 2017/8/25.
 */

public class ToDayApi {
    public final static int CONNECT_TIMEOUT =5;
    public final static int READ_TIMEOUT=5;
    public final static int WRITE_TIMEOUT=5;

    //服务器地址
//    public static final String JUHE_URL = "http://api.juheapi.com/";
    public static final String JUHE_URL = "http://route.showapi.com/";
//    public static final String APPKEY = "9252e686c8aad026bd2c3d545e961ace";
    public static final String APPKEY = "24037";
    public static final String APPSIGN = "76e3b44c58e841458ad832387acf37f9";
    public static Retrofit mRetrofit;

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
                    .baseUrl(JUHE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return mRetrofit;
    }
}

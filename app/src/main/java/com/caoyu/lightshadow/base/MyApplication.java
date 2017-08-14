package com.caoyu.lightshadow.base;

import android.app.Application;

import com.caoyu.lightshadow.util.CrashHandler;

/**
 * 自定义Application
 * Created by caoyu on 2017/8/14.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }
}

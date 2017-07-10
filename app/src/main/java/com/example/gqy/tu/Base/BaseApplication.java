package com.example.gqy.tu.Base;

import android.app.Application;
import android.content.Context;

import com.example.gqy.tu.Bean.Data;
import com.example.gqy.tu.BuildConfig;
import com.example.gqy.tu.Utile.Utils;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mszf on 2017/7/3.
 */

public class BaseApplication extends Application {

    private static BaseApplication instance = null;
    private static Context mContext;
    public static List<Data> initData = new ArrayList<>();

    public static BaseApplication getInstance() {
        if (instance == null) {
            instance = new BaseApplication();

        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        if (BuildConfig.DEBUG) {
            Logger
                    .init("BaseRecyclerViewAdapter")                 // default PRETTYLOGGER or use just init()
                    .methodCount(3)                 // default 2
                    .logLevel(LogLevel.FULL)        // default LogLevel.FULL
                    .methodOffset(2)                // default 0
            ; //default AndroidLogAdapter


        }
    }
}


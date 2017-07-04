package com.example.gqy.tu.Base;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.example.gqy.tu.Bean.ImageInfo;
import com.example.gqy.tu.BuildConfig;
import com.example.gqy.tu.Utile.MoreUtile;
import com.example.gqy.tu.Utile.Utils;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mszf on 2017/7/3.
 */

public class BaseApplication extends Application{

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

package com.example.xx.myapplication01;

import android.app.Application;

import com.example.xx.myapplication01.utils.Utils;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}

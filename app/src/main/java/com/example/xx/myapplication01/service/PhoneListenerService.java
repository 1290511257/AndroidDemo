package com.example.xx.myapplication01.service;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.xx.myapplication01.TelListener;


public class PhoneListenerService extends Service {

    private static String TAG = "==============";
    private TelephonyManager telephonyManager;
    private TelListener telListener;


    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG,"onbind");
        return null;
    }

    //Service被创建时调用
    @Override
    public void onCreate() {
        Log.i(TAG, "电话监听Service创建!");
        super.onCreate();
    }

    //Service被启动时调用
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "电话监听Service启动!");

        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        telListener = new TelListener();

        telephonyManager.listen(telListener, PhoneStateListener.LISTEN_CALL_STATE);

        return super.onStartCommand(intent, flags, startId);
    }

    //Service被关闭之前回调
    @Override
    public void onDestroy() {
        Log.i(TAG, "电话监听Service关闭!");
        if(telephonyManager != null && telListener != null){
            //取消电话监听状态
            telephonyManager.listen(telListener, PhoneStateListener.LISTEN_NONE);
        }
        super.onDestroy();
    }

}

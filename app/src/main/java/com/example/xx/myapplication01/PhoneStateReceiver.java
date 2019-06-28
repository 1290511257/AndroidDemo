package com.example.xx.myapplication01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

public class PhoneStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        int phoneState = intent.getIntExtra(TelephonyManager.EXTRA_STATE,0);

        switch (phoneState){
            case TelephonyManager.CALL_STATE_IDLE: //无任何状态时
                Log.i("=================","CALL_STATE_IDLE");
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK: //接通
                Log.i("=================","CALL_STATE_OFFHOOK");
                break;
            case TelephonyManager.CALL_STATE_RINGING://响铃
                //响铃时的具体操作
                Log.i("=================","CALL_STATE_RINGING");
                break;
        }
    }
}

package com.example.xx.myapplication01.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.xx.myapplication01.R;
import com.example.xx.myapplication01.service.TestService;

public class ServiceActivity extends Activity {

    private Button start;
    private Button stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicetest);

        start = (Button) findViewById(R.id.btnstart);
        stop = (Button) findViewById(R.id.btnstop);
//        创建启动Service的Intent,以及Intent属性
        final Intent intent = new Intent(ServiceActivity.this,TestService.class);
        //intent.setAction("com.example.xx.myapplication01.service.TestService");
        //为两个按钮设置点击事件,分别是启动与停止service
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startService(new Intent(ServiceActivity.this, PhoneListenerService.class));
                startService(intent);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(intent);

            }
        });
    }
}

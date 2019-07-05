package com.example.xx.myapplication01.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.xx.myapplication01.FileHelper;
import com.example.xx.myapplication01.R;
import com.example.xx.myapplication01.service.PhoneListenerService;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editname;
    private EditText editdetail;
    private Button btnsave;
    private Button btnclean;
    private Button btnread;
    private Context mContext;
    private Button btncall;
    private Button btntelmonitor;
    private Button btntelmonitorclose;

    private Intent phoneListenerIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        bindViews();
    }


    private void bindViews() {

        editdetail = findViewById(R.id.editdetail);
        editname = findViewById(R.id.editname);
        btnclean = findViewById(R.id.btnclean);
        btnsave = findViewById(R.id.btnsave);
        btnread = findViewById(R.id.btnread);
        btncall = findViewById(R.id.btncall);
        btntelmonitor = findViewById(R.id.btntelmonitor);
        btntelmonitorclose = findViewById(R.id.btntelmonitorclose);

        btnclean.setOnClickListener(this);
        btnsave.setOnClickListener(this);
        btnread.setOnClickListener(this);
        btncall.setOnClickListener(this);
        btntelmonitor.setOnClickListener(this);
        btntelmonitorclose.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnclean:
                editdetail.setText("");
                editname.setText("");
                break;
            case R.id.btnsave:
                FileHelper fHelper = new FileHelper(mContext);
                String filename = editname.getText().toString();
                String filedetail = editdetail.getText().toString();
                try {
                    fHelper.save(filename, filedetail);
                    Toast.makeText(getApplicationContext(), "数据写入成功", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "数据写入失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnread:
                String detail = "";
                FileHelper fHelper2 = new FileHelper(getApplicationContext());

                try {
                    String fname = editname.getText().toString();
                    detail = fHelper2.read(fname);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Toast.makeText(getApplicationContext(), detail, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btncall:
                Log.i("btncall","start");
                Uri uri = Uri.parse("tel:10086");
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
                Log.i("btncall","end");
                break;
            case R.id.btntelmonitor:
                phoneListenerIntent = new Intent(MainActivity.this, PhoneListenerService.class);
                startService(phoneListenerIntent);
                break;
            case R.id.btntelmonitorclose:
                if(phoneListenerIntent !=null){
                    stopService(phoneListenerIntent);
                }
                break;
        }
    }


    public void buttonServiceOnClick(View view) {

        final Intent intent = new Intent(MainActivity.this,ServiceActivity.class);

        startActivity(intent);
    }

    public void seekbarBtnOnClick(View view) {

        final Intent intent = new Intent(MainActivity.this,seekbar.class);

        startActivity(intent);
    }
}

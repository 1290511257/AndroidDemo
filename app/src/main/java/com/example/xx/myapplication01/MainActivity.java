package com.example.xx.myapplication01;

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

        editdetail = (EditText) findViewById(R.id.editdetail);
        editname = (EditText) findViewById(R.id.editname);
        btnclean = (Button) findViewById(R.id.btnclean);
        btnsave = (Button) findViewById(R.id.btnsave);
        btnread = (Button) findViewById(R.id.btnread);
        btncall = (Button) findViewById(R.id.btncall);
        btntelmonitor = (Button) findViewById(R.id.btntelmonitor);
        btntelmonitorclose = (Button) findViewById(R.id.btntelmonitorclose);

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

    public void targetServiceStart(){


    }
}

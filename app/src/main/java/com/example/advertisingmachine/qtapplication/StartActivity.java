package com.example.advertisingmachine.qtapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.usage.UsageEvents;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;

import com.example.administrator.qtapplication.R;



public class StartActivity extends Activity {
    String modeIp = "model";
    String deviceIp = "advertise";
    // http://47.96.225.99/bill/public/api/adver/model
    //http://47.96.225.99/bill/public/api/adver/advertise

    public static final int START_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        handler.sendEmptyMessageDelayed(0,START_TIME);
    }
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            getHome();
            super.handleMessage(msg);
        }
    };


    public void getHome(){
        Intent intent = new Intent();
        intent.setClass(StartActivity.this,SelectModes.class);
        startActivity(intent);
        finish();
    }








}
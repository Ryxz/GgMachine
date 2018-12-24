package com.example.advertisingmachine.qtapplication;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.administrator.qtapplication.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import bean.GgInfoBean;
import utils.L;
import utils.MyApplicationContext;
import utils.ToastUtil;

public class SelectModes extends AppCompatActivity implements View.OnClickListener{
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_modes);
        if (MyApplicationContext.isNetworkAvailable(this)) {
            MyApplicationContext.getInstance()
                    .isNetConnecting(GgInfoBean.DEVICE_ID_1);
//            getDeviceId();
        } else {
            ToastUtil.showMessage(R.string.not_connect_net);
        }
        setButtonLisener();
        registerBoradcastReceiver();

    }

    /**
     * 设置监听
     */
    private void setButtonLisener() {
        button1 = (Button) findViewById(R.id.next_1);
        button2 = (Button) findViewById(R.id.next_2);
        button3 = (Button) findViewById(R.id.next_3);
        button4 = (Button) findViewById(R.id.next_4);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
    }
    //注册系统广播
    private void registerBoradcastReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        registerReceiver(timeReceiver,intentFilter);
    }


    //每小时发起一次网络请求
    private BroadcastReceiver timeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            @SuppressLint("SimpleDateFormat") SimpleDateFormat sf2 = new SimpleDateFormat("HHmmss");
            String strtime2 = sf2.format(new Date());
            long wholehour = Integer.valueOf(strtime2);

            L.e("date:",Long.toString(wholehour));
            //判断整点，发起请求
            while (wholehour % 10000 == 0) {

                MyApplicationContext.getInstance()
                        .isNetConnecting(GgInfoBean.DEVICE_ID_1);
//                getDeviceId();
//                L.e("整点报分：",Integer.toString(seconds));
                L.e("date:",Long.toString(wholehour));

            }

        }
    };

    /**\
     * 取消注册
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(timeReceiver);
    }

    /**
     * 根据button事件选择所需模板
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.next_1:
                intent.setClass(SelectModes.this,FirstModesActivity.class);
                startActivity(intent);
                break;
            case R.id.next_2:
                intent.setClass(SelectModes.this,SecondModesActivity.class);
                startActivity(intent);
                break;
            case R.id.next_3:
                intent.setClass(SelectModes.this,ThreeModesActivity.class);
                startActivity(intent);
                break;
            case R.id.next_4:
                intent.setClass(SelectModes.this,FourModesActivity.class);
                startActivity(intent);
                break;
        }
    }
}


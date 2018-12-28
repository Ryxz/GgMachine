package com.example.advertisingmachine.qtapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.administrator.qtapplication.R;

import bean.GgInfoBean;
import http.HttpRequest;
import http.ServerResultListener;
import service.LongRunningService;
import utils.JsonUtil;
import utils.MyApplicationContext;
import utils.ToastUtil;


public class BegainRequestActivity extends AppCompatActivity {
    private BindDialog dialog;
    public static String deviceId;
    private EditText editText;
    private GgInfoBean ggInfoBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        doData();
        editText = (EditText) dialog.findViewById(R.id.et_mac);
        deviceId = editText.getText().toString();
//        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
//            if (ContextCompat.checkSelfPermission(BegainRequestActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
//                //没有权限则申请权限
//                ActivityCompat.requestPermissions(BegainRequestActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
//            }
//        }

    }

    /**
     * 弹出输入框
     */
    private void doData() {
        dialog = new BindDialog(BegainRequestActivity.this, new BindDialog.LeaveLisenner() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_bind:
                        if (MyApplicationContext.isNetworkAvailable(BegainRequestActivity.this)){
                            requestInfo();
                            Intent intent = new Intent(BegainRequestActivity.this, LongRunningService.class);
                            startService(intent);
                        } else {
                            ToastUtil.showMessage(R.string.not_connect_net);
                        }
                        break;
                    case R.id.btn_cancel:
                        MyApplicationContext.getInstance().clearBeanInfo();
                        BegainRequestActivity.this.finish();
                        dialog.dismiss();
                        break;
                        }

                }

        });
        dialog.show();
    }

    /**
     * 请求模板ID和广告信息
     */
    public void requestInfo() {

        deviceId = editText.getText().toString();
        HttpRequest.getModeId(BegainRequestActivity.this, deviceId, new ServerResultListener() {
            @Override
            public void onSuccess(String json, String msg) {

                final String devId = json;

                HttpRequest.getGgInfo(BegainRequestActivity.this, deviceId, devId, new ServerResultListener() {
                    @Override
                    public void onSuccess(String json, String msg) {
                        if (json.equals("")) {
                            ToastUtil.showMessage("设备码错误，请重新输入");
                            dialog.clearEditText();
                        } else {
                            if (ggInfoBean == null) {
                                ggInfoBean = new GgInfoBean();
                                ggInfoBean = JsonUtil.parseJsonToBean(json, GgInfoBean.class);
                                MyApplicationContext.getInstance().saveGgInfoBean(ggInfoBean);
//                                nextActivity(devId);
                            }

                            nextActivity(devId);
                        }

                    }

                    @Override
                    public void onFailure(String msg) {
                        ToastUtil.showMessage("获取模板信息失败");
                    }
                });


            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.showMessage("获取广告信息失败");
            }
        });
    }

    /**
     * 根据服务器返回的json跳转不同模板
     * @param json
     */
    public void nextActivity(String json) {
        Intent intent = new Intent();
        switch (json) {
            case "1":
                intent.setClass(BegainRequestActivity.this,FirstModesActivity.class);
                startActivity(intent);
                break;
            case "2":
                intent.setClass(BegainRequestActivity.this,SecondModesActivity.class);
                startActivity(intent);
                break;
            case "3":
                intent.setClass(BegainRequestActivity.this,ThreeModesActivity.class);
                startActivity(intent);
                break;
            case "4":
                intent.setClass(BegainRequestActivity.this,FourModesActivity.class);
                startActivity(intent);
                break;
        }
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (dialog != null && !this.isFinishing()) {
//            dialog.dismiss();
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}

package com.example.advertisingmachine.qtapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.administrator.qtapplication.R;

import bean.GgInfoBean;
import http.HttpRequest;
import http.ServerResultListener;
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
//        setContentView();
        doData();
        editText = (EditText) dialog.findViewById(R.id.et_mac);
    }


    private void doData() {
        dialog = new BindDialog(BegainRequestActivity.this, new BindDialog.LeaveLisenner() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_bind:
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
                                                nextActivity(devId);
                                            }
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

}

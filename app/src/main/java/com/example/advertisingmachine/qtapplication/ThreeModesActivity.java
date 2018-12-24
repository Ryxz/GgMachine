package com.example.advertisingmachine.qtapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.administrator.qtapplication.R;
import com.ryane.banner.AdPlayBanner;

import bean.GgInfoBean;
import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;
import utils.BannerUtil;
import utils.MyApplicationContext;
import utils.PlayVideoUtil;
import utils.ToastUtil;

public class ThreeModesActivity extends AppCompatActivity {
    private VideoView videoView;
    private GgInfoBean ggInfoBean;
    private ProgressBar bar;
    private LinearLayout linearLayoutTop;
    private LinearLayout linearLayoutMid;
    private LinearLayout linearLayoutBottom;
    private RelativeLayout layout_v;
    private LinearLayout layout_b;
    private LinearLayout layout_bo;
    private AdPlayBanner banner1;
    private AdPlayBanner banner2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.three_modes_laoyut);
        final LayoutInflater inflater = LayoutInflater.from(this);
        if (ggInfoBean == null) {
            ggInfoBean = MyApplicationContext.getInstance().getGgInforBean();
        }
        if (!LibsChecker.checkVitamioLibs(this)) {
            return;
        }
        linearLayoutTop = (LinearLayout) findViewById(R.id.third_layout_top);
        linearLayoutMid = (LinearLayout) findViewById(R.id.third_layout_mid);
        linearLayoutBottom = (LinearLayout) findViewById(R.id.third_layout_bottom);
        layout_v = (RelativeLayout) inflater.inflate(R.layout.video_fragment, null);
        layout_b = (LinearLayout) inflater.inflate(R.layout.banner_fragment_1, null);
        layout_bo = (LinearLayout) inflater.inflate(R.layout.banner_fragment_2, null);

        selectModelByType();
        initVideoAndBanner();

    }

    public void initVideoAndBanner() {
        banner1 = (AdPlayBanner) findViewById(R.id.banner_view_bf);
        banner2 = (AdPlayBanner) findViewById(R.id.banner_view_bf2);

        videoView = (VideoView) findViewById(R.id.vitamio_videoview_v);
        bar = (ProgressBar) findViewById(R.id.probar_first);

        PlayVideoUtil.play(videoView,bar,ggInfoBean,this);
        BannerUtil.BannerViewPlay(banner1,ggInfoBean);
        BannerUtil.BannerViewPlay(banner2,ggInfoBean);
    }
    public void selectModelByType() {
        int type = ggInfoBean.getA_type();
        switch (type) {
            case 0:
                linearLayoutTop.addView(layout_v);
                linearLayoutMid.addView(layout_b);
                linearLayoutBottom.addView(layout_bo);
                break;
            case 1:
                linearLayoutTop.addView(layout_b);
                linearLayoutMid.addView(layout_v);
                linearLayoutBottom.addView(layout_bo);
                break;
            case 2:
                linearLayoutTop.addView(layout_bo);
                linearLayoutMid.addView(layout_b);
                linearLayoutBottom.addView(layout_v);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        if (banner1 != null && banner2 != null) {
            banner1.stop();
            banner2.stop();
        }
        super.onDestroy();
    }
}
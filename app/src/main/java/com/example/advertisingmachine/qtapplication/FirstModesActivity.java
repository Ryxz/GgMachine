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
import io.vov.vitamio.widget.VideoView;
import utils.BannerUtil;
import utils.MyApplicationContext;
import utils.PlayVideoUtil;


public class FirstModesActivity  extends AppCompatActivity {

    private GgInfoBean ggInfoBean;
    private LinearLayout linearLayoutTop;
    private LinearLayout linearLayoutBottom;
    private RelativeLayout layout_v;
    private LinearLayout layout_b;
    private AdPlayBanner adPlayBanner;
    private VideoView videoView;
    private ProgressBar bar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_modes_laoyut);
        final LayoutInflater inflater = LayoutInflater.from(this);

        if (!LibsChecker.checkVitamioLibs(this)) {
            return;
        }
        if (ggInfoBean == null) {
            ggInfoBean = MyApplicationContext.getInstance().getGgInforBean();
        }
        linearLayoutTop = (LinearLayout) findViewById(R.id.first_models_t);
        linearLayoutBottom = (LinearLayout) findViewById(R.id.first_models_b);
        layout_v = (RelativeLayout) inflater.inflate(R.layout.video_fragment, null);
        layout_b = (LinearLayout) inflater.inflate(R.layout.banner_fragment_1, null);

        initView();
    }

    /**
     * 根据type选择播放类型
     */
    public void initView() {
        int type = ggInfoBean.getA_type();
        switch (type) {
            case 0:
                linearLayoutTop.addView(layout_v);
                linearLayoutBottom.addView(layout_b);
                videoView = (VideoView) findViewById(R.id.vitamio_videoview_v);
                bar = (ProgressBar) findViewById(R.id.probar_first);
                adPlayBanner = (AdPlayBanner) findViewById(R.id.banner_view_bf);
                PlayVideoUtil.play(videoView, bar, ggInfoBean, this);
                BannerUtil.BannerViewPlay(adPlayBanner,ggInfoBean);
                break;
            case 1:
                linearLayoutTop.addView(layout_b);
                linearLayoutBottom.addView(layout_v);
                videoView = (VideoView) findViewById(R.id.vitamio_videoview_v);
                bar = (ProgressBar) findViewById(R.id.probar_first);
                adPlayBanner = (AdPlayBanner) findViewById(R.id.banner_view_bf);
                PlayVideoUtil.play(videoView, bar, ggInfoBean, this);
                BannerUtil.BannerViewPlay(adPlayBanner,ggInfoBean);
                break;
        }
    }


    //销毁时停止banner播放
    @Override
    public void onDestroy() {
        if (adPlayBanner != null) {
            adPlayBanner.stop();
        }
        super.onDestroy();
    }
}




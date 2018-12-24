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

public class SecondModesActivity extends AppCompatActivity {

    private VideoView videoView;
    private GgInfoBean ggInfoBean;
    private ProgressBar bar;
    private LinearLayout linearLayout;
    private RelativeLayout layout_v;
    private LinearLayout layout_b;
    private AdPlayBanner adPlayBanner;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_modes_laoyut);
        final LayoutInflater inflater = LayoutInflater.from(this);
        if (ggInfoBean == null) {
            ggInfoBean = MyApplicationContext.getInstance().getGgInforBean();
        }
        if (!LibsChecker.checkVitamioLibs(this)) {
            return;
        }
        linearLayout = (LinearLayout) findViewById(R.id.second_model);
        layout_v = (RelativeLayout) inflater.inflate(R.layout.video_fragment, null);
        layout_b = (LinearLayout) inflater.inflate(R.layout.banner_fragment_1, null);
        selectModelByType();

    }

    /**
     * 根据type选择上传类型
     */
    public void selectModelByType() {
            int type = ggInfoBean.getA_type();
            switch (type) {
                case 0:
                    linearLayout.addView(layout_v);
                    videoView = (VideoView) findViewById(R.id.vitamio_videoview_v);
                    bar = (ProgressBar) findViewById(R.id.probar_first);
                    PlayVideoUtil.play(videoView,bar,ggInfoBean,this);
                    break;
                case 1:
                    linearLayout.addView(layout_b);
                    adPlayBanner = (AdPlayBanner) findViewById(R.id.banner_view_bf);
                    BannerUtil.BannerViewPlay(adPlayBanner,ggInfoBean);
                    break;
            }
        }


}

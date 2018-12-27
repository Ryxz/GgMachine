package com.example.advertisingmachine.qtapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.administrator.qtapplication.R;
import com.ryane.banner.AdPlayBanner;

import java.util.ArrayList;
import java.util.List;

import adapter.ViewPageAdapter;
import bean.GgInfoBean;
import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;
import utils.BannerUtil;
import utils.MyApplicationContext;
import utils.PlayVideoUtil;
import utils.ToastUtil;


public class FourModesActivity extends AppCompatActivity {
    private AdPlayBanner adPlayBanner;
    private VideoView videoView;
    //BASE图片地址集合
    private List<String> ggList;
    //完整图片地址集合
    private List<String> picList;
    private GgInfoBean ggInfoBean;
    //图片url
    private String picData;
    private ViewPager viewPager;
    //小圆点集合
    private List<ImageView> imageViews;
    private LinearLayout linearLayout;
    private static final String GG_BASEURL = "http://47.96.225.99/bill/public";
    private LinearLayout linearLayouttop;
    private RelativeLayout layout_v;
    private LinearLayout layout_b;
    private ProgressBar bar;
    @SuppressLint("HandlerLeak")
    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //获取当前正在显示的页面
            int index=viewPager.getCurrentItem();
            viewPager.setCurrentItem(index+1);
            //改变小圆点
            setSelectedPoint((index+1)%ggList.size());
            //延迟发送消息
            sendEmptyMessageDelayed(1,2000);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.four_modes_laoyut);
        final LayoutInflater inflater = LayoutInflater.from(this);
        if (ggInfoBean == null) {
            ggInfoBean = MyApplicationContext.getInstance().getGgInforBean();
        }
        if (!LibsChecker.checkVitamioLibs(this)) {
            return;
        }
        viewPager = (ViewPager) findViewById(R.id.recommend_viewPager);
        linearLayout = (LinearLayout) findViewById(R.id.recommend_banner_indicator);
        linearLayouttop = (LinearLayout) findViewById(R.id.four_layout_id);
        layout_v = (RelativeLayout) inflater.inflate(R.layout.video_fragment,null);
        layout_b = (LinearLayout) inflater.inflate(R.layout.banner_fragment_1,null);

        ggList = ggInfoBean.getA_image2();
        picList = new ArrayList<>();
        if (ggList.size() > 0) {
            for (int i=0;i<ggList.size();i++) {
                picData = GG_BASEURL +ggList.get(i);
                picList.add(picData);
            }
        }

        selectModelByType();
        playButtomImage();
    }

    /**
     * 根据服务器返回的type选择播放类型
     */
    public void selectModelByType() {
        int type = ggInfoBean.getA_type();
        switch (type) {
            case 0:
                linearLayouttop.addView(layout_v);
                videoView = (VideoView) findViewById(R.id.vitamio_videoview_v);
                bar = (ProgressBar) findViewById(R.id.probar_first);
                PlayVideoUtil.play(videoView,bar,ggInfoBean,this);
                break;
            case 1:
                linearLayouttop.addView(layout_b);
                adPlayBanner = (AdPlayBanner) findViewById(R.id.banner_view_bf);
                BannerUtil.BannerViewPlay(adPlayBanner,ggInfoBean);
                break;
        }
    }

    /**
     * 初始化图片播放，设置延迟播放
     */
    private void playButtomImage() {
        viewPager.setAdapter(new ViewPageAdapter(picList,this));
        initDoc();
        viewPager.setCurrentItem(picList.size()*10);
        myHandler.sendEmptyMessageDelayed(1,2000);
    }
    //初始化小圆点
    private void initDoc() {
        imageViews = new ArrayList<>();
        for(int i=0;i<picList.size();i++){
            ImageView imgPoint=new ImageView(this);
            //设置图片的缩放模式
            imgPoint.setScaleType(ImageView.ScaleType.FIT_XY);
            //设置要显示的图片
            if(i==0){
                //选中的点
                imgPoint.setImageResource(R.drawable.point_selected);
            }else{
                imgPoint.setImageResource(R.drawable.point_un_selected);
            }
            //设置宽度与高度
            LinearLayout.LayoutParams  params=new LinearLayout.LayoutParams(18,18);
            params.setMargins(5,0,5,0);
            //添加到底部容器中
            linearLayout.addView(imgPoint,params);
            //添加到集合中
            imageViews.add(imgPoint);
        }
    }

    /**
     * 改变小圆点
     * @param index 页面下标
     */
    public  void setSelectedPoint(int index){
        for(int i=0;i<imageViews.size();i++){
            if(i==index){
                imageViews.get(i).setImageResource(R.drawable.point_selected);
            }else{
                imageViews.get(i).setImageResource(R.drawable.point_un_selected);
            }
        }
    }

    /**
     * activity销毁时，停止播放轮播图
     */
    @Override
    protected void onDestroy() {
        if (adPlayBanner != null) {
            adPlayBanner.stop();
        }
        super.onDestroy();
    }

}

package utils;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import bean.GgInfoBean;


/**
 * @Author Ryx on 2018/12/12
 */
public class BannerUtil {
    private static List<String> mDatas;
    private static final String GG_BASEURL = "http://47.96.225.99/bill/public";

    public BannerUtil() {
    }
    public static void BannerViewPlay(final Banner banner, GgInfoBean ggInfoBean) {
        mDatas = new ArrayList<>();
        final List<String> ggList = ggInfoBean.getA_image2();
        if (ggList.size() > 0) {
            for (int i=0;i<ggList.size();i++) {
                String picData = GG_BASEURL + ggList.get(i);
                mDatas.add(picData);
            }
        }

        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setImageLoader(new GlideImageLoader())
                .setImages(mDatas)
                .setBannerAnimation(Transformer.Default)
                .isAutoPlay(true)
                .setDelayTime(2000)
                .setIndicatorGravity(BannerConfig.CENTER)
                .start();


    }

}

package utils;

import com.ryane.banner.AdPageInfo;
import com.ryane.banner.AdPlayBanner;

import java.util.ArrayList;
import java.util.List;

import bean.GgInfoBean;

import static com.ryane.banner.AdPlayBanner.ImageLoaderType.FRESCO;
import static com.ryane.banner.AdPlayBanner.IndicatorType.POINT_INDICATOR;
import static com.ryane.banner.AdPlayBanner.ScaleType.CENTER_CROP;

/**
 * @Author Ryx on 2018/12/12
 */
public class BannerUtil {
    private static List<AdPageInfo> mDatas;
    private static final String GG_BASEURL = "http://47.96.225.99/bill/public";

    public BannerUtil() {
    }
    public static void BannerViewPlay(final AdPlayBanner banner, GgInfoBean ggInfoBean) {
        mDatas = new ArrayList<>();
        final List<String> ggList = ggInfoBean.getA_image2();
//        String picData;
        if (ggList.size() > 0) {
            for (int i=0;i<ggList.size();i++) {
                String picData = GG_BASEURL + ggList.get(i);
                AdPageInfo info = new AdPageInfo(null, picData,null,i);
                mDatas.add(info);
            }
        }
        //轮播banner设置
        banner
                .setInfoList(mDatas)
                .setImageLoadType(FRESCO)
                .setImageViewScaleType(CENTER_CROP)
                .setIndicatorType(POINT_INDICATOR)
                .setAutoPlay(true)
                .setCanScroll(true)
                .setInterval(3000)
                .setUp();

    }

}

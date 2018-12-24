package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ViewPageAdapter extends PagerAdapter {
    //图片url
    List<String> mList;
    Context mContext;

    public ViewPageAdapter(List<String> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    /**
     * 返回页面的数量
     * @return
     */
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public float getPageWidth(int position) {
//        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics dm = new DisplayMetrics();
//        wm.getDefaultDisplay().getMetrics(dm);
//        int width = dm.widthPixels;
//        int height = dm.heightPixels;
//        float density = dm.density;
//        int densityDpi = dm.densityDpi;
//        int screenWidth = (int) (width/density);
//        int widthSure = screenWidth/3;

        return (float) 0.34;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(mContext).load(mList.get(position%mList.size())).into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}

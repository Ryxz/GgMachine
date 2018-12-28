package utils;

import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;


public class GlideUtils {

    public static void displayImage(ImageView imageView, String url, int defImg) {

        GlideApp.with(MyApplicationContext.getInstance())
                .load(resolveUrl(url))
                .placeholder(defImg)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(imageView);
    }
    public static void loadImages(ImageView imageView, String url, int defImg) {

        GlideApp.with(MyApplicationContext.getInstance())
                .load(resolveUrl(url))
                .placeholder(defImg)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }
    public static void displayImageResize(ImageView imageView, String url) {
        GlideApp.with(MyApplicationContext.getInstance()).load(resolveUrl(url)).override(imageView.getMeasuredWidth()).into(imageView);
    }

    public static void displayImageResize(ImageView imageView, String url, int size) {
        GlideApp.with(MyApplicationContext.getInstance()).load(resolveUrl(url)).override(size).into(imageView);
    }
    public static void loadImage(ImageView imageView, String url, int size) {
        GlideApp.with(MyApplicationContext.getInstance()).load(url).override(size).into(imageView);
    }
    public static String resolveUrl(String url) {
        if (url == null)
            return "";
        if (url.startsWith("http:")) {
            return url;
        } else {
            return "http://47.96.225.99/bill/public" + (url.startsWith("/") ? url : "/" + url);
        }
    }
}

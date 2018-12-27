package utils;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.util.HashMap;
import java.util.Map;

import bean.GgInfoBean;
import http.HttpRequest;
import http.ServerRequestUtil;
import http.ServerResultListener;
import http.ServerUrl;
import io.vov.vitamio.Vitamio;

public class MyApplicationContext extends Application {

    public static MyApplicationContext myContext;
    private  GgInfoBean ggInfoBean;
    private static final String TAG = "MyApplicationContext";


    @Override
    public void onCreate() {
        super.onCreate();
        myContext = this;
        Vitamio.isInitialized(this);
        Fresco.initialize(this);

    }

    public static MyApplicationContext getInstance() {
        if (myContext == null)
            return new MyApplicationContext();
        return myContext;
    }

//    public String getMacAddress() {
//
//    }

    /**
     * 保存缓存用户信息
     *
     * @param gg
     */
    public void saveGgInfoBean(final GgInfoBean gg) {
        if (gg != null) {
            clearBeanInfo();
            saveGgInfoBeanInfoCache(gg);
        }
    }
    /**
     * 清除缓存用户信息数据
     */
    public  void clearBeanInfo() {
        new File(MyApplicationContext.getInstance().getCacheDir().getPath() + "/"
                + "gg_gginfo.bean").delete();
    }

    /**
     * 读取缓存数据
     *
     * @return
     */
    public static GgInfoBean readGgInfoCache() {
        File file = new File(MyApplicationContext.getInstance().getCacheDir().getPath()
                + "/" + "gg_gginfo.bean");
        if (!file.exists())
            return null;

        if (file.isDirectory())
            return null;

        if (!file.canRead())
            return null;

        try {
            @SuppressWarnings("resource")
            GgInfoBean ggInfoBean = (GgInfoBean) new ObjectInputStream(
                    new BufferedInputStream(new FileInputStream(file)))
                    .readObject();
            return ggInfoBean;
        } catch (OptionalDataException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存用户信息至缓存
     *
     * @param gg
     */
    public static void saveGgInfoBeanInfoCache(GgInfoBean gg) {
        try {
            new ObjectOutputStream(new FileOutputStream(new File(MyApplicationContext
                    .getInstance().getCacheDir().getPath()
                    + "/" + "gg_gginfo.bean"))).writeObject(gg);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取缓存用户信息
     *
     * @return
     */
    public GgInfoBean getGgInforBean() {
        return readGgInfoCache() == null ? new GgInfoBean() : readGgInfoCache();

    }

    /**
     * 判断网络连接状态，连接，则请求服务器
     * @param deviceId
     */
    public  void isNetConnecting(String deviceId){
        if (isNetworkAvailable(MyApplicationContext.getInstance())){
//            getDeviceId(key,value,dAdvertId);
            getData(deviceId);
        }
    }
    /**
     * 检测当的网络（WLAN、3G/2G）状态
     *
     * @param context Context
     * @return true 表示网络可用
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 请求服务器获取数据
     * @param deviceId
     */
    public void getData(final String deviceId) {
        HttpRequest.getModeId(this, deviceId, new ServerResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                L.e("Tag",msg);
                HttpRequest.getGgInfo(getInstance(),deviceId,json, new
                        ServerResultListener() {
                            @Override
                            public void onSuccess(String json, String msg) {
                                L.e("TAG:",msg);
                                if (ggInfoBean == null) {
                                    ggInfoBean = new GgInfoBean();
                                    //将服务器返回的Json转换为GgInfoBean实体类
                                    ggInfoBean = JsonUtil.parseJsonToBean(json, GgInfoBean.class);
                                    MyApplicationContext.getInstance().saveGgInfoBean(ggInfoBean);
                                }

                            }

                            @Override
                            public void onFailure(String msg) {
                                L.e("TAG",msg);
                                ToastUtil.showMessage("请求错误");
                            }
                        });
            }

            @Override
            public void onFailure(String msg) {
                L.e("TAG",msg);
                ToastUtil.showMessage("请求错误");
            }
        });
    }
    /**
     * 失效
     * 获取广告模板的ID
     * @param key
     * @param value
     * @param dAdvertId
     */
    public  void getDeviceId(final String key, final String value, final String dAdvertId) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);

        //请求模板id
        ServerRequestUtil.requestHttp(myContext, ServerUrl.getModeIdUrl, map, "获取中", new ServerResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
//                getGgInfo(key, value, dAdvertId, json);
//                selectMode(json);
                L.e("模板id:", json);
            }

            @Override
            public void onFailure(String msg) {
                L.e("error:", msg);
            }
        });

    }

    /**失效
     * 获取广告信息 json
     * @param key
     * @param value
     * @param dAdvertId
     * @param json
     */
    public  void getGgInfo(String key, String value, String dAdvertId, String json) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        map.put(dAdvertId, json);
        //请求广告信息
        ServerRequestUtil.requestHttp(MyApplicationContext.getInstance(), ServerUrl.getGgInfoUrl, map, "获取中", new ServerResultListener() {

            @Override
            public void onSuccess(String json, String msg) {
                L.e("广告信息:", json);
                if (ggInfoBean == null) {
                    ggInfoBean = new GgInfoBean();
                    //将服务器返回的Json转换为GgInfoBean实体类
                    ggInfoBean = JsonUtil.parseJsonToBean(json, GgInfoBean.class);
                    MyApplicationContext.getInstance().saveGgInfoBean(ggInfoBean);
                }

            }

            @Override
            public void onFailure(String msg) {
                L.e("error:",msg);
            }

        });

    }
}
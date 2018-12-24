package http;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    /**
     * 获取模板id
     * @param context
     * @param deviceId
     * @param serverResultListener
     */
    public static void getModeId(Context context,String deviceId,ServerResultListener serverResultListener) {
        Map<String,Object> map = new HashMap<>();
        map.put("device_id",deviceId);
        ServerRequestUtil.requestHttp(context,ServerUrl.getModeIdUrl,map,"获取中...",serverResultListener);

    }

    /**
     * 获取广告信息
     * @param context
     * @param dAdvertId
     * @param serverResultListener
     */
    public static void getGgInfo(Context context,String deviceId,String dAdvertId,ServerResultListener serverResultListener) {
        Map<String,Object> map = new HashMap<>();
        map.put("device_id",deviceId);
        map.put("d_advert_id",dAdvertId);
        ServerRequestUtil.requestHttp(context,ServerUrl.getGgInfoUrl,map,"获取中...",serverResultListener);
    }
}

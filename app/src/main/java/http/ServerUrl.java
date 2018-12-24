package http;

public class ServerUrl {
    private static final String BASE_URL = "http://47.96.225.99/bill/";
    // http://47.96.225.99/bill/public/api/adver/model
    //http://47.96.225.99/bill/public/api/adver/advertise

    //获取模板id
    public static String getModeIdUrl = BASE_URL + "public/api/adver/model";

    //获取广告信息
    public static String getGgInfoUrl = BASE_URL + "public/api/adver/advertise";
}


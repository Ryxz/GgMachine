package bean;

import java.io.Serializable;
import java.util.List;

public class GgInfoBean implements Serializable {
    public static final String DEVIDE_ID = "device_id";
    public static final String D_ADVERT_ID = "d_advert_id";
    public static final String D_ADVERT_ID_1 = "1";
    public static final String D_ADVERT_ID_2 = "2";
    public static final String D_ADVERT_ID_3 = "3";
    public static final String D_ADVERT_ID_4 = "4";
    public static final String DEVICE_ID_1 = "1234567890";
    public static final String DEVICE_ID_2 = "";
    public static final String DEVICE_ID_3 = "";
    public static final String DEVICE_ID_4 = "";
    /**
     * id : 2
     * a_name : 呵呵哒
     * status : 1
     * a_type : 0
     * a_video : /tmp/uploads/20181127/84c464f2fe3634c8a33114fcbc9875d0.mp4
     * a_image1 :
     * a_image2 : ["/tmp/uploads/20181127/e272bd687b014d555f156cd92d697258.jpg","/tmp/uploads/20181127/1f83a14021bf6df0998d0e356dd24ac4.jpg","/tmp/uploads/20181127/a74ffe080e33f76ab620ac08c128c518.jpg"]
     * c_time : 2018-11-27 19:17:17
     * s_time : 2018-11-27
     * e_time : 2018-11-28
     * a_remake :
     */

    private int id;
    private String a_name;
    private int status;
    private int a_type;
    private String a_video;
    private String a_image1;
    private String c_time;
    private String s_time;
    private String e_time;
    private String a_remake;
    private List<String> a_image2;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getA_name() {
        return a_name;
    }

    public void setA_name(String a_name) {
        this.a_name = a_name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getA_type() {
        return a_type;
    }

    public void setA_type(int a_type) {
        this.a_type = a_type;
    }

    public String getA_video() {
        return a_video;
    }

    public void setA_video(String a_video) {
        this.a_video = a_video;
    }

    public String getA_image1() {
        return a_image1;
    }

    public void setA_image1(String a_image1) {
        this.a_image1 = a_image1;
    }

    public String getC_time() {
        return c_time;
    }

    public void setC_time(String c_time) {
        this.c_time = c_time;
    }

    public String getS_time() {
        return s_time;
    }

    public void setS_time(String s_time) {
        this.s_time = s_time;
    }

    public String getE_time() {
        return e_time;
    }

    public void setE_time(String e_time) {
        this.e_time = e_time;
    }

    public String getA_remake() {
        return a_remake;
    }

    public void setA_remake(String a_remake) {
        this.a_remake = a_remake;
    }

    public List<String> getA_image2() {
        return a_image2;
    }

    public void setA_image2(List<String> a_image2) {
        this.a_image2 = a_image2;
    }
}

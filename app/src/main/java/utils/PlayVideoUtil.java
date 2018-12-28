package utils;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.ProgressBar;

import com.example.administrator.qtapplication.R;

import java.util.ArrayList;
import java.util.List;

import bean.GgInfoBean;
import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * @Author Ryx on 2018/12/12
 */
public class PlayVideoUtil {
    private static String videoUrl;
    private static int count = 0;
    private static final String GG_BASEURL = "http://47.96.225.99/bill/public";


    /**
     * 视频播放
     * @param view
     * @param bar
     * @param ggInfoBean
     * @param mContext
     */
    public static void play(final VideoView view , final ProgressBar bar, GgInfoBean ggInfoBean,Context mContext) {
        final List<String> videoList = new ArrayList<>();
        final String videoData = ggInfoBean.getA_video();
        if (videoData != null) {
            videoList.add(videoData);
        } else {
            ToastUtil.showMessage(R.string.video_notfound_erro);
        }


        if (videoList.size() > 0 && videoList.get(count) != null) {
            videoUrl = GG_BASEURL + videoList.get(count);
            Uri uri = Uri.parse(videoUrl);
            //官方推荐的缓存策略
            Uri ur =Uri.parse("cache:" + Environment.getExternalStorageDirectory() + "/DCIM/machineVideo.MP4:" + uri);
            view.setVideoURI(ur);
//            }
        } else {
            ToastUtil.showMessage(R.string.video_notfound_erro);
        }

        view.setMediaController(new MediaController(mContext));
        view.requestFocus();
        view.start();
        /**
         * 视频加载完成时发生
         */
        view.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                mp.setPlaybackSpeed(1.0f);
                mp.start();
                mp.setLooping(true);
            }
        });

        view.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                count++;
                if (count >= videoList.size()) {
                    count = 0;
                }
                mp.start();

            }
        });
        /**
         * 视频状态更新监听的回调
         */
        view.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                switch (what) {
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                        //缓存开始，判断是否正在播放；true:暂停
                        if (view.isPlaying()) {
                            view.pause();
                            bar.setVisibility(View.VISIBLE);
//                            loadrate.setVisibility(View.VISIBLE);
//                            loadrate.setText("");
                        }
                        break;
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                        //缓存结束，开始播放，响应控件消失
                        view.start();
                        bar.setVisibility(View.GONE);
//                        loadrate.setVisibility(View.GONE);
                        break;
                    case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                        break;
                }
                return false;
            }
        });
//        /**
//         * 数据更新监听的回调
//         */
//        view.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
//            @Override
//            public void onBufferingUpdate(MediaPlayer mp, int percent) {
//                loadrate.setText(percent + "%");
//            }
//        });

    }
    }


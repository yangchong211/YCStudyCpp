package cn.ycbjie.ycaudioplayer.constant;

import com.blankj.utilcode.util.Utils;


/**
 * <pre>
 *     @author yangchong
 *     blog  : www.pedaily.cn
 *     time  : 2017/03/22
 *     desc  : 存放常量
 *     revise:
 * </pre>
 */
public class Constant {

    /**----------------------------------同轨迹音视频文件----------------------------------------**/
    public static final String AUDIO_URL = "http://p2modh813.bkt.clouddn.com/sqdx.mp3";
    public static final String DEVIE_URL = "http://p2modh813.bkt.clouddn.com/sqdx.mp4";
    public static String[] AUDIO_LIST = {
            "http://p2modh813.bkt.clouddn.com/sqdx.mp3",
            "http://p2modh813.bkt.clouddn.com/Kalimba.mp3",
            "http://p2modh813.bkt.clouddn.com/Sleep%20Away.mp3",
            "http://p2modh813.bkt.clouddn.com/hah.mp3",
    };

    /**
     * 由于之前，我把视频文章发到七牛云上，有众多限制。
     * 所有有时导致视频无法播放，流量过高，一个星期竟然用了20多个G。
     */
    public static String[] VideoPlayerList = {
            "http://jzvd.nathen.cn/c494b340ff704015bb6682ffde3cd302/64929c369124497593205a4190d7d128-5287d2089db37e62345123a1be272f8b.mp4",
            "http://jzvd.nathen.cn/63f3f73712544394be981d9e4f56b612/69c5767bb9e54156b5b60a1b6edeb3b5-5287d2089db37e62345123a1be272f8b.mp4",
            "http://jzvd.nathen.cn/b201be3093814908bf987320361c5a73/2f6d913ea25941ffa78cc53a59025383-5287d2089db37e62345123a1be272f8b.mp4",
            "http://jzvd.nathen.cn/d2438fd1c37c4618a704513ad38d68c5/68626a9d53ca421c896ac8010f172b68-5287d2089db37e62345123a1be272f8b.mp4",
            "http://jzvd.nathen.cn/25a8d119cfa94b49a7a4117257d8ebd7/f733e65a22394abeab963908f3c336db-5287d2089db37e62345123a1be272f8b.mp4",
            "http://jzvd.nathen.cn/7512edd1ad834d40bb5b978402274b1a/9691c7f2d7b74b5e811965350a0e5772-5287d2089db37e62345123a1be272f8b.mp4",
            "http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4"
    };
    public static String[] VideoPlayerTitle = {
            "大家好，我是潇湘剑雨",
            "如果项目可以，可以给个star",
            "有bug，可以直接提出来，欢迎一起探讨",
            "把本地项目代码复制到拷贝的仓库",
            "依次输入命令上传代码",
            "把本地项目代码复制到拷贝的仓库",
            "依次输入命令上传代码",
    };



    public static final String SP_NAME = "yc";
    public static final long CLICK_TIME = 500;

    public interface ViewType{
        int TYPE_VIEW = 0;
        int TYPE_BANNER = 1;
        int TYPE_GV = 2;
        int TYPE_TITLE = 3;
        int TYPE_MORE = 4;
        int TYPE_AD = 5;
        int TYPE_LIST2 = 6;
        int TYPE_AD2 = 7 ;
        int TYPE_GV2 = 8;
        int TYPE_LIST3 = 9;
        int TYPE_GV_BOTTOM = 10;
        int TYPE_LIST4 = 11;
    }

    /**
     * 广告页
     */
    public static final String DOWNLOAD_SPLASH_AD = "download_splash_ad";
    public static final String EXTRA_DOWNLOAD = "extra_download";
    /**
     * 动态闪屏序列化地址
     */
    public static final String SPLASH_PATH = Utils.getApp().getFilesDir().getAbsolutePath() + "/alpha/splash";
    public static final String SPLASH_FILE_NAME = "splash.srr";


    /**--------------------------------------action----------------------------------------------**/
    public static final String EXTRA_NOTIFICATION = "extra_notification";
    public static final String LOCK_SCREEN = "lock_screen";
    public static final String LOCK_SCREEN_ACTION = "cn.ycbjie.lock";

    /**--------------------------------------键--------------------------------------------------**/
    public static final String KEY_IS_LOGIN = "is_login";
    public static final String KEY_FIRST_SPLASH = "first_splash";
    public static final String KEY_NIGHT_STATE = "night_state";
    public static final String FILTER_SIZE = "filter_size";
    public static final String FILTER_TIME = "filter_time";
    public static final String MUSIC_ID = "music_id";
    public static final String PLAY_MODE = "play_mode";
    public static final String IS_SCREEN_LOCK = "is_screen_lock";
    public static final String APP_OPEN_COUNT = "app_open_count";
    public static final String PLAY_POSITION = "play_position";

    /**
     * 网络缓存最大值
     */
    public static final int CACHE_MAXSIZE = 1024 * 1024 * 30;

    /**
     * 网络缓存保存时间
     */
    public static final int TIME_CACHE = 60 * 60; // 一小时

}

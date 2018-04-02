package cn.ycbjie.ycaudioplayer.api.constant;

import com.blankj.utilcode.util.Utils;


public class Constant {

    //sp的名称
    public static String SP_NAME = "yc";
    public static final long CLICK_TIME = 500;

    public interface viewType{
        int typeView = 0;           //自定义
        int typeBanner = 1;         //轮播图
        int typeGv = 2;             //九宫格
        int typeTitle = 3;          //标题
        int typeMore = 4;           //更多
        int typeAd = 5;             //广告
        int typeList2 = 6;          //list2
        int typeAd2 = 7 ;           //广告2
        int typeGv3 = 8;            //list3
        int typeList4 = 9;          //list4
        int typeGvBottom = 10;      //九宫格
        int typeList5 = 11;          //list4
    }

    /**
     * 广告页
     */
    public static final String DOWNLOAD_SPLASH_AD = "download_splash_ad";
    public static final String EXTRA_DOWNLOAD = "extra_download";
    /**动态闪屏序列化地址*/
    public static final String SPLASH_PATH = Utils.getApp().getFilesDir().getAbsolutePath() + "/alpha/splash";
    public static final String SPLASH_FILE_NAME = "splash.srr";


    /**--------------------------------------action----------------------------------------------**/
    public static final String EXTRA_NOTIFICATION = "extra_notification";
    public static final String LOCK_SCREEN = "lock_screen";
    public static final String LOCK_SCREEN_ACTION = "cn.ycbjie.lock";

    /**--------------------------------------键--------------------------------------------------**/
    public static final String KEY_IS_LOGIN = "is_login";
    public static final String KEY_NIGHT_STATE = "night_state";
    public static final String FILTER_SIZE = "filter_size";
    public static final String FILTER_TIME = "filter_time";
    public static final String MUSIC_ID = "music_id";
    public static final String PLAY_MODE = "play_mode";
    public static final String IS_SCREEN_LOCK = "is_screen_lock";
    public static final String APP_OPEN_COUNT = "app_open_count";

}

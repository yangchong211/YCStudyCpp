package cn.ycbjie.ycaudioplayer.model;

/**
 * Created by yc on 2018/1/24.
 */

public class MusicPlayAction {

    /**--------------播放类型--------------------------------*/

    /** 点击了上一首按钮*/
    public static final String TYPE_PRE = "TYPE_PRE";
    /** 点击了下一首按钮*/
    public static final String TYPE_NEXT = "TYPE_NEXT";
    /** 点击了播放暂停按钮*/
    public static final String TYPE_START_PAUSE = "TYPE_START_PAUSE";


    /**--------------播放状态--------------------------------*/

    /** 默认状态*/
    public static final int STATE_IDLE = 100;
    /** 正在准备中*/
    public static final int STATE_PREPARING = 101;
    /** 正在播放中*/
    public static final int STATE_PLAYING = 102;
    /** 暂停状态*/
    public static final int STATE_PAUSE = 103;


    /**--------------通知栏--------------------------------*/

    /** 点击了通知栏上的上一首按钮*/
    public static final int TYPE_NOTIFICATION_PRE = 1001;
    /** 点击了通知栏上的下一首按钮*/
    public static final int TYPE_NOTIFICATION_NEXT = 1002;
    /** 点击了通知栏上的根容器*/
    public static final int TYPE_NOTIFICATION_ROOT = 1003;
    /** 点击了通知栏上的播放暂停按钮*/
    public static final int TYPE_NOTIFICATION_START = 1004;


    /**--------------播放页面--------------------------------*/

    /** 播放模式：顺序播放并且循环 */
    public static final int PLAY_MODE_ORDER = 1005;
    /** 播放模式：单曲播放 */
    public static final int PLAY_MODE_SINGLE = 1006;
    /** 播放模式：随机播放 */
    public static final int PLAY_MODE_RANDOM = 1007;


    /**--------------底部控制栏--------------------------------*/


}

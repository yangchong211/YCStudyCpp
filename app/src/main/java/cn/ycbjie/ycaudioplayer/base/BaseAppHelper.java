package cn.ycbjie.ycaudioplayer.base;

import android.app.Application;
import android.content.Context;
import android.support.v4.util.LongSparseArray;


import java.util.ArrayList;
import java.util.List;

import cn.ycbjie.ycaudioplayer.model.DownloadMusicInfo;
import cn.ycbjie.ycaudioplayer.ui.local.model.LocalMusic;
import cn.ycbjie.ycaudioplayer.ui.onLine.model.bean.OnLineSongListInfo;
import cn.ycbjie.ycaudioplayer.service.PlayService;


public class BaseAppHelper {

    private Context mContext;
    /**
     * 播放音乐service
     */
    private PlayService mPlayService;
    /**
     * 本地歌曲列表
     */
    private final List<LocalMusic> mMusicList = new ArrayList<>();
    /**
     * 歌单列表
     */
    private final List<OnLineSongListInfo> mSongListInfo = new ArrayList<>();

    private final LongSparseArray<DownloadMusicInfo> mDownloadList = new LongSparseArray<>();

    private BaseAppHelper() {}

    private static class SingletonHolder {
        private final static BaseAppHelper instance = new BaseAppHelper();
    }

    public static BaseAppHelper get() {
        return SingletonHolder.instance;
    }

    void init(Application application) {
        mContext = application.getApplicationContext();
        //这里可以做一些初始化的逻辑
    }

    public Context getContext() {
        return mContext;
    }

    /**
     * 获取PlayService对象
     * @return              返回PlayService对象
     */
    public PlayService getPlayService() {
        return mPlayService;
    }

    /**
     * 设置PlayService服务
     */
    public void setPlayService(PlayService service) {
        mPlayService = service;
    }

    /**
     * 获取扫描到的音乐数据集合
     * @return              返回list集合
     */
    public List<LocalMusic> getMusicList() {
        return mMusicList;
    }

    /**
     * 获取扫描到的音乐信息数据集合
     * @return              返回list集合
     */
    public List<OnLineSongListInfo> getSongListInfos() {
        return mSongListInfo;
    }

    /**
     * 获取扫描到的音乐下载数据集合
     * @return              返回list集合
     */
    public LongSparseArray<DownloadMusicInfo> getDownloadList() {
        return mDownloadList;
    }

}

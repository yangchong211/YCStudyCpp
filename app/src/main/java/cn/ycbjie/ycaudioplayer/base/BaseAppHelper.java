package cn.ycbjie.ycaudioplayer.base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.v4.util.LongSparseArray;


import java.util.ArrayList;
import java.util.List;

import cn.ycbjie.ycaudioplayer.model.bean.DownloadMusicInfo;
import cn.ycbjie.ycaudioplayer.ui.music.local.model.AudioMusic;
import cn.ycbjie.ycaudioplayer.ui.music.onLine.model.bean.OnLineSongListInfo;
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
    private final List<AudioMusic> mMusicList = new ArrayList<>();
    /**
     * 歌单列表
     */
    private final List<OnLineSongListInfo> mSongListInfo = new ArrayList<>();

    private final LongSparseArray<DownloadMusicInfo> mDownloadList = new LongSparseArray<>();

    private BaseAppHelper() {}

    private static class SingletonHolder {
        @SuppressLint("StaticFieldLeak")
        private final static BaseAppHelper INSTANCE = new BaseAppHelper();
    }

    public static BaseAppHelper get() {
        return SingletonHolder.INSTANCE;
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
    public List<AudioMusic> getMusicList() {
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

package cn.ycbjie.ycaudioplayer.api.http;


import cn.ycbjie.ycaudioplayer.api.manager.RetrofitWrapper;
import cn.ycbjie.ycaudioplayer.model.bean.MusicLrc;
import cn.ycbjie.ycaudioplayer.model.bean.SearchMusic;
import cn.ycbjie.ycaudioplayer.ui.music.onLine.model.bean.ArtistInfo;
import cn.ycbjie.ycaudioplayer.ui.music.onLine.model.bean.OnlineMusicList;
import rx.Observable;

/**
 * Created by PC on 2017/8/21.
 * 作者：PC
 */

public class OnLineMusicModel {

    private static final String BASE_URL = "http://tingapi.ting.baidu.com/";
    public static final String METHOD_LINE_MUSIC = "baidu.ting.billboard.billList";
    public static final String METHOD_ARTIST_INFO = "baidu.ting.artist.getInfo";
    public static final String METHOD_SEARCH_MUSIC = "baidu.ting.search.catalogSug";
    public static final String METHOD_LRC = "baidu.ting.song.lry";
    public static final String METHOD_GET_MUSIC_LIST = "baidu.ting.billboard.billList";


    private static OnLineMusicModel model;
    private OnLineMusicApi mApiService;

    private OnLineMusicModel() {
        mApiService = RetrofitWrapper
                .getInstance(BASE_URL)
                .create(OnLineMusicApi.class);
    }


    public static OnLineMusicModel getInstance(){
        if(model == null) {
            model = new OnLineMusicModel();
        }
        return model;
    }

    /**
     * 获取专辑信息
     */
    public Observable<OnlineMusicList> getList(String method , String type, int size, int offset) {
        return mApiService.getList2(method, type, size, offset);
    }

    /**
     * 个人详情
     */
    public Observable<ArtistInfo> getArtistInfo(String method , String tinguid) {
        return mApiService.getArtistInfo(method, tinguid);
    }


    /**
     * 搜索音乐
     */
    public Observable<SearchMusic> startSearchMusic(String method , String query) {
        return mApiService.startSearchMusic(method, query);
    }

    /**
     * 搜索音乐歌词
     */
    public Observable<MusicLrc> getLrc(String method , String songid) {
        return mApiService.getLrc(method, songid);
    }

    /**
     *
     */
    public Observable<OnlineMusicList> getSongListInfo(String method , String type , String size , String offset) {
        return mApiService.getSongListInfo(method, type, size,offset);
    }


}

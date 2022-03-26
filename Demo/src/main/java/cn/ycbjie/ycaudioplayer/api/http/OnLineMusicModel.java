package cn.ycbjie.ycaudioplayer.api.http;



import cn.ycbjie.ycaudioplayer.api.manager.RetrofitWrapper;
import cn.ycbjie.ycaudioplayer.model.bean.DownloadInfo;
import cn.ycbjie.ycaudioplayer.model.bean.MusicLrc;
import cn.ycbjie.ycaudioplayer.model.bean.SearchMusic;
import cn.ycbjie.ycaudioplayer.ui.music.model.ArtistInfo;
import cn.ycbjie.ycaudioplayer.ui.music.model.OnlineMusicList;
import cn.ycbjie.ycaudioplayer.utils.logger.AppLogUtils;
import io.reactivex.Observable;


public class OnLineMusicModel {


    /*
     * 静态代码块
     * 主要是作用于设置中心debug模式下，点击三下标题可以切换环境
     * 1001，测试接口
     * 2001，开发接口
     * 3001，正式接口
     */
    static {
        //int anInt = SPUtils.getInstance(Constant.SP_NAME).getInt(DebugActivity.SELECT_STATUS);
        int anInt = 1001;
        switch (anInt){
            case 1001:
                BASE_URL = "http://tingapi.ting.baidu.com/";
                AppLogUtils.eTag("网络环境","测试数据");
                break;
            case 2001:
                BASE_URL = "http://tingapi.ting.baidu.com/";
                AppLogUtils.eTag("网络环境","开发数据");
                break;
            case 3001:
                BASE_URL = "http://tingapi.ting.baidu.com/";
                AppLogUtils.eTag("网络环境","正式数据");
                break;
            default:
                BASE_URL = "http://tingapi.ting.baidu.com/";
                AppLogUtils.eTag("网络环境","正式数据");
                break;
        }
    }



    private static String BASE_URL = "http://tingapi.ting.baidu.com/";
    public static final String METHOD_LINE_MUSIC = "baidu.ting.billboard.billList";
    public static final String METHOD_ARTIST_INFO = "baidu.ting.artist.getInfo";
    public static final String METHOD_SEARCH_MUSIC = "baidu.ting.search.catalogSug";
    public static final String METHOD_LRC = "baidu.ting.song.lry";
    public static final String METHOD_GET_MUSIC_LIST = "baidu.ting.billboard.billList";
    public static final String METHOD_DOWNLOAD_MUSIC = "baidu.ting.song.play";



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
     * 获取歌词信息
     */
    public Observable<OnlineMusicList> getSongListInfo(String method , String type , String size , String offset) {
        return mApiService.getSongListInfo(method, type, size,offset);
    }

    /**
     * 获取下载链接
     */
    public Observable<DownloadInfo> getMusicDownloadInfo(String method , String songid) {
        return mApiService.getMusicDownloadInfo(method, songid);
    }


}

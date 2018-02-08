package cn.ycbjie.ycaudioplayer.api.http;

import cn.ycbjie.ycaudioplayer.model.bean.MusicLrc;
import cn.ycbjie.ycaudioplayer.model.bean.SearchMusic;
import cn.ycbjie.ycaudioplayer.ui.music.onLine.model.bean.ArtistInfo;
import cn.ycbjie.ycaudioplayer.ui.music.onLine.model.bean.OnlineMusicList;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;


public interface OnLineMusicApi {


    /**
     * 最新音乐
     * http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.billboard.billList&type=2&size=3&offset=0
     */
    @GET("v1/restserver/ting/{method}/{type}/{size}/{offset}")
    Observable<OnlineMusicList> getList1(@Path("method") String method,
                                             @Path("type") String type,
                                             @Path("size") int size,
                                             @Path("offset") int offset);


    /**
     * 最新音乐
     * http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.billboard.billList&type=2&size=3&offset=0
     */
    @GET("v1/restserver/ting")
    Observable<OnlineMusicList> getList2(@Query("method") String method,
                                        @Query("type") String type,
                                        @Query("size") int size,
                                        @Query("offset") int offset);


    /**
     * 个人详情
     */
    @GET("v1/restserver/ting")
    Observable<ArtistInfo> getArtistInfo(@Query("method") String method,
                                    @Query("tinguid") String tinguid);



    /**
     * 搜索音乐
     */
    @GET("v1/restserver/ting")
    Observable<SearchMusic> startSearchMusic(@Query("method") String method,
                                             @Query("query") String query);


    /**
     * 搜索音乐歌词
     */
    @GET("v1/restserver/ting")
    Observable<MusicLrc> getLrc(@Query("method") String method,
                                @Query("songid") String songid);



    /**
     *
     */
    @GET("v1/restserver/ting")
    Observable<OnlineMusicList> getSongListInfo(@Query("method") String method,
                                                @Query("type") String type,
                                                @Query("size") String size,
                                                @Query("offset") String offset);

}

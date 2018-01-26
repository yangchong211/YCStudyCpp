package cn.ycbjie.ycaudioplayer.ui.music.onLine.model.api;

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
    Observable<OnlineMusicList> getList(@Path("method") String method,
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


    /*@GET("data/{category}/{number}/{page}")
    Observable<CategoryResult> getCategoryDate(@Path("category") String category,
                                               @Path("number") int number,
                                               @Path("page") int page);


    @GET("search/query/{key}/category/all/count/{count}/page/{page}")
    Observable<SearchResult> getSearchResult(@Path("key") String key,
                                             @Path("count") int count,
                                             @Path("page") int page);*/

}

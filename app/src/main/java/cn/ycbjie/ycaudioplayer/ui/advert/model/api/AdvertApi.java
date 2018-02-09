package cn.ycbjie.ycaudioplayer.ui.advert.model.api;

import cn.ycbjie.ycaudioplayer.model.bean.MusicLrc;
import cn.ycbjie.ycaudioplayer.model.bean.SearchMusic;
import cn.ycbjie.ycaudioplayer.ui.advert.model.bean.AdvertCommon;
import cn.ycbjie.ycaudioplayer.ui.music.onLine.model.bean.ArtistInfo;
import cn.ycbjie.ycaudioplayer.ui.music.onLine.model.bean.OnlineMusicList;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;


public interface AdvertApi {


    @GET("fundworks/media/getFlashScreen")
    Observable<AdvertCommon> getSplashImage(@Query("type") int type);


}

package cn.ycbjie.ycaudioplayer.ui.advert.model.api;


import cn.ycbjie.ycaudioplayer.api.manager.RetrofitWrapper;
import cn.ycbjie.ycaudioplayer.model.bean.MusicLrc;
import cn.ycbjie.ycaudioplayer.model.bean.SearchMusic;
import cn.ycbjie.ycaudioplayer.ui.advert.model.bean.AdvertCommon;
import cn.ycbjie.ycaudioplayer.ui.music.onLine.model.bean.ArtistInfo;
import cn.ycbjie.ycaudioplayer.ui.music.onLine.model.bean.OnlineMusicList;
import rx.Observable;

/**
 * Created by PC on 2017/8/21.
 * 作者：PC
 */

public class AdvertModel {

    private static final String BASE_URL = "http://beta.goldenalpha.com.cn/";

    private static AdvertModel model;
    private AdvertApi mApiService;

    private AdvertModel() {
        mApiService = RetrofitWrapper
                .getInstance(BASE_URL)
                .create(AdvertApi.class);
    }


    public static AdvertModel getInstance(){
        if(model == null) {
            model = new AdvertModel();
        }
        return model;
    }

    /**
     * 获取首页广告图片
     */
    public Observable<AdvertCommon> getSplashImage(int method) {
        return mApiService.getSplashImage(method);
    }

}

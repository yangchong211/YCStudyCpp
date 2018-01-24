package cn.ycbjie.ycaudioplayer.ui.onLine.model.api;


import cn.ycbjie.ycaudioplayer.api.RetrofitWrapper;
import cn.ycbjie.ycaudioplayer.ui.onLine.model.bean.OnlineMusicList;
import rx.Observable;

/**
 * Created by PC on 2017/8/21.
 * 作者：PC
 */

public class OnLineMusicModel {

    private static final String baseUrl = "http://tingapi.ting.baidu.com/";
    private static OnLineMusicModel model;
    private OnLineMusicApi mApiService;

    private OnLineMusicModel() {
        mApiService = RetrofitWrapper
                .getInstance(baseUrl)
                .create(OnLineMusicApi.class);
    }

    public static OnLineMusicModel getInstance(){
        if(model == null) {
            model = new OnLineMusicModel();
        }
        return model;
    }

    public Observable<OnlineMusicList> getList(String method , String type, int size, int offset) {
        return mApiService.getList2(method, type, size, offset);
    }


}

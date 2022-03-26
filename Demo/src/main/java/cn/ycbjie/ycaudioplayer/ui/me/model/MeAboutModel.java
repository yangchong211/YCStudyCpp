package cn.ycbjie.ycaudioplayer.ui.me.model;


import cn.ycbjie.ycaudioplayer.api.http.OnLineMusicModel;
import cn.ycbjie.ycaudioplayer.ui.music.model.OnlineMusicList;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 数据model类
 */
public class MeAboutModel implements InterModel {

    @Override
    public void getAboutData(final AboutDataListener<OnlineMusicList> listener) {
        //开始请求网络
        OnLineMusicModel model = OnLineMusicModel.getInstance();
        model.getList(OnLineMusicModel.METHOD_LINE_MUSIC , "", 0 ,3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<OnlineMusicList>() {
                    @Override
                    public void accept(OnlineMusicList onlineMusicList) throws Exception {
                        listener.onSuccess(onlineMusicList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        listener.onError();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });
    }

}

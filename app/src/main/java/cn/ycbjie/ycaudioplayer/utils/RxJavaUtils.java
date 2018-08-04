package cn.ycbjie.ycaudioplayer.utils;

import android.graphics.Bitmap;
import android.view.View;

import com.blankj.utilcode.util.ImageUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * <pre>
 *     @author yangchong
 *     blog  :
 *     time  : 2017/6/6
 *     desc  : RxJava工具类
 *     revise:
 * </pre>
 */
public class RxJavaUtils {

    public interface RxJavaCallback {
        void onSuccess();
    }


    /**
     * 点击view
     *
     * @param view              view
     * @param time              时间
     * @param callback          回调
     */
    public static void clickView(final View view, int time, final RxJavaCallback callback) {
        if (view == null) return;
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //订阅没取消
                        if (!emitter.isDisposed()) {
                            emitter.onNext("");
                        }
                    }
                });
            }
        })
                //控制 time 的时间内
                .throttleFirst(time, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String aVoid) throws Exception {
                        if (callback != null) {
                            callback.onSuccess();
                        }
                    }
                });
    }



    public interface DownLoadCallback {
        void onSuccess(List<Bitmap> bitmapList);
    }

    /**
     * 下载图片
     * @param list
     * @param callback
     */
    public static void downloadImg(final List<String> list, final DownLoadCallback callback) {
        if (list == null || list.size() == 0) return;
        Observable.just(list)
                .flatMap(new Function<List<String>, ObservableSource<List<Bitmap>>>() {
                    @Override
                    public ObservableSource<List<Bitmap>> apply(List<String> list) throws Exception {
                        final List<Bitmap> bitmapList = new ArrayList<>();
                        for (String img_url : list) {
                            //Bitmap bitmap = BitmapUtils.returnBitMap(img_url);
                            //将图片转化成bitmap
                            Bitmap bitmap = null;
                            bitmapList.add(bitmap);
                        }
                        return Observable.just(bitmapList);

                    }
                })
                .subscribeOn(Schedulers.io()) // 被观察者 所在线程
                .observeOn(AndroidSchedulers.mainThread())  // 观察者所在线程
                .subscribe(new Consumer<List<Bitmap>>() {
                    @Override
                    public void accept(List<Bitmap> list) throws Exception {
                        callback.onSuccess(list);
                    }
                });
    }

}

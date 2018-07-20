package cn.ycbjie.ycaudioplayer.kotlin.model.helper

import cn.ycbjie.ycaudioplayer.api.manager.RetrofitWrapper
import com.mg.axechen.wanandroid.javabean.HomeListBean
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import network.request.RequestApi
import retrofit2.Response


class AndroidHelper private constructor() {

    private var mApiService: RequestApi = RetrofitWrapper
            .getInstance(RequestApi.HOST)
            .create(RequestApi::class.java)


    companion object {
        private var model: AndroidHelper? = null
        // 初始化单例
        fun instance() : AndroidHelper{
            if (model == null) {
                model = AndroidHelper()
            }
            return model as AndroidHelper
        }

    }

    fun <T> toObservable(o: Observable<T>): Observable<T> {
        return o.subscribeOn(Schedulers.io())//网络请求在子线程，所以是在io线程，避免阻塞线程
                .unsubscribeOn(Schedulers.io())//取消请求的的时候在 io 线程，避免阻塞线程
                .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * 获取主页文章
     */
    fun getHomeList(page: Int): Observable<HomeListBean> {
        return mApiService.getHomeList(page)
    }

}

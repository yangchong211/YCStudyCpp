package cn.ycbjie.ycaudioplayer.kotlin.model.helper

import cn.ycbjie.ycaudioplayer.api.manager.RetrofitWrapper
import com.mg.axechen.wanandroid.javabean.HomeListBean
import network.request.RequestApi
import retrofit2.Response
import rx.Observable

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


    /**
     * 获取主页文章
     */
    fun getHomeList(page: Int): Observable<Response<HomeListBean>> {
        return mApiService.getHomeList(page)
    }

}

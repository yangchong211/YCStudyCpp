package cn.ycbjie.ycaudioplayer.kotlin.model.model

import cn.ycbjie.ycaudioplayer.kotlin.contract.AndroidHomeContract
import cn.ycbjie.ycaudioplayer.kotlin.model.bean.BannerBean
import cn.ycbjie.ycaudioplayer.kotlin.model.helper.AndroidHelper
import com.mg.axechen.wanandroid.javabean.HomeListBean
import io.reactivex.Observable
import network.response.ResponseBean
import org.json.JSONObject


class HomeModel : AndroidHomeContract.mode{

    override fun getHomeList(page: Int): Observable<ResponseBean<HomeListBean>> {
        val instance = AndroidHelper.instance()
        return instance.getHomeList(page)
    }

    override fun getBannerData(): Observable<ResponseBean<List<BannerBean>>> {
        val instance = AndroidHelper.instance()
        return instance.getBanner()
    }

    override fun collectInArticle(selectId: Int): Observable<ResponseBean<JSONObject>> {
        val instance = AndroidHelper.instance()
        return instance.collectInArticle(selectId)
    }

    override fun unCollectArticle(selectId: Int): Observable<ResponseBean<JSONObject>> {
        val instance = AndroidHelper.instance()
        return instance.unCollectArticle(selectId)
    }

}
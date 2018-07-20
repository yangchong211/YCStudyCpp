package cn.ycbjie.ycaudioplayer.kotlin.contract

import cn.ycbjie.ycaudioplayer.base.mvp.BasePresenter
import cn.ycbjie.ycaudioplayer.base.mvp.BaseView
import com.mg.axechen.wanandroid.javabean.HomeListBean

interface AndroidHomeContract {

    interface View : BaseView{
        fun setDataErrorView()
        fun setNetWorkErrorView()
        fun setDataView(homeListBean: HomeListBean)
    }

    interface Presenter : BasePresenter {
        fun getHomeList(page: Int)
    }

}

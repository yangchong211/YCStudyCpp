package cn.ycbjie.ycaudioplayer.kotlin.contract

import cn.ycbjie.ycaudioplayer.base.mvp.BasePresenter
import cn.ycbjie.ycaudioplayer.base.mvp.BaseView
import cn.ycbjie.ycaudioplayer.kotlin.model.bean.HomeData

interface AndroidHomeContract {

    interface View : BaseView{
        fun setDataView(data: List<HomeData>)
    }

    interface Presenter : BasePresenter {
        fun getHomeList(page: Int)
    }

}

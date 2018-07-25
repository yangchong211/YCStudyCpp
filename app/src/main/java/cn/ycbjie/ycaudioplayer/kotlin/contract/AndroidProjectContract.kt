package cn.ycbjie.ycaudioplayer.kotlin.contract

import cn.ycbjie.ycaudioplayer.base.mvp.BasePresenter
import cn.ycbjie.ycaudioplayer.base.mvp.BaseView
import cn.ycbjie.ycaudioplayer.kotlin.model.bean.BannerBean
import cn.ycbjie.ycaudioplayer.kotlin.model.bean.ProjectListBean
import cn.ycbjie.ycaudioplayer.kotlin.model.bean.TreeBean
import com.mg.axechen.wanandroid.javabean.HomeListBean
import network.response.ResponseBean

interface AndroidProjectContract {

    interface View : BaseView{
        fun setProjectTreeSuccess(bean: ResponseBean<List<TreeBean>>?)
        fun setProjectListByCidSuccess(bean: ResponseBean<ProjectListBean>?, refresh: Boolean)
    }

    interface Presenter : BasePresenter {
        fun getProjectTree()
        fun getProjectTreeList(id: Int, b: Boolean)
    }

}

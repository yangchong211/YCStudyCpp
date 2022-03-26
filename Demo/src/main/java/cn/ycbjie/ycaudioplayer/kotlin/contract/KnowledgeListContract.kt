package cn.ycbjie.ycaudioplayer.kotlin.contract

import cn.ycbjie.ycaudioplayer.base.mvp.BasePresenter
import cn.ycbjie.ycaudioplayer.base.mvp.BaseView
import cn.ycbjie.ycaudioplayer.kotlin.model.bean.ProjectListBean


interface KnowledgeListContract{

    interface View : BaseView {
        fun loadAllArticles(bean: ProjectListBean?, refresh: Boolean)
        fun getKnowledgeFail(message: String, refresh: Boolean)

    }

    interface Presenter : BasePresenter {
        fun getKnowledgeList(id: Int, refresh: Boolean)
    }

}

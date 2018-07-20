package cn.ycbjie.ycaudioplayer.kotlin.contract

import cn.ycbjie.ycaudioplayer.base.mvp.BasePresenter
import cn.ycbjie.ycaudioplayer.base.mvp.BaseView
import cn.ycbjie.ycaudioplayer.kotlin.model.bean.TreeBean
import network.response.ResponseBean


class KnowledgeListContract {

    interface View : BaseView{
        fun getTreeSuccess(bean: ResponseBean<List<TreeBean>>?)
    }

    interface Presenter : BasePresenter {
        fun getKnowledgeTree()
    }


}
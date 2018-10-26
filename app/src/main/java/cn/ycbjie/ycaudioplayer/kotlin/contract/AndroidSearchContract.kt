package cn.ycbjie.ycaudioplayer.kotlin.contract

import cn.ycbjie.ycaudioplayer.base.mvp.BasePresenter
import cn.ycbjie.ycaudioplayer.base.mvp.BaseView
import cn.ycbjie.ycaudioplayer.kotlin.model.bean.ProjectListBean
import cn.ycbjie.ycaudioplayer.kotlin.model.bean.SearchTag

class AndroidSearchContract{

    interface View : BaseView {
        fun setSearchTagSuccess(t: MutableList<SearchTag>?)
        fun setSearchTagFail(message: String)
        fun setAllData(t: ProjectListBean?, refresh: Boolean)
        fun setSearchResultSuccess(t: ProjectListBean?, refresh: Boolean)
        fun setSearchResultFail(message: String)
    }

    interface Presenter : BasePresenter {
        fun search(str: String, b: Boolean)
        fun getSearchTag()
    }

}

package cn.ycbjie.ycaudioplayer.kotlin.contract

import cn.ycbjie.ycaudioplayer.base.mvp.BasePresenter
import cn.ycbjie.ycaudioplayer.base.mvp.BaseView

interface AndroidCollectContract {

    interface View : BaseView{

    }

    interface Presenter : BasePresenter {
        fun unCollectArticle(articleId: Int)
        fun collectInArticle(articleId: Int)
    }

}

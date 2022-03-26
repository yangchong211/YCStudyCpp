package cn.ycbjie.ycaudioplayer.kotlin.contract

import cn.ycbjie.ycaudioplayer.base.mvp.BasePresenter
import cn.ycbjie.ycaudioplayer.base.mvp.BaseView
import cn.ycbjie.ycaudioplayer.kotlin.model.bean.NaviBean


/**
 * <pre>
 *     @author 杨充
 *     blog  :
 *     time  : 2017/05/30
 *     desc  : 网站导航页面
 *     revise:
 * </pre>
 */
interface NavWebsiteContract {

    interface View : BaseView{
        fun getNaviWebSiteSuccess(t: MutableList<NaviBean>?)
        fun getNaiWebSiteFail(message: String)
    }

    interface Presenter : BasePresenter {
        fun getWebsiteNavi()
    }

}

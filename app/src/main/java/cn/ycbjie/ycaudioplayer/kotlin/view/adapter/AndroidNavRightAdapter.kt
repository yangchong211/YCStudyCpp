package cn.ycbjie.ycaudioplayer.kotlin.view.adapter


import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import cn.ycbjie.ycaudioplayer.R
import cn.ycbjie.ycaudioplayer.kotlin.model.bean.HomeData
import cn.ycbjie.ycaudioplayer.kotlin.model.bean.NaviBean
import cn.ycbjie.ycaudioplayer.utils.app.ImageUtil
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter
import org.yczbj.ycrefreshviewlib.viewHolder.BaseViewHolder


/**
 * <pre>
 *     @author 杨充
 *     blog  :
 *     time  : 2017/05/30
 *     desc  : 网站导航页面
 *     revise:
 * </pre>
 */
class AndroidNavRightAdapter: RecyclerArrayAdapter<NaviBean> {


    private var activity: Activity?

    constructor(activity: Activity?) : super(activity){
        this.activity = activity
    }

    override fun OnCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<NaviBean> {
        return MyViewHolder(parent)
    }

    private inner class MyViewHolder internal constructor(parent: ViewGroup) :
            BaseViewHolder<NaviBean>(parent, R.layout.item_nav_web_kind) {

        private val tvText: TextView = getView(R.id.tvText)

        init {

        }

        @SuppressLint("SetTextI18n")
        override fun setData(item: NaviBean) {
            super.setData(item)
            tvText.text = item.name
        }
    }


}


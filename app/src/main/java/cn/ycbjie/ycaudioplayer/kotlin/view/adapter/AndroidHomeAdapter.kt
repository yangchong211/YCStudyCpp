package cn.ycbjie.ycaudioplayer.kotlin.view.adapter


import android.view.ViewGroup
import android.widget.TextView
import cn.ycbjie.ycaudioplayer.R
import cn.ycbjie.ycaudioplayer.kotlin.model.bean.HomeData
import cn.ycbjie.ycaudioplayer.kotlin.view.activity.AndroidActivity
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter
import org.yczbj.ycrefreshviewlib.viewHolder.BaseViewHolder


class AndroidHomeAdapter : RecyclerArrayAdapter<HomeData>{


    constructor(activity: AndroidActivity?) : super(activity)

    override fun OnCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<HomeData> {
        return ViewHolder(parent)
    }


    private inner class ViewHolder internal constructor(parent: ViewGroup) :
            BaseViewHolder<HomeData>(parent, R.layout.item_android_home_news) {

        internal var tvName: TextView = getView(R.id.tv_name)
        internal var tvTitle: TextView = getView(R.id.tv_title)

        override fun setData(data: HomeData?) {
            super.setData(data)
            tvName.text = data!!.chapterName
            tvTitle.text = data.title
        }
    }


}


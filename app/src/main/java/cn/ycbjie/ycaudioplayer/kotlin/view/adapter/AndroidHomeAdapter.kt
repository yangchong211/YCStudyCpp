package cn.ycbjie.ycaudioplayer.kotlin.view.adapter


import android.app.Activity
import android.os.Build
import android.support.annotation.RequiresApi
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cn.ycbjie.ycaudioplayer.R
import cn.ycbjie.ycaudioplayer.R.id.tvName
import cn.ycbjie.ycaudioplayer.kotlin.model.bean.HomeData
import cn.ycbjie.ycaudioplayer.kotlin.view.activity.AndroidActivity
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter
import org.yczbj.ycrefreshviewlib.viewHolder.BaseViewHolder


class AndroidHomeAdapter : RecyclerArrayAdapter<HomeData>{


    constructor(activity: Activity?) : super(activity)

    override fun OnCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<HomeData> {
        return ViewHolder(parent)
    }


    private inner class ViewHolder internal constructor(parent: ViewGroup) :
            BaseViewHolder<HomeData>(parent, R.layout.item_android_home_news) {

        internal var ttIvHead: ImageView = getView(R.id.ttIvHead)
        internal var ttTvName: TextView = getView(R.id.ttTvName)
        internal var ivMore: ImageView = getView(R.id.ivMore)
        internal var ivLike: ImageView = getView(R.id.ivLike)
        internal var tvContent: TextView = getView(R.id.tvContent)
        internal var tvTagTitle: TextView = getView(R.id.tvTagTitle)
        internal var tvSuperChapterName: TextView = getView(R.id.tvSuperChapterName)
        internal var tvLip: TextView = getView(R.id.tvLip)
        internal var tvChildChapterName: TextView = getView(R.id.tvChildChapterName)
        internal var tvTime: TextView = getView(R.id.tvTime)

        init {

        }

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun setData(data: HomeData?) {
            super.setData(data)
            ttTvName.text = data?.author
            tvContent.text = data?.title
            tvTime.text = data?.niceDate
            tvSuperChapterName.text = data?.superChapterName
            tvChildChapterName.text = data?.chapterName
            tvContent.text = data?.title

        }
    }


}


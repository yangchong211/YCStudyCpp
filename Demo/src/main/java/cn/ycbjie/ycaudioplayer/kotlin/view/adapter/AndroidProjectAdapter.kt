package cn.ycbjie.ycaudioplayer.kotlin.view.adapter


import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import cn.ycbjie.ycaudioplayer.R
import cn.ycbjie.ycaudioplayer.kotlin.model.bean.HomeData
import cn.ycbjie.ycaudioplayer.utils.app.ImageUtil
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter
import org.yczbj.ycrefreshviewlib.viewHolder.BaseViewHolder


/**
 * <pre>
 *     @author yangchong
 *     blog  :
 *     time  : 2018/7/17
 *     desc  : 选择优惠券适配器
 *     revise:
 * </pre>
 */
class AndroidProjectAdapter: RecyclerArrayAdapter<HomeData> {


    private var activity: Activity?

    constructor(activity: Activity?) : super(activity){
        this.activity = activity
    }

    override fun OnCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<HomeData> {
        return MyViewHolder(parent)
    }

    private inner class MyViewHolder internal constructor(parent: ViewGroup) :
            BaseViewHolder<HomeData>(parent, R.layout.item_project_list) {

        private val ivHead: ImageView = getView(R.id.ivHead)
        private val tvName: TextView = getView(R.id.tvName)
        private val ivMore: ImageView = getView(R.id.ivMore)
        private val flLike: FrameLayout = getView(R.id.flLike)
        private val ivLike: ImageView = getView(R.id.ivLike)
        private val ivImage: ImageView = getView(R.id.ivImage)
        private val tvContent: TextView = getView(R.id.tvContent)
        private val tvTime: TextView = getView(R.id.tvTime)

        init {
            //绑定子view的点击事件
            val listener = View.OnClickListener { v ->
                onItemChildClickListener?.onChildClick(v,adapterPosition)
            }
            flLike.setOnClickListener(listener)
            ivMore.setOnClickListener(listener)
        }

        @SuppressLint("SetTextI18n")
        override fun setData(item: HomeData) {
            super.setData(item)
            tvName.text = item.author
            tvContent.text = item.title
            tvTime.text = item.niceDate
            tvName.text = item.author
            ImageUtil.loadImgByPicasso(context,item.envelopePic,ivImage)
        }
    }

    private var onItemChildClickListener: OnItemChildClickListener? = null
    interface OnItemChildClickListener {
        fun onChildClick(view: View, position: Int)
    }
    fun setOnItemChildClickListener(listener: OnItemChildClickListener) {
        this.onItemChildClickListener = listener
    }

}


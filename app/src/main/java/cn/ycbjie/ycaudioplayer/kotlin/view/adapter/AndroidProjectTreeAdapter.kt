package cn.ycbjie.ycaudioplayer.kotlin.view.adapter


import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import cn.ycbjie.ycaudioplayer.R
import cn.ycbjie.ycaudioplayer.R.id.*
import cn.ycbjie.ycaudioplayer.kotlin.model.bean.HomeData
import cn.ycbjie.ycaudioplayer.kotlin.model.bean.ProjectListBean
import cn.ycbjie.ycaudioplayer.kotlin.model.bean.TreeBean
import cn.ycbjie.ycaudioplayer.utils.ImageUtil
import com.bumptech.glide.Glide
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter
import org.yczbj.ycrefreshviewlib.viewHolder.BaseViewHolder
import java.util.*


/**
 * <pre>
 *     @author yangchong
 *     blog  :
 *     time  : 2018/7/17
 *     desc  : 选择优惠券适配器
 *     revise:
 * </pre>
 */
class AndroidProjectTreeAdapter: RecyclerArrayAdapter<TreeBean> {


    private var activity: Activity?

    constructor(activity: Activity?) : super(activity){
        this.activity = activity
    }

    override fun OnCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<TreeBean> {
        return MyViewHolder(parent)
    }

    private inner class MyViewHolder internal constructor(parent: ViewGroup) :
            BaseViewHolder<TreeBean>(parent, R.layout.item_project_tree_list) {

        private val rbCheck: CheckBox = getView(R.id.rbCheck)
        private val tvText: TextView = getView(R.id.tvText)

        init {

        }

        @SuppressLint("SetTextI18n")
        override fun setData(item: TreeBean) {
            super.setData(item)
            tvText.text = item.name
            rbCheck.isChecked = selectProject?.id == item.id
        }
    }

    private var selectProject: TreeBean? = null
    fun setSelect(selectBean: TreeBean) {
        this.selectProject = selectBean
    }

}


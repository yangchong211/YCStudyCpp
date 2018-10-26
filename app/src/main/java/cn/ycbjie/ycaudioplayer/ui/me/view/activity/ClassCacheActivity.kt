package cn.ycbjie.ycaudioplayer.ui.me.view.activity

import android.graphics.Rect
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import java.util.ArrayList

import butterknife.Bind
import cn.ycbjie.ycaudioplayer.R
import cn.ycbjie.ycaudioplayer.base.view.BaseActivity
import cn.ycbjie.ycaudioplayer.db.dl.TasksManager
import cn.ycbjie.ycaudioplayer.db.dl.TasksManagerModel
import cn.ycbjie.ycaudioplayer.kotlin.presenter.AndroidPresenter
import cn.ycbjie.ycaudioplayer.ui.me.view.adapter.CacheDownloadedAdapter
import cn.ycbjie.ycaudioplayer.ui.me.view.adapter.CacheDownloadingAdapter
import cn.ycbjie.ycaudioplayer.utils.dialog.DialogUtils
import cn.ycbjie.ycaudioplayer.utils.logger.AppLogUtils
import com.pedaily.yc.ycdialoglib.toast.ToastUtils

/**
 * <pre>
 * @author yangchong
 * blog  : www.pedaily.cn
 * time  : 2018/03/26
 * desc  : 课程缓存页面
 * revise: java文件直接转化成kotlin
</pre> *
 */

class ClassCacheActivity : BaseActivity<AndroidPresenter>(), View.OnClickListener {

    @Bind(R.id.ll_title_menu)
    private var flTitleMenu: FrameLayout? = null
    @Bind(R.id.toolbar_title)
    private var toolbarTitle: TextView? = null
    @Bind(R.id.tv_title_right)
    private var tvTitleRight: TextView? = null
    @Bind(R.id.tv_start_title)
    internal var tvStartTitle: TextView? = null
    @Bind(R.id.recyclerView_start)
    private var recyclerViewStart: RecyclerView? = null
    @Bind(R.id.tv_complete_title)
    internal var tvCompleteTitle: TextView? = null
    @Bind(R.id.recyclerView_complete)
    private var recyclerViewComplete: RecyclerView? = null

    private var cacheDownloadingAdapter: CacheDownloadingAdapter? = null
    private var cacheDownloadedAdapter: CacheDownloadedAdapter? = null
    private var downloadingData: List<TasksManagerModel>? = null
    private var downloadedData: List<TasksManagerModel>? = null

    var presenter: AndroidPresenter? = null

    override fun getContentView(): Int {
        return R.layout.activity_class_cache
    }

    override fun initView() {
        toolbarTitle!!.text = "离线课程"
        tvTitleRight!!.visibility = View.VISIBLE
        tvTitleRight!!.text = "批量删除"
        initRecyclerView()
    }

    override fun initListener() {
        flTitleMenu!!.setOnClickListener(this)
        tvTitleRight!!.setOnClickListener(this)
        cacheDownloadingAdapter!!.setOnListItemClickListener { view, position ->
            ToastUtils.showRoundRectToast("点击事件$position")
        }
        cacheDownloadingAdapter!!.setOnMoreClickListener { position -> showDialogAnim(position) }
        cacheDownloadingAdapter!!.setOnCompleteListener { model, position ->
            if (model != null) {
                //downloadingData.remove(position);
                //cacheDownloadingAdapter.notifyItemRemoved(position);

                cacheDownloadedAdapter!!.insertData(model)
                cacheDownloadedAdapter!!.notifyItemInserted(0)
            }
        }
        cacheDownloadedAdapter!!.setOnListItemClickListener { view, position -> }
        cacheDownloadedAdapter!!.setOnMoreClickListener { position -> showDialogAnim(position) }
    }


    override fun initData() {
        downloadingData = TasksManager.getImpl().modelList
        if (downloadingData != null && downloadingData!!.size > 0) {
            cacheDownloadingAdapter!!.addAllData(downloadingData)
            AppLogUtils.e("initData------downloadingData-----" + downloadingData!!.size)
        }

        downloadedData = TasksManager.getImpl().downloadedList
        if (downloadedData != null && downloadedData!!.size > 0) {
            cacheDownloadedAdapter!!.addAllData(downloadedData)
            AppLogUtils.e("initData-------downloadedData----" + downloadedData!!.size)
        }
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.ll_title_menu -> finish()
            R.id.tv_title_right -> deleteData()
            else -> {
            }
        }
    }


    private fun initRecyclerView() {
        val manager1 = LinearLayoutManager(this)
        manager1.orientation = OrientationHelper.VERTICAL
        cacheDownloadingAdapter = CacheDownloadingAdapter(this)
        recyclerViewStart!!.adapter = cacheDownloadingAdapter
        recyclerViewStart!!.layoutManager = manager1
        recyclerViewStart!!.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
                outRect.top = 2
            }
        })


        val manager2 = LinearLayoutManager(this)
        manager2.orientation = OrientationHelper.VERTICAL
        cacheDownloadedAdapter = CacheDownloadedAdapter(this)
        recyclerViewComplete!!.adapter = cacheDownloadedAdapter
        recyclerViewComplete!!.layoutManager = manager2
        recyclerViewComplete!!.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
                outRect.top = 2
            }
        })
    }


    private fun deleteData() {
        if ("批量删除" == tvTitleRight!!.text) {
            tvTitleRight!!.text = "取消"
        } else {
            tvTitleRight!!.text = "批量删除"
        }
    }


    private fun showDialogAnim(index: Int) {
        val names = ArrayList<String>()
        names.add("下载")
        names.add("分享")
        names.add("取消收藏")
        DialogUtils.showDialog(this, { parent, view, position, id ->
            when (position) {
                0 -> ToastUtils.showRoundRectToast("下载$index")
                1 -> ToastUtils.showRoundRectToast( "分享$index")
                2 -> ToastUtils.showRoundRectToast( "取消收藏$index")
                else -> {
                }
            }
        }, names)
    }


}

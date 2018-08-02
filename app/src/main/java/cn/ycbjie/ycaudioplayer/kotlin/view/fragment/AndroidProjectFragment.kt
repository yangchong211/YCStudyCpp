package cn.ycbjie.ycaudioplayer.kotlin.view.fragment

import android.graphics.Color
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import cn.ycbjie.ycaudioplayer.R
import cn.ycbjie.ycaudioplayer.base.view.BaseLazyFragment
import cn.ycbjie.ycaudioplayer.kotlin.contract.AndroidProjectContract
import cn.ycbjie.ycaudioplayer.kotlin.model.bean.HomeData
import cn.ycbjie.ycaudioplayer.kotlin.model.bean.ProjectListBean
import cn.ycbjie.ycaudioplayer.kotlin.model.bean.TreeBean
import cn.ycbjie.ycaudioplayer.kotlin.presenter.AndroidProjectPresenter
import cn.ycbjie.ycaudioplayer.kotlin.view.adapter.AndroidProjectAdapter
import cn.ycbjie.ycaudioplayer.kotlin.view.adapter.AndroidProjectTreeAdapter
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.fragment_android_project.*
import network.response.ResponseBean
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter
import java.util.ArrayList

class AndroidProjectFragment : BaseLazyFragment()  , AndroidProjectContract.View, View.OnClickListener {

    var presenter : AndroidProjectPresenter? = null
    private lateinit var listsAdapter: AndroidProjectAdapter
    private lateinit var kindsAdapter: AndroidProjectTreeAdapter
    private var kinds = mutableListOf<TreeBean>()
    private var selectProject: TreeBean? = null

    override fun getContentView(): Int {
        return R.layout.fragment_android_project
    }

    override fun initView(view: View) {
        presenter = AndroidProjectPresenter(this)
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        drawerLayout.setScrimColor(Color.TRANSPARENT)
        initProjectsRecyclerView()
        initRProjectTreeRecyclerView()
        initRefresh()
    }


    override fun initListener() {
        tvKind.setOnClickListener(this)
    }

    override fun initData() {

    }

    override fun onLazyLoad() {
        presenter?.getProjectTree()
    }


    override fun onClick(v: View?) {
        when (v?.id){
            R.id.tvKind ->{
                changeRightPage()
            }
        }
    }

    private fun changeRightPage() {
        if (drawerLayout.isDrawerOpen(flRight)) {
            drawerLayout.closeDrawer(flRight)
        } else {
            drawerLayout.openDrawer(flRight)
        }
    }




    private fun initProjectsRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(activity)
        rvList.layoutManager = linearLayoutManager
        listsAdapter = AndroidProjectAdapter(activity)
        rvList.adapter = listsAdapter
        listsAdapter.setOnItemClickListener({ position ->
            if (listsAdapter.allData.size > position && position > -1) {
                //条目点击事件
            }
        })
        listsAdapter.setOnItemChildClickListener(object : AndroidProjectAdapter.OnItemChildClickListener {
            override fun onChildClick(view: View, position: Int) {
                //子View点击事件
                when(view.id){
                    R.id.flLike ->{

                    }
                    R.id.ivMore ->{

                    }
                }
            }
        })

        //加载更多
        listsAdapter.setMore(R.layout.view_recycle_more, object : RecyclerArrayAdapter.OnMoreListener {
            override fun onMoreShow() {
                if (NetworkUtils.isConnected()) {
                    presenter?.getProjectTreeList(selectProject!!.id, false)
                } else {
                    listsAdapter.pauseMore()
                    ToastUtils.showShort("没有网络")
                }
            }

            override fun onMoreClick() {

            }
        })

        //设置没有数据
        listsAdapter.setNoMore(R.layout.view_recycle_no_more, object : RecyclerArrayAdapter.OnNoMoreListener {
            override fun onNoMoreShow() {
                if (NetworkUtils.isConnected()) {
                    listsAdapter.resumeMore()
                } else {
                    ToastUtils.showShort("没有网络")
                }
            }

            override fun onNoMoreClick() {
                if (NetworkUtils.isConnected()) {
                    listsAdapter.resumeMore()
                } else {
                    ToastUtils.showShort("没有网络")
                }
            }
        })

        //设置错误
        listsAdapter.setError(R.layout.view_recycle_error, object : RecyclerArrayAdapter.OnErrorListener {
            override fun onErrorShow() {
                listsAdapter.resumeMore()
            }

            override fun onErrorClick() {
                listsAdapter.resumeMore()
            }
        })
    }

    private fun initRProjectTreeRecyclerView() {
        rvKinds.layoutManager = LinearLayoutManager(activity)
        kindsAdapter = AndroidProjectTreeAdapter(activity)
        rvKinds.adapter = kindsAdapter
        kindsAdapter.setOnItemClickListener({ position ->
            if (kindsAdapter.allData.size > position && position > -1) {
                //条目点击事件
                //关闭侧滑。请求数据
                drawerLayout.closeDrawer(flRight)
                selectProject = kinds[position]
                //结束更多刷新
                //listAdapter?.loadMoreEnd(true)
                kindsAdapter.setSelect(selectProject!!)
                tvKind.text = selectProject!!.name
                presenter?.getProjectTreeList(selectProject!!.id, true)
                kindsAdapter.notifyDataSetChanged()
            }
        })
    }



    private fun initRefresh() {
        sRefresh.setOnRefreshListener({
            presenter?.getProjectTreeList(selectProject!!.id, true)
        })
    }


    override fun setProjectTreeSuccess(bean: List<TreeBean>) {
        kinds = bean as MutableList<TreeBean>
        selectProject = kinds[0]
        kindsAdapter.clear()
        kindsAdapter.addAll(bean)
        kindsAdapter.setSelect(selectProject!!)
        kindsAdapter.notifyDataSetChanged()

        tvKind.text = selectProject!!.name
        presenter?.getProjectTreeList(selectProject!!.id, true)

    }

    override fun setProjectListByCidSuccess(bean: ResponseBean<ProjectListBean>, isRefresh: Boolean) {
        sRefresh.isRefreshing = false
        if (isRefresh) {
            val data = bean.data
            val size = data?.size
            val homeData = ArrayList<HomeData>()
            homeData.addAll(data?.datas!!)
            LogUtils.e("size数量-----$size")
            listsAdapter.clear()
            //这种为什么不行？？？？
            listsAdapter.addAll(homeData)
            listsAdapter.notifyDataSetChanged()
        } else {
            if (bean.data?.size  != 0) {
                listsAdapter.clear()
                listsAdapter.addAll(bean.data?.datas!!)
                listsAdapter.notifyDataSetChanged()
            }
        }
    }

}
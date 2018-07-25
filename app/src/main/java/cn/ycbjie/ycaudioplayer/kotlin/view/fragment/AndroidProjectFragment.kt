package cn.ycbjie.ycaudioplayer.kotlin.view.fragment

import android.graphics.Color
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import cn.ycbjie.ycaudioplayer.R
import cn.ycbjie.ycaudioplayer.base.view.BaseFragment
import cn.ycbjie.ycaudioplayer.kotlin.contract.AndroidProjectContract
import cn.ycbjie.ycaudioplayer.kotlin.model.bean.ProjectListBean
import cn.ycbjie.ycaudioplayer.kotlin.model.bean.TreeBean
import cn.ycbjie.ycaudioplayer.kotlin.presenter.AndroidProjectPresenter
import cn.ycbjie.ycaudioplayer.kotlin.view.adapter.AndroidProjectAdapter
import kotlinx.android.synthetic.main.fragment_android_project.*
import network.response.ResponseBean

class AndroidProjectFragment : BaseFragment<AndroidProjectPresenter>()  , AndroidProjectContract.View, View.OnClickListener {

    private var presenter : AndroidProjectPresenter? = null
    private lateinit var adapter: AndroidProjectAdapter
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
        initRefresh()
    }


    override fun initListener() {
        tvKind.setOnClickListener(this)
    }

    override fun initData() {
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
        rvList!!.layoutManager = linearLayoutManager
        adapter = AndroidProjectAdapter(activity)
        rvList!!.adapter = adapter
        adapter.setOnItemClickListener({ position ->
            if (adapter.allData.size > position && position > -1) {
                //条目点击事件
            }
        })
        adapter.setOnItemChildClickListener(object : AndroidProjectAdapter.OnItemChildClickListener {
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
    }

    private fun initRefresh() {

    }


    override fun setProjectTreeSuccess(bean: ResponseBean<List<TreeBean>>?) {
        kinds = bean as MutableList<TreeBean>
        selectProject = kinds[0]
        tvKind.text = selectProject!!.name
        presenter?.getProjectTreeList(selectProject!!.id, true)
    }

    override fun setProjectListByCidSuccess(bean: ResponseBean<ProjectListBean>?, isRefresh: Boolean) {
        sRefresh.isRefreshing = false
        val data = bean?.data!!
        if (isRefresh) {
            adapter.clear()
            adapter.addAll(data.datas)
            adapter.notifyDataSetChanged()

            // 计算页数，是否开启加载下一页
            /*if (bean.size >= bean.total) {
                listAdapter.setEnableLoadMore(false)
            }*/
        } else {
            if (data.size != 0) {
                adapter.clear()
                adapter.addAll(data.datas)
                adapter.notifyDataSetChanged()
            }
        }
    }

}
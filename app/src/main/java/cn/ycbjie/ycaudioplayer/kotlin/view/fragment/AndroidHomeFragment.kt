package cn.ycbjie.ycaudioplayer.kotlin.view.fragment

import android.content.Context
import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import cn.ycbjie.ycaudioplayer.R
import cn.ycbjie.ycaudioplayer.base.view.BaseFragment
import cn.ycbjie.ycaudioplayer.kotlin.contract.AndroidHomeContract
import cn.ycbjie.ycaudioplayer.kotlin.model.bean.HomeData
import cn.ycbjie.ycaudioplayer.kotlin.presenter.AndroidHomePresenter
import cn.ycbjie.ycaudioplayer.kotlin.view.activity.AndroidActivity
import cn.ycbjie.ycaudioplayer.kotlin.view.adapter.AndroidHomeAdapter
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.SizeUtils
import com.mg.axechen.wanandroid.javabean.HomeListBean
import com.pedaily.yc.ycdialoglib.customToast.ToastUtil
import org.yczbj.ycrefreshviewlib.YCRefreshView
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter
import org.yczbj.ycrefreshviewlib.item.RecycleViewItemLine


class AndroidHomeFragment : BaseFragment<AndroidHomePresenter>() , AndroidHomeContract.View{


    private var recyclerView : YCRefreshView ?=null
    private var activity: AndroidActivity? = null
    var presenter: AndroidHomePresenter? = null
    private var page: Int = 0
    private lateinit var adapter: AndroidHomeAdapter

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        activity = context as AndroidActivity?
    }

    override fun onDetach() {
        super.onDetach()
        activity = null
    }

    override fun getContentView(): Int {
        return R.layout.base_easy_recycle
    }

    override fun initView(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView) as YCRefreshView
        presenter = AndroidHomePresenter(this)
        initRecyclerView()
    }


    override fun initListener() {

    }

    override fun initData() {
        presenter?.getHomeList(page)
    }

    private fun initRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(activity)
        recyclerView!!.setLayoutManager(linearLayoutManager)
        val line = RecycleViewItemLine(activity, LinearLayout.HORIZONTAL,
                SizeUtils.dp2px(1f), Color.parseColor("#f5f5f7"))
        recyclerView!!.addItemDecoration(line)
        adapter = AndroidHomeAdapter(activity)
        recyclerView!!.adapter = adapter
        recyclerView!!.setRefreshing(false)
        recyclerView!!.scrollTo(0, 0)
        recyclerView!!.scrollBy(0, 0)
        recyclerView!!.setRefreshListener({
            if (NetworkUtils.isConnected()) {
                page = 0
                initData()
            } else {
                recyclerView!!.setRefreshing(false)
                ToastUtil.showToast(activity, "没有网络")
            }
        })
        //加载更多
        adapter.setMore(R.layout.view_recycle_more, object : RecyclerArrayAdapter.OnMoreListener {
            override fun onMoreShow() {
                if (NetworkUtils.isConnected()) {
                    if (adapter.allData.size > 0) {
                        page++
                        initData()
                    } else {
                        adapter.pauseMore()
                    }
                } else {
                    adapter.pauseMore()
                    ToastUtil.showToast(activity, "网络不可用")
                }
            }

            override fun onMoreClick() {
            }
        })

        //设置没有数据
        adapter.setNoMore(R.layout.view_recycle_no_more, object : RecyclerArrayAdapter.OnNoMoreListener {
            override fun onNoMoreShow() {
                if (NetworkUtils.isConnected()) {
                    adapter.resumeMore()
                } else {
                    ToastUtil.showToast(activity, "网络不可用")
                }
            }

            override fun onNoMoreClick() {
                if (NetworkUtils.isConnected()) {
                    adapter.resumeMore()
                } else {
                    ToastUtil.showToast(activity, "网络不可用")
                }
            }
        })

        //设置错误
        adapter.setError(R.layout.view_recycle_error, object : RecyclerArrayAdapter.OnErrorListener {
            override fun onErrorShow() {
                adapter.resumeMore()
            }

            override fun onErrorClick() {
                adapter.resumeMore()
            }
        })
    }

    override fun setDataView(homeListBean: HomeListBean) {
        if(homeListBean.datas!=null){
            recyclerView?.showRecycler()
            adapter.addAll(homeListBean.datas)
            adapter.notifyDataSetChanged()
        }else{
            recyclerView?.showEmpty()
        }
    }

    override fun setNetWorkErrorView() {


        recyclerView?.showError()
    }

    override fun setDataErrorView() {
        recyclerView?.showError()
    }

}
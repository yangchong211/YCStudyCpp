package cn.ycbjie.ycaudioplayer.kotlin.view.fragment

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import cn.ycbjie.ycaudioplayer.R
import cn.ycbjie.ycaudioplayer.base.view.BaseFragment
import cn.ycbjie.ycaudioplayer.kotlin.base.BaseItemView
import cn.ycbjie.ycaudioplayer.kotlin.base.KotlinConstant
import cn.ycbjie.ycaudioplayer.kotlin.contract.AndroidHomeContract
import cn.ycbjie.ycaudioplayer.kotlin.model.bean.BannerBean
import cn.ycbjie.ycaudioplayer.kotlin.model.bean.HomeData
import cn.ycbjie.ycaudioplayer.kotlin.presenter.AndroidHomePresenter
import cn.ycbjie.ycaudioplayer.kotlin.view.activity.AndroidDetailActivity
import cn.ycbjie.ycaudioplayer.kotlin.view.adapter.AndroidHomeAdapter
import cn.ycbjie.ycaudioplayer.kotlin.view.adapter.BannerPagerAdapter
import cn.ycbjie.ycaudioplayer.ui.web.WebViewActivity
import cn.ycbjie.ycaudioplayer.utils.app.DoShareUtils
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.SizeUtils
import com.mg.axechen.wanandroid.javabean.HomeListBean
import com.pedaily.yc.ycdialoglib.customToast.ToastUtil
import com.yc.cn.ycbannerlib.BannerView
import com.yc.cn.ycbannerlib.banner.util.SizeUtil
import network.response.ResponseBean
import org.yczbj.ycrefreshviewlib.YCRefreshView
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter
import org.yczbj.ycrefreshviewlib.item.RecycleViewItemLine


class AndroidHomeFragment : BaseFragment<AndroidHomePresenter>() , AndroidHomeContract.View{


    private var recyclerView : YCRefreshView ?=null
    private var activity: Activity? = null
    private var presenter: AndroidHomePresenter? = null
    private var page: Int = 0
    private lateinit var adapter: AndroidHomeAdapter
    private var mBanner: BannerView? = null
    /**
     * 循环轮询的数据
     */
    private val bannerLists = mutableListOf<BannerBean>()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        //as 是一个中缀操作符，as是不安全的转换操作符，如果as转换失败，会抛出一个异常，这就是不安全的。
        activity = context as Activity?
        //activity = context as? Activity
    }

    override fun onDetach() {
        super.onDetach()
        activity = null
    }

    override fun onResume() {
        super.onResume()
        if (mBanner!=null){
            mBanner!!.resume()
        }
    }

    override fun onPause() {
        super.onPause()
        if (mBanner!=null){
            mBanner!!.pause()
        }
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
        adapter.setOnItemClickListener { position ->
            val homeData: HomeData = adapter.allData[position] as HomeData
            AndroidDetailActivity.lunch(activity, homeData, homeData.collect, homeData.id)
        }
        adapter.setOnItemChildClickListener { view, position ->
            if(adapter.allData.size>position && position>=0){
                when (view.id){
                    R.id.flLike ->{
                        if (SPUtils.getInstance().getInt(KotlinConstant.USER_ID)==0){
                            ToastUtil.showToast(activity,getString(R.string.collect_fail_pls_login))
                        }else{
                            val homeData = adapter.allData[position]
                            //var homdata: HomeData = datas[position].item as HomeData
                            val selectId = homeData.id
                            if (homeData.collect) {
                                presenter?.unCollectArticle(selectId)
                                addCollectStatus(homeData,position)
                            } else {
                                removeCollectStatus(homeData,position)
                                presenter?.collectInArticle(selectId)
                            }
                        }
                    }
                    R.id.ivMore ->{
                        val data = adapter.allData[position]
                        DoShareUtils.shareText(activity,data.title,data.link)
                    }
                }
            }
        }
    }

    override fun initData() {
        recyclerView?.showProgress()
        presenter?.getHomeList(page)
        presenter?.getBannerData(true)
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
                presenter?.getHomeList(page)
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
                        presenter?.getHomeList(page)
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

    override fun setBannerView(bean: ResponseBean<List<BannerBean>>?) {
        if(bean?.data != null){
            adapter.removeAllHeader()
            val itemView = object : BaseItemView(activity, R.layout.view_vlayout_banner) {
                override fun setBindView(headerView: View) {
                    bannerLists.addAll(bean.data!!)
                    mBanner = headerView.findViewById<View>(R.id.banner) as BannerView
                    mBanner!!.setHintGravity(1)
                    mBanner!!.setAnimationDuration(1000)
                    mBanner!!.setPlayDelay(2000)
                    mBanner!!.setHintPadding(0, 0, 0, SizeUtil.dip2px(activity, 10f))
                    mBanner!!.setAdapter(BannerPagerAdapter(activity, bannerLists))
                    mBanner!!.setOnBannerClickListener { position: Int ->
                        if(position>=0 && bannerLists.size>position){
                            val bannerBean = bean.data!![position]
                            WebViewActivity.lunch(activity, bannerBean.url!!, bannerBean.title!!)
                        }
                    }
                }
            }
            adapter.addHeader(itemView)
        }
    }


    private fun addCollectStatus(homeData: HomeData, position: Int) {
        homeData.collect = false
        adapter.notifyItemChanged(position)
        //adapter.notifyDataSetChanged()
    }


    private fun removeCollectStatus(homeData: HomeData, position: Int) {
        homeData.collect = true
        adapter.notifyItemChanged(position)
        //adapter.notifyDataSetChanged()
    }

    override fun setDataView(bean: ResponseBean<HomeListBean>) {
        if(bean.data!=null){
            recyclerView?.showRecycler()
            adapter.addAll(bean.data!!.datas)
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

    override fun unCollectArticleSuccess() {
        ToastUtil.showToast(activity,"取消收藏成功")
    }

    override fun unCollectArticleFail(t: Throwable) {
        ToastUtil.showToast(activity,t.message)
    }

    override fun collectInArticleSuccess() {
        ToastUtil.showToast(activity,"收藏成功")
    }

    override fun collectInArticleFail(t: Throwable) {
        ToastUtil.showToast(activity,t.message)
    }


}
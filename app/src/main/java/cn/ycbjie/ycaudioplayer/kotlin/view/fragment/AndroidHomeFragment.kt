package cn.ycbjie.ycaudioplayer.kotlin.view.fragment

import android.content.Context
import android.graphics.Color
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
import com.blankj.utilcode.util.SizeUtils
import org.yczbj.ycrefreshviewlib.YCRefreshView
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
        presenter!!.getHomeList(page)
    }

    private fun initRecyclerView() {
        var linearLayoutManager = LinearLayoutManager(activity)
        recyclerView!!.setLayoutManager(linearLayoutManager)
        val line = RecycleViewItemLine(activity, LinearLayout.HORIZONTAL,
                SizeUtils.dp2px(1f), Color.parseColor("#f5f5f7"))
        recyclerView!!.addItemDecoration(line)
        adapter = AndroidHomeAdapter(activity)
        recyclerView!!.adapter = adapter
        recyclerView!!.setRefreshing(false)
        recyclerView!!.scrollTo(0, 0)
        recyclerView!!.scrollBy(0, 0)
    }

    override fun setDataView(data: List<HomeData>) {
        adapter.addAll(data)
        adapter.notifyDataSetChanged()
    }

}
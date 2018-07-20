package cn.ycbjie.ycaudioplayer.kotlin.view.fragment

import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import cn.ycbjie.ycaudioplayer.R
import cn.ycbjie.ycaudioplayer.base.view.BaseFragment
import cn.ycbjie.ycaudioplayer.kotlin.contract.KnowledgeListContract
import cn.ycbjie.ycaudioplayer.kotlin.model.bean.TreeBean
import cn.ycbjie.ycaudioplayer.kotlin.presenter.KnowledgeListPresenter
import cn.ycbjie.ycaudioplayer.kotlin.view.adapter.AndroidHomeAdapter
import cn.ycbjie.ycaudioplayer.kotlin.view.adapter.KnowledgeListAdapter
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.SizeUtils
import kotlinx.android.synthetic.main.base_bar_easy_recycle.*
import network.response.ResponseBean
import org.yczbj.ycrefreshviewlib.item.RecycleViewItemLine
import java.util.*


class AndroidKnowledgeFragment : BaseFragment<KnowledgeListPresenter>()  , KnowledgeListContract.View{

    var presenter : KnowledgeListPresenter? = null
    private lateinit var adapter: KnowledgeListAdapter


    override fun getContentView(): Int {
        return R.layout.base_easy_recycle
    }

    override fun initView(view: View) {
        presenter = KnowledgeListPresenter(this)
        initRecyclerView()
    }

    override fun initListener() {
        adapter.setOnItemClickListener { position: Int ->
            if (adapter.allData.size>position && position>=0){

            }
        }
        adapter.knowledgeItemClick = object : KnowledgeListAdapter.KnowledgeItemListener{
            override fun knowledgeItemClick(bean: TreeBean, index: Int, position: Int) {

            }
        }
    }

    override fun initData() {
        recyclerView.showProgress()
        presenter?.getKnowledgeTree()
    }



    private fun initRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(activity)
        recyclerView!!.setLayoutManager(linearLayoutManager)
        val line = RecycleViewItemLine(activity, LinearLayout.HORIZONTAL,
                SizeUtils.dp2px(1f), Color.parseColor("#f5f5f7"))
        recyclerView!!.addItemDecoration(line)
        adapter = KnowledgeListAdapter(activity)
        recyclerView!!.adapter = adapter
        recyclerView!!.setRefreshing(false)
        recyclerView!!.scrollTo(0, 0)
        recyclerView!!.scrollBy(0, 0)
        recyclerView!!.setRefreshListener({

        })
    }

    override fun getTreeSuccess(bean: ResponseBean<List<TreeBean>>?) {
       if(bean!=null){
           val data = bean.data
           adapter.addAll(data)
           adapter.notifyDataSetChanged()
           recyclerView.showRecycler()
       }else{
           recyclerView.showEmpty()
       }
    }

}
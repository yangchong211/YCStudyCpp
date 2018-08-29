package cn.ycbjie.ycaudioplayer.kotlin.view.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import cn.ycbjie.ycaudioplayer.R
import cn.ycbjie.ycaudioplayer.base.view.BaseActivity
import cn.ycbjie.ycaudioplayer.kotlin.contract.NavWebsiteContract
import cn.ycbjie.ycaudioplayer.kotlin.model.bean.HomeData
import cn.ycbjie.ycaudioplayer.kotlin.model.bean.NaviBean
import cn.ycbjie.ycaudioplayer.kotlin.presenter.NavWebsitePresenter
import cn.ycbjie.ycaudioplayer.kotlin.view.adapter.AndroidNavRightAdapter
import cn.ycbjie.ycaudioplayer.kotlin.view.adapter.AndroidNavWebsiteAdapter
import cn.ycbjie.ycaudioplayer.kotlin.view.adapter.AndroidProjectAdapter
import cn.ycbjie.ycaudioplayer.ui.web.WebViewActivity
import cn.ycbjie.ycstatusbarlib.bar.YCAppBar
import com.blankj.utilcode.util.LogUtils
import kotlinx.android.synthetic.main.activity_nav_website.*
import kotlinx.android.synthetic.main.base_title_bar.*
import network.schedules.SchedulerProvider

/**
 * <pre>
 *     @author 杨充
 *     blog  :
 *     time  : 2017/05/30
 *     desc  : 网站导航页面
 *     revise:
 * </pre>
 */
class NavWebsiteActivity : BaseActivity<NavWebsitePresenter>() , NavWebsiteContract.View{

    private lateinit var listsAdapter: AndroidNavWebsiteAdapter
    private lateinit var kindsAdapter: AndroidNavRightAdapter

    private val presenter: NavWebsiteContract.Presenter by lazy {
        NavWebsitePresenter(this, SchedulerProvider.getInstatnce()!!)
    }


    /**
     * Kotlin语言中使用"companion object"修饰静态方法，可以使用类名.方法名的形式调用
     * 比如：NavWebsiteActivity.lunch(this)
     */
    companion object {
        fun lunch(context: Context) {
            //val intent = Intent(context, NavWebsiteActivity.javaClass)
            //问题：为什么不用上面那个而是用下面这个
            val intent = Intent(context,NavWebsiteActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.kotlin_navi_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        } else if (item?.itemId == R.id.actionMenu) {
            changeRightPage()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unSubscribe()
    }

    override fun getContentView(): Int {
        return R.layout.activity_nav_website
    }

    override fun initView() {
        YCAppBar.setStatusBarColor(this, ContextCompat.getColor(this, R.color.redTab))
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        drawerLayout.setScrimColor(Color.TRANSPARENT)
        initToolBar()
        initListsAdapter()
        initKindsAdapter()
    }

    override fun initListener() {

    }

    override fun initData() {
        presenter.getWebsiteNavi()
    }


    private fun initToolBar() {
        ll_title_menu.visibility = View.GONE
        toolbar_title.visibility = View.GONE
        toolbar.run {
            title = "网址导航"
            setSupportActionBar(toolbar)
            setTitleTextColor(Color.WHITE)
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }


    private fun changeRightPage() {
        if (drawerLayout.isDrawerOpen(flRight)) {
            drawerLayout.closeDrawer(flRight)
        } else {
            drawerLayout.openDrawer(flRight)
        }
    }


    private fun initListsAdapter() {
        listsAdapter = AndroidNavWebsiteAdapter(this)
        rvList.adapter = listsAdapter
        rvList.layoutManager = LinearLayoutManager(this)

        /*rvList.run {
            adapter = listsAdapter
            layoutManager = LinearLayoutManager(this@NavWebsiteActivity)
        }*/

        listsAdapter.setOnItemClickListener { position ->
            if (position>=0 && listsAdapter.allData.size>position){
                //WebViewActivity.lunch(this@NaviWebsiteActivity, data.link!!, data.title!!)
            }
        }

        listsAdapter.itemClickLister = object : AndroidNavWebsiteAdapter.ItemClickListener {
            override fun itemClick(data: HomeData) {
                WebViewActivity.lunch(this@NavWebsiteActivity, data.link!!, data.title!!)
            }
        }
    }


    private fun initKindsAdapter() {
        kindsAdapter = AndroidNavRightAdapter(this)
        rvNavList.run {
            adapter = kindsAdapter
            layoutManager = LinearLayoutManager(this@NavWebsiteActivity)
        }
        kindsAdapter.setOnItemClickListener { position ->
            if (position>=0 && kindsAdapter.allData.size>position){

                rvList.scrollToPosition(position)
                val mLayoutManager = rvList.layoutManager as LinearLayoutManager
                mLayoutManager.scrollToPositionWithOffset(position, 0)
            }
        }
    }


    override fun getNaviWebSiteSuccess(bean: MutableList<NaviBean>?) {
        listsAdapter.clear()
        listsAdapter.addAll(bean)
        listsAdapter.notifyDataSetChanged()
        kindsAdapter.clear()
        kindsAdapter.addAll(bean)
        kindsAdapter.notifyDataSetChanged()
    }

    override fun getNaiWebSiteFail(message: String) {
        LogUtils.e(message)
    }


}









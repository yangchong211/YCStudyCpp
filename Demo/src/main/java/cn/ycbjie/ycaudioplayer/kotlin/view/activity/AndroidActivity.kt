package cn.ycbjie.ycaudioplayer.kotlin.view.activity

import android.graphics.Typeface
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import cn.ycbjie.ycaudioplayer.R
import cn.ycbjie.ycaudioplayer.base.view.BaseActivity
import cn.ycbjie.ycaudioplayer.base.view.BasePagerAdapter
import cn.ycbjie.ycaudioplayer.kotlin.presenter.AndroidPresenter
import cn.ycbjie.ycaudioplayer.kotlin.view.fragment.AndroidHomeFragment
import cn.ycbjie.ycaudioplayer.kotlin.view.fragment.AndroidKnowledgeFragment
import cn.ycbjie.ycaudioplayer.kotlin.view.fragment.AndroidProfileFragment
import cn.ycbjie.ycaudioplayer.kotlin.view.fragment.AndroidProjectFragment
import cn.ycbjie.ycaudioplayer.model.bean.TabEntity
import cn.ycbjie.ycaudioplayer.utils.app.DoShareUtils
import cn.ycbjie.ycaudioplayer.utils.logger.AppLogUtils
import cn.ycbjie.ycstatusbarlib.bar.StateAppBar
import com.flyco.tablayout.CommonTabLayout
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import kotlinx.android.synthetic.main.base_title_bar.*
import java.lang.ref.WeakReference
import java.util.*

/**
 * <pre>
 *     @author 杨充
 *     blog  :
 *     time  : 2018/01/30
 *     desc  : kotlin学习：
 *     revise:
 * </pre>
 */
class AndroidActivity : BaseActivity<AndroidPresenter>(){

    /**
     * var关键字声明可变属性
     * val关键字声明只读属性
     * 属性的类型在后面，变量名在前面，中间加冒号和空格
     */
    var presenter : AndroidPresenter? = null

    /**
     * 定义局部变量和常量
     * 0. Kotlin声明变量与Java声明变量有些不一样，Java变量类型在前，变量名在后，
     *   而Kotlin则相反，变量名在前，变量类型在后，中间加:(冒号)
     *   并且Kotlin可以自动判断变量的类型。
     *
     * 1.常量
     *      常量使用val关键字，val代表只读
     *
     * 2.变量
     *      变量使用var关键字，val代表可变
     *
     * val是线程安全的，并且必须在定义时初始化，所以不需要担心 null 的问题
     * 强烈推荐能用val的地方就用val
     */
    private var mTvTitleLeft :TextView?=null
    private var mLlTitleMenu :FrameLayout?=null
    private var mToolbarTitle :TextView?=null
    private var mIvRightImg :ImageView?=null
    private var viewPager: ViewPager? = null
    private var ctlTable : CommonTabLayout? =null
    private var fragments = mutableListOf<Fragment>()
    private var pageAdapter : BasePagerAdapter? = null
    private var index: Int = 0      //定义具体的类型

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.kotlin_menu_main,menu)
        menu?.add(0, 1, 0, "登录")
        menu?.add(0, 3, 1,"分享此软件")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.actionSearch -> {
                AndroidSearchActivity.lunch(this)
            }
            R.id.actionUrlNav -> {
                NavWebsiteActivity.lunch(this)
            }
            1 -> {

            }
            2 -> {

            }
            3->{
                DoShareUtils.shareApp(this)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * 定义函数
     * 1.与Java定义函数的区别在于：Kotlin在定义函数的时候要加个fun关键词，函数的返回值前后不同，Java的在前面，kotlin在后面
     * 2.如果一个函数只有一个并且是表达式函数体并且是返回类型自动推断的话，可以直接这样写
     *   fun getResult(a: Int, b: Int) = a + b
     * 3.如果函数返回一个无意义的值，相当于Java的void，则可以这样写
     *   fun initView(){}
     */
    override fun getContentView(): Int {
        return R.layout.activity_wan_android
    }


    /**
     * 1. ?: Elvis 操作符
     *       val l = b?.length ?: -1
     *       如果 ?: 左侧表达式非空，elvis 操作符就返回其左侧表达式，否则返回右侧表达式。
     *       注意:当且仅当左侧为空时，才会对右侧表达式求值。
     *
     * 2. !! 操作符：
     *      这是为空指针爱好者准备的，非空断言运算符（!!）将任何值转换为非空类型，若该值为空则抛出异常
     *      能不用!!操作符就不要用。。。
     */
    override fun initView() {
        StateAppBar.setStatusBarColor(this, ContextCompat.getColor(this, R.color.redTab))
        mTvTitleLeft = findViewById(R.id.tv_title_left)
        mLlTitleMenu = findViewById(R.id.ll_title_menu)
        mToolbarTitle = findViewById(R.id.toolbar_title)
        mIvRightImg = findViewById(R.id.iv_right_img)
        viewPager = findViewById(R.id.vp_pager)
        ctlTable = this.findViewById(R.id.ctl_table)
        mTvTitleLeft!!.visibility = View.VISIBLE
        mTvTitleLeft!!.textSize = 16.0f
        mTvTitleLeft!!.typeface = Typeface.DEFAULT
        mLlTitleMenu!!.visibility = View.GONE
        mIvRightImg!!.visibility = View.VISIBLE

        initToolBar()
        initFragment()
        initTabLayout()
    }


    override fun initListener() {

    }


    override fun initData() {

    }

    private fun initToolBar() {
        mTvTitleLeft?.text = "首页"
        ll_title_menu.visibility = View.GONE
        toolbar_title.visibility = View.GONE
        toolbar.run {
            setSupportActionBar(toolbar)
            supportActionBar?.title = ""
            //setTitleTextColor(Color.WHITE)
        }
    }

    private fun initFragment() {
        /**
         * Kotlin不需要使用new关键字，直接写：类()
         */
        fragments.add(AndroidHomeFragment())
        fragments.add(AndroidKnowledgeFragment())
        fragments.add(AndroidProjectFragment())
        fragments.add(AndroidProfileFragment())

        pageAdapter = BasePagerAdapter(supportFragmentManager, fragments)
        viewPager.run {
            this!!.adapter = pageAdapter
            addOnPageChangeListener(PagerChangeListener(this@AndroidActivity))
            offscreenPageLimit = fragments.size
        }
    }

    private fun initTabLayout() {
        val mTabEntities = ArrayList<CustomTabEntity>()
        val mIconUnSelectIds = this.resources.obtainTypedArray(R.array.main_tab_un_select)
        val mIconSelectIds = this.resources.obtainTypedArray(R.array.main_tab_select)
        val mainTitles = this.resources.getStringArray(R.array.main_title)
        for (i in mainTitles.indices) {
            val unSelectId = mIconUnSelectIds.getResourceId(i, R.drawable.ic_tab_main_art_uncheck)
            val selectId = mIconSelectIds.getResourceId(i, R.drawable.ic_tab_main_art_checked)
            mTabEntities.add(TabEntity(mainTitles[i], selectId, unSelectId))
        }
        mIconUnSelectIds.recycle()
        mIconSelectIds.recycle()

        /**
         * 此处for仅供学习使用
         * 使用for循环
         * in操作符可以判断是否a是否在mTabEntities里面
         */
        for (a in mTabEntities){
            AppLogUtils.e("使用for循环"+ a.tabTitle)
        }

        ctlTable?.setTabData(mTabEntities)
        ctlTable?.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                if(position>=0 && position<fragments.size){
                    viewPager!!.currentItem = position
                }
            }

            override fun onTabReselect(position: Int) {

            }
        })
    }

    class PagerChangeListener constructor(activity: AndroidActivity) : ViewPager.OnPageChangeListener {
        private var weakActivity: WeakReference<AndroidActivity>? = null
        init {
            this.weakActivity = WeakReference(activity)
        }

        override fun onPageScrollStateChanged(state: Int) {}

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

        override fun onPageSelected(position: Int) {
            val activity: AndroidActivity? = weakActivity?.get()
            if (activity != null) {
                activity.index = position
                activity.selectByIndex(position)
            }
        }
    }

    /**
     * 使用when表达式
     * when表达式就相当于Java的switch表达式，省去了case和break，并且支持各种类型。
     *
     * 控制流(Control Flow)：Kotlin的控制流有if``when``for``while四种
     */
    private fun selectByIndex(position: Int) {
        mTvTitleLeft?.visibility = View.VISIBLE
        when (position){
            0 ->{
                ctlTable?.currentTab = 0
                mTvTitleLeft?.text = "首页"
                //supportActionBar!!.title = "首页"
            }
            1 ->{
                ctlTable?.currentTab = 1
                mTvTitleLeft?.text = "体系"
                //supportActionBar!!.title = "项目"
            }
            2 ->{
                ctlTable?.currentTab = 2
                mTvTitleLeft?.text = "项目"
                //supportActionBar!!.title = "博客"
            }
            3 ->{
                ctlTable?.currentTab = 3
                mTvTitleLeft?.text = "用户"
                //supportActionBar!!.title = "我的"
            }
            else -> {
                // 默认，相当于switch中default
                print("x is neither 1 nor 2")
                print(Foo(10, 20))
                print(Foo(10, 20))
            }
        }
    }


    // 一个简单的数据类
    // 用于重载运算符的所有函数都必须使用operator关键字标记。
    // 算术运算符：https://www.jianshu.com/p/d445209091f0
    data class Foo(private val x: Int, private val y: Int) {
        // a + b
        operator fun plus(other: Foo) {
            Foo(x + other.x, y + other.y)
        }
        // a * b
        operator fun times(other: Foo): Foo = Foo(x * other.x, y * other.y)
        // a % b
        operator fun rem(other: Foo): Foo = Foo(x % other.x, y % other.y)
        // a / b
        operator fun div(other: Foo): Foo = Foo(x % other.x, y % other.y)
        // a - b
        operator fun minus(other: Foo): Foo = Foo(x % other.x, y % other.y)

        // 支持运算符两边互换使用
        operator fun Double.times(other: Foo): Foo = Foo((this * other.x).toInt(), (this * other.y).toInt())
    }

    /**
     * 关键字
     * object           为同时声明一个类及其实例
     * typealias        类型别名为现有类型提供替代名称
     * as               是一个中缀操作符，as是不安全的转换操作符，如果as转换失败，会抛出一个异常，这就是不安全的。
     * as?              as?与as类似，也是转换操作符，但是与as不同的是，as?是安全的，也就是可空的，可以避免抛出异常，在转换失败是会返回null
     * fun              表示声明一个函数
     * in               用于指定for循环中迭代的对象
     * !in              表示与in相反，用作中缀操作符以检查一个值不属于一个区间、一个集合或者其他定义contains方法的实体。
     * is和!is          是否符合给定类型，类似与Java的instanceOf，is操作符或其否定形式!is来检查对象是否符合给定类型
     * constructor      声明一个主构造函数或次构造函数
     * init             主构造函数不能包含任何的代码。初始化的代码可以放到以init关键字作为前缀的初始化块中：
     * where            用于指定泛型多个类型的上界约束
     *
     */


}

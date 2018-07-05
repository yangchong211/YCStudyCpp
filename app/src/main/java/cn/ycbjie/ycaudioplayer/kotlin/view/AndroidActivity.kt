package cn.ycbjie.ycaudioplayer.kotlin.view

import android.widget.FrameLayout
import cn.ycbjie.ycaudioplayer.R
import cn.ycbjie.ycaudioplayer.base.view.BaseActivity
import cn.ycbjie.ycaudioplayer.kotlin.presenter.AndroidPresenter
import com.flyco.tablayout.CommonTabLayout

class AndroidActivity : BaseActivity<AndroidPresenter>(){

    var presenter: AndroidPresenter? = null
    private var mFlMain : FrameLayout?=null
    private var mCtlTable : CommonTabLayout?=null

    override fun getContentView(): Int {
        return R.layout.activity_wan_android
    }

    override fun initView() {
        mFlMain = findViewById(R.id.fl_main) as FrameLayout?
        mCtlTable = findViewById(R.id.ctl_table) as CommonTabLayout?

    }

    override fun initListener() {

    }


    override fun initData() {

    }

}

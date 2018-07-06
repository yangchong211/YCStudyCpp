package cn.ycbjie.ycaudioplayer.kotlin.view.fragment

import android.view.View
import cn.ycbjie.ycaudioplayer.R
import cn.ycbjie.ycaudioplayer.base.view.BaseLazyFragment

class AndroidProjectFragment : BaseLazyFragment(){

    override fun onLazyLoad() {

    }

    override fun getContentView(): Int {
        return R.layout.fragment_play_music
    }

    override fun initView(view: View) {
    }

    override fun initListener() {
    }

    override fun initData() {
    }

}
package cn.ycbjie.ycaudioplayer.kotlin.view.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView


import cn.ycbjie.ycaudioplayer.R
import cn.ycbjie.ycaudioplayer.kotlin.model.bean.BannerBean
import cn.ycbjie.ycaudioplayer.utils.app.ImageUtil
import com.yc.cn.ycbannerlib.banner.adapter.AbsStaticPagerAdapter


class BannerPagerAdapter(private val ctx: Activity?, private val list: MutableList<BannerBean>) : AbsStaticPagerAdapter() {

    override fun getView(container: ViewGroup, position: Int): View {
        val imageView = ImageView(ctx)
        imageView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        //加载图片
        ImageUtil.loadImgByPicasso(ctx, list[position].imagePath, R.drawable.bg_small_autumn_tree_min, imageView)
        return imageView
    }

    override fun getCount(): Int {
        return list.size
    }

}
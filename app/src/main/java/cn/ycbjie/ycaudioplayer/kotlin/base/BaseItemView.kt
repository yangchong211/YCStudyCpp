package cn.ycbjie.ycaudioplayer.kotlin.base

import android.app.Activity
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter


abstract class BaseItemView(private val activity: Activity?, @param:LayoutRes private val id: Int)
    : RecyclerArrayAdapter.ItemView {

    override fun onCreateView(parent: ViewGroup): View {
        return LayoutInflater.from(activity).inflate(id, parent, false)
    }

    override fun onBindView(headerView: View) {
        setBindView(headerView)
    }

    abstract fun setBindView(headerView: View)

}

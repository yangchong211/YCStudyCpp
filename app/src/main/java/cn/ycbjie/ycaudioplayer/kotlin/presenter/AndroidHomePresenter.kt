package cn.ycbjie.ycaudioplayer.kotlin.presenter

import cn.ycbjie.ycaudioplayer.kotlin.contract.AndroidHomeContract
import cn.ycbjie.ycaudioplayer.kotlin.model.helper.AndroidHelper
import com.mg.axechen.wanandroid.javabean.HomeListBean
import rx.Subscriber
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription


class AndroidHomePresenter : AndroidHomeContract.Presenter {

    lateinit var mView: AndroidHomeContract.View
    private lateinit var mSubscriptions: CompositeSubscription

    constructor(androidView: AndroidHomeContract.View){
        this.mView = androidView
        mSubscriptions = CompositeSubscription()
    }


    override fun subscribe() {

    }

    override fun unSubscribe() {

    }


    override fun getHomeList(page: Int) {
        AndroidHelper.instance().getHomeList(page)
                .subscribeOn(Schedulers.io())
                .subscribe {
                    object : Subscriber<HomeListBean>() {
                        override fun onCompleted() {

                        }

                        override fun onError(e: Throwable) {

                        }

                        override fun onNext(homeListBean: HomeListBean) {
                            if(homeListBean.datas!=null && homeListBean.datas!!.size>0){
                                mView.setDataView(homeListBean.datas!!)
                            }else{

                            }
                        }
                    }
                }
    }

}

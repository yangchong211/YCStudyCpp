package cn.ycbjie.ycaudioplayer.kotlin.presenter

import cn.ycbjie.ycaudioplayer.kotlin.contract.AndroidCollectContract
import io.reactivex.disposables.CompositeDisposable
import network.schedules.BaseSchedulerProvider
import network.schedules.SchedulerProvider

class AndroidCollectPresenter : AndroidCollectContract.Presenter {

    private var mView: AndroidCollectContract.View
    private var scheduler: BaseSchedulerProvider? = null


    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    constructor(androidView: AndroidCollectContract.View){
        this.mView = androidView
        scheduler = SchedulerProvider.getInstatnce()
    }

    override fun subscribe() {

    }

    override fun unSubscribe() {
        compositeDisposable.dispose()
    }

    override fun collectInArticle(articleId: Int) {

    }

    override fun unCollectArticle(articleId: Int) {

    }

}

package cn.ycbjie.ycaudioplayer.kotlin.presenter

import cn.ycbjie.ycaudioplayer.kotlin.contract.NavWebsiteContract
import cn.ycbjie.ycaudioplayer.kotlin.model.helper.AndroidHelper
import cn.ycbjie.ycaudioplayer.kotlin.network.ResponseTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import network.schedules.BaseSchedulerProvider
import network.schedules.SchedulerProvider

class NavWebsitePresenter : NavWebsiteContract.Presenter {


    private var mView: NavWebsiteContract.View
    private var scheduler: BaseSchedulerProvider? = null

    constructor(androidView: NavWebsiteContract.View, scheduler: SchedulerProvider){
        this.mView = androidView
        this.scheduler = scheduler
    }

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun subscribe() {

    }

    override fun unSubscribe() {

    }

    override fun getWebsiteNavi() {
        val instance = AndroidHelper.instance()
        var disapose: Disposable = instance.getNaviJson().compose(scheduler?.applySchedulers())
                .compose(ResponseTransformer.handleResult())
                .subscribe(
                        { t ->
                            mView?.getNaviWebSiteSuccess(t)
                        }
                        ,
                        { t: Throwable ->
                            mView?.getNaiWebSiteFail(t.message!!)
                        })
        compositeDisposable.add(disapose)
    }


}

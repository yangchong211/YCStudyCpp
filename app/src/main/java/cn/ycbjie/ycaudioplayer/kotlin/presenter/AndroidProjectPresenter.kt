package cn.ycbjie.ycaudioplayer.kotlin.presenter

import cn.ycbjie.ycaudioplayer.kotlin.contract.AndroidProjectContract
import cn.ycbjie.ycaudioplayer.kotlin.model.helper.AndroidHelper
import cn.ycbjie.ycaudioplayer.kotlin.network.ResponseTransformer
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.NetworkUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import network.schedules.BaseSchedulerProvider
import network.schedules.SchedulerProvider


class AndroidProjectPresenter : AndroidProjectContract.Presenter {

    private var mView: AndroidProjectContract.View
    private var page: Int = 0
    private var scheduler: BaseSchedulerProvider? = null

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }


    constructor(androidView: AndroidProjectContract.View){
        this.mView = androidView
        scheduler = SchedulerProvider.getInstatnce()
    }


    override fun subscribe() {

    }

    override fun unSubscribe() {
        compositeDisposable.dispose()
    }

    override fun getProjectTree() {
        val instance = AndroidHelper.instance()
        val disposable: Disposable = instance.getProjectTree()
                //网络请求在子线程，所以是在io线程，避免阻塞线程
                /*.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())*/
                .compose(ResponseTransformer.handleResult())
                .compose(scheduler?.applySchedulers())
                .subscribe ({ bean ->
                    mView.setProjectTreeSuccess(bean)
                    LogUtils.e("getProjectTree-----"+"成功")
                }, { t ->
                    LogUtils.e("getProjectTree-----"+"onError"+t.localizedMessage)
                    if(NetworkUtils.isConnected()){

                    }else{

                    }
                })
        compositeDisposable.add(disposable)
    }

    override fun getProjectTreeList(id: Int, isRefresh: Boolean) {
        if (isRefresh) {
            page = 0
        }
        val instance = AndroidHelper.instance()
        val disposable: Disposable = instance.getProjectListByCid(page,id)
                //网络请求在子线程，所以是在io线程，避免阻塞线程
                //.compose(ResponseTransformer.handleResult())
                //.compose(scheduler?.applySchedulers())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ bean ->
                    mView.setProjectListByCidSuccess(bean, isRefresh)
                    page++
                    LogUtils.e("getProjectTreeList-----"+"成功"+ (bean.data?.size))
                }, { t ->
                    LogUtils.e("getProjectTreeList-----"+"onError"+t.localizedMessage)
                    if(NetworkUtils.isConnected()){
                        mView.setProjectListByCidError()
                    }else{
                        mView.setProjectListByCidNetError()
                    }
                })
        compositeDisposable.add(disposable)
    }

}

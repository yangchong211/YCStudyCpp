package cn.ycbjie.ycaudioplayer.kotlin.presenter


import android.util.Log
import cn.ycbjie.ycaudioplayer.R.id.recyclerView
import cn.ycbjie.ycaudioplayer.kotlin.contract.AndroidHomeContract
import cn.ycbjie.ycaudioplayer.kotlin.model.helper.AndroidHelper
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.NetworkUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class AndroidHomePresenter : AndroidHomeContract.Presenter {

    var mView: AndroidHomeContract.View

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    constructor(androidView: AndroidHomeContract.View){
        this.mView = androidView
    }


    override fun subscribe() {

    }

    override fun unSubscribe() {

    }


    override fun getHomeList(page: Int) {
        val instance = AndroidHelper.instance()
        val disposable: Disposable = instance.getHomeList(page)
                //网络请求在子线程，所以是在io线程，避免阻塞线程
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ homeListBean ->
                    LogUtils.e("getHomeList-----"+"onNext")
                    mView.setDataView(homeListBean)
                }, { t ->
                    LogUtils.e("getHomeList-----"+"onError"+t.localizedMessage)
                    if(NetworkUtils.isConnected()){
                        mView.setDataErrorView()
                    }else{
                        mView.setNetWorkErrorView()
                    }
                }
            )
        compositeDisposable.add(disposable)
    }

}

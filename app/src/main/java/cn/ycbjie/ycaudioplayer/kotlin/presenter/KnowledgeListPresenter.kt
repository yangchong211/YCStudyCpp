package cn.ycbjie.ycaudioplayer.kotlin.presenter

import cn.ycbjie.ycaudioplayer.kotlin.contract.KnowledgeListContract
import cn.ycbjie.ycaudioplayer.kotlin.model.helper.AndroidHelper
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.NetworkUtils
import com.tencent.bugly.proguard.t
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class KnowledgeListPresenter : KnowledgeListContract.Presenter {

    var mView: KnowledgeListContract.View

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }


    constructor(androidView: KnowledgeListContract.View){
        this.mView = androidView
    }



    override fun subscribe() {

    }

    override fun unSubscribe() {

    }

    override fun getKnowledgeTree() {
        val instance = AndroidHelper.instance()
        val disposable: Disposable = instance.getKnowledgeTree()
                //网络请求在子线程，所以是在io线程，避免阻塞线程
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ bean ->
                    LogUtils.e("getHomeList-----"+"成功")
                    mView.getTreeSuccess(bean)
                }, { t ->
                    LogUtils.e("getHomeList-----"+"onError"+t.localizedMessage)
                    if(NetworkUtils.isConnected()){

                    }else{

                    }
                }
                )
        compositeDisposable.add(disposable)
    }

}

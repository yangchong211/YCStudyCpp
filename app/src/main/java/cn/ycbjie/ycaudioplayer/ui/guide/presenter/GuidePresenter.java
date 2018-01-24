package cn.ycbjie.ycaudioplayer.ui.guide.presenter;

import android.app.Activity;

import cn.ycbjie.ycaudioplayer.ui.guide.contract.GuideContract;
import rx.subscriptions.CompositeSubscription;



public class GuidePresenter implements GuideContract.Presenter {

    private GuideContract.View mView;
    private CompositeSubscription mSubscriptions;
    private Activity activity;

    public GuidePresenter(GuideContract.View androidView) {
        this.mView = androidView;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void subscribe() {

    }


    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
        if(activity!=null){
            activity = null;
        }
    }



}

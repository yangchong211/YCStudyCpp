package cn.ycbjie.ycaudioplayer.ui.guide.presenter;

import android.app.Activity;

import cn.ycbjie.ycaudioplayer.ui.guide.contract.GuideContract;



public class GuidePresenter implements GuideContract.Presenter {

    private GuideContract.View mView;
    private Activity activity;

    public GuidePresenter(GuideContract.View androidView) {
        this.mView = androidView;
    }

    @Override
    public void subscribe() {

    }


    @Override
    public void unSubscribe() {
        if(activity!=null){
            activity = null;
        }
    }



}

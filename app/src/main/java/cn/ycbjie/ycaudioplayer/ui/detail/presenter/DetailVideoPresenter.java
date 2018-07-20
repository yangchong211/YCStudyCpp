package cn.ycbjie.ycaudioplayer.ui.detail.presenter;

import android.app.Activity;


import java.util.ArrayList;
import java.util.List;

import cn.ycbjie.ycaudioplayer.ui.detail.contract.DetailVideoContract;


public class DetailVideoPresenter implements DetailVideoContract.Presenter {

    private DetailVideoContract.View mView;
    private Activity activity;

    public DetailVideoPresenter(DetailVideoContract.View androidView) {
        this.mView = androidView;
        this.activity = (Activity) androidView;
    }

    @Override
    public void subscribe() {

    }


    @Override
    public void unSubscribe() {

    }


    @Override
    public void getData() {
        List<String> data = new ArrayList<>();
        for(int a=0 ; a<3 ; a++){
            data.add("假数据"+a);
        }
        mView.setDataView(data);
    }
}

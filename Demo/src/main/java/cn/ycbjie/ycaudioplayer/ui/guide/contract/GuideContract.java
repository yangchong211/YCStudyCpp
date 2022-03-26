package cn.ycbjie.ycaudioplayer.ui.guide.contract;


import cn.ycbjie.ycaudioplayer.base.mvp.BasePresenter;
import cn.ycbjie.ycaudioplayer.base.mvp.BaseView;

public interface GuideContract {

    //View(activity/fragment)继承，需要实现的方法
    interface View extends BaseView {

    }

    //Presenter控制器
    interface Presenter extends BasePresenter {

    }


}

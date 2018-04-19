package cn.ycbjie.ycaudioplayer.ui.detail.contract;



import java.util.List;

import cn.ycbjie.ycaudioplayer.base.BasePresenter;
import cn.ycbjie.ycaudioplayer.base.BaseView;

public interface DetailVideoContract {

    interface View extends BaseView {
        void setDataView(List<String> data);
    }

    interface Presenter extends BasePresenter {
        void getData();
    }


}

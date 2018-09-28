package cn.ycbjie.ycaudioplayer.ui.me.view.activity;


import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.base.view.BaseActivity;
import cn.ycbjie.ycaudioplayer.ui.me.model.AboutDataListener;
import cn.ycbjie.ycaudioplayer.ui.me.model.MeAboutModel;
import cn.ycbjie.ycaudioplayer.ui.music.model.OnlineMusicList;

public class MeAboutActivity extends BaseActivity {

    @Override
    public int getContentView() {
        return R.layout.activity_me_about;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        MeAboutModel mvpModel = new MeAboutModel();
        mvpModel.getAboutData(new AboutDataListener<OnlineMusicList>() {
            @Override
            public void onSuccess(OnlineMusicList onlineMusicList) {

            }

            @Override
            public void onError() {

            }
        });
    }
}

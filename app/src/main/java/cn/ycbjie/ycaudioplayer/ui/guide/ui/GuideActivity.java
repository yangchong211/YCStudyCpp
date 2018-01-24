package cn.ycbjie.ycaudioplayer.ui.guide.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ns.yc.yccountdownviewlib.CountDownView;

import java.util.Calendar;

import butterknife.Bind;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.base.BaseAppHelper;
import cn.ycbjie.ycaudioplayer.base.BaseActivity;
import cn.ycbjie.ycaudioplayer.service.PlayService;
import cn.ycbjie.ycaudioplayer.ui.MainActivity;
import cn.ycbjie.ycaudioplayer.ui.guide.contract.GuideContract;
import cn.ycbjie.ycaudioplayer.ui.guide.presenter.GuidePresenter;
import cn.ycbjie.ycaudioplayer.util.LogUtils;

/**
 * Created by yc on 2018/1/19.
 */

public class GuideActivity extends BaseActivity implements GuideContract.View {

    @Bind(R.id.iv_splash)
    ImageView ivSplash;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.cdv_time)
    CountDownView cdvTime;

    protected Handler mHandler = new Handler(Looper.getMainLooper());
    private GuideContract.Presenter presenter = new GuidePresenter(this);
    private PlayServiceConnection mPlayServiceConnection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        if (mPlayServiceConnection != null) {
            unbindService(mPlayServiceConnection);
        }
        super.onDestroy();
        presenter.unSubscribe();
        if(mHandler!=null){
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }

    /**
     * 屏蔽返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        setTimeText();
        startLoading();
        startCheckService();
    }

    @Override
    public void initListener() {
        cdvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdvTime.stop();
                finish();
            }
        });
    }

    @Override
    public void initData() {

    }


    private void setTimeText() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        tvTime.setText(getString(R.string.guideTime, year));
    }

    /**
     * 开始启动倒计时
     * 关于倒计时自定义控件：https://github.com/yangchong211/YCCountDownView
     * 欢迎star
     */
    private void startLoading() {
        cdvTime.setTime(5);
        cdvTime.start();
        cdvTime.setOnLoadingFinishListener(new CountDownView.OnLoadingFinishListener() {
            @Override
            public void finish() {
                //跳转主页面
                toMainActivity();
            }
        });
    }

    /**
     * 检测服务
     */
    private void startCheckService() {
        if (BaseAppHelper.get().getPlayService() == null) {
            startService();
            startShowSplash();
            startUpdateSplash();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //绑定服务
                    bindService();
                }
            }, 1000);
        }
    }

    /**
     * 开启服务
     */
    private void startService() {
        Intent intent = new Intent(this, PlayService.class);
        startService(intent);
    }

    /**
     * 绑定服务
     * 注意对于绑定服务一定要解绑
     */
    private void bindService() {
        Intent intent = new Intent();
        intent.setClass(this, PlayService.class);
        mPlayServiceConnection = new PlayServiceConnection();
        bindService(intent, mPlayServiceConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * 展示图片，从网络获取
     */
    private void startShowSplash() {
        ivSplash.setImageResource(R.drawable.bg_page_background);
    }

    /**
     * 从网络获取图片
     */
    private void startUpdateSplash() {

    }

    /**
     * 直接跳转主页面
     */
    private void toMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
        finish();
    }

    private class PlayServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtils.e("onServiceConnected"+name);
            final PlayService playService = ((PlayService.PlayBinder) service).getService();
            BaseAppHelper.get().setPlayService(playService);
            scanMusic(playService);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtils.e("onServiceDisconnected"+name);
        }
    }

    /**
     * 可以在服务中扫描本地音乐
     * @param playService           service
     */
    private void scanMusic(PlayService playService) {
        playService.updateMusicList(null);
    }


}

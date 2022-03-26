package cn.ycbjie.ycaudioplayer.ui.advert;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ns.yc.yccountdownviewlib.CountDownView;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.base.view.BaseActivity;
import cn.ycbjie.ycaudioplayer.constant.Constant;
import cn.ycbjie.ycaudioplayer.service.SplashDownLoadService;
import cn.ycbjie.ycaudioplayer.ui.advert.model.bean.AdvertCommon;
import cn.ycbjie.ycaudioplayer.ui.main.ui.activity.MainActivity;
import cn.ycbjie.ycaudioplayer.utils.app.ImageUtil;
import cn.ycbjie.ycaudioplayer.utils.app.SerializableUtils;
import cn.ycbjie.ycaudioplayer.utils.logger.AppLogUtils;
import cn.ycbjie.ycstatusbarlib.bar.StateAppBar;


public class AdvertActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.iv_splash)
    ImageView ivSplash;
    @BindView(R.id.cdv_time)
    CountDownView cdvTime;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;

    private AdvertCommon.Splash mSplashAd;
    private boolean isClickAd = false;

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        StateAppBar.translucentStatusBar(this, true);
        cdvTime.setVisibility(View.VISIBLE);
        llBottom.setVisibility(View.GONE);
        startLoading();
        getLocalSplash();
    }

    @Override
    public void initListener() {
        cdvTime.setOnClickListener(this);
        ivSplash.setOnClickListener(this);


    }


    @Override
    public void initData() {
        startImageDownLoad();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cdv_time:
                cdvTime.stop();
                finish();
                break;
            case R.id.iv_splash:
                isClickAd = true;
                cdvTime.stop();
                startActivity(AdvertDetailActivity.class);
                finish();
                break;
            default:
                break;
        }
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
                if (!isClickAd) {
                    //跳转主页面
                    toMainActivity();
                }
            }
        });
    }

    /**
     * 开始请求网络图片并且下载到本地
     * 可以在service操作
     */
    private void startImageDownLoad() {
        Intent intent = new Intent(this, SplashDownLoadService.class);
        intent.putExtra(Constant.EXTRA_DOWNLOAD, Constant.DOWNLOAD_SPLASH_AD);
        startService(intent);
    }

    /**
     * 先判断本地是否已经缓存了网络图片
     * 如果已经缓存了，
     */
    private void getLocalSplash() {
        mSplashAd = getLocalAdSplash();
        if (mSplashAd != null) {
            ImageUtil.loadImgByPicasso(this, mSplashAd.savePath, R.drawable.bg_page_background, ivSplash);
        }else {
            ivSplash.setImageResource(R.drawable.bg_page_background);
        }
    }

    /**
     * 从本地获取缓存的内容
     *
     * @return Splash
     */
    private AdvertCommon.Splash getLocalAdSplash() {
        AdvertCommon.Splash splash = null;
        try {
            File splashFile = SerializableUtils.getSerializableFile(
                    Constant.SPLASH_PATH, Constant.SPLASH_FILE_NAME);
            splash = (AdvertCommon.Splash) SerializableUtils.readObject(splashFile);
            AppLogUtils.e("AdvertActivity" + "存储路径" + Constant.SPLASH_PATH);
        } catch (IOException e) {
            e.printStackTrace();
            AppLogUtils.e("AdvertActivity" + "SplashActivity 获取本地序列化闪屏失败" + e.getMessage());
        }
        return splash;
    }


    private void toMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
        finish();
    }


}

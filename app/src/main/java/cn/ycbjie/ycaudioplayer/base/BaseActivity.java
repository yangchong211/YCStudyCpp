package cn.ycbjie.ycaudioplayer.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;

import butterknife.ButterKnife;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.service.PlayService;
import cn.ycbjie.ycaudioplayer.ui.guide.ui.GuideActivity;
import cn.ycbjie.ycaudioplayer.util.bar.AppBar;
import cn.ycbjie.ycstatusbarlib.bar.YCAppBar;

/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2017/5/18
 * 描    述：所有Activity的父类
 * 修订历史：
 * ================================================
 */
public abstract class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (BaseConfig.INSTANCE.isNight()) {
            setTheme(getDarkTheme());
        }
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        ButterKnife.bind(this);
        //避免切换横竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //将当前Activity添加到容器
        AppManager.getAppManager().addActivity(this);
        initView();
        initListener();
        initData();
        if(!NetworkUtils.isConnected()){
            ToastUtils.showShort("请检查网络是否连接");
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //测试内存泄漏，正式一定要隐藏
        initLeakCanary();
        //将当前Activity移除到容器
        AppManager.getAppManager().removeActivity(this);
        //AppManager.getAppManager().finishActivity(this);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    /** 返回一个用于显示界面的布局id */
    public abstract int getContentView();

    /** 初始化View的代码写在这个方法中 */
    public abstract void initView();

    /** 初始化监听器的代码写在这个方法中 */
    public abstract void initListener();

    /** 初始数据的代码写在这个方法中，用于从服务器获取数据 */
    public abstract void initData();

    @StyleRes
    protected int getDarkTheme() {
        return R.style.AppThemeDark;
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 用来检测所有Activity的内存泄漏
     */
    private void initLeakCanary() {
        /*RefWatcher refWatcher = BaseApplication.getRefWatcher(this);
        refWatcher.watch(this);*/
    }

    /**
     * 获取到播放音乐的服务
     * @return              PlayService对象
     */
    public PlayService getPlayService() {
        PlayService playService = BaseAppHelper.get().getPlayService();
        if (playService == null) {
            throw new NullPointerException("play service is null");
        }
        return playService;
    }

    protected boolean checkServiceAlive() {
        if (BaseAppHelper.get().getPlayService() == null) {
            startActivity(new Intent(this, GuideActivity.class));
            AppManager.getAppManager().finishAllActivity();
            return false;
        }
        return true;
    }


}

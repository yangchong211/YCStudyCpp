package cn.ycbjie.ycaudioplayer.base.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.Utils;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection;
import com.tencent.bugly.crashreport.CrashReport;

import java.net.Proxy;

import cn.ycbjie.ycaudioplayer.base.BaseAppHelper;
import cn.ycbjie.ycaudioplayer.base.callback.BaseLifecycleCallback;
import cn.ycbjie.ycaudioplayer.inter.callback.LogCallback;
import cn.ycbjie.ycaudioplayer.service.InitializeService;
import cn.ycbjie.ycaudioplayer.utils.AppToolUtils;
import cn.ycbjie.ycthreadpoollib.PoolThread;

/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2016/8/18
 * 描    述：BaseApplication
 * 修订历史：
 * ================================================
 */
public class BaseApplication extends Application {


    private static BaseApplication instance;
    private PoolThread executor;

    public static synchronized BaseApplication getInstance() {
        if (null == instance) {
            instance = new BaseApplication();
        }
        return instance;
    }


    public BaseApplication(){}


    /**
     * 这个最先执行
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    /**
     * 程序启动的时候执行
     */
    @Override
    public void onCreate() {
        Log.d("Application", "onCreate");
        super.onCreate();
        instance = this;
        Utils.init(this);
        BaseLifecycleCallback.getInstance().init(this);
        BaseAppHelper.get().init(this);
        initBugly();
        initDownLoadLib();
        initThreadPool();
        InitializeService.start(this);
    }


    /**
     * 程序终止的时候执行
     */
    @Override
    public void onTerminate() {
        Log.d("Application", "onTerminate");
        super.onTerminate();
    }


    /**
     * 低内存的时候执行
     */
    @Override
    public void onLowMemory() {
        Log.d("Application", "onLowMemory");
        super.onLowMemory();
    }


    /**
     * HOME键退出应用程序
     * 程序在内存清理的时候执行
     */
    @Override
    public void onTrimMemory(int level) {
        Log.d("Application", "onTrimMemory");
        super.onTrimMemory(level);
    }


    /**
     * onConfigurationChanged
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d("Application", "onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }

    /**
     * 初始化腾讯bug管理平台
     */
    private void initBugly() {
        /* Bugly SDK初始化
        * 参数1：上下文对象
        * 参数2：APPID，平台注册时得到,注意替换成你的appId
        * 参数3：是否开启调试模式，调试模式下会输出'CrashReport'tag的日志
        * 注意：如果您之前使用过Bugly SDK，请将以下这句注释掉。
        */
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
        // 设置版本号
        strategy.setAppVersion(AppUtils.getAppVersionName());
        // 设置版本名称
        String appPackageName = AppUtils.getAppPackageName();
        strategy.setAppPackageName(appPackageName);
        // 获取当前进程名
        String processName = AppToolUtils.getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        strategy.setUploadProcess(processName == null || processName.equals(appPackageName));
        //Bugly会在启动20s后联网同步数据
        strategy.setAppReportDelay(20000);
        //正式版
        CrashReport.initCrashReport(getApplicationContext(), "521262bdd7", false, strategy);
    }


    /**
     * 初始化下载库
     */
    private void initDownLoadLib() {
        FileDownloader.setupOnApplicationOnCreate(this)
                .connectionCreator(new FileDownloadUrlConnection
                        .Creator(new FileDownloadUrlConnection.Configuration()
                        .connectTimeout(15_000)
                        .readTimeout(15_000)
                        .proxy(Proxy.NO_PROXY)
                ))
                .commit();
    }


    /**
     * 初始化线程池管理器
     */
    private void initThreadPool() {
        // 创建一个独立的实例进行使用
        executor = PoolThread.ThreadBuilder
                .createFixed(4)
                .setPriority(Thread.MAX_PRIORITY)
                .setCallback(new LogCallback())
                .build();
    }

    /**
     * 获取线程池管理器对象，统一的管理器维护所有的线程池
     * @return                      executor对象
     */
    public PoolThread getExecutor(){
        if(executor ==null){
            executor = PoolThread.ThreadBuilder
                    .createFixed(4)
                    .setPriority(Thread.MAX_PRIORITY)
                    .setCallback(new LogCallback())
                    .build();
        }
        return executor;
    }


}



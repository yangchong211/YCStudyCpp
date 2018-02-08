package cn.ycbjie.ycaudioplayer.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.Utils;
import com.tencent.bugly.crashreport.CrashReport;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import cn.ycbjie.ycaudioplayer.api.http.HttpInterceptor;
import cn.ycbjie.ycaudioplayer.util.other.BuglyUtils;
import cn.ycbjie.ycaudioplayer.util.other.LogUtils;
import okhttp3.OkHttpClient;

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
        initUtils();
        //初始化配置信息
        BaseConfig.INSTANCE.initConfig();
        BaseAppHelper.get().init(this);
        LogUtils.logDebug = true;
        initOkHttpUtils();
        initBugly();
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
     * 初始化utils工具类
     */
    private void initUtils() {
        Utils.init(this);
    }


    private void initOkHttpUtils() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(new HttpInterceptor())
                .addInterceptor(new LoggerInterceptor("TAG"))
                .build();
        OkHttpUtils.initClient(okHttpClient);
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
        String processName = BuglyUtils.getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        strategy.setUploadProcess(processName == null || processName.equals(appPackageName));
        //Bugly会在启动20s后联网同步数据
        strategy.setAppReportDelay(20000);
        //正式版
        CrashReport.initCrashReport(getApplicationContext(), "521262bdd7", false, strategy);
    }


}



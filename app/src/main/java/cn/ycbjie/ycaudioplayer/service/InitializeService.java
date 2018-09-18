package cn.ycbjie.ycaudioplayer.service;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection;
import com.tencent.bugly.crashreport.CrashReport;

import java.net.Proxy;

import cn.ycbjie.ycaudioplayer.BuildConfig;
import cn.ycbjie.ycaudioplayer.base.app.BaseApplication;
import cn.ycbjie.ycaudioplayer.constant.BaseConfig;
import cn.ycbjie.ycaudioplayer.utils.app.AppToolUtils;
import cn.ycbjie.ycaudioplayer.utils.file.FileSaveUtils;
import cn.ycbjie.ycaudioplayer.utils.logger.AppLogUtils;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2016/8/18
 *     desc  : 初始化工作，子线程
 *     revise:
 * </pre>
 */
@SuppressLint("Registered")
public class InitializeService extends IntentService {

    private static final String ACTION_INIT = "initApplication";

    public static void start(Context context) {
        Intent intent = new Intent(context, InitializeService.class);
        intent.setAction(ACTION_INIT);
        context.startService(intent);
    }


    public InitializeService(){
        super("InitializeService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INIT.equals(action)) {
                initApplication();
            }
        }
    }

    private void initApplication() {
        //初始化配置信息
        BaseConfig.INSTANCE.initConfig();
        initUtils();
        initBugly();
        initDownLoadLib();
    }

    /**
     * 初始化utils工具类
     */
    private void initUtils() {
        AppLogUtils.Config config = AppLogUtils.getConfig();
        if(BuildConfig.IS_DEBUG){
            //边框开关，设置打开
            config.setBorderSwitch(false);
            //logcat 是否打印，设置打印
            config.setConsoleSwitch(true);
            //设置打印日志总开关，线上时关闭
            config.setLogSwitch(true);
            //设置可以写入文件。默认是false
            config.setLog2FileSwitch(true);
            //String property = System.getProperty("file.separator");
            //String cacheDir = this.getCacheDir() + property + "yc" + property;
            //设置log日志的文件路径
            String logger = FileSaveUtils.getLocalRootSavePathDir("logger");
            ///storage/emulated/0/yc/logger/
            Log.e("日志存储地址" ,logger);
            //设置存储的路径
            config.setDir(logger);
        }else {
            config.setLogSwitch(false);
        }

        //这里的BuildConfig是由gradle动态生成的
        switch (BuildConfig.URL_CONFIG){
            case 0:
                Log.e("BuildConfig","测试");
                break;
            case 1:
                Log.e("BuildConfig","仿真");
                break;
            case 2:
                Log.e("BuildConfig","线上");
                break;
            default:
                break;
        }
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
        FileDownloader.setupOnApplicationOnCreate(BaseApplication.getInstance())
                .connectionCreator(new FileDownloadUrlConnection
                        .Creator(new FileDownloadUrlConnection.Configuration()
                        .connectTimeout(15_000)
                        .readTimeout(15_000)
                        .proxy(Proxy.NO_PROXY)
                ))
                .commit();
    }



}

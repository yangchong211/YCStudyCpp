package cn.ycbjie.ycaudioplayer.service;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;

import cn.ycbjie.ycaudioplayer.BuildConfig;
import cn.ycbjie.ycaudioplayer.constant.BaseConfig;
import cn.ycbjie.ycaudioplayer.utils.file.FileSaveUtils;
import cn.ycbjie.ycaudioplayer.utils.logger.AppLogUtils;

/**
 * <pre>
 *     @author yangchong
 *     blog  : www.pedaily.cn
 *     time  : 2018/01/22
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


}

package cn.ycbjie.ycaudioplayer.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import cn.ycbjie.ycaudioplayer.constant.Constant;
import cn.ycbjie.ycaudioplayer.ui.advert.model.api.AdvertModel;
import cn.ycbjie.ycaudioplayer.ui.advert.model.bean.AdvertCommon;
import cn.ycbjie.ycaudioplayer.ui.advert.utils.DownLoadUtils;
import cn.ycbjie.ycaudioplayer.utils.SerializableUtils;
import rx.Observer;
import rx.schedulers.Schedulers;

/**
 * Created by yc on 2018/2/8.
 */

public class SplashDownLoadService extends IntentService {

    private AdvertCommon.Splash mScreen;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     * @param name Used to name the worker thread, important only for debugging.
     */
    public SplashDownLoadService(String name) {
        super("SplashDownLoadService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String action = intent.getStringExtra(Constant.EXTRA_DOWNLOAD);
            if (action.equals(Constant.DOWNLOAD_SPLASH_AD)) {
                loadSplashNetDate();
            }
        }
    }

    private void loadSplashNetDate() {
        AdvertModel model = AdvertModel.getInstance();
        model.getSplashImage(1)
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<AdvertCommon>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.e("SplashDownLoadService"+ "onCompleted" );
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("SplashDownLoadService"+ e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(AdvertCommon advertCommon) {
                        if(advertCommon!=null){
                            mScreen = advertCommon.splash;
                            AdvertCommon.Splash splashLocal = getSplashLocal();
                            if (mScreen != null) {
                                if (splashLocal == null) {
                                    LogUtils.e("SplashDownLoadService"+ "splashLocal 为空导致下载");
                                    startDownLoadSplash(Constant.SPLASH_PATH, mScreen.burl);
                                } else if (isNeedDownLoad(splashLocal.savePath, mScreen.burl)) {
                                    LogUtils.e("SplashDownLoadService"+ "isNeedDownLoad 导致下载");
                                    startDownLoadSplash(Constant.SPLASH_PATH, mScreen.burl);
                                }
                            } else {
                                if (splashLocal != null) {
                                    File splashFile = null;
                                    try {
                                        splashFile = SerializableUtils.getSerializableFile(Constant.SPLASH_PATH, Constant.SPLASH_FILE_NAME);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    if (splashFile!=null) {
                                        if(splashFile.exists()){
                                            //noinspection ResultOfMethodCallIgnored
                                            splashFile.delete();
                                        }
                                        Log.d("SplashDemo","mScreen为空删除本地文件");
                                    }
                                }
                            }
                        }
                    }
                });
    }



    /**
     * @param path 本地存储的图片绝对路径
     * @param url  网络获取url
     * @return 比较储存的 图片名称的哈希值与 网络获取的哈希值是否相同
     */
    private boolean isNeedDownLoad(String path, String url) {
        if (TextUtils.isEmpty(path)) {
            Log.d("SplashDemo","本地url " + TextUtils.isEmpty(path));
            Log.d("SplashDemo","本地url " + TextUtils.isEmpty(url));
            return true;
        }
        File file = new File(path);
        if (!file.exists()) {
            Log.d("SplashDemo","本地file " + file.exists());
            return true;
        }
        if (getImageName(path).hashCode() != getImageName(url).hashCode()) {
            Log.d("SplashDemo","path hashcode " + getImageName(path) + " " + getImageName(path).hashCode());
            Log.d("SplashDemo","url hashcode " + getImageName(url) + " " + getImageName(url).hashCode());
            return true;
        }
        return false;
    }


    private AdvertCommon.Splash getSplashLocal() {
        AdvertCommon.Splash splash = null;
        try {
            File splashFile = SerializableUtils.getSerializableFile(
                    Constant.SPLASH_PATH, Constant.SPLASH_FILE_NAME);
            splash = (AdvertCommon.Splash) SerializableUtils.readObject(splashFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return splash;
    }


    private String getImageName(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        String[] split = url.split("/");
        String nameWith = split[split.length - 1];
        String[] split1 = nameWith.split("\\.");
        return split1[0];
    }

    private void startDownLoadSplash(String splashPath, String burl) {
        DownLoadUtils.downLoad(splashPath, new DownLoadUtils.DownLoadInterFace() {
            @Override
            public void afterDownLoad(ArrayList<String> savePaths) {
                if (savePaths.size() == 1) {
                    Log.d("SplashDemo","闪屏页面下载完成" + savePaths);
                    if (mScreen != null) {
                        mScreen.savePath = savePaths.get(0);
                    }
                    SerializableUtils.writeObject(mScreen, Constant.SPLASH_PATH + "/" + Constant.SPLASH_FILE_NAME);
                } else {
                    Log.d("SplashDemo","闪屏页面下载失败" + savePaths);
                }
            }
        }, burl);
    }


}

package com.yc.wave;

import android.content.Context;

import com.danikula.videocache.HttpProxyCacheServer;

/**
 * 管理者
 */

public class Manager {

    private static Manager mInstance;
    //视频代理
    private HttpProxyCacheServer mProxy;

    public static synchronized Manager newInstance() {
        if (mInstance == null) {
            mInstance = new Manager();
        }
        return mInstance;
    }

    public HttpProxyCacheServer getProxy(Context context) {
        if (mProxy == null) {
            mProxy = newProxy(context);
        }
        return mProxy;
    }

    /**
     * 创建缓存代理服务
     */
    private HttpProxyCacheServer newProxy(Context context) {
        return new HttpProxyCacheServer(context.getApplicationContext());
    }

}

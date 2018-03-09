package cn.ycbjie.ycaudioplayer.util.other;

import android.content.Context;
import android.os.Handler;

import java.lang.ref.WeakReference;

/**
 * Created by yc on 2016/3/26.
 * handler工具类
 */
public class HandlerUtil extends Handler {

    private static HandlerUtil instance = null;
    private WeakReference<Context> mActivityReference;

    public static HandlerUtil getInstance(Context context) {
        if (instance == null) {
            instance = new HandlerUtil(context.getApplicationContext());
        }
        return instance;
    }

    private HandlerUtil(Context context) {
        mActivityReference = new WeakReference<>(context);
    }

}

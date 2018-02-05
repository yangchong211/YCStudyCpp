package cn.ycbjie.ycaudioplayer.util;

import android.content.Context;
import android.os.Handler;

import java.lang.ref.WeakReference;

public class HandlerUtils extends Handler {

    private static HandlerUtils instance = null;

    public static HandlerUtils getInstance(Context context) {
        if (instance == null) {
            instance = new HandlerUtils(context.getApplicationContext());
        }
        return instance;
    }

    private HandlerUtils(Context context) {
        WeakReference<Context> mActivityReference = new WeakReference<>(context);
    }
}

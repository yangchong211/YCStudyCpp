package cn.ycbjie.ycaudioplayer.util.other;

import android.app.Activity;
import android.content.Context;
import android.text.format.DateUtils;

import java.lang.ref.WeakReference;
import java.util.Locale;

/**
 * Created by yc on 2018/1/24.
 */

public class AppUtils {


    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height",
                "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    /**
     * 格式化时间
     */
    public static String formatTime(String pattern, long milli) {
        int m = (int) (milli / DateUtils.MINUTE_IN_MILLIS);
        int s = (int) ((milli / DateUtils.SECOND_IN_MILLIS) % 60);
        String mm = String.format(Locale.getDefault(), "%02d", m);
        String ss = String.format(Locale.getDefault(), "%02d", s);
        return pattern.replace("mm", mm).replace("ss", ss);
    }


    /**
     * 判断某Activity是否挂掉，主要是用于弹窗
     * @param activity
     * @return
     */
    public static boolean isActivityLiving(Activity activity) {
        if (activity == null) {
            android.util.Log.d("wisely", "activity == null");
            return false;
        }
        if (activity.isFinishing()) {
            android.util.Log.d("wisely", "activity is finishing");
            return false;
        }
        String name = activity.getClass().getName();
        android.util.Log.d("wisely",name+"---");
        android.util.Log.d("wisely", "activity is living");
        return true;
    }


    /**
     * 判断某Activity是否挂掉，主要是用于弹窗
     */
    private static boolean isActivityLiving(WeakReference<Activity> weakReference) {
        if(weakReference != null){
            Activity activity = weakReference.get();
            if (activity == null) {
                return false;
            }
            if (activity.isFinishing()) {
                return false;
            }
            return true;
        }
        return false;
    }


}

package cn.ycbjie.ycaudioplayer.utils.app;


import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import cn.ycbjie.ycaudioplayer.base.app.BaseApplication;
import cn.ycbjie.ycstatusbarlib.StatusBarHeightUtils;

/**
 * <pre>
 *     @author yangchong
 *     blog  :
 *     time  : 2018/8/2
 *     desc  : 头条适配方案
 *     revise:
 *             参考博客：
 *             头条一种极低成本的Android屏幕适配方式 ：https://mp.weixin.qq.com/s/d9QCoBP6kV9VSWvVldVVwA
 *             今日头条适配方案：https://www.jianshu.com/p/55e0fca23b4f
 *             Android目前最稳定和高效的UI适配方案:https://www.jianshu.com/p/a4b8e4c5d9b0
 * </pre>
 */
public class DensityUtils {

    /**
     * android中的dp在渲染前会将dp转为px，计算公式：
     * px = density * dp;
     * density = dpi / 160;
     * px = dp * (dpi / 160);
     */

    private static float appDensity;
    private static float appScaledDensity;
    private static DisplayMetrics appDisplayMetrics;
    private static int barHeight;
    private final static String WIDTH = "width";
    private final static String HEIGHT = "height";


    public static void setDensity(@NonNull Application application) {
        //获取application的DisplayMetrics
        appDisplayMetrics = application.getResources().getDisplayMetrics();
        //获取状态栏高度
        barHeight = StatusBarHeightUtils.getStatusBarHeight(application);

        if (appDensity == 0) {
            //初始化的时候赋值
            appDensity = appDisplayMetrics.density;
            appScaledDensity = appDisplayMetrics.scaledDensity;

            //添加字体变化的监听
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    //字体改变后,将appScaledDensity重新赋值
                    if (newConfig != null && newConfig.fontScale > 0) {
                        appScaledDensity = BaseApplication.getInstance().getResources()
                                .getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }
    }

    /**
     * 此方法在BaseActivity中做初始化(如果不封装BaseActivity的话,直接用下面那个方法就好)
     * 在setContentView()之前设置
     * @param activity
     */
    public static void setDefault(Activity activity) {
        setAppOrientation(activity, WIDTH);
    }

    /**
     * 此方法用于在某一个Activity里面更改适配的方向
     * 在setContentView()之前设置
     * @param activity
     * @param orientation
     */
    public static void setOrientation(Activity activity, String orientation) {
        setAppOrientation(activity, orientation);
    }

    /**
     * targetDensity
     * targetScaledDensity
     * targetDensityDpi
     * 这三个参数是统一修改过后的值
     * orientation:方向值,传入width或height
     */
    private static void setAppOrientation(@Nullable Activity activity, String orientation) {
        float targetDensity;
        if (orientation.equals(HEIGHT)) {
            targetDensity = (appDisplayMetrics.heightPixels - barHeight) / 667f;//设计图的高度 单位:dp
        } else {
            targetDensity = appDisplayMetrics.widthPixels / 360f;//设计图的宽度 单位:dp
        }

        float targetScaledDensity = targetDensity * (appScaledDensity / appDensity);
        int targetDensityDpi = (int) (160 * targetDensity);

        /*
         *
         * 最后在这里将修改过后的值赋给系统参数
         * 只修改Activity的density值
         */
        DisplayMetrics activityDisplayMetrics ;
        if (activity != null) {
            activityDisplayMetrics = activity.getResources().getDisplayMetrics();
            activityDisplayMetrics.density = targetDensity;
            activityDisplayMetrics.scaledDensity = targetScaledDensity;
            activityDisplayMetrics.densityDpi = targetDensityDpi;
        }
    }


}

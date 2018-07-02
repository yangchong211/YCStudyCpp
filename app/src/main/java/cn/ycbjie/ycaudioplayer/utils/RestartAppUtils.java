package cn.ycbjie.ycaudioplayer.utils;

import android.content.Context;
import android.content.Intent;

import com.ns.yc.ycutilslib.activityManager.AppManager;

import cn.ycbjie.ycaudioplayer.service.KillSelfService;


/**
 * <pre>
 *     @author yangchong
 *     blog  :
 *     time  : 2017/7/2
 *     desc  : 此工具类用来重启APP，只是单纯的重启，不做任何处理。
 *     revise:
 * </pre>
 */
public class RestartAppUtils {


    /**
     * 重启整个APP
     * @param context                       上下文
     * @param Delayed                       延迟多少毫秒
     */

    private static void restartAPP(Context context, long Delayed){
        /*开启一个新的服务，用来重启本APP*/
        Intent intent = new Intent(context,KillSelfService.class);
        intent.putExtra("PackageName",context.getPackageName());
        intent.putExtra("Delayed",Delayed);
        context.startService(intent);
        AppManager.getAppManager().finishAllActivity();
        /*杀死整个进程**/
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 重启整个APP
     * @param context                       上下文
     */
    public static void restartAPP(Context context){
        restartAPP(context,2000);
    }


}

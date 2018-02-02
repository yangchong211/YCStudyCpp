package cn.ycbjie.ycaudioplayer.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;

import com.blankj.utilcode.util.ToastUtils;

import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.base.AppManager;
import cn.ycbjie.ycaudioplayer.base.BaseAppHelper;
import cn.ycbjie.ycaudioplayer.base.BaseConfig;
import cn.ycbjie.ycaudioplayer.service.PlayService;
import cn.ycbjie.ycaudioplayer.ui.me.MeAboutActivity;
import cn.ycbjie.ycaudioplayer.ui.me.MeSettingActivity;
import cn.ycbjie.ycaudioplayer.util.AppUtils;
import cn.ycbjie.ycaudioplayer.util.QuitTimer;

/**
 * Created by yc on 2018/1/20.
 * 这个是侧滑栏的条目点击事件
 */

class NavigationExecutor {


    static boolean onNavigationItemSelected(MenuItem item, Activity activity) {
        switch (item.getItemId()) {
            case R.id.action_home:

                return true;
            case R.id.action_night:
                setNightMode(activity);
                break;
            case R.id.action_timer:
                timerDialog(activity);
                return true;
            case R.id.action_exit:
                exit();
                return true;
            case R.id.action_setting:
                startActivity(activity, MeSettingActivity.class);
                return true;
            case R.id.action_about:
                startActivity(activity, MeAboutActivity.class);
                return true;
            default:
                break;
        }
        return false;
    }


    private static void startActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }


    /**
     * 设置夜间模式
     * @param activity              activity上下文
     */
    private static void setNightMode(Activity activity) {
        BaseConfig.INSTANCE.setNight(!BaseConfig.INSTANCE.isNight());
        activity.recreate();
    }


    /**
     * 弹出定时停止播放对话框
     * @param activity              activity上下文
     */
    private static void timerDialog(final Activity activity) {
        if(AppUtils.isActivityLiving(activity)){
            String[] stringArray = activity.getResources().getStringArray(R.array.timer_text);
            new AlertDialog.Builder(activity)
                    .setTitle("定时停止播放")
                    .setItems(stringArray, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int[] times = activity.getResources().getIntArray(R.array.timer_int);
                            startTimer(activity, times[which]);
                        }
                    })
                    .show();
        }
    }


    /**
     * 退出程序
     */
    private static void exit() {
        PlayService service = BaseAppHelper.get().getPlayService();
        if (service != null) {
            service.quit();
        }
        AppManager.getAppManager().AppExit(false);
    }


    /**
     * 开启倒计时器
     * @param activity              activity上下文
     * @param time                  time时间
     */
    private static void startTimer(Activity activity, int time) {
        QuitTimer.getInstance().start(time * 60 * 1000);
        if (time > 0) {
            ToastUtils.showShort(activity.getString(R.string.timer_set, String.valueOf(time)));
        } else {
            ToastUtils.showShort(R.string.timer_cancel);
        }
    }


}

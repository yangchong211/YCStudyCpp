package cn.ycbjie.ycaudioplayer.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import com.pedaily.yc.ycdialoglib.toast.ToastUtil;

import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.base.BaseConfig;
import cn.ycbjie.ycaudioplayer.ui.me.MeAboutActivity;
import cn.ycbjie.ycaudioplayer.ui.me.MeSettingActivity;

/**
 * Created by yc on 2018/1/20.
 */

class NavigationExecutor {

    static boolean onNavigationItemSelected(MenuItem item, Activity activity) {
        switch (item.getItemId()) {
            case R.id.action_home:

                return true;
            case R.id.action_night:
                setNightMode(activity);
                break;
            case R.id.action_msg:
                ToastUtil.showToast(activity,"消息");
                return true;
            case R.id.action_exit:
                ToastUtil.showToast(activity,"退出");
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
     */
    private static void setNightMode(Activity activity) {
        BaseConfig.INSTANCE.setNight(!BaseConfig.INSTANCE.isNight());
        activity.recreate();
    }


}

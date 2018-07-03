package cn.ycbjie.ycaudioplayer.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationManagerCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import cn.ycbjie.ycaudioplayer.base.view.BaseActivity;
import cn.ycbjie.ycaudioplayer.weight.dialog.AlertNormalDialog;


public class AppToolUtils {



    /**
     * 获取进程号对应的进程名
     *
     * @param pid   进程号
     * @return      进程名
     */
    public static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 设置焦点
     * @param et                    et
     */
    public static void SetEditTextFocus(EditText et){
        et.setFocusable(true);
        et.setFocusableInTouchMode(true);
        et.requestFocus();
    }

    /**
     *
     * @param context
     */
    public static void requestMsgPermission(final FragmentActivity context) {
        try {
            // 6.0以上系统才可以判断权限
            if (!NotificationManagerCompat.from(context).areNotificationsEnabled()) {
                final AlertNormalDialog alertCustomDialog = new AlertNormalDialog(context);
                alertCustomDialog.setTitle("开启消息通知");
                alertCustomDialog.setContentText("开启消息通知能够帮助你快速查看信息哦~");
                alertCustomDialog.setLeftText("下次再说");
                alertCustomDialog.setRightText("立即开启");
                alertCustomDialog.setLeftClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertCustomDialog.dialogClose();
                    }
                });
                alertCustomDialog.setRightClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent localIntent = new Intent();
                        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                        String packageName = context.getPackageName();
                        localIntent.setData(Uri.fromParts("package",packageName, null));
                        context.startActivity(localIntent);
                        alertCustomDialog.dialogClose();

                    }
                });
                alertCustomDialog.show(context.getSupportFragmentManager());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

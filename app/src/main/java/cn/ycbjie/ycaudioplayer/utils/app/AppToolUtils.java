package cn.ycbjie.ycaudioplayer.utils.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationManagerCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.NetworkUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
     * 开启日志消息权限
     * @param context                   上下文
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


    public static String getAidlCheckAppInfoSign(){
        String appPackageName = AppUtils.getAppPackageName();
        //String appPackageName = "cn.ycbjie.ycaudioplayer";
        @SuppressLint("SimpleDateFormat")
        // 设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH:mm");
        // new Date()为获取当前系统时间，也可使用当前时间戳
        String date = df.format(new Date());
        return encrypt(date+"_"+appPackageName);
    }

    /**
     * MD5加密
     * @param plaintext             明文
     * @return                      密文
     */
    private static String  encrypt(String plaintext) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] btInput = plaintext.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    public final static String SHA1 = "SHA1";
    /**
     * 返回一个签名的对应类型的字符串
     */
    public static String getSingInfo(Context context, String packageName, String type) {
        String tmp = null;
        Signature[] signs = getSignatures(context, packageName);
        assert signs != null;
        for (Signature sig : signs) {
            if (SHA1.equals(type)) {
                tmp = getSignatureString(sig, SHA1);
                break;
            }
        }
        return tmp;
    }

    /**
     * 返回对应包的签名信息
     * @return
     */
    @SuppressLint("PackageManagerGetSignatures")
    private static Signature[] getSignatures(Context context, String packageName) {
        PackageInfo packageInfo ;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);
            return packageInfo.signatures;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取相应的类型的字符串（把签名的byte[]信息转换成16进制）
     * @return
     */
    private static String getSignatureString(Signature sig, String type) {
        byte[] hexBytes = sig.toByteArray();
        String fingerprint = "error!";
        try {
            MessageDigest digest = MessageDigest.getInstance(type);
            if (digest != null) {
                byte[] digestBytes = digest.digest(hexBytes);
                StringBuilder sb = new StringBuilder();
                for (byte digestByte : digestBytes) {
                    sb.append((Integer.toHexString((digestByte & 0xFF) | 0x100)).substring(1, 3));
                }
                fingerprint = sb.toString();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return fingerprint;
    }

    /**
     * 判断app是否正在运行
     * @param context                       上下文
     * @param packageName                   应用的包名
     * @return true 表示正在运行，false 表示没有运行
     */
    public static boolean isAppRunning(Context context, String packageName) {
        ActivityManager am = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list ;
        if (am != null) {
            list = am.getRunningTasks(100);
            if (list.size() <= 0) {
                return false;
            }
            for (ActivityManager.RunningTaskInfo info : list) {
                if (info.baseActivity.getPackageName().equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

}

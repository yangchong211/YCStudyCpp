package cn.ycbjie.ycaudioplayer.utils.dialog;

import android.app.Activity;
import android.app.ProgressDialog;

import com.pedaily.yc.ycdialoglib.dialog.CustomSelectDialog;

import org.yczbj.ycvideoplayerlib.VideoPlayerUtils;

import java.util.List;

import cn.ycbjie.ycaudioplayer.R;


/**
 * <pre>
 *     @author yangchong
 *     blog  : www.pedaily.cn
 *     time  : 2018/03/22
 *     desc  : 弹窗工具类
 *     revise:
 * </pre>
 */

public class DialogUtils {

    /**
     * 显示进度条对话框
     **/
    private static ProgressDialog dialog;
    public static void showProgressDialog(Activity activity) {
        if (dialog == null) {
            dialog = new ProgressDialog(activity);
            dialog.setMessage("玩命加载中……");
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
        }
        dialog.show();
    }

    /**
     * 关闭进度条对话框
     */
    public static void dismissProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }



    /**
     * 展示对话框视图，构造方法创建对象
     */
    public static CustomSelectDialog showDialog(Activity activity ,
                                                CustomSelectDialog.SelectDialogListener listener,
                                                List<String> names) {
        CustomSelectDialog dialog = new CustomSelectDialog(activity,
                R.style.transparentFrameWindowStyle, listener, names);
        if(VideoPlayerUtils.isActivityLiving(activity)){
            dialog.show();
        }
        return dialog;
    }


}

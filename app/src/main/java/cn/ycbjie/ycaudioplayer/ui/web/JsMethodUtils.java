package cn.ycbjie.ycaudioplayer.ui.web;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;
import android.webkit.WebView;


import com.ns.yc.ycutilslib.activityManager.AppManager;
import com.pedaily.yc.ycdialoglib.customToast.ToastUtil;

import org.json.JSONObject;


/**
 * Created by yc on 2018/8/13.
 * 方法，可以写n个，使用反射可以解耦
 */

class JsMethodUtils {

    /**
     * callback回调code
     * 10000：成功 ，
     * -2： js给我们传的参数错误，
     * -3:app原生方法执行失败（例如分享取消、失败）
     * -4:app内部错误
     */

    public final static int successCode = 10000;

    private Context mContext;
    private WebView mWebView;

    /**
     * 构造方法
     * @param context                   上下文
     * @param webView                   webView
     */
    public JsMethodUtils(Context context, WebView webView) {
        this.mContext = context;
        this.mWebView = webView;
    }

    /**
     * 关闭当前activity
     */
    public void closeWindow(String callbackId) {
        Activity activity = AppManager.getAppManager().currentActivity();
        if (activity != null) {
            activity.finish();
        }
    }


    /**
     * 复制文本
     *
     * @param result                        复制内容
     * @param callbackId                    回调id
     */
    public void copyText(String result, String callbackId) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            String pageName = jsonObject.optString("text");
            int code = successCode;
            if (!TextUtils.isEmpty(pageName)) {
                ClipboardManager cmb = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData myClip = ClipData.newPlainText("text", pageName);
                if (cmb != null) {
                    cmb.setPrimaryClip(myClip);
                }
                ToastUtil.showToast(mContext,"复制成功");
            } else {
                code = -2;//
            }
            String callMethod = "javascript:" + callbackId + "('" + JsAppInterface.getH5CallBackJson(code, "", "success") + "')";
            JsAppInterface.callBackH5method(mContext, mWebView, callMethod);
        } catch (Exception e) {
            String callMethod = "javascript:" + callbackId + "('" + JsAppInterface.getH5CallBackJson(-4, "", "fail") + "')";
            JsAppInterface.callBackH5method(mContext, mWebView, callMethod);
            e.printStackTrace();
        }
    }


}

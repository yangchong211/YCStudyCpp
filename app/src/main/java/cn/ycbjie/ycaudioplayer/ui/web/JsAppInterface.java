package cn.ycbjie.ycaudioplayer.ui.web;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.blankj.utilcode.util.LogUtils;

import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;

import cn.ycbjie.ycaudioplayer.BuildConfig;
import cn.ycbjie.ycaudioplayer.base.view.BaseActivity;
import cn.ycbjie.ycaudioplayer.utils.app.JsonUtils;


/**
 * Created by yc on 2018/8/13.
 * js
 */

class JsAppInterface {

    private Context mContext;
    private WebView mWebView;

    JsAppInterface(Context context, WebView webView) {
        this.mContext = context;
        this.mWebView = webView;
    }

    /**
     * 注册
     */
    void register() {

    }

    /**
     * 解除
     */
    void unregister() {

    }

    /**
     * 在js中调用window.AndroidWebView.showInfoFromJs(name)，便会触发此方法。
     * 此方法名称一定要和js中showInfoFromJava方法一样
     * 注意此注解一定需要添加上！！！！！
     * @param valJson                      json字符串
     */
    @JavascriptInterface
    public String jsToAppInterface(String valJson) {
        if (mContext instanceof WebViewActivity) {
            if (!((WebViewActivity) (mContext)).isJsToAppCallBack) {
                LogUtils.i("url不在白名单或者证书错误");
                return "";
            }
        }
        try {
            JSONObject jsonObject = new JSONObject(valJson);
            String method = jsonObject.optString("method");
            String data = jsonObject.optString("data");
            String callbackId = jsonObject.optString("callbackId");
            LogUtils.i("jsToAppInterface:" + valJson);
            //this.mWebView.loadUrl("javascript:"+method+"('" + data + "')");
            routePageNew(method, data, callbackId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 利用反射获取对应的方法
     * @param jsMethod                      方法名
     * @param data                          数据
     * @param callbackId                    h5回调id
     */
    private void routePageNew(String jsMethod, String data, String callbackId) {
        Class<?> clazz ;
        try {
            clazz = Class.forName("cn.ycbjie.ycaudioplayer.ui.web.JsMethodUtils");
            Constructor constructor = clazz.getDeclaredConstructor(Context.class, WebView.class);
            //设置安全检查，访问私有构造函数必须
            constructor.setAccessible(true);
            Object[] obj = new Object[]{mContext, mWebView};
            Object clazzObj = constructor.newInstance(obj);

            if (!TextUtils.isEmpty(data) && !TextUtils.isEmpty(callbackId)) {
                Method method = clazz.getDeclaredMethod(jsMethod, String.class, String.class);
                //设置安全检查，访问私有成员方法必须
                method.setAccessible(true);
                boolean methodHasAnno = method.isAnnotationPresent(MethodNoVersionCode.class);
                Object content;
                Type t;
                if (methodHasAnno) {
                    //得到注解
                    MethodNoVersionCode methodAnno = method.getAnnotation(MethodNoVersionCode.class);
                    //输出注解属性
                    String[] versionCode = methodAnno.value();
                    int index = Arrays.binarySearch(versionCode, BuildConfig.VERSION_NAME);
                    if (index < 0) {
                        content = method.invoke(clazzObj, data, callbackId);
                        t = method.getGenericReturnType();
                        if ("void".equals(t.toString())) {
                            LogUtils.e("isAnnotationPresent---------1"+t.toString());
                        } else if ("class java.lang.String".equals(t.toString())) {
                            String ss = (String) content;
                            LogUtils.e("isAnnotationPresent"+ss);
                        }
                    } else {//方法不支持当前版本
                        String callMethod = "javascript:" + callbackId + "(\"" + getH5CallBackJson(-1, "", "method not found") + "\")";
                        callBackH5method(mContext, mWebView, callMethod);
                    }
                } else {
                    content = method.invoke(clazzObj, data, callbackId);
                    t = method.getGenericReturnType();
                    if ("void".equals(t.toString())) {
                        LogUtils.e("isAnnotationPresent---------2"+t.toString());
                    } else if ("class java.lang.String".equals(t.toString())) {
                        String ss = (String) content;
                        LogUtils.e("isAnnotationPresent"+ss);
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    static String getH5CallBackJson(int code, String data, String msg) {
        return JsonUtils.getJson().toJson(new CallBackModel(code, data, msg));
    }


    static void callBackH5method(Context context, final WebView mWebView, final String callMethod) {
        LogUtils.i("callBackH5method:" + callMethod);
        if (context instanceof BaseActivity) {
            ((BaseActivity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mWebView.loadUrl(callMethod);
                }
            });
        }
    }

}

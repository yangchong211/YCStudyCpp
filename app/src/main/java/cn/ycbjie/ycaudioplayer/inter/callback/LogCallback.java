package cn.ycbjie.ycaudioplayer.inter.callback;

import com.blankj.utilcode.util.LogUtils;

import cn.ycbjie.ycaudioplayer.utils.logger.AppLogUtils;
import cn.ycbjie.ycthreadpoollib.callback.ThreadCallback;


/**
 * <pre>
 *     author: yangchong
 *     blog  : www.pedaily.cn
 *     time  : 2017/08/22
 *     desc  : 回调数据
 *     revise:
 * </pre>
 */
public class LogCallback implements ThreadCallback {

    private final String TAG = "LogCallback";

    @Override
    public void onError(String name, Throwable t) {
        AppLogUtils.e("LogCallback"+"------onError");
        AppLogUtils.e(TAG, String.format("[任务线程%s]/[回调线程%s]执行失败: %s", name, Thread.currentThread(), t.getMessage()), t);
    }

    @Override
    public void onCompleted(String name) {
        AppLogUtils.e("LogCallback"+"------onCompleted");
        AppLogUtils.e(TAG, String.format("[任务线程%s]/[回调线程%s]执行完毕：", name, Thread.currentThread()));
    }

    @Override
    public void onStart(String name) {
        AppLogUtils.e("LogCallback"+"------onStart");
        AppLogUtils.e(TAG, String.format("[任务线程%s]/[回调线程%s]执行开始：", name, Thread.currentThread()));
    }

}

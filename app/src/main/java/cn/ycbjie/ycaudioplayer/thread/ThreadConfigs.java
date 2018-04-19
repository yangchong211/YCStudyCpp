package cn.ycbjie.ycaudioplayer.thread;


import java.util.concurrent.Executor;


/**
 * <pre>
 *     author: yangchong
 *     blog  : www.pedaily.cn
 *     time  : 2017/08/22
 *     desc  : 存储当前任务的某些配置
 *     revise:
 * </pre>
 */
final class ThreadConfigs {
    /**
     * thread name
     */
    String name;
    /**
     * thread callback
     */
    ThreadCallback callback;
    /**
     * delay time
     */
    long delay;
    /**
     * thread deliver
     */
    Executor deliver;
    /**
     * asyncCallback
     */
    AsyncCallback asyncCallback;
}

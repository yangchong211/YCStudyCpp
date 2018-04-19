package cn.ycbjie.ycaudioplayer.thread;


/**
 * <pre>
 *     author: yangchong
 *     blog  : www.pedaily.cn
 *     time  : 2017/08/22
 *     desc  : 一个回调接口，用于通知用户任务的状态回调委托类
 *     revise:
 * </pre>
 */
public interface ThreadCallback {

    /**
     * 当线程发生错误时，将调用此方法。
     * @param threadName            正在运行线程的名字
     * @param t                     异常
     */
    void onError(String threadName, Throwable t);

    /**
     * 通知用户知道它已经完成
     * @param threadName            正在运行线程的名字
     */
    void onCompleted(String threadName);

    /**
     * 通知用户任务开始运行
     * @param threadName            正在运行线程的名字
     */
    void onStart(String threadName);
}

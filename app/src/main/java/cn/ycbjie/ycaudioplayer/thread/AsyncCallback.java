package cn.ycbjie.ycaudioplayer.thread;


/**
 * <pre>
 *     author: yangchong
 *     blog  : www.pedaily.cn
 *     time  : 2017/08/22
 *     desc  : 异步callback回调接口
 *     revise:
 * </pre>
 */
public interface AsyncCallback<T> {
    /**
     * 成功时调用
     * @param t         泛型
     */
    void onSuccess(T t);

    /**
     * 异常时调用
     * @param t         异常
     */
    void onFailed(Throwable t);
}

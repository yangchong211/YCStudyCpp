package cn.ycbjie.ycaudioplayer.thread;

import java.util.concurrent.Callable;

/**
 * <pre>
 *     author: yangchong
 *     blog  : www.pedaily.cn
 *     time  : 2017/08/22
 *     desc  : RunnableWrapper
 *     revise:
 * </pre>
 */
final class RunnableWrapper implements Runnable {

    private String name;
    private CallbackDelegate delegate;
    private Runnable runnable;
    private Callable callable;

    RunnableWrapper(ThreadConfigs configs) {
        this.name = configs.name;
        this.delegate = new CallbackDelegate(configs.callback, configs.deliver, configs.asyncCallback);
    }

    RunnableWrapper setRunnable(Runnable runnable) {
        this.runnable = runnable;
        return this;
    }

    RunnableWrapper setCallable(Callable callable) {
        this.callable = callable;
        return this;
    }

    @Override
    public void run() {
        Thread current = Thread.currentThread();
        ThreadTools.resetThread(current, name, delegate);
        delegate.onStart(name);
        // avoid NullPointException
        if (runnable != null) {
            runnable.run();
        } else if (callable != null) {
            try {
                Object result = callable.call();
                delegate.onSuccess(result);
            } catch (Exception e) {
                delegate.onError(name, e);
            }
        }
        delegate.onCompleted(name);
    }
}

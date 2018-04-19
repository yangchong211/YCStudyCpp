package cn.ycbjie.ycaudioplayer.thread;


import java.util.concurrent.Callable;

final class CallableWrapper<T> implements Callable<T> {

    private String name;
    private ThreadCallback callback;
    private Callable<T> proxy;

    CallableWrapper(ThreadConfigs configs, Callable<T> proxy) {
        this.name = configs.name;
        this.proxy = proxy;
        this.callback = new CallbackDelegate(configs.callback, configs.deliver, configs.asyncCallback);
    }

    @Override
    public T call() throws Exception {
        ThreadTools.resetThread(Thread.currentThread(),name,callback);
        if (callback != null) {
            callback.onStart(name);
        }
        T t = proxy == null ? null : proxy.call();
        if (callback != null)  {
            callback.onCompleted(name);
        }
        return t;
    }
}

package cn.ycbjie.ycaudioplayer.thread;


import java.util.concurrent.Executor;

/**
 * <pre>
 *     author: yangchong
 *     blog  : www.pedaily.cn
 *     time  : 2017/08/22
 *     desc  : 回调委托类
 *     revise:
 * </pre>
 */
final class CallbackDelegate implements ThreadCallback, AsyncCallback {

    private ThreadCallback callback;
    private AsyncCallback async;
    private Executor deliver;

    CallbackDelegate(ThreadCallback callback, Executor deliver, AsyncCallback async) {
        this.callback = callback;
        this.deliver = deliver;
        this.async = async;
    }

    @Override
    public void onSuccess(final Object o) {
        if (async == null) {
            return;
        }
        deliver.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //noinspection unchecked
                    async.onSuccess(o);
                } catch (Throwable t) {
                    onFailed(t);
                }
            }
        });
    }

    @Override
    public void onFailed(final Throwable t) {
        if (async == null) {
            return;
        }
        deliver.execute(new Runnable() {
            @Override
            public void run() {
                async.onFailed(t);
            }
        });
    }

    @Override
    public void onError(final String name, final Throwable t) {
        onFailed(t);

        if (callback == null) {
            return;
        }
        deliver.execute(new Runnable() {
            @Override
            public void run() {
                callback.onError(name, t);
            }
        });
    }

    @Override
    public void onCompleted(final String name) {
        if (callback == null) {
            return;
        }
        deliver.execute(new Runnable() {
            @Override
            public void run() {
                callback.onCompleted(name);
            }
        });
    }

    @Override
    public void onStart(final String name) {
        if (callback == null) {
            return;
        }
        deliver.execute(new Runnable() {
            @Override
            public void run() {
                callback.onStart(name);
            }
        });
    }
}

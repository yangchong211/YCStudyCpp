package cn.ycbjie.ycaudioplayer.thread;



import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *     author: yangchong
 *     blog  : www.pedaily.cn
 *     time  : 2017/08/22
 *     desc  : 线程池
 *     revise:
 * </pre>
 */
public final class PoolThread implements Executor{

    /**
     * 线程池
     */
    private ExecutorService pool;
    /**
     * 默认线程名字
     */
    private String defName;
    /**
     * 默认线程回调
     */
    private ThreadCallback defCallback;
    /**
     * 默认线程传递
     */
    private Executor defDeliver;

    /**
     * 确保多线程配置没有冲突
     */
    private ThreadLocal<ThreadConfigs> local;

    private PoolThread(int type, int size, int priority, String name, ThreadCallback callback, Executor deliver, ExecutorService pool) {
        if (pool == null) {
            pool = createPool(type, size, priority);
        }
        this.pool = pool;
        this.defName = name;
        this.defCallback = callback;
        this.defDeliver = deliver;
        this.local = new ThreadLocal<>();
    }

    /**
     * 为当前的任务设置线程名。
     * @param name              线程名字
     * @return                  EasyThread
     */
    public PoolThread setName(String name) {
        getLocalConfigs().name = name;
        return this;
    }

    /**
     * 设置当前任务的线程回调，如果未设置，则应使用默认回调。
     * @param callback          线程回调
     * @return                  EasyThread
     */
    public PoolThread setCallback (ThreadCallback callback) {
        getLocalConfigs().callback = callback;
        return this;
    }

    /**
     * 设置当前任务的延迟时间.
     * 只有当您的线程池创建时，它才会产生效果。
     * @param time              时长
     * @param unit              time unit
     * @return                  EasyThread
     */
    public PoolThread setDelay (long time, TimeUnit unit) {
        long delay = unit.toMillis(time);
        getLocalConfigs().delay = Math.max(0, delay);
        return this;
    }

    /**
     * 设置当前任务的线程传递。如果未设置，则应使用默认传递。
     * @param deliver           thread deliver
     * @return                  EasyThread
     */
    public PoolThread setDeliver(Executor deliver){
        getLocalConfigs().deliver = deliver;
        return this;
    }

    /**
     * 启动异步任务
     * @param runnable              task
     */
    @Override
    public void execute (Runnable runnable) {
        ThreadConfigs configs = getLocalConfigs();
        runnable = new RunnableWrapper(configs).setRunnable(runnable);
        DelayTaskDispatcher.get().postDelay(configs.delay, pool, runnable);
        resetLocalConfigs();
    }

    /**
     * 启动异步任务，回调用于接收可调用任务的结果。
     * @param callable              callable
     * @param callback              callback
     * @param <T> type
     */
    public <T> void async(Callable<T> callable, AsyncCallback<T> callback) {
        ThreadConfigs configs = getLocalConfigs();
        configs.asyncCallback = callback;
        Runnable runnable = new RunnableWrapper(configs).setCallable(callable);
        DelayTaskDispatcher.get().postDelay(configs.delay, pool, runnable);
        resetLocalConfigs();
    }

    /**
     * 发射任务
     * @param callable              callable
     * @param <T>                   type
     * @return {@link Future}
     */
    public <T> Future<T> submit (Callable<T> callable) {
        Future<T> result;
        callable = new CallableWrapper<>(getLocalConfigs(), callable);
        result = pool.submit(callable);
        resetLocalConfigs();
        return result;
    }

    /**
     * 获取要创建的线程池。
     * @return                      线程池
     */
    public ExecutorService getExecutor() {
        return pool;
    }

    public void close(){
        if(local!=null){
            local.remove();
            local = null;
        }
    }

    private ExecutorService createPool(int type, int size, int priority) {
        switch (type) {
            case Builder.TYPE_CACHEABLE:
                return Executors.newCachedThreadPool(new DefaultFactory(priority));
            case Builder.TYPE_FIXED:
                return Executors.newFixedThreadPool(size, new DefaultFactory(priority));
            case Builder.TYPE_SCHEDULED:
                return Executors.newScheduledThreadPool(size, new DefaultFactory(priority));
            case Builder.TYPE_SINGLE:
            default:
                return Executors.newSingleThreadExecutor(new DefaultFactory(priority));
        }
    }

    private synchronized void resetLocalConfigs() {
        local.set(null);
    }

    private synchronized ThreadConfigs getLocalConfigs() {
        ThreadConfigs configs = local.get();
        if (configs == null) {
            configs = new ThreadConfigs();
            configs.name = defName;
            configs.callback = defCallback;
            configs.deliver = defDeliver;
            local.set(configs);
        }
        return configs;
    }

    private static class DefaultFactory implements ThreadFactory {

        private int priority;
        DefaultFactory(int priority) {
            this.priority = priority;
        }

        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setPriority(priority);
            return thread;
        }
    }

    public static class Builder {
        final static int TYPE_CACHEABLE = 0;
        final static int TYPE_FIXED = 1;
        final static int TYPE_SINGLE = 2;
        final static int TYPE_SCHEDULED = 3;

        int type;
        int size;
        int priority = Thread.NORM_PRIORITY;
        String name;
        ThreadCallback callback;
        Executor deliver;
        ExecutorService pool;

        private Builder(int size,  int type, ExecutorService pool) {
            this.size = Math.max(1, size);
            this.type = type;
            this.pool = pool;
        }

        public static Builder create(ExecutorService pool) {
            return new Builder(1, TYPE_SINGLE, pool);
        }

        /**
         * 通过Executors.newCachedThreadPool()创建线程池
         */
        public static Builder createCacheable() {
            return new Builder(0, TYPE_CACHEABLE, null);
        }

        /**
         * 通过Executors.newFixedThreadPool()创建线程池
         */
        public static Builder createFixed(int size) {
            return new Builder(size, TYPE_FIXED, null);
        }

        /**
         * 通过Executors.newScheduledThreadPool()创建线程池
         */
        public static Builder createScheduled(int size) {
            return new Builder(size, TYPE_SCHEDULED, null);
        }

        /**
         * 通过Executors.newSingleThreadPool()创建线程池
         */
        public static Builder createSingle() {
            return new Builder(0, TYPE_SINGLE, null);
        }

        /**
         * 将默认线程名设置为“已使用”。
         */
        public Builder setName (String name) {
            if (!ThreadTools.isEmpty(name)) {
                this.name = name;
            }
            return this;
        }

        /**
         * 将默认线程优先级设置为“已使用”。
         */
        public Builder setPriority (int priority) {
            this.priority = priority;
            return this;
        }

        /**
         * 将默认线程回调设置为“已使用”。
         */
        public Builder setCallback (ThreadCallback callback) {
            this.callback = callback;
            return this;
        }

        /**
         * 设置默认线程交付使用
         */
        public Builder setDeliver (Executor deliver) {
            this.deliver = deliver;
            return this;
        }

        /**
         * 创建用于某些配置的线程管理器。
         * @return                  EasyThread instance
         */
        public PoolThread build () {
            priority = Math.max(Thread.MIN_PRIORITY, priority);
            priority = Math.min(Thread.MAX_PRIORITY, priority);

            size = Math.max(1, size);
            if (ThreadTools.isEmpty(name)) {
                // set default thread name
                switch (type) {
                    case TYPE_CACHEABLE:
                        name = "CACHEABLE";
                        break;
                    case TYPE_FIXED:
                        name = "FIXED";
                        break;
                    case TYPE_SINGLE:
                        name = "SINGLE";
                        break;
                    default:
                        name = "PoolThread";
                }
            }

            if (deliver == null) {
                if (ThreadTools.isAndroid) {
                    deliver = AndroidDeliver.getInstance();
                } else {
                    deliver = JavaDeliver.getInstance();
                }
            }

            return new PoolThread(type, size, priority, name, callback, deliver, pool);
        }
    }
}

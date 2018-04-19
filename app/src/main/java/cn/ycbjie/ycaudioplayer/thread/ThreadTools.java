package cn.ycbjie.ycaudioplayer.thread;


/**
 * <pre>
 *     author: yangchong
 *     blog  : www.pedaily.cn
 *     time  : 2017/08/22
 *     desc  : 工具
 *     revise:
 * </pre>
 */
final class ThreadTools {

    /**
     * 标志：在android平台上
     */
    static boolean isAndroid;

    /**
     * 重置线程名并设置UnCaughtExceptionHandler包装回调，以便在发生异常时通知用户
     * @param thread The thread who should be reset.
     * @param name  non-null, thread name
     * @param callback a callback to notify user.
     */
    static void resetThread(Thread thread, final String name, final ThreadCallback callback) {
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                if (callback != null) {
                    callback.onError(name, e);
                }
            }
        });
        thread.setName(name);
    }

    static void sleepThread(long time) {
        if (time <= 0) {
            return;
        }
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException("Thread has been interrupted", e);
        }
    }

    static boolean isEmpty(CharSequence data) {
        return data == null || data.length() == 0;
    }

    static {
        try {
            Class.forName("android.os.Build");
            isAndroid = true;
        } catch (Exception e) {
            isAndroid = false;
        }
    }
}
package cn.ycbjie.ycaudioplayer.thread;

import java.util.concurrent.Executor;


/**
 * <pre>
 *     author: yangchong
 *     blog  : www.pedaily.cn
 *     time  : 2017/08/22
 *     desc  : 默认情况下，用于Java平台的交付。
 *     revise:
 * </pre>
 */
final class JavaDeliver implements Executor {

    private static JavaDeliver instance = new JavaDeliver();

    static JavaDeliver getInstance() {
        return instance;
    }

    @Override
    public void execute(Runnable runnable) {
        runnable.run();
    }
}

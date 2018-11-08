package cn.ycbjie.ycaudioplayer.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import java.util.concurrent.TimeUnit;

import cn.ycbjie.ycaudioplayer.base.app.BaseApplication;
import cn.ycbjie.ycthreadpoollib.PoolThread;


/**
 * <pre>
 *     @author yangchong
 *     blog  :
 *     time  : 2017/7/2
 *     desc  : 重启
 *     revise:
 * </pre>
 */
public class KillSelfService extends Service {

    /**
     * 关闭应用后多久重新启动
     */
    private String PackageName;

    public KillSelfService() {
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        long stopDelayed = intent.getLongExtra("Delayed", 2000);
        PackageName = intent.getStringExtra("PackageName");
        PoolThread executor = BaseApplication.getInstance().getExecutor();
        executor.setName("KillSelfService");
        executor.setDelay(stopDelayed, TimeUnit.MILLISECONDS);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(PackageName);
                startActivity(LaunchIntent);
                KillSelfService.this.stopSelf();
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}

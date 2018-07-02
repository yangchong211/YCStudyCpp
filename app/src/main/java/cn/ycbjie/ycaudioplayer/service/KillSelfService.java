package cn.ycbjie.ycaudioplayer.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;


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
    private Handler handler;
    private String PackageName;

    public KillSelfService() {
        handler = new Handler();
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        long stopDelayed = intent.getLongExtra("Delayed", 2000);
        PackageName = intent.getStringExtra("PackageName");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(PackageName);
                startActivity(LaunchIntent);
                KillSelfService.this.stopSelf();
            }
        },stopDelayed);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(handler!=null){
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }
}

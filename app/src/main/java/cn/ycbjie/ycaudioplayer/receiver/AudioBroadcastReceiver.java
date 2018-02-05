package cn.ycbjie.ycaudioplayer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

import cn.ycbjie.ycaudioplayer.api.Constant;
import cn.ycbjie.ycaudioplayer.model.MusicPlayAction;
import cn.ycbjie.ycaudioplayer.service.PlayService;
import cn.ycbjie.ycaudioplayer.ui.lock.LockAudioActivity;


/**
 * 屏幕亮了，灭了，弹出锁屏页面逻辑
 * 其实这个跟通知处理逻辑一样
 */
public class AudioBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if(action!=null && action.length()>0){
            switch (action){
                //锁屏时处理的逻辑
                case Constant.LOCK_SCREEN:
                    PlayService.startCommand(context,Constant.LOCK_SCREEN);
                    break;
                //当屏幕灭了
                case Intent.ACTION_SCREEN_OFF:
                    PlayService.startCommand(context,Intent.ACTION_SCREEN_OFF);
                    break;
                //当屏幕亮了
                case Intent.ACTION_SCREEN_ON:
                    PlayService.startCommand(context,Intent.ACTION_SCREEN_ON);
                    break;
                default:
                    break;
            }
        }
    }

}

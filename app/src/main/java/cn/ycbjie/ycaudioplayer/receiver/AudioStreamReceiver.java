package cn.ycbjie.ycaudioplayer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.ycbjie.ycaudioplayer.model.MusicPlayAction;
import cn.ycbjie.ycaudioplayer.service.PlayService;


/**
 * 来电/耳机拔出时暂停播放
 * 其实这个跟通知处理逻辑一样
 */
public class AudioStreamReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        PlayService.startCommand(context, MusicPlayAction.TYPE_START_PAUSE);
    }

}

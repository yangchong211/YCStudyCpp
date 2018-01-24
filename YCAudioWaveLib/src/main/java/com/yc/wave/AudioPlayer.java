package com.yc.wave;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 声音播放
 */
public class AudioPlayer{

    //当前播放状态时间
    public final static int HANDLER_CUR_TIME = 1;
    //装备好了
    public final static int HANDLER_PREPARED = 2;
    //完成
    public final static int HANDLER_COMPLETE = 0;
    //错误
    public final static int HANDLER_ERROR = -28;


    private MediaPlayer mMediaPlayer;
    private Handler mRemoteHandler;
    private Timer mTimer;
    private AudioManager mAudioManager;


    /**
     * 音频播放器
     * @param context 上下文
     * @param handler 音频状态handler
     */
    public AudioPlayer(Context context, Handler handler) {
        super();
        this.mRemoteHandler = handler;
        try {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);// 设置媒体流类型
            mMediaPlayer.setOnBufferingUpdateListener(onBufferingUpdateListener);
            mMediaPlayer.setOnPreparedListener(onPreparedListener);
            mMediaPlayer.setOnCompletionListener(onCompletionListener);
            mMediaPlayer.setOnErrorListener(onErrorListener);
            mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        mAudioManager.setMode(AudioManager.MODE_NORMAL);
        if(mMediaPlayer != null){
            mMediaPlayer.start();
        }

    }

    /**
     * @param url url地址
     */
    public int playUrl(String url) {
        try {
            mAudioManager.setMode(AudioManager.MODE_NORMAL);
            mMediaPlayer.reset();
            // 设置数据源
            mMediaPlayer.setDataSource(url);
            // prepare自动播放
            mMediaPlayer.prepareAsync();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return -1;
        } catch (SecurityException e) {
            e.printStackTrace();
            return -1;
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return -1;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }

        mTimer = new Timer();
        TimerTask mTimerTask = new TimerTask() {
            @Override
            public void run() {
                if (mMediaPlayer == null || !mMediaPlayer.isPlaying()) {
                    return;
                }
                Message msg = new Message();
                msg.obj = mMediaPlayer.getCurrentPosition();
                msg.what = 1;
                mRemoteHandler.sendMessageAtTime(msg, 0);
            }
        };
        mTimer.schedule(mTimerTask, 0, 10);
        return 100;
    }

    // 暂停
    public void pause() {
        if(mMediaPlayer != null){
            mMediaPlayer.pause();
        }
    }

    // 停止
    public void stop() {
        if (mTimer != null) {
            mTimer.cancel();
        }
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    /**
     * 滑动至
     * @param time      时间
     */
    public void seekTo(int time) {
        if (mMediaPlayer != null) {
            mMediaPlayer.seekTo(time);
            mMediaPlayer.start();
        }
    }



    public long getDuration() {
        return mMediaPlayer.getDuration();
    }

    /**
     * 获取当前播放时长
     *
     * @return 本地播放时长
     */
    public static long getDurationLocation(Context context, String path) {
        MediaPlayer player = MediaPlayer.create(context, Uri.fromFile(new File(path)));
        if (player != null){
            return player.getDuration();
        } else{
            return 0;
        }
    }


    /**
     * 缓冲更新
     */
    private MediaPlayer.OnBufferingUpdateListener onBufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {

        }
    };

    /**
     * 播放准备
     */
    private MediaPlayer.OnPreparedListener onPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            if (mRemoteHandler != null) {
                Message msg = new Message();
                msg.obj = mMediaPlayer.getDuration();
                msg.what = 2;
                mRemoteHandler.sendMessageAtTime(msg, 0);
            }
            mp.start();
        }
    };


    /**
     * 播放完成
     */
    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            if (mRemoteHandler != null) {
                Message msg = new Message();
                msg.what = 0;
                mRemoteHandler.sendMessageAtTime(msg, 0);
            }
        }
    };

    /**
     * 播放错误
     */
    private MediaPlayer.OnErrorListener onErrorListener = new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            if (mRemoteHandler != null) {
                Message msg = new Message();
                msg.what = -28;
                mRemoteHandler.sendMessageAtTime(msg, 0);
            }
            return false;
        }
    };

}


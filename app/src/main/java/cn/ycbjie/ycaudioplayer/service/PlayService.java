package cn.ycbjie.ycaudioplayer.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.SPUtils;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import cn.ycbjie.ycaudioplayer.api.Constant;
import cn.ycbjie.ycaudioplayer.base.BaseAppHelper;
import cn.ycbjie.ycaudioplayer.inter.OnPlayerEventListener;
import cn.ycbjie.ycaudioplayer.model.callback.EventCallback;
import cn.ycbjie.ycaudioplayer.model.MusicPlayAction;
import cn.ycbjie.ycaudioplayer.model.enums.PlayModeEnum;
import cn.ycbjie.ycaudioplayer.receiver.AudioBroadcastReceiver;
import cn.ycbjie.ycaudioplayer.receiver.AudioEarPhoneReceiver;
import cn.ycbjie.ycaudioplayer.ui.lock.LockAudioActivity;
import cn.ycbjie.ycaudioplayer.ui.music.local.model.LocalMusic;
import cn.ycbjie.ycaudioplayer.util.LogUtils;
import cn.ycbjie.ycaudioplayer.util.QuitTimer;
import cn.ycbjie.ycaudioplayer.util.musicUtils.AudioFocusManager;
import cn.ycbjie.ycaudioplayer.util.musicUtils.FileScanManager;
import cn.ycbjie.ycaudioplayer.util.musicUtils.MediaSessionManager;
import cn.ycbjie.ycaudioplayer.util.musicUtils.NotificationUtils;


public class PlayService extends Service {

    /**
     * 正在播放的本地歌曲的序号
     */
    private int mPlayingPosition = -1;
    /**
     * 正在播放的歌曲[本地|网络]
     */
    private LocalMusic mPlayingMusic;
    /**
     * 播放状态
     */
    private int mPlayState = MusicPlayAction.STATE_IDLE;
    /**
     * 播放器
     */
    private MediaPlayer mPlayer;
    /**
     * 播放进度监听器
     */
    private OnPlayerEventListener mListener;
    /**
     * 更新播放进度的显示，时间的显示
     */
    private static final int UPDATE_PLAY_PROGRESS_SHOW = 0;
    /**
     * 允许与媒体控制器、音量键、媒体按钮和传输控件交互
     */
    private MediaSessionManager mMediaSessionManager;
    /**
     * 捕获/丢弃音乐焦点处理
     */
    private AudioFocusManager mAudioFocusManager;
    /**
     * 是否锁屏了，默认是false
     */
    private boolean mIsLocked = false;
    /**
     * 来电/耳机拔出时暂停播放
     * 在播放时调用，在暂停时注销
     */
    private final AudioEarPhoneReceiver mNoisyReceiver = new AudioEarPhoneReceiver();
    private final IntentFilter mFilter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
    /**
     * 其他广播
     * 比如：屏幕灭了后再次亮了，会显示锁屏页面
     * 这个在onCreate中创建，在onDestroy中销毁
     */
    private final AudioBroadcastReceiver mAudioReceiver = new AudioBroadcastReceiver();



    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case UPDATE_PLAY_PROGRESS_SHOW:
                    updatePlayProgressShow();
                    break;
                default:
                    break;
            }
        }
    };


    /**
     * 绑定服务时才会调用
     * 必须要实现的方法
     * @param intent        intent
     * @return              IBinder对象
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new PlayBinder();
    }


    /**
     * 通过通知栏点击按钮实现音乐切换
     * @param context       上下文
     * @param type          类型
     */
    public static void startCommand(Context context, String type) {
        Intent intent = new Intent(context, PlayService.class);
        intent.setAction(type);
        context.startService(intent);
    }


    public class PlayBinder extends Binder {
        public PlayService getService() {
            return PlayService.this;
        }
    }


    /**
     * 首次创建服务时，系统将调用此方法来执行一次性设置程序（在调用 onStartCommand() 或 onBind() 之前）。
     * 如果服务已在运行，则不会调用此方法。该方法只被调用一次
     */
    @Override
    public void onCreate() {
        super.onCreate();
        NotificationUtils.init(this);
        createMediaPlayer();
        initMediaSessionManager();
        initAudioFocusManager();
        initEarPhoneBroadcastReceiver();
        initAudioBroadcastReceiver();
        initQuitTimer();
    }


    /**
     * 创建MediaPlayer对象
     */
    private void createMediaPlayer() {
        if(mPlayer==null){
            mPlayer = new MediaPlayer();
        }
    }


    /**
     * 允许与媒体控制器、音量键、媒体按钮和传输控件交互。
     * 播放器除了播放了音乐之外什么都没做，就可以分别在任务管理、锁屏、负一屏控制我的播放器
     */
    private void initMediaSessionManager() {
        mMediaSessionManager = new MediaSessionManager(this);
    }

    /**
     * 捕获/丢弃音乐焦点处理
     */
    private void initAudioFocusManager() {
        mAudioFocusManager = new AudioFocusManager(this);
    }


    /**
     * 初始化耳机插入和拔出监听
     */
    private void initEarPhoneBroadcastReceiver() {

    }


    /**
     * 初始化IntentFilter添加action意图
     * 主要是监听屏幕亮了与灭了
     */
    private void initAudioBroadcastReceiver() {
        final IntentFilter filter = new IntentFilter();
        //来电/耳机
        filter.addAction(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        //锁屏
        filter.addAction(Constant.LOCK_SCREEN);
        //当屏幕灭了
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        //当屏幕亮了
        filter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(mAudioReceiver, filter);
    }


    /**
     * 初始化计时器
     */
    private void initQuitTimer() {
        QuitTimer.getInstance().init(this, handler, new EventCallback<Long>() {
            @Override
            public void onEvent(Long aLong) {
                if (mListener != null) {
                    mListener.onTimer(aLong);
                }
            }
        });
    }


    /**
     * 服务在销毁时调用该方法
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        //销毁handler
        if(handler!=null){
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        //销毁MediaPlayer
        mPlayer.reset();
        mPlayer.release();
        mPlayer = null;
        //放弃音频焦点
        mAudioFocusManager.abandonAudioFocus();
        mMediaSessionManager.release();
        //注销广播接收者
        unregisterReceiver(mAudioReceiver);
        //结束notification通知
        NotificationUtils.cancelAll();
        //设置service为null
        BaseAppHelper.get().setPlayService(null);
    }


    /**
     * 每次通过startService()方法启动Service时都会被回调。
     * @param intent                intent
     * @param flags                 flags
     * @param startId               startId
     * @return
     * onStartCommand方法返回值作用：
     * START_STICKY：粘性，service进程被异常杀掉，系统重新创建进程与服务，会重新执行onCreate()、onStartCommand(Intent)
     * START_STICKY_COMPATIBILITY：START_STICKY的兼容版本，但不保证服务被kill后一定能重启。
     * START_NOT_STICKY：非粘性，Service进程被异常杀掉，系统不会自动重启该Service。
     * START_REDELIVER_INTENT：重传Intent。使用这个返回值时，如果在执行完onStartCommand后，服务被异常kill掉，系统会自动重启该服务，并将Intent的值传入。
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            switch (intent.getAction()) {
                //上一首
                case MusicPlayAction.TYPE_PRE:
                    prev();
                    break;
                //下一首
                case MusicPlayAction.TYPE_NEXT:
                    next();
                    break;
                //播放或暂停
                case MusicPlayAction.TYPE_START_PAUSE:
                    playPause();
                    break;
                //添加锁屏界面
                case Constant.LOCK_SCREEN:
                    mIsLocked = intent.getBooleanExtra(Constant.IS_SCREEN_LOCK,true);
                    break;
                //当屏幕灭了，添加锁屏页面
                case Intent.ACTION_SCREEN_OFF:
                    startLockAudioActivity();
                    break;
                default:
                    break;
            }
        }
        return START_NOT_STICKY;
    }



    /**---------------------播放或暂停，上一首，下一首-----------------------------------------*/

    /**
     * 播放或暂停
     * 逻辑：
     * 1.如果正在准备，点击则是停止播放
     * 2.如果是正在播放，点击则是暂停
     * 3.如果是暂停状态，点击则是开始播放
     * 4.其他情况是直接播放
     */
    public void playPause() {
        if (isPreparing()) {
            stop();
        } else if (isPlaying()) {
            pause();
        } else if (isPausing()) {
            start();
        } else {
            play(getPlayingPosition());
        }
    }


    /**
     * 上一首
     * 记住有播放类型，单曲循环，顺序循环，随机播放
     * 逻辑：如果不是第一首，则还有上一首；如果没有上一首，则切换到最后一首
     */
    public void prev() {
        //建议都添加这个判断
        if (BaseAppHelper.get().getMusicList().isEmpty()) {
            return;
        }
        int playMode = SPUtils.getInstance(Constant.SP_NAME).getInt(Constant.PLAY_MODE, 0);
        int size = BaseAppHelper.get().getMusicList().size();
        PlayModeEnum mode = PlayModeEnum.valueOf(playMode);
        switch (mode) {
            //随机
            case SHUFFLE:
                mPlayingPosition = new Random().nextInt(size);
                play(mPlayingPosition);
                break;
            //单曲
            case SINGLE:
                play(mPlayingPosition);
                break;
            //顺序播放并且循环
            case LOOP:
            default:
                if(mPlayingPosition != 0){
                    // 如果不是第一首，则还有上一首
                    mPlayingPosition--;
                } else {
                    // 如果没有上一首，则切换到最后一首
                    mPlayingPosition = size;
                }
                play(mPlayingPosition);
                break;
        }
    }


    /**
     * 下一首
     * 记住有播放类型，单曲循环，顺序循环，随机播放
     * 逻辑：如果不是最后一首，则还有下一首；如果是最后一首，则切换回第一首
     */
    public void next() {
        //建议都添加这个判断
        if (BaseAppHelper.get().getMusicList().isEmpty()) {
            return;
        }
        int playMode = SPUtils.getInstance(Constant.SP_NAME).getInt(Constant.PLAY_MODE, 0);
        int size = BaseAppHelper.get().getMusicList().size();
        PlayModeEnum mode = PlayModeEnum.valueOf(playMode);
        switch (mode) {
            //随机
            case SHUFFLE:
                mPlayingPosition = new Random().nextInt(size);
                play(mPlayingPosition);
                break;
            //单曲
            case SINGLE:
                play(mPlayingPosition);
                break;
            //顺序播放并且循环
            case LOOP:
            default:
                if (mPlayingPosition != size - 1) {
                    // 如果不是最后一首，则还有下一首
                    mPlayingPosition++;
                } else {
                    // 如果是最后一首，则切换回第一首
                    mPlayingPosition = 0;
                }
                play(mPlayingPosition);
                break;
        }
    }

    /**---------------------开始播放，暂停播放，停止播放等-----------------------------------------*/


    /**
     * 开始播放
     */
    private void start() {
        if (!isPreparing() && !isPausing()) {
            return;
        }

        if(mAudioFocusManager.requestAudioFocus()){
            if(mPlayer!=null){
                mPlayer.start();
                mPlayState = MusicPlayAction.STATE_PLAYING;
                //开始发送消息，执行进度条进度更新
                handler.sendEmptyMessage(UPDATE_PLAY_PROGRESS_SHOW);
                if (mListener != null) {
                    mListener.onPlayerStart();
                }
                //当点击播放按钮时(播放详情页面或者底部控制栏)，同步通知栏中播放按钮状态
                NotificationUtils.showPlay(mPlayingMusic);
                //注册监听来电/耳机拔出时暂停播放广播
                registerReceiver(mNoisyReceiver, mFilter);
                mMediaSessionManager.updatePlaybackState();
            }
        }
    }


    /**
     * 暂停
     */
    public void pause() {
        if(mPlayer!=null){
            //暂停
            mPlayer.pause();
            //切换状态
            mPlayState = MusicPlayAction.STATE_PAUSE;
            //移除，注意一定要移除，否则一直走更新方法
            handler.removeMessages(UPDATE_PLAY_PROGRESS_SHOW);
            //监听
            if (mListener != null) {
                mListener.onPlayerPause();
            }
            //当点击暂停按钮时(播放详情页面或者底部控制栏)，同步通知栏中暂停按钮状态
            NotificationUtils.showPause(mPlayingMusic);
            //注销监听来电/耳机拔出时暂停播放广播
            unregisterReceiver(mNoisyReceiver);
            mMediaSessionManager.updatePlaybackState();
        }
    }


    /**
     * 停止播放
     */
    public void stop() {
        if (isDefault()) {
            return;
        }
        pause();
        if(mPlayer!=null){
            mPlayer.reset();
            mPlayState = MusicPlayAction.STATE_IDLE;
        }
    }


    /**
     * 播放索引为position的音乐
     * @param position              索引
     */
    public void play(int position) {
        if (BaseAppHelper.get().getMusicList().isEmpty()) {
            return;
        }

        if (position < 0) {
            position = BaseAppHelper.get().getMusicList().size() - 1;
        } else if (position >= BaseAppHelper.get().getMusicList().size()) {
            //如果是最后一首音乐，则播放时直接播放第一首音乐
            position = 0;
        }

        mPlayingPosition = position;
        LocalMusic music = BaseAppHelper.get().getMusicList().get(mPlayingPosition);
        //保存当前播放的musicId，下次进来可以记录状态
        SPUtils.getInstance(Constant.SP_NAME).put(Constant.MUSIC_ID,music.getId());
        play(music);
    }


    /**
     * 拖动seekBar时，调节进度
     * @param progress          进度
     */
    public void seekTo(int progress) {
        if (isPlaying() || isPausing()) {
            mPlayer.seekTo(progress);
            if(mListener!=null){
                mListener.onUpdateProgress(progress);
            }
            mMediaSessionManager.updatePlaybackState();
        }
    }


    /**
     * 播放
     * @param music         music
     */
    public void play(LocalMusic music) {
        mPlayingMusic = music;
        createMediaPlayer();
        try {
            mPlayer.reset();
            //把音频路径传给播放器
            mPlayer.setDataSource(music.getPath());
            //准备
            mPlayer.prepareAsync();
            //设置状态为准备中
            mPlayState = MusicPlayAction.STATE_PREPARING;
            //监听
            mPlayer.setOnPreparedListener(mOnPreparedListener);
            mPlayer.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
            mPlayer.setOnCompletionListener(mOnCompletionListener);
            mPlayer.setOnSeekCompleteListener(mOnSeekCompleteListener);
            //当播放的时候，需要刷新界面信息
            if (mListener != null) {
                mListener.onChange(music);
            }
            //更新通知栏
            NotificationUtils.showPlay(music);

            //更新
            mMediaSessionManager.updateMetaData(mPlayingMusic);
            mMediaSessionManager.updatePlaybackState();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 更新播放进度的显示，时间的显示
     */
    private void updatePlayProgressShow() {
        if (isPlaying() && mListener != null) {
            int currentPosition =  mPlayer.getCurrentPosition();
            mListener.onUpdateProgress(currentPosition);
        }
        LogUtils.e("updatePlayProgressShow");
        // 每30毫秒更新一下显示的内容
        handler.sendEmptyMessageDelayed(UPDATE_PLAY_PROGRESS_SHOW, 300);
    }


    /** 音频准备好的监听器 */
    private MediaPlayer.OnPreparedListener mOnPreparedListener = new MediaPlayer.OnPreparedListener() {
        /** 当音频准备好可以播放了，则这个方法会被调用  */
        @Override
        public void onPrepared(MediaPlayer mp) {
            if (isPreparing()) {
                start();
            }
        }
    };


    /** 当音频播放结果的时候的监听器 */
    private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        /** 当音频播放结果的时候这个方法会被调用 */
        @Override
        public void onCompletion(MediaPlayer mp) {
            next();
        }
    };


    /** 当音频缓冲的监听器 */
    private MediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {

        }
    };


    /** 跳转完成时的监听 */
    private MediaPlayer.OnSeekCompleteListener mOnSeekCompleteListener = new MediaPlayer.OnSeekCompleteListener() {
        @Override
        public void onSeekComplete(MediaPlayer mp) {

        }
    };


    /**
     * 是否正在播放
     * @return          true表示正在播放
     */
    public boolean isPlaying() {
        return mPlayState == MusicPlayAction.STATE_PLAYING;
    }


    /**
     * 是否暂停
     * @return          true表示暂停
     */
    public boolean isPausing() {
        return mPlayState == MusicPlayAction.STATE_PAUSE;
    }


    /**
     * 是否正在准备中
     * @return          true表示正在准备中
     */
    public boolean isPreparing() {
        return mPlayState == MusicPlayAction.STATE_PREPARING;
    }


    /**
     * 是否正在准备中
     * @return          true表示正在准备中
     */
    public boolean isDefault() {
        return mPlayState == MusicPlayAction.STATE_IDLE;
    }

    /**------------------------------------------------------------------------------------------*/

    /**
     * 退出时候调用
     */
    public void quit() {
        // 先停止播放
        stop();
        // 移除定时器
        QuitTimer.getInstance().stop();
        // 当另一个组件（如 Activity）通过调用 startService() 请求启动服务时，系统将调用onStartCommand。
        // 一旦执行此方法，服务即会启动并可在后台无限期运行。 如果自己实现此方法，则需要在服务工作完成后，
        // 通过调用 stopSelf() 或 stopService() 来停止服务。
        stopSelf();
    }


    /**
     * 获取正在播放的本地歌曲的序号
     */
    public int getPlayingPosition() {
        return mPlayingPosition;
    }


    /**
     * 获取正在播放的歌曲[本地|网络]
     */
    public LocalMusic getPlayingMusic() {
        return mPlayingMusic;
    }


    /**
     * 获取播放的进度
     * @return          long类型值
     */
    public long getCurrentPosition() {
        if (isPlaying() || isPausing()) {
            return mPlayer.getCurrentPosition();
        } else {
            return 0;
        }
    }


    /**
     * 扫描音乐
     */
    @SuppressLint("StaticFieldLeak")
    public void updateMusicList(final EventCallback<Void> callback) {
        new AsyncTask<Void, Void, List<LocalMusic>>() {
            @Override
            protected List<LocalMusic> doInBackground(Void... params) {
                return FileScanManager.getInstance().scanMusic(PlayService.this);
            }

            @Override
            protected void onPostExecute(List<LocalMusic> musicList) {
                //首先先清空
                BaseAppHelper.get().getMusicList().clear();
                //然后添加所有扫描到的音乐
                BaseAppHelper.get().getMusicList().addAll(musicList);

                //如果获取音乐数据集合不为空
                if (!BaseAppHelper.get().getMusicList().isEmpty()) {
                    //刷新正在播放的本地歌曲的序号
                    updatePlayingPosition();
                    //获取正在播放的音乐
                    mPlayingMusic = BaseAppHelper.get().getMusicList().get(mPlayingPosition);
                }
                if (callback != null) {
                    callback.onEvent(null);
                }
            }
        }.execute();
    }


    /**
     * 删除或下载歌曲后刷新正在播放的本地歌曲的序号
     */
    public void updatePlayingPosition() {
        int position = 0;
        long id = SPUtils.getInstance(Constant.SP_NAME).getLong(Constant.MUSIC_ID,-1);
        for (int i = 0; i < BaseAppHelper.get().getMusicList().size(); i++) {
            if (BaseAppHelper.get().getMusicList().get(i).getId() == id) {
                position = i;
                break;
            }
        }
        mPlayingPosition = position;
        long musicId = BaseAppHelper.get().getMusicList().get(mPlayingPosition).getId();
        SPUtils.getInstance(Constant.SP_NAME).put(Constant.MUSIC_ID,musicId);
    }


    /**
     * 获取播放进度监听器对象
     * @return                  OnPlayerEventListener对象
     */
    public OnPlayerEventListener getOnPlayEventListener() {
        return mListener;
    }

    /**
     * 设置播放进度监听器
     * @param listener          listener
     */
    public void setOnPlayEventListener(OnPlayerEventListener listener) {
        mListener = listener;
    }


    /**-------------------------------------添加锁屏界面----------------------------------------*/


    /**
     * 打开锁屏页面，这块伤透了脑筋
     * 不管是播放状态是哪一个，只要屏幕灭了到亮了，就展现这个锁屏页面
     * 有些APP限制了状态，比如只有播放时才走这个逻辑
     */
    private void startLockAudioActivity() {
        if(!mIsLocked){
            Intent lockScreen = new Intent(this, LockAudioActivity.class);
            lockScreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            startActivity(lockScreen);
        }
    }


}

package cn.ycbjie.ycaudioplayer.ui.lock;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.api.constant.Constant;
import cn.ycbjie.ycaudioplayer.base.BaseAppHelper;
import cn.ycbjie.ycaudioplayer.inter.OnPlayerEventListener;
import cn.ycbjie.ycaudioplayer.receiver.AudioBroadcastReceiver;
import cn.ycbjie.ycaudioplayer.service.PlayService;
import cn.ycbjie.ycaudioplayer.ui.music.local.model.AudioMusic;
import cn.ycbjie.ycaudioplayer.util.musicUtils.CoverLoader;
import cn.ycbjie.ycaudioplayer.util.other.AppUtils;
import cn.ycbjie.ycaudioplayer.util.other.HandlerUtils;
import cn.ycbjie.ycaudioplayer.weight.layout.SlitherFinishLayout;

/**
 * Created by yc on 2018/2/2.
 * 参考博客：
 * https://www.cnblogs.com/qianyukun/p/5855880.html
 */
public class LockAudioActivity extends AppCompatActivity implements View.OnClickListener {


    @Bind(R.id.iv_play_page_bg)
    ImageView ivPlayPageBg;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_date)
    TextView tvDate;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_author)
    TextView tvAuthor;
    @Bind(R.id.iv_image)
    ImageView ivImage;
    @Bind(R.id.tv_current_time)
    TextView tvCurrentTime;
    @Bind(R.id.sb_progress)
    SeekBar sbProgress;
    @Bind(R.id.tv_total_time)
    TextView tvTotalTime;
    @Bind(R.id.iv_prev)
    ImageView ivPrev;
    @Bind(R.id.iv_play)
    ImageView ivPlay;
    @Bind(R.id.iv_next)
    ImageView ivNext;
    @Bind(R.id.ll_content)
    LinearLayout llContent;
    @Bind(R.id.slide_layout)
    SlitherFinishLayout slideLayout;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    private Handler mHandler;
    private PlayService playService;
    private int mLastProgress;
    /**
     * 是否拖进度，默认是false
     */
    private boolean isDraggingProgress;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Intent intent = new Intent(this, AudioBroadcastReceiver.class);
        intent.setAction(Constant.LOCK_SCREEN_ACTION);
        sendBroadcast(intent);
        super.onCreate(savedInstanceState);
        initWindow();
        setContentView(R.layout.activity_audio_lock);
        ButterKnife.bind(this);
        playService = BaseAppHelper.get().getPlayService();
        initPlayServiceListener();
        initView();
        initListener();
        initLockData();
        initHandler();
    }


    private void initWindow() {
        //注意需要做一下判断
        if (getWindow() != null) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN |
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            // 锁屏的activity内部也要做相应的配置，让activity在锁屏时也能够显示，同时去掉系统锁屏。
            // 当然如果设置了系统锁屏密码，系统锁屏是没有办法去掉的
            // FLAG_DISMISS_KEYGUARD用于去掉系统锁屏页
            // FLAG_SHOW_WHEN_LOCKED使Activity在锁屏时仍然能够显示
            window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                window.getDecorView().setSystemUiVisibility(
                        // SYSTEM_UI_FLAG_LAYOUT_STABLE保持整个View稳定，使View不会因为SystemUI的变化而做layout
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                                // SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION，开发者容易被其中的HIDE_NAVIGATION所迷惑，
                                // 其实这个Flag没有隐藏导航栏的功能，只是控制导航栏浮在屏幕上层，不占据屏幕布局空间；
                                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                                // SYSTEM_UI_FLAG_HIDE_NAVIGATION，才是能够隐藏导航栏的Flag；
                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                // SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN，由上面可知，也不能隐藏状态栏，只是使状态栏浮在屏幕上层。
                                View.SYSTEM_UI_FLAG_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_IMMERSIVE);
            }
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && getWindow() != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().getDecorView().setSystemUiVisibility(
                        // SYSTEM_UI_FLAG_LAYOUT_STABLE保持整个View稳定，使View不会因为SystemUI的变化而做layout
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                                // SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION，开发者容易被其中的HIDE_NAVIGATION所迷惑，
                                // 其实这个Flag没有隐藏导航栏的功能，只是控制导航栏浮在屏幕上层，不占据屏幕布局空间；
                                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                                // SYSTEM_UI_FLAG_HIDE_NAVIGATION，才是能够隐藏导航栏的Flag；
                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                // SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN，由上面可知，也不能隐藏状态栏，只是使状态栏浮在屏幕上层。
                                View.SYSTEM_UI_FLAG_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_IMMERSIVE);
            }
        }
    }


    @Override
    public void onBackPressed() {
        // 不做任何事，为了屏蔽back键
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int key = event.getKeyCode();
        switch (key) {
            case KeyEvent.KEYCODE_BACK: {
                return true;
            }
            case KeyEvent.KEYCODE_MENU: {
                return true;
            }
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, AudioBroadcastReceiver.class);
        intent.setAction(Constant.LOCK_SCREEN_ACTION);
        intent.putExtra(Constant.IS_SCREEN_LOCK, false);
        sendBroadcast(intent);
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }


    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        Intent intent = new Intent(this, AudioBroadcastReceiver.class);
        intent.setAction(Constant.LOCK_SCREEN_ACTION);
        intent.putExtra(Constant.IS_SCREEN_LOCK, false);
        sendBroadcast(intent);
        finish();
    }


    private void initView() {
        slideLayout.setOnSlitherFinishListener(new SlitherFinishLayout.OnSlitherFinishListener() {
            @Override
            public void onSlitherFinish() {
                finish();
            }
        });
        slideLayout.setTouchView(getWindow().getDecorView());
    }


    private void initListener() {
        ivPrev.setOnClickListener(this);
        ivNext.setOnClickListener(this);
        ivPlay.setOnClickListener(this);
        initSeekBarListener();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_prev:
                playService.prev();
                break;
            case R.id.iv_next:
                playService.next();
                break;
            case R.id.iv_play:
                playService.playPause();
                if (playService.isPlaying()) {
                    ivPlay.setImageResource(R.drawable.ic_play_btn_pause);
                } else {
                    ivPlay.setImageResource(R.drawable.ic_play_btn_play);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 初始化服务播放音频播放进度监听器
     * 这个是要是通过监听即时更新主页面的底部控制器视图
     * 同时还要同步播放详情页面mPlayFragment的视图
     */
    public void initPlayServiceListener() {
        if(playService==null){
            return;
        }
        playService.setOnPlayEventListener(new OnPlayerEventListener() {
            /**
             * 切换歌曲
             * 主要是切换歌曲的时候需要及时刷新界面信息
             */
            @Override
            public void onChange(AudioMusic music) {
                onChangeImpl(music);
            }

            /**
             * 继续播放
             * 主要是切换歌曲的时候需要及时刷新界面信息，比如播放暂停按钮
             */
            @Override
            public void onPlayerStart() {
                //ivPlay.setImageResource(R.drawable.ic_play_btn_pause);
            }

            /**
             * 暂停播放
             * 主要是切换歌曲的时候需要及时刷新界面信息，比如播放暂停按钮
             */
            @Override
            public void onPlayerPause() {
                //ivPlay.setImageResource(R.drawable.ic_play_btn_play);
            }

            /**
             * 更新进度
             * 主要是播放音乐或者拖动进度条时，需要更新进度
             */
            @Override
            public void onUpdateProgress(int progress) {
                /*sbProgress.setProgress(progress);*/
                //如果没有拖动进度，则开始更新进度条进度
                if (!isDraggingProgress) {
                    sbProgress.setProgress(progress);
                }
            }

            @Override
            public void onBufferingUpdate(int percent) {
                sbProgress.setSecondaryProgress(sbProgress.getMax() * 100 / percent);
            }

            /**
             * 更新定时停止播放时间
             */
            @Override
            public void onTimer(long remain) {

            }
        });
    }


    private void initSeekBarListener() {
        sbProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (seekBar == sbProgress) {
                    if (Math.abs(progress - mLastProgress) >= DateUtils.SECOND_IN_MILLIS) {
                        tvCurrentTime.setText(AppUtils.formatTime("mm:ss", progress));
                        mLastProgress = progress;
                    }
                }
            }

            /**
             * 通知用户已启动触摸手势,开始触摸时调用
             * @param seekBar               seekBar
             */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (seekBar == sbProgress) {
                    isDraggingProgress = true;
                }
            }


            /**
             * 通知用户已结束触摸手势,触摸结束时调用
             * @param seekBar               seekBar
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar == sbProgress) {
                    isDraggingProgress = false;
                    //如果是正在播放，或者暂停，那么直接拖动进度
                    if (playService.isPlaying() || playService.isPausing()) {
                        //获取进度
                        int progress = seekBar.getProgress();
                        //直接移动进度
                        playService.seekTo(progress);
                    } else {
                        //其他情况，直接设置进度为0
                        seekBar.setProgress(0);
                    }
                }
            }
        });
    }


    private void initLockData() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm-MM月dd日 E", Locale.CHINESE);
        String[] date = simpleDateFormat.format(new Date()).split("-");
        tvTime.setText(date[0]);
        tvDate.setText(date[1]);
        tvAuthor.setText("作者：杨充");
        tvName.setText("潇湘剑雨");
        ivImage.setImageResource(R.drawable.ic_person_author);
    }


    private void initHandler() {
        if (mHandler == null) {
            mHandler = HandlerUtils.getInstance(this);
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    initMusicData();
                }

            }
        });
    }

    private void initMusicData() {
        //当在播放音频详细页面切换歌曲的时候，需要刷新底部控制器，和音频详细页面的数据
        onChangeImpl(playService.getPlayingMusic());
    }


    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    private void onChangeImpl(AudioMusic music) {
        if (music == null) {
            return;
        }
        tvTime.setText(TimeUtils.date2String(new Date(),new SimpleDateFormat("mm:ss")));
        Bitmap cover = CoverLoader.getInstance().loadThumbnail(music);
        ivImage.setImageBitmap(cover);
        tvTitle.setText(music.getTitle()+ " / " + music.getArtist());
        mLastProgress = 0;
        //更新进度条
        sbProgress.setMax((int) music.getDuration());
        sbProgress.setProgress((int) playService.getCurrentPosition());
        tvCurrentTime.setText("00:00");
        tvTotalTime.setText(AppUtils.formatTime("mm:ss", music.getDuration()));
    }


}

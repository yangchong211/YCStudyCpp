package cn.ycbjie.ycaudioplayer.ui.lock;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.api.Constant;
import cn.ycbjie.ycaudioplayer.base.BaseAppHelper;
import cn.ycbjie.ycaudioplayer.service.PlayService;
import cn.ycbjie.ycaudioplayer.util.HandlerUtils;
import cn.ycbjie.ycaudioplayer.weight.other.SlitherFinishLayout;

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
    private Handler mHandler;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Intent intent = new Intent();
        intent.setAction(Constant.LOCK_SCREEN);
        sendBroadcast(intent);
        super.onCreate(savedInstanceState);
        initWindow();
        setContentView(R.layout.activity_audio_lock);
        ButterKnife.bind(this);
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
        if(hasFocus && getWindow()!=null){
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
            case KeyEvent.KEYCODE_MENU:{
                return true;
            }
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        Intent intent = new Intent();
        intent.setAction(Constant.LOCK_SCREEN);
        intent.putExtra(Constant.IS_SCREEN_LOCK, false);
        sendBroadcast(intent);
        super.onDestroy();
        if(mHandler!=null){
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }


    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        Intent intent = new Intent();
        intent.setAction(Constant.LOCK_SCREEN);
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
    }


    @Override
    public void onClick(View v) {
        PlayService playService = BaseAppHelper.get().getPlayService();
        switch (v.getId()) {
            case R.id.iv_prev:
                playService.prev();
                //ToastUtil.showToast(this,"上一首");
                break;
            case R.id.iv_next:
                playService.next();
                //ToastUtil.showToast(this,"下一首");
                break;
            case R.id.iv_play:
                playService.playPause();
                if(playService.isPlaying()){
                    //ToastUtil.showToast(this,"暂停");
                    ivPlay.setImageResource(R.drawable.ic_play_btn_pause);
                }else {
                    //ToastUtil.showToast(this,"播放");
                    ivPlay.setImageResource(R.drawable.ic_play_btn_play);
                }
                break;
            default:
                break;
        }
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
        if(mHandler==null){
            mHandler = HandlerUtils.getInstance(this);
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {

            }
        });
    }


}

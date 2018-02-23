package cn.ycbjie.ycaudioplayer.ui.lock;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.api.constant.Constant;
import cn.ycbjie.ycaudioplayer.weight.layout.SlideFinishLayout;

/**
 * Created by yc on 2018/2/2.
 */
public class LockTestActivity extends AppCompatActivity implements View.OnClickListener {


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
    SlideFinishLayout slideLayout;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Intent intent = new Intent();
        intent.setAction(Constant.LOCK_SCREEN);
        sendBroadcast(intent);
        super.onCreate(savedInstanceState);
        initWindow();
        setContentView(R.layout.activity_audio_test_lock);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initWindow() {
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
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE);
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
        slideLayout.setEnableLeftSlideEvent(true);
        slideLayout.setEnableRightSlideEvent(false);
    }


    private void initListener() {
        ivPrev.setOnClickListener(this);
        ivNext.setOnClickListener(this);
        ivPlay.setOnClickListener(this);
        //左右滑动的监听
        slideLayout.setOnSlideFinishListener(new SlideFinishLayout.OnSlideFinishListener() {
            @Override
            public void onSlideBack() {
                finish();
            }

            @Override
            public void onSlideForward() {
                finish();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_prev:

                break;
            case R.id.iv_next:

                break;
            case R.id.iv_play:

                break;
            default:
                break;
        }
    }

}

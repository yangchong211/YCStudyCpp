package cn.ycbjie.ycaudioplayer.ui.guide.ui;

import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.Bind;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.base.view.BaseActivity;
import cn.ycbjie.ycaudioplayer.ui.main.ui.activity.MainActivity;
import cn.ycbjie.ycaudioplayer.weight.animView.ViewStars;
import cn.ycbjie.ycstatusbarlib.bar.YCAppBar;

/**
 * Created by yc on 2018/1/30.
 */

public class SplashAdActivity extends BaseActivity {


    @Bind(R.id.fl_ad)
    FrameLayout flAd;
    @Bind(R.id.tv_time)
    TextView tvTime;
    private TimeCount timeCount;
    private GLSurfaceView mGLSurfaceView;
    private boolean isClick = false;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timeCount != null) {
            timeCount.cancel();
            timeCount = null;
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (mGLSurfaceView != null) {
            mGLSurfaceView.onPause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGLSurfaceView != null) {
            mGLSurfaceView.onResume();
        }
    }

    @Override
    public int getContentView() {
        return R.layout.activity_splash_anim;
    }

    @Override
    public void initView() {
        YCAppBar.translucentStatusBar(this, true);
        initTimer();
        initFlView();
    }

    private void initTimer() {
        timeCount = new TimeCount(5 * 1000, 1000, tvTime);
        timeCount.start();
    }


    private void initFlView() {
        mGLSurfaceView = new ViewStars(this);
        flAd.addView(mGLSurfaceView);
    }

    @Override
    public void initListener() {
        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isClick = true;
                //跳转主页面
                toMainActivity();
            }
        });
    }

    @Override
    public void initData() {

    }

    private class TimeCount extends CountDownTimer {

        private TextView tv;

        TimeCount(long millisInFuture, long countDownInterval, TextView tv) {
            super(millisInFuture, countDownInterval);
            //参数依次为总时长,和计时的时间间隔
            this.tv = tv;
        }

        /**
         * 计时完毕时触发
         */
        @Override
        public void onFinish() {
            //如果是点击了翻转，那么就不执行这里面的代码
            if(!isClick){
                //跳转主页面
                toMainActivity();
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
            //计时过程显示
            StringBuilder sb = new StringBuilder();
            sb.append("倒计时");
            sb.append(millisUntilFinished / 1000);
            sb.append("秒");
            tv.setText(sb.toString());
        }
    }


    private void toMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
        finish();
    }


}

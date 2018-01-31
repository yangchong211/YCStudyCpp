package cn.ycbjie.ycaudioplayer.ui.guide.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.base.BaseActivity;
import cn.ycbjie.ycaudioplayer.ui.main.MainActivity;

/**
 * Created by yc on 2018/1/30.
 */

public class SplashAdSecondActivity extends BaseActivity {

    @Bind(R.id.iv_ad)
    ImageView ivAd;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.fl_ad)
    FrameLayout flAd;
    private TimeCount timeCount;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timeCount != null) {
            timeCount.cancel();
            timeCount = null;
        }
    }

    @Override
    public int getContentView() {
        return R.layout.activity_splash_ad;
    }

    @Override
    public void initView() {
        initTimer();
    }

    private void initTimer() {
        timeCount = new TimeCount(5 * 1000, 1000, tvTime);
        timeCount.start();
    }

    @Override
    public void initListener() {
        ivAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        //计时完毕时触发
        @Override
        public void onFinish() {
            //如果是点击了翻转，那么就不执行这里面的代码
            //跳转主页面
            toMainActivity();
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
        overridePendingTransition(R.anim.screen_revolve_zoom_in, R.anim.screen_revolve_zoom_out);
        finish();
    }


}

package cn.ycbjie.ycaudioplayer.ui.guide.ui;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.base.BaseActivity;
import cn.ycbjie.ycaudioplayer.ui.main.MainHomeActivity;
import cn.ycbjie.ycstatusbarlib.bar.YCAppBar;

/**
 * Created by yc on 2018/2/1.
 */

public class SplashAdThirdActivity extends BaseActivity {

    @Bind(R.id.fl_ad)
    FrameLayout flAd;
    @Bind(R.id.fl_main)
    FrameLayout flMain;
    @Bind(R.id.fl)
    LinearLayout fl;
    @Bind(R.id.iv_ad)
    ImageView ivAd;
    @Bind(R.id.tv_time)
    TextView tvTime;


    private TimeCount timeCount;
    private boolean isClick = false;

    private ScaleAnimation sato0 = new ScaleAnimation(1,0,1,1,
            Animation.RELATIVE_TO_PARENT,0.5f, Animation.RELATIVE_TO_PARENT,0.5f);

    private ScaleAnimation sato1 = new ScaleAnimation(0,1,1,1,
            Animation.RELATIVE_TO_PARENT,0.5f,Animation.RELATIVE_TO_PARENT,0.5f);

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
        YCAppBar.translucentStatusBar(this, true);
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
                isClick = true;
                flAd.startAnimation(sato0);
            }
        });
    }

    @Override
    public void initData() {
        showView1();
        sato0.setDuration(500);
        sato1.setDuration(500);
        sato0.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (flAd.getVisibility() == View.VISIBLE){
                    flAd.setAnimation(null);
                    showView2();
                    flMain.startAnimation(sato1);
                }else{
                    flMain.setAnimation(null);
                    showView1();
                    flAd.startAnimation(sato1);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
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
            if(!isClick){
                //第一阶段翻转
                flAd.startAnimation(sato0);
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

    private void showView1(){
        flAd.setVisibility(View.VISIBLE);
        flMain.setVisibility(View.GONE);
    }


    private void showView2(){
        flAd.setVisibility(View.GONE);
        flMain.setVisibility(View.GONE);
        Intent intent = new Intent(this, MainHomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
        finish();
    }



}

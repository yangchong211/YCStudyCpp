package cn.ycbjie.ycaudioplayer.ui.guide.ui;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.base.BaseActivity;
import cn.ycbjie.ycaudioplayer.ui.main.MainActivity;
import cn.ycbjie.ycaudioplayer.util.rotate.Rotate3DUtils;

/**
 * Created by yc on 2018/1/30.
 * 参考博客：https://www.2cto.com/kf/201606/520290.html
 */

public class SplashAdFirstActivity extends BaseActivity {

    @Bind(R.id.iv_ad)
    ImageView ivAd;
    @Bind(R.id.tv_time)
    TextView tvTime;

    //页面翻转容器FrameLayout
    @Bind(R.id.fl)
    LinearLayout fl;

    //布局1界面RelativeLayout
    @Bind(R.id.fl_ad)
    FrameLayout flAd;

    //布局2界面RelativeLayout
    @Bind(R.id.fl_main)
    FrameLayout flMain;

    private TimeCount timeCount;
    private boolean isClick = false;
    //z轴翻转
    private float z = 200.0f;


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
                isClick = true;
                applyRotation(1,0,90);
                //applyRotation(0,0,-90);
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
            if(!isClick){
                //第一阶段翻转
                applyRotation(1,0,90);
                //applyRotation(0,0,-90);
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



    /**
     * 执行翻转第一阶段翻转动画
     * @param tag view索引
     * @param start 起始角度
     * @param end 结束角度
     */
    private void applyRotation(int tag, float start, float end) {
        // 得到中心点(以中心翻转)
        //X轴中心点
        final float centerX = fl.getWidth() / 2.0f;
        //Y轴中心点
        final float centerY = fl.getHeight() / 2.0f;
        //Z轴中心点
        final float depthZ = z;
        // 根据参数创建一个新的三维动画,并且监听触发下一个动画
        final Rotate3DUtils rotation = new Rotate3DUtils(start, end, centerX, centerY, depthZ, true);
        //设置动画持续时间
        rotation.setDuration(800);
        //设置动画变化速度
        //rotation.setInterpolator(new AccelerateInterpolator());
        //设置第一阶段动画监听器
        rotation.setAnimationListener(new DisplayNextView(tag));
        fl.startAnimation(rotation);
    }


    /**
     * 第一阶段动画监听器
     */
    private final class DisplayNextView implements Animation.AnimationListener {
        private final int tag;

        private DisplayNextView(int tag) {
            this.tag = tag;
        }

        @Override
        public void onAnimationStart(Animation animation) {}

        @Override
        public void onAnimationEnd(Animation animation) {
            //第一阶段动画结束时，也就是整个Activity垂直于手机屏幕，
            //执行第二阶段动画
            fl.post(new SwapViews(tag));
            //调整两个界面各自的visibility
            adjustVisibility();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    }


    /**
     * 执行翻转第二个阶段动画
     */
    private final class SwapViews implements Runnable {

        private final int tag;
        SwapViews(int position) {
            tag = position;
        }
        @Override
        public void run() {
            if (tag == 0) {
                //首页页面以90~0度翻转
                showView(flAd, flMain, 90, 0);
            } else if (tag == 1) {
                //音乐页面以-90~0度翻转
                showView(flMain, flAd, -90, 0);
            }
        }
    }

    /**
     * 显示第二个视图动画
     * @param showView 要显示的视图
     * @param hiddenView 要隐藏的视图
     * @param startDegree 开始角度
     * @param endDegree 目标角度
     */
    private void showView(FrameLayout showView, FrameLayout hiddenView, int startDegree, int endDegree) {
        //同样以中心点进行翻转
        float centerX = showView.getWidth() / 2.0f;
        float centerY = showView.getHeight() / 2.0f;
        float centerZ = z;
        if (centerX == 0 || centerY == 0) {
            //调用该方法getMeasuredWidth()，必须先执行measure()方法，否则会出异常。
            showView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            //获取该view在父容器里面占的大小
            centerX = showView.getMeasuredWidth() / 2.0f;
            centerY = showView.getMeasuredHeight() / 2.0f;
        }
        hiddenView.setVisibility(View.GONE);
        //showView.setVisibility(View.VISIBLE);
        showView.setVisibility(View.GONE);
        Rotate3DUtils rotation = new Rotate3DUtils(startDegree, endDegree, centerX, centerY, centerZ, true);
        //设置动画持续时间
        rotation.setDuration(800);
        //设置动画变化速度
        //rotation.setInterpolator(new DecelerateInterpolator());
        fl.startAnimation(rotation);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        //overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
        finish();
    }


    /**
     * 两个布局的visibility调节
     */
    private void adjustVisibility(){
        if(flAd.getVisibility() == View.VISIBLE){
            flAd.setVisibility(View.GONE);
        }else {
            flMain.setVisibility(View.VISIBLE);
        }
        if(flMain.getVisibility() == View.VISIBLE){
            flMain.setVisibility(View.GONE);
        }else {
            flAd.setVisibility(View.VISIBLE);
        }
    }

}

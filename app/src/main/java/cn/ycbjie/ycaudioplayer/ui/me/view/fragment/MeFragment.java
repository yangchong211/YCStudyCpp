package cn.ycbjie.ycaudioplayer.ui.me.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.flyco.tablayout.SegmentTabLayout;
import com.ns.yc.ycutilslib.activityManager.AppManager;

import org.yczbj.ycvideoplayerlib.VideoPlayerUtils;

import butterknife.Bind;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.base.BaseAppHelper;
import cn.ycbjie.ycaudioplayer.base.view.BaseFragment;
import cn.ycbjie.ycaudioplayer.service.PlayService;
import cn.ycbjie.ycaudioplayer.ui.main.ui.activity.MainActivity;
import cn.ycbjie.ycaudioplayer.ui.me.view.activity.MeSettingActivity;
import cn.ycbjie.ycaudioplayer.utils.QuitTimer;

/**
 * Created by yc on 2018/1/24.
 *
 */

public class MeFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.iv_menu)
    ImageView ivMenu;
    @Bind(R.id.stl_layout)
    SegmentTabLayout stlLayout;
    @Bind(R.id.ll_other)
    LinearLayout llOther;
    @Bind(R.id.fl_search)
    FrameLayout flSearch;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rl_me_collect)
    RelativeLayout rlMeCollect;
    @Bind(R.id.rl_me_question)
    RelativeLayout rlMeQuestion;
    @Bind(R.id.rl_me_setting)
    RelativeLayout rlMeSetting;
    @Bind(R.id.rl_me_feed_back)
    RelativeLayout rlMeFeedBack;
    @Bind(R.id.ll_timer)
    LinearLayout llTimer;

    @Bind(R.id.tv_me_phone_number)
    TextView tvMePhoneNumber;
    @Bind(R.id.rl_me_phone)
    LinearLayout rlMePhone;
    @Bind(R.id.btn_exit)
    Button btnExit;
    private MainActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }


    @Override
    public int getContentView() {
        return R.layout.fragment_me;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {
        rlMeCollect.setOnClickListener(this);
        rlMeQuestion.setOnClickListener(this);
        rlMeSetting.setOnClickListener(this);
        llTimer.setOnClickListener(this);
        rlMeFeedBack.setOnClickListener(this);
        rlMePhone.setOnClickListener(this);
        btnExit.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_me_collect:

                break;
            case R.id.rl_me_question:

                break;
            case R.id.rl_me_setting:
                startActivity(MeSettingActivity.class);
                break;
            case R.id.ll_timer:
                timerDialog(activity);
                break;
            case R.id.rl_me_feed_back:

                break;
            case R.id.rl_me_phone:

                break;
            case R.id.btn_exit:
                exit();
                break;
            default:
                break;
        }
    }


    /**
     * 退出程序
     */
    private static void exit() {
        //先销毁service
        PlayService service = BaseAppHelper.get().getPlayService();
        if (service != null) {
            service.setOnPlayEventListener(null);
            service.quit();
        }
        //然后退出应用程序
        AppManager.getAppManager().appExit(false);
    }


    /**
     * 弹出定时停止播放对话框
     * @param activity              activity上下文
     */
    private static void timerDialog(final Activity activity) {
        if(VideoPlayerUtils.isActivityLiving(activity)){
            String[] stringArray = activity.getResources().getStringArray(R.array.timer_text);
            new AlertDialog.Builder(activity)
                    .setTitle("定时停止播放")
                    .setItems(stringArray, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int[] times = activity.getResources().getIntArray(R.array.timer_int);
                            startTimer(activity, times[which]);
                        }
                    })
                    .show();
        }
    }


    /**
     * 开启倒计时器
     * @param activity              activity上下文
     * @param time                  time时间
     */
    private static void startTimer(Activity activity, int time) {
        QuitTimer.getInstance().start(time * 60 * 1000);
        if (time > 0) {
            ToastUtils.showShort(activity.getString(R.string.timer_set, String.valueOf(time)));
        } else {
            ToastUtils.showShort(R.string.timer_cancel);
        }
    }




}

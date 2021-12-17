package cn.ycbjie.ycaudioplayer.ui.me.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.flyco.tablayout.SegmentTabLayout;
import com.ns.yc.ycutilslib.activityManager.AppManager;

import org.yczbj.ycvideoplayerlib.VideoPlayerUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.base.BaseAppHelper;
import cn.ycbjie.ycaudioplayer.base.view.BaseFragment;
import cn.ycbjie.ycaudioplayer.constant.Constant;
import cn.ycbjie.ycaudioplayer.kotlin.view.activity.AndroidActivity;
import cn.ycbjie.ycaudioplayer.service.PlayService;
import cn.ycbjie.ycaudioplayer.ui.main.ui.activity.MainActivity;
import cn.ycbjie.ycaudioplayer.ui.me.view.activity.MeSettingActivity;
import cn.ycbjie.ycaudioplayer.utils.app.QuitTimer;
import cn.ycbjie.ycaudioplayer.utils.share.ShareComment;
import cn.ycbjie.ycaudioplayer.utils.share.ShareDetailBean;
import cn.ycbjie.ycaudioplayer.utils.share.ShareDialog;
import cn.ycbjie.ycaudioplayer.utils.share.ShareTypeBean;

public class MeFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.stl_layout)
    SegmentTabLayout stlLayout;
    @BindView(R.id.ll_other)
    LinearLayout llOther;
    @BindView(R.id.fl_search)
    FrameLayout flSearch;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rl_me_collect)
    RelativeLayout rlMeCollect;
    @BindView(R.id.rl_me_question)
    RelativeLayout rlMeQuestion;
    @BindView(R.id.rl_me_setting)
    RelativeLayout rlMeSetting;
    @BindView(R.id.rl_me_feed_back)
    RelativeLayout rlMeFeedBack;
    @BindView(R.id.ll_timer)
    LinearLayout llTimer;

    @BindView(R.id.tv_me_phone_number)
    TextView tvMePhoneNumber;
    @BindView(R.id.rl_me_phone)
    LinearLayout rlMePhone;
    @BindView(R.id.btn_exit)
    TextView btnExit;
    @BindView(R.id.tv_author)
    TextView tvAuthor;
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
    public void initView(View view) {
        tvAuthor.setText(SPUtils.getInstance(Constant.SP_NAME).getString("name", "杨充"));
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
        switch (v.getId()) {
            case R.id.rl_me_collect:
                ShareDetailBean shareDetailBean = new ShareDetailBean();
                shareDetailBean.setShareType(ShareComment.ShareType.SHARE_GOODS);
                shareDetailBean.setContent("商品详情页分享");
                shareDetailBean.setTitle("分享");
                shareDetailBean.setImage("https://upload-images.jianshu.io/upload_images/4432347-fb25131b5629346a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240");
                ShareDialog shareDialog = new ShareDialog(activity,shareDetailBean);
                shareDialog.show(getChildFragmentManager());
                break;
            case R.id.rl_me_question:

                break;
            case R.id.rl_me_setting:
                ActivityUtils.startActivity(MeSettingActivity.class);
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
     *
     * @param activity activity上下文
     */
    private static void timerDialog(final Activity activity) {
        if (VideoPlayerUtils.isActivityLiving(activity)) {
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
     *
     * @param activity activity上下文
     * @param time     time时间
     */
    private static void startTimer(Activity activity, int time) {
        QuitTimer.getInstance().start(time * 60 * 1000);
        if (time > 0) {
            ToastUtils.showShort(activity.getString(R.string.timer_set, String.valueOf(time)));
        } else {
            ToastUtils.showShort(R.string.timer_cancel);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

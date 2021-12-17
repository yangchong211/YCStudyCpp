package cn.ycbjie.ycaudioplayer;

import android.annotation.SuppressLint;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;

import butterknife.BindView;
import cn.ycbjie.ycaudioplayer.base.view.BaseActivity;
import cn.ycbjie.ycaudioplayer.constant.Constant;
import cn.ycbjie.ycaudioplayer.utils.app.RestartAppUtils;
import cn.ycbjie.ycstatusbarlib.bar.StateAppBar;


public class DebugActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.ll_title_menu)
    FrameLayout llTitleMenu;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.change_test)
    RadioButton changeTest;
    @BindView(R.id.change_preview)
    RadioButton changePreview;
    @BindView(R.id.change_release)
    RadioButton changeRelease;
    @BindView(R.id.tv_restart_app)
    TextView tvRestartApp;


    public static final String SELECT_STATUS = "select_status";


    @Override
    public int getContentView() {
        return R.layout.activity_debug_view;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void initView() {
        StateAppBar.setStatusBarColor(this,
                ContextCompat.getColor(this, R.color.redTab));
        toolbarTitle.setText("设置Debug模式");
    }


    @Override
    public void initListener() {
        llTitleMenu.setOnClickListener(this);
        changeTest.setOnClickListener(this);
        changePreview.setOnClickListener(this);
        changeRelease.setOnClickListener(this);
        tvRestartApp.setOnClickListener(this);
    }


    @Override
    public void initData() {
        int anInt = SPUtils.getInstance(Constant.SP_NAME).getInt(SELECT_STATUS);
        switch (anInt){
            case 1001:
                changeTest.setChecked(true);
                changePreview.setChecked(false);
                changeRelease.setChecked(false);
                break;
            case 2001:
                changeTest.setChecked(false);
                changePreview.setChecked(true);
                changeRelease.setChecked(false);
                break;
            case 3001:
                changeTest.setChecked(false);
                changePreview.setChecked(false);
                changeRelease.setChecked(true);
                break;
            default:
                changeTest.setChecked(true);
                changePreview.setChecked(false);
                changeRelease.setChecked(false);
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_title_menu:
                finish();
                break;
            case R.id.change_test:
                SPUtils.getInstance(Constant.SP_NAME).put(SELECT_STATUS, 1001);
                break;
            case R.id.change_preview:
                SPUtils.getInstance(Constant.SP_NAME).put(SELECT_STATUS, 2001);
                break;
            case R.id.change_release:
                SPUtils.getInstance(Constant.SP_NAME).put(SELECT_STATUS, 3001);
                break;
            case R.id.tv_restart_app:
                RestartAppUtils.restartAPP(this);
                break;
            default:
                break;
        }
    }


}

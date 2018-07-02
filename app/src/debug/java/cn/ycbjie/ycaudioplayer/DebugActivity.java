package cn.ycbjie.ycaudioplayer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;

import butterknife.Bind;
import cn.ycbjie.ycaudioplayer.base.view.BaseActivity;
import cn.ycbjie.ycaudioplayer.utils.RestartAppUtils;
import cn.ycbjie.ycstatusbarlib.bar.YCAppBar;


public class DebugActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.ll_title_menu)
    FrameLayout llTitleMenu;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.tv_state)
    TextView tvState;
    @Bind(R.id.change_test)
    RadioButton changeTest;
    @Bind(R.id.change_preview)
    RadioButton changePreview;
    @Bind(R.id.change_release)
    RadioButton changeRelease;
    @Bind(R.id.tv_restart_app)
    TextView tvRestartApp;


    public static final String SELECT_STATUS = "select_status";


    @Override
    public int getContentView() {
        return R.layout.activity_debug_view;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void initView() {
        YCAppBar.setStatusBarColor(this, ContextCompat.getColor(this, R.color.redTab));
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

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_title_menu:
                finish();
                break;
            case R.id.change_test:
                SPUtils.getInstance().put(SELECT_STATUS, 1001);
                break;
            case R.id.change_preview:
                SPUtils.getInstance().put(SELECT_STATUS, 2001);
                break;
            case R.id.change_release:
                SPUtils.getInstance().put(SELECT_STATUS, 3001);
                break;
            case R.id.tv_restart_app:
                RestartAppUtils.restartAPP(this);
                break;
            default:
                break;
        }
    }


}

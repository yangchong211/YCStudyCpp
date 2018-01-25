package cn.ycbjie.ycaudioplayer.ui.guide.ui;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.ns.yc.yccountdownviewlib.CountDownView;
import com.pedaily.yc.ycdialoglib.toast.ToastUtil;

import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.base.BaseAppHelper;
import cn.ycbjie.ycaudioplayer.base.BaseActivity;
import cn.ycbjie.ycaudioplayer.service.PlayService;
import cn.ycbjie.ycaudioplayer.ui.MainActivity;
import cn.ycbjie.ycaudioplayer.ui.guide.contract.GuideContract;
import cn.ycbjie.ycaudioplayer.ui.guide.presenter.GuidePresenter;
import cn.ycbjie.ycaudioplayer.util.LogUtils;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by yc on 2018/1/19.
 */

public class GuideActivity extends BaseActivity implements GuideContract.View ,EasyPermissions.PermissionCallbacks {

    @Bind(R.id.iv_splash)
    ImageView ivSplash;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.cdv_time)
    CountDownView cdvTime;

    protected Handler mHandler = new Handler(Looper.getMainLooper());
    private GuideContract.Presenter presenter = new GuidePresenter(this);
    private PlayServiceConnection mPlayServiceConnection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        if (mPlayServiceConnection != null) {
            unbindService(mPlayServiceConnection);
        }
        super.onDestroy();
        presenter.unSubscribe();
        if(mHandler!=null){
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }

    /**
     * 屏蔽返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        setTimeText();
        startShowSplash();
        startUpdateSplash();
        initPermissions();
    }

    @Override
    public void initListener() {
        cdvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdvTime.stop();
                finish();
            }
        });
    }

    @Override
    public void initData() {

    }


    private void setTimeText() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        tvTime.setText(getString(R.string.guideTime, year));
    }

    /**
     * 开始启动倒计时
     * 关于倒计时自定义控件：https://github.com/yangchong211/YCCountDownView
     * 欢迎star
     */
    private void startLoading() {
        cdvTime.setTime(5);
        cdvTime.start();
        cdvTime.setOnLoadingFinishListener(new CountDownView.OnLoadingFinishListener() {
            @Override
            public void finish() {
                //跳转主页面
                toMainActivity();
            }
        });
    }

    /**
     * 检测服务
     */
    private void startCheckService() {
        if (BaseAppHelper.get().getPlayService() == null) {
            startService();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //绑定服务
                    bindService();
                }
            }, 1000);
        }
    }

    /**
     * 开启服务
     */
    private void startService() {
        Intent intent = new Intent(this, PlayService.class);
        startService(intent);
    }

    /**
     * 绑定服务
     * 注意对于绑定服务一定要解绑
     */
    private void bindService() {
        Intent intent = new Intent();
        intent.setClass(this, PlayService.class);
        mPlayServiceConnection = new PlayServiceConnection();
        bindService(intent, mPlayServiceConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * 展示图片，从网络获取
     */
    private void startShowSplash() {
        ivSplash.setImageResource(R.drawable.bg_page_background);
    }

    /**
     * 从网络获取图片
     */
    private void startUpdateSplash() {

    }

    /**
     * 直接跳转主页面
     */
    private void toMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
        finish();
    }

    private class PlayServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtils.e("onServiceConnected"+name);
            final PlayService playService = ((PlayService.PlayBinder) service).getService();
            BaseAppHelper.get().setPlayService(playService);
            scanMusic(playService);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtils.e("onServiceDisconnected"+name);
        }
    }

    /**
     * 可以在服务中扫描本地音乐
     * @param playService           service
     */
    private void scanMusic(PlayService playService) {
        playService.updateMusicList(null);
    }


    /**
     * 这个地方一定先要初始化权限设置，因为对于音频播放，会扫描本地音乐，涉及权限。如果不允许，可能会导致崩溃。
     * 但是这样体验可能不太好，如果不允许权限，则直接退出。
     * 后来发现华为音乐，混沌大学，QQ音乐等，没有打开权限，都是直接退出的。
     */
    private void initPermissions() {
        locationPermissionsTask();
    }

    private static final int RC_LOCATION_CONTACTS_PERM = 124;
    private static final String[] LOCATION_AND_CONTACTS = {
            Manifest.permission.CALL_PHONE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
    };

    @AfterPermissionGranted(RC_LOCATION_CONTACTS_PERM)
    public void locationPermissionsTask() {
        //检查是否获取该权限
        if (hasPermissions()) {
            //具备权限 直接进行操作
            startLoading();
            startCheckService();
        } else {
            //权限拒绝 申请权限
            //第二个参数是被拒绝后再次申请该权限的解释
            //第三个参数是请求码
            //第四个参数是要申请的权限
            EasyPermissions.requestPermissions(this,
                    getString(R.string.easy_permissions), RC_LOCATION_CONTACTS_PERM, LOCATION_AND_CONTACTS);
        }
    }

    /**
     * 判断是否添加了权限
     *
     * @return true
     */
    private boolean hasPermissions() {
        return EasyPermissions.hasPermissions(this, LOCATION_AND_CONTACTS);
    }

    /**
     * 将结果转发到EasyPermissions
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 将结果转发到EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * 某些权限已被授予
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        //某些权限已被授予
        Log.d("权限", "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    /**
     * 某些权限已被拒绝
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        //某些权限已被拒绝
        Log.d("权限", "onPermissionsDenied:" + requestCode + ":" + perms.size());
        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(GuideActivity.this, perms)) {
            //new AppSettingsDialog.Builder(MainActivity.this).build().show();
            AppSettingsDialog.Builder builder = new AppSettingsDialog.Builder(GuideActivity.this);
            builder.setTitle("允许权限")
                    .setRationale("没有该权限，此应用程序部分功能可能无法正常工作。打开应用设置界面以修改应用权限")
                    .setPositiveButton("去设置")
                    .setNegativeButton("取消")
                    .setRequestCode(RC_LOCATION_CONTACTS_PERM)
                    .build()
                    .show();
        }else {
            ToastUtils.showShort("没有存储空间权限，无法扫描本地歌曲！");
            finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
            // 当用户从应用设置界面返回的时候，可以做一些事情
            toMainActivity();
        }
    }



}

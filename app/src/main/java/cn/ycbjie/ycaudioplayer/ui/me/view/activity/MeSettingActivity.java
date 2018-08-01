package cn.ycbjie.ycaudioplayer.ui.me.view.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.ns.yc.ycutilslib.loadingDialog.LoadDialog;
import com.ns.yc.ycutilslib.switchButton.SwitchButton;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ycbjie.ycaudioplayer.BuildConfig;
import cn.ycbjie.ycaudioplayer.DebugActivity;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.base.app.BaseApplication;
import cn.ycbjie.ycaudioplayer.base.view.BaseActivity;
import cn.ycbjie.ycaudioplayer.ui.webView.WebViewActivity;
import cn.ycbjie.ycaudioplayer.utils.file.FileCacheUtils;
import cn.ycbjie.ycaudioplayer.utils.url.UrlUtils;
import cn.ycbjie.ycstatusbarlib.bar.YCAppBar;
import cn.ycbjie.ycthreadpoollib.PoolThread;
import cn.ycbjie.ycthreadpoollib.callback.ThreadCallback;


public class MeSettingActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.tv_title_left)
    TextView tvTitleLeft;
    @Bind(R.id.ll_title_menu)
    FrameLayout llTitleMenu;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.iv_right_img)
    ImageView ivRightImg;
    @Bind(R.id.ll_search)
    FrameLayout llSearch;
    @Bind(R.id.tv_title_right)
    TextView tvTitleRight;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rl_set_go_star)
    RelativeLayout rlSetGoStar;
    @Bind(R.id.switch_pic)
    SwitchButton switchPic;
    @Bind(R.id.switch_button)
    SwitchButton switchButton;
    @Bind(R.id.switch_night)
    SwitchButton switchNight;
    @Bind(R.id.tv_set_cache_size)
    TextView tvSetCacheSize;
    @Bind(R.id.rl_set_clean_cache)
    RelativeLayout rlSetCleanCache;
    @Bind(R.id.rl_set_revise_pwd)
    RelativeLayout rlSetRevisePwd;
    @Bind(R.id.tv_is_cert)
    TextView tvIsCert;
    @Bind(R.id.rl_set_phone)
    RelativeLayout rlSetPhone;
    @Bind(R.id.rl_set_about_us)
    RelativeLayout rlSetAboutUs;
    @Bind(R.id.rl_set_binding)
    RelativeLayout rlSetBinding;
    @Bind(R.id.rl_set_feedback)
    RelativeLayout rlSetFeedback;
    @Bind(R.id.tv_update_name)
    TextView tvUpdateName;
    @Bind(R.id.rl_set_update)
    RelativeLayout rlSetUpdate;
    @Bind(R.id.tv_exit)
    TextView tvExit;

    // 数组长度代表点击次数
    private long[] mHits = new long[3];

    @Override
    public int getContentView() {
        return R.layout.activity_me_setting;
    }

    @Override
    public void initView() {
        initActionBar();
        YCAppBar.setStatusBarColor(this, ContextCompat.getColor(this, R.color.redTab));
    }

    private void initActionBar() {
        if (BuildConfig.IS_DEBUG) {
            toolbarTitle.setText("个人设置(3连击切换调试页面)");
        } else {
            toolbarTitle.setText("个人设置");
        }
    }

    @Override
    public void initListener() {
        rlSetCleanCache.setOnClickListener(this);
        toolbarTitle.setOnClickListener(this);
    }

    @Override
    public void initData() {
        initCacheSize();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_set_clean_cache:
                cleanCache();
                break;
            case R.id.toolbar_title:
                if (BuildConfig.IS_DEBUG) {
                    // 数组依次先前移动一位
                    System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
                    // 开机后运行时间
                    mHits[mHits.length - 1] = SystemClock.uptimeMillis();
                    if (mHits[0] >= (mHits[mHits.length - 1] - 500)) {
                        mHits = new long[3];
                        ActivityUtils.startActivity(DebugActivity.class);
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 获取App缓存大小
     * hwmc/crash        hwmc/share/images
     * hwmc目录文件夹：crash崩溃日志；downApk下载文件；share分享保存图片文件
     * 注意：该目录文件不是data/data目录下，
     */
    private void initCacheSize() {
        //目前只是展示内部缓存的大小
        try {
            if (FileCacheUtils.isSDCardEnable(this)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (this.getCacheDir() == null || this.getCodeCacheDir() == null) {
                        return;
                    }
                }
                long cache = FileCacheUtils.getFolderSize(this.getCacheDir());
                long size = cache / (1024 * 1024);
                //当大于5M时显示
                if (size > 0) {
                    String formatSize = FileCacheUtils.getFormatSize(cache);
                    tvSetCacheSize.setText(formatSize);
                } else {
                    tvSetCacheSize.setText("");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void cleanCache() {
        PoolThread executor = BaseApplication.getInstance().getExecutor();
        LoadDialog.show(this, "清除中");
        executor.setDelay(2, TimeUnit.SECONDS);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                String path = BaseApplication.getInstance().getCacheDir().getPath();
                FileCacheUtils.deleteFolderFile(path, true);
            }
        });
        executor.setCallback(new ThreadCallback() {
            @Override
            public void onError(String threadName, Throwable t) {

            }

            @Override
            public void onCompleted(String threadName) {
                LoadDialog.dismiss(MeSettingActivity.this);
            }

            @Override
            public void onStart(String threadName) {

            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

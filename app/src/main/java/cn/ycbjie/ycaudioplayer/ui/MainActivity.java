package cn.ycbjie.ycaudioplayer.ui;

import android.Manifest;
import android.content.Intent;
import android.content.res.TypedArray;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.ns.yc.ycutilslib.viewPager.NoSlidingViewPager;
import com.pedaily.yc.ycdialoglib.toast.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.base.BaseActivity;
import cn.ycbjie.ycaudioplayer.base.BaseAppHelper;
import cn.ycbjie.ycaudioplayer.base.BasePagerAdapter;
import cn.ycbjie.ycaudioplayer.inter.OnPlayerEventListener;
import cn.ycbjie.ycaudioplayer.model.TabEntity;
import cn.ycbjie.ycaudioplayer.service.PlayService;
import cn.ycbjie.ycaudioplayer.ui.local.model.LocalMusic;
import cn.ycbjie.ycaudioplayer.ui.local.view.PlayMusicFragment;
import cn.ycbjie.ycaudioplayer.ui.me.MeFragment;
import cn.ycbjie.ycaudioplayer.ui.music.MusicFragment;
import cn.ycbjie.ycaudioplayer.ui.practise.PractiseFragment;
import cn.ycbjie.ycaudioplayer.ui.study.StudyFragment;
import cn.ycbjie.ycaudioplayer.util.musicUtils.CoverLoader;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends BaseActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.iv_menu)
    ImageView ivMenu;
    @Bind(R.id.tv_local_music)
    public TextView tvLocalMusic;
    @Bind(R.id.tv_online_music)
    public TextView tvOnlineMusic;
    @Bind(R.id.tv_cut_music)
    public TextView tvCutMusic;
    @Bind(R.id.ll_music)
    LinearLayout llMusic;
    @Bind(R.id.tv_bar_title)
    TextView tvBarTitle;
    @Bind(R.id.ll_other)
    LinearLayout llOther;
    @Bind(R.id.iv_search)
    TextView ivSearch;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.vp_home)
    NoSlidingViewPager vpHome;
    @Bind(R.id.ctl_table)
    CommonTabLayout ctlTable;
    @Bind(R.id.navigation_view)
    NavigationView navigationView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.iv_play_bar_cover)
    ImageView ivPlayBarCover;
    @Bind(R.id.tv_play_bar_title)
    TextView tvPlayBarTitle;
    @Bind(R.id.tv_play_bar_artist)
    TextView tvPlayBarArtist;
    @Bind(R.id.iv_play_bar_list)
    ImageView ivPlayBarList;
    @Bind(R.id.iv_play_bar_play)
    ImageView ivPlayBarPlay;
    @Bind(R.id.iv_play_bar_next)
    ImageView ivPlayBarNext;
    @Bind(R.id.pb_play_bar)
    ProgressBar pbPlayBar;
    @Bind(R.id.fl_play_bar)
    public FrameLayout flPlayBar;
    @Bind(R.id.ll_main)
    LinearLayout llMain;

    private MusicFragment musicFragment;
    private boolean isPlayFragmentShow = false;
    private PlayMusicFragment mPlayFragment;


    @Override
    public void onBackPressed() {
        if (mPlayFragment != null && isPlayFragmentShow) {
            hidePlayingFragment();
            return;
        }
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        PlayService service = BaseAppHelper.get().getPlayService();
        if (service != null) {
            service.setOnPlayEventListener(null);
        }
        super.onDestroy();
    }


    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }


    @Override
    public void initView() {
        initFragment();
        initTabLayout();
        initNavigationView();
        initPermissions();
        initPlayServiceListener();
    }


    @Override
    public void initListener() {
        ivMenu.setOnClickListener(this);
        ivSearch.setOnClickListener(this);
        tvLocalMusic.setOnClickListener(this);
        tvOnlineMusic.setOnClickListener(this);
        tvCutMusic.setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
                drawerLayout.closeDrawers();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        item.setChecked(false);
                    }
                }, 500);
                return NavigationExecutor.onNavigationItemSelected(item, MainActivity.this);
            }
        });

        //音乐播放
        flPlayBar.setOnClickListener(this);
        ivPlayBarList.setOnClickListener(this);
        ivPlayBarPlay.setOnClickListener(this);
        ivPlayBarNext.setOnClickListener(this);
    }

    @Override
    public void initData() {
        onChangeImpl(getPlayService().getPlayingMusic());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_menu:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.iv_search:
                ToastUtil.showToast(this, "搜索");
                break;
            case R.id.tv_local_music:
                musicFragment.vpMusic.setCurrentItem(0);
                flPlayBar.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_online_music:
                musicFragment.vpMusic.setCurrentItem(1);
                flPlayBar.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_cut_music:
                musicFragment.vpMusic.setCurrentItem(2);
                flPlayBar.setVisibility(View.GONE);
                break;
            case R.id.fl_play_bar:
                showPlayingFragment();
                break;
            case R.id.iv_play_bar_list:
                ToastUtil.showToast(this, "谈出对话框");
                break;
            case R.id.iv_play_bar_play:
                ToastUtil.showToast(this, "播放");
                break;
            case R.id.iv_play_bar_next:
                ToastUtil.showToast(this, "下一首");
                break;
            default:
                break;
        }
    }

    private void initFragment() {
        List<Fragment> fragments = new ArrayList<>();
        StudyFragment studyFragment = new StudyFragment();
        PractiseFragment practiseFragment = new PractiseFragment();
        musicFragment = new MusicFragment();
        MeFragment meFragment = new MeFragment();
        fragments.add(studyFragment);
        fragments.add(practiseFragment);
        fragments.add(musicFragment);
        fragments.add(meFragment);

        BasePagerAdapter adapter = new BasePagerAdapter(getSupportFragmentManager(), fragments);
        vpHome.setAdapter(adapter);
        vpHome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                ctlTable.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        vpHome.setOffscreenPageLimit(4);
        vpHome.setCurrentItem(0);
        tvBarTitle.setText("上课");
    }


    private void initTabLayout() {
        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
        TypedArray mIconUnSelectIds = this.getResources().obtainTypedArray(R.array.main_tab_un_select);
        TypedArray mIconSelectIds = this.getResources().obtainTypedArray(R.array.main_tab_select);
        String[] mainTitles = this.getResources().getStringArray(R.array.main_title);
        for (int i = 0; i < mainTitles.length; i++) {
            int unSelectId = mIconUnSelectIds.getResourceId(i, R.drawable.ic_tab_main_art_uncheck);
            int selectId = mIconSelectIds.getResourceId(i, R.drawable.ic_tab_main_art_checked);
            mTabEntities.add(new TabEntity(mainTitles[i], selectId, unSelectId));
        }
        mIconUnSelectIds.recycle();
        mIconSelectIds.recycle();
        ctlTable.setTabData(mTabEntities);
        llMusic.setVisibility(View.GONE);
        llOther.setVisibility(View.VISIBLE);
        ctlTable.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vpHome.setCurrentItem(position);
                flPlayBar.setVisibility(View.VISIBLE);
                switch (position) {
                    case 0:
                        llMusic.setVisibility(View.GONE);
                        llOther.setVisibility(View.VISIBLE);
                        tvBarTitle.setText("上课");
                        break;
                    case 1:
                        llMusic.setVisibility(View.GONE);
                        llOther.setVisibility(View.VISIBLE);
                        tvBarTitle.setText("练习");
                        break;
                    case 2:
                        llMusic.setVisibility(View.VISIBLE);
                        llOther.setVisibility(View.GONE);
                        break;
                    case 3:
                        llMusic.setVisibility(View.GONE);
                        llOther.setVisibility(View.VISIBLE);
                        tvBarTitle.setText("我的");
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }


    private void initNavigationView() {
        View header = LayoutInflater.from(this).inflate(R.layout.navigation_header, navigationView, false);
        navigationView.addHeaderView(header);
    }


    private void showPlayingFragment() {
        if (isPlayFragmentShow) {
            return;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_slide_up, 0);
        if (mPlayFragment == null) {
            mPlayFragment = new PlayMusicFragment();
            ft.replace(android.R.id.content, mPlayFragment);
        } else {
            ft.show(mPlayFragment);
        }
        ft.commitAllowingStateLoss();
        isPlayFragmentShow = true;
    }


    private void hidePlayingFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(0, R.anim.fragment_slide_down);
        ft.hide(mPlayFragment);
        ft.commitAllowingStateLoss();
        isPlayFragmentShow = false;
    }


    /**
     * 初始化权限
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
            //Toast.makeText(this, "Location and Contacts things", Toast.LENGTH_LONG).show();
        } else {
            //权限拒绝 申请权限
            //第二个参数是被拒绝后再次申请该权限的解释
            //第三个参数是请求码
            //第四个参数是要申请的权限
            EasyPermissions.requestPermissions(MainActivity.this,
                    getString(R.string.easy_permissions), RC_LOCATION_CONTACTS_PERM, LOCATION_AND_CONTACTS);
        }
    }

    /**
     * 判断是否添加了权限
     *
     * @return true
     */
    private boolean hasPermissions() {
        return EasyPermissions.hasPermissions(MainActivity.this, LOCATION_AND_CONTACTS);
    }

    /**
     * 将结果转发到EasyPermissions
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 将结果转发到EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, MainActivity.this);
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
        if (EasyPermissions.somePermissionPermanentlyDenied(MainActivity.this, perms)) {
            //new AppSettingsDialog.Builder(MainActivity.this).build().show();
            AppSettingsDialog.Builder builder = new AppSettingsDialog.Builder(MainActivity.this);
            builder.setTitle("允许权限")
                    .setRationale("没有该权限，此应用程序部分功能可能无法正常工作。打开应用设置界面以修改应用权限")
                    .setPositiveButton("去设置")
                    .setNegativeButton("取消")
                    .setRequestCode(RC_LOCATION_CONTACTS_PERM)
                    .build()
                    .show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
            // 当用户从应用设置界面返回的时候，可以做一些事情，比如弹出一个土司。
        }
    }


    /**
     * 初始化服务播放音频播放进度监听器
     */
    public void initPlayServiceListener() {
        getPlayService().setOnPlayEventListener(new OnPlayerEventListener() {
            @Override
            public void onChange(LocalMusic music) {
                onChangeImpl(music);
                if (mPlayFragment != null && mPlayFragment.isAdded()) {
                    mPlayFragment.onChange(music);
                }
            }

            @Override
            public void onPlayerStart() {
                ivPlayBarPlay.setSelected(true);
                if (mPlayFragment != null && mPlayFragment.isAdded()) {
                    mPlayFragment.onPlayerStart();
                }
            }

            @Override
            public void onPlayerPause() {
                ivPlayBarPlay.setSelected(false);
                if (mPlayFragment != null && mPlayFragment.isAdded()) {
                    mPlayFragment.onPlayerPause();
                }
            }
        });
    }


    /**
     * 当在播放音频详细页面切换歌曲的时候，需要刷新底部控制器，和音频详细页面的数据
     * 之前关于activity，Fragment，service之间用EventBus通信
     * 案例：https://github.com/yangchong211/LifeHelper
     * 本项目中直接通过定义接口来实现功能，尝试中……
     * @param music             LocalMusic
     */
    private void onChangeImpl(LocalMusic music) {
        if (music == null) {
            return;
        }
        Bitmap cover = CoverLoader.getInstance().loadThumbnail(music);
        ivPlayBarCover.setImageBitmap(cover);
        tvPlayBarTitle.setText(music.getTitle());
        tvPlayBarArtist.setText(music.getArtist());
        ivPlayBarPlay.setSelected(getPlayService().isPlaying() || getPlayService().isPreparing());
        pbPlayBar.setMax((int) music.getDuration());
        pbPlayBar.setProgress((int) getPlayService().getCurrentPosition());
        /*if (mLocalMusicFragment != null && mLocalMusicFragment.isAdded()) {
            mLocalMusicFragment.onItemPlay();
        }*/
    }


}

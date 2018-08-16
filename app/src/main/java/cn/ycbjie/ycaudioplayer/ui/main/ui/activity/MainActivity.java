package cn.ycbjie.ycaudioplayer.ui.main.ui.activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ServiceUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.ns.yc.ycutilslib.managerLeak.InputMethodManagerLeakUtils;
import com.pedaily.yc.ycdialoglib.bottomLayout.BottomDialogFragment;
import com.pedaily.yc.ycdialoglib.customToast.ToastUtil;

import org.yczbj.ycrefreshviewlib.item.RecycleViewItemLine;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.constant.Constant;
import cn.ycbjie.ycaudioplayer.base.view.BaseActivity;
import cn.ycbjie.ycaudioplayer.base.BaseAppHelper;
import cn.ycbjie.ycaudioplayer.base.BaseFragmentFactory;
import cn.ycbjie.ycaudioplayer.inter.listener.OnListItemClickListener;
import cn.ycbjie.ycaudioplayer.inter.listener.OnPlayerEventListener;
import cn.ycbjie.ycaudioplayer.model.bean.TabEntity;
import cn.ycbjie.ycaudioplayer.service.AppLogService;
import cn.ycbjie.ycaudioplayer.ui.me.view.fragment.MeFragment;
import cn.ycbjie.ycaudioplayer.ui.music.view.fragment.MusicFragment;
import cn.ycbjie.ycaudioplayer.model.bean.AudioBean;
import cn.ycbjie.ycaudioplayer.ui.music.view.adapter.DialogMusicListAdapter;
import cn.ycbjie.ycaudioplayer.ui.music.view.fragment.PlayMusicFragment;
import cn.ycbjie.ycaudioplayer.ui.news.ui.fragment.PractiseFragment;
import cn.ycbjie.ycaudioplayer.ui.home.ui.fragment.HomeFragment;
import cn.ycbjie.ycaudioplayer.utils.app.AppToolUtils;
import cn.ycbjie.ycaudioplayer.utils.logger.AppLogUtils;
import cn.ycbjie.ycaudioplayer.utils.musicUtils.CoverLoader;
import cn.ycbjie.ycstatusbarlib.bar.YCAppBar;

import static com.meituan.android.walle.WalleChannelReader.*;

/**
 * 关于bug整理，主页面
 *
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.fl_main)
    FrameLayout flMain;
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
    FrameLayout flPlayBar;
    @Bind(R.id.ctl_table)
    CommonTabLayout ctlTable;
    @Bind(R.id.ll_main)
    LinearLayout llMain;

    private Bundle bundle;
    private MusicFragment mMusicFragment;
    private HomeFragment mHomeFragment;
    private PractiseFragment mPractiseFragment;
    private MeFragment mMeFragment;
    private PlayMusicFragment mPlayFragment;

    private static final int FRAGMENT_MUSIC = 0;
    private static final int FRAGMENT_HOME = 1;
    private static final int FRAGMENT_PRACTISE = 2;
    private static final int FRAGMENT_ME = 3;

    private static final String POSITION = "position";
    private static final String SELECT_ITEM = "selectItem";
    private int positionIndex;
    private boolean isPlayFragmentShow = false;
    private long firstClickTime = 0;
    private long time;


    /*@Override
    public void onBackPressed() {
        if (mPlayFragment != null && isPlayFragmentShow) {
            hidePlayingFragment();
            return;
        }
        super.onBackPressed();
    }*/

    /**
     * 是当某个按键被按下是触发。所以也有人在点击返回键的时候去执行该方法来做判断
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LogUtils.e("触摸监听", "onKeyDown");
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mPlayFragment != null && isPlayFragmentShow) {
                hidePlayingFragment();
            }else {
                //双击返回桌面
                if ((System.currentTimeMillis() - time > 1000)) {
                    ToastUtil.showToast(MainActivity.this, "再按一次返回桌面");
                    time = System.currentTimeMillis();
                } else {
                    moveTaskToBack(true);
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        InputMethodManagerLeakUtils.fixInputMethodManagerLeak(this);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // recreate 时记录当前位置 (在 Manifest 已禁止 Activity 旋转,所以旋转屏幕并不会执行以下代码)
        // 程序意外崩溃时保存状态信息
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION, positionIndex);
        outState.putInt(SELECT_ITEM, ctlTable.getCurrentTab());
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = savedInstanceState;
    }


    @Override
    public int getContentView() {
        return R.layout.activity_home_main;
    }


    @Override
    public void initView() {
        //音频播放器需要让服务长期存在
        if (!checkServiceAlive()) {
            return;
        }
        YCAppBar.setStatusBarColor(this, ContextCompat.getColor(this, R.color.redTab));
        initFragment();
        initTabLayout();
        initPlayServiceListener();
        parseIntent();
        AppToolUtils.requestMsgPermission(this);
        ServiceUtils.startService(AppLogService.class);
        String channel = getChannel(this.getApplicationContext());
        AppLogUtils.eTag("渠道"+channel);
    }

    /**
     * 处理onNewIntent()，以通知碎片管理器 状态未保存。
     * 如果您正在处理新的意图，并且可能是 对碎片状态进行更改时，要确保调用先到这里。
     * 否则，如果你的状态保存，但活动未停止，则可以获得 onNewIntent()调用，发生在onResume()之前，
     * 并试图 此时执行片段操作将引发IllegalStateException。 因为碎片管理器认为状态仍然保存。
     * @param intent intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        parseIntent();
    }


    @Override
    public void initListener() {
        //音乐播放
        flPlayBar.setOnClickListener(this);
        ivPlayBarList.setOnClickListener(this);
        ivPlayBarPlay.setOnClickListener(this);
        ivPlayBarNext.setOnClickListener(this);
    }


    @Override
    public void initData() {
        //当在播放音频详细页面切换歌曲的时候，需要刷新底部控制器，和音频详细页面的数据
        onChangeImpl(getPlayService().getPlayingMusic());
    }

    /**
     * 当关闭锁屏页面(锁屏页面为栈顶页面)的时候，会返回该MainActivity
     * 那么则需要刷新页面数据
     */
    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_search:
                ActivityUtils.startActivity(SearchMusicActivity.class);
                break;
            case R.id.fl_play_bar:
                showPlayingFragment();
                break;
            case R.id.iv_play_bar_list:
                showListDialog();
                break;
            case R.id.iv_play_bar_play:
                getPlayService().playPause();
                break;
            case R.id.iv_play_bar_next:
                getPlayService().next();
                break;
            default:
                break;
        }
    }


    private void initFragment() {
        if (bundle != null) {
            int index = bundle.getInt(POSITION);
            mMusicFragment = BaseFragmentFactory.getInstance().getMusicFragment();
            mHomeFragment = BaseFragmentFactory.getInstance().getHomeFragment();
            mPractiseFragment = BaseFragmentFactory.getInstance().getPractiseFragment();
            mMeFragment = BaseFragmentFactory.getInstance().getMeFragment();
            showFragment(index);
            ctlTable.setCurrentTab(bundle.getInt(SELECT_ITEM));
        } else {
            showFragment(FRAGMENT_MUSIC);
        }
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
        ctlTable.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                flPlayBar.setVisibility(View.VISIBLE);
                switch (position) {
                    case 0:
                        showFragment(FRAGMENT_MUSIC);
                        doubleClick(FRAGMENT_MUSIC);
                        break;
                    case 1:
                        showFragment(FRAGMENT_HOME);
                        break;
                    case 2:
                        showFragment(FRAGMENT_PRACTISE);
                        break;
                    case 3:
                        showFragment(FRAGMENT_ME);
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


    private void showFragment(int index) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        hideFragment(ft);
        positionIndex = index;
        switch (index) {
            case FRAGMENT_MUSIC:
                if (mMusicFragment == null) {
                    mMusicFragment = BaseFragmentFactory.getInstance().getMusicFragment();
                    ft.add(R.id.fl_main, mMusicFragment, MusicFragment.class.getName());
                }
                ft.show(mMusicFragment);
                break;
            case FRAGMENT_PRACTISE:
                if (mPractiseFragment == null) {
                    mPractiseFragment = BaseFragmentFactory.getInstance().getPractiseFragment();
                    ft.add(R.id.fl_main, mPractiseFragment, PractiseFragment.class.getName());
                }
                ft.show(mPractiseFragment);
                break;
            case FRAGMENT_HOME:
                /*
                 * 如果Fragment为空，就新建一个实例
                 * 如果不为空，就将它从栈中显示出来
                 */
                if (mHomeFragment == null) {
                    mHomeFragment = BaseFragmentFactory.getInstance().getHomeFragment();
                    ft.add(R.id.fl_main, mHomeFragment, HomeFragment.class.getName());
                }
                ft.show(mHomeFragment);
                break;
            case FRAGMENT_ME:
                if (mMeFragment == null) {
                    mMeFragment = BaseFragmentFactory.getInstance().getMeFragment();
                    ft.add(R.id.fl_main, mMeFragment, MeFragment.class.getName());
                }
                ft.show(mMeFragment);
                break;
            default:
                break;
        }
        ft.commit();
    }


    private void hideFragment(FragmentTransaction ft) {
        // 如果不为空，就先隐藏起来
        if (mHomeFragment != null) {
            setHide(ft, mHomeFragment);
        }
        if (mPractiseFragment != null) {
            setHide(ft, mPractiseFragment);
        }
        if (mMusicFragment != null) {
            setHide(ft, mMusicFragment);
        }
        if (mMeFragment != null) {
            setHide(ft, mMeFragment);
        }
    }


    private void setHide(FragmentTransaction ft, Fragment fragment) {
        if (fragment.isAdded()) {
            ft.hide(fragment);
        }
    }


    private void doubleClick(int index) {
        long secondClickTime = System.currentTimeMillis();
        if ((secondClickTime - firstClickTime < Constant.CLICK_TIME)) {
            switch (index) {
                case FRAGMENT_MUSIC:
                    mMusicFragment.onDoubleClick();
                    break;
                default:
                    break;
            }
        } else {
            firstClickTime = secondClickTime;
        }
    }


    /**
     * 从通知栏点击进入音频播放详情页面
     */
    private void parseIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra(Constant.EXTRA_NOTIFICATION)) {
            showPlayingFragment();
            setIntent(new Intent());
        }
    }


    /**
     * 展示页面
     */
    private void showPlayingFragment() {
        if (isPlayFragmentShow) {
            return;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_slide_up, 0);
        if (mPlayFragment == null) {
            mPlayFragment = PlayMusicFragment.newInstance("Main");
            ft.replace(android.R.id.content, mPlayFragment);
        } else {
            ft.show(mPlayFragment);
        }
        ft.commitAllowingStateLoss();
        isPlayFragmentShow = true;
    }


    /**
     * 隐藏页面
     */
    private void hidePlayingFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(0, R.anim.fragment_slide_down);
        ft.hide(mPlayFragment);
        ft.commitAllowingStateLoss();
        isPlayFragmentShow = false;
    }


    /**
     * 初始化服务播放音频播放进度监听器
     * 这个是要是通过监听即时更新主页面的底部控制器视图
     * 同时还要同步播放详情页面mPlayFragment的视图
     */
    public void initPlayServiceListener() {
        if (getPlayService() == null) {
            return;
        }
        getPlayService().setOnPlayEventListener(new OnPlayerEventListener() {
            /**
             * 切换歌曲
             * 主要是切换歌曲的时候需要及时刷新界面信息
             */
            @Override
            public void onChange(AudioBean music) {
                onChangeImpl(music);
                if (mPlayFragment != null && mPlayFragment.isAdded()) {
                    mPlayFragment.onChange(music);
                }
            }

            /**
             * 继续播放
             * 主要是切换歌曲的时候需要及时刷新界面信息，比如播放暂停按钮
             */
            @Override
            public void onPlayerStart() {
                ivPlayBarPlay.setSelected(true);
                if (mPlayFragment != null && mPlayFragment.isAdded()) {
                    mPlayFragment.onPlayerStart();
                }
            }

            /**
             * 暂停播放
             * 主要是切换歌曲的时候需要及时刷新界面信息，比如播放暂停按钮
             */
            @Override
            public void onPlayerPause() {
                ivPlayBarPlay.setSelected(false);
                if (mPlayFragment != null && mPlayFragment.isAdded()) {
                    mPlayFragment.onPlayerPause();
                }
            }

            /**
             * 更新进度
             * 主要是播放音乐或者拖动进度条时，需要更新进度
             */
            @Override
            public void onUpdateProgress(int progress) {
                pbPlayBar.setProgress(progress);
                if (mPlayFragment != null && mPlayFragment.isAdded()) {
                    mPlayFragment.onUpdateProgress(progress);
                }
            }

            @Override
            public void onBufferingUpdate(int percent) {
                if (mPlayFragment != null && mPlayFragment.isAdded()) {
                    mPlayFragment.onBufferingUpdate(percent);
                }
            }

            /**
             * 更新定时停止播放时间
             */
            @Override
            public void onTimer(long remain) {

            }
        });
    }


    /**
     * 当在播放音频详细页面切换歌曲的时候，需要刷新底部控制器，和音频详细页面的数据
     * 之前关于activity，Fragment，service之间用EventBus通信
     * 案例：https://github.com/yangchong211/LifeHelper
     * 本项目中直接通过定义接口来实现功能，尝试中……
     *
     * @param music LocalMusic
     */
    private void onChangeImpl(AudioBean music) {
        if (music == null) {
            return;
        }
        Bitmap cover = CoverLoader.getInstance().loadThumbnail(music);
        ivPlayBarCover.setImageBitmap(cover);
        tvPlayBarTitle.setText(music.getTitle());
        tvPlayBarArtist.setText(music.getArtist());
        ivPlayBarPlay.setSelected(getPlayService().isPlaying() || getPlayService().isPreparing());
        //更新进度条
        pbPlayBar.setMax((int) music.getDuration());
        pbPlayBar.setProgress((int) getPlayService().getCurrentPosition());


        /*点击MainActivity中的控制器，如何更新musicFragment中的mLocalMusicFragment呢？*/
        if (mMusicFragment != null && mMusicFragment.isAdded()) {
            mMusicFragment.onItemPlay();
        }
    }


    public void showListDialog() {
        final List<AudioBean> musicList = BaseAppHelper.get().getMusicList();
        final BottomDialogFragment dialog = new BottomDialogFragment();
        dialog.setFragmentManager(getSupportFragmentManager());
        dialog.setViewListener(new BottomDialogFragment.ViewListener() {
            @Override
            public void bindView(View v) {
                RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
                TextView tvPlayType = (TextView) v.findViewById(R.id.tv_play_type);
                TextView tvCollect = (TextView) v.findViewById(R.id.tv_collect);
                ImageView ivClose = (ImageView) v.findViewById(R.id.iv_close);

                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                final DialogMusicListAdapter mAdapter = new DialogMusicListAdapter(MainActivity.this, musicList);
                recyclerView.setAdapter(mAdapter);
                mAdapter.updatePlayingPosition(getPlayService());
                final RecycleViewItemLine line = new RecycleViewItemLine(MainActivity.this, LinearLayout.HORIZONTAL,
                        SizeUtils.dp2px(1), MainActivity.this.getResources().getColor(R.color.grayLine));
                recyclerView.addItemDecoration(line);
                mAdapter.setOnItemClickListener(new OnListItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        List<AudioBean> musicList = BaseAppHelper.get().getMusicList();
                        getPlayService().play(musicList,position);
                        mAdapter.updatePlayingPosition(getPlayService());
                        mAdapter.notifyDataSetChanged();
                    }
                });
                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.tv_play_type:

                                break;
                            case R.id.tv_collect:
                                ToastUtil.showToast(MainActivity.this, "收藏，后期在做");
                                break;
                            case R.id.iv_close:
                                dialog.dismissDialogFragment();
                                break;
                            default:
                                break;
                        }
                    }
                };
                tvPlayType.setOnClickListener(listener);
                tvCollect.setOnClickListener(listener);
                ivClose.setOnClickListener(listener);
            }
        });
        dialog.setLayoutRes(R.layout.dialog_bottom_list_view);
        dialog.setDimAmount(0.5f);
        dialog.setTag("BottomDialogFragment");
        dialog.setCancelOutside(true);
        //这个高度可以自己设置，十分灵活
        dialog.setHeight(ScreenUtils.getScreenHeight() * 6 / 10);
        dialog.show();
    }


}

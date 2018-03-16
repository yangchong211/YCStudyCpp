package cn.ycbjie.ycaudioplayer.ui.main;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.ns.yc.ycutilslib.managerLeak.InputMethodManagerLeakUtils;
import com.pedaily.yc.ycdialoglib.bottomLayout.BottomDialogFragment;
import com.pedaily.yc.ycdialoglib.customToast.ToastUtil;

import org.yczbj.ycrefreshviewlib.item.RecycleViewItemLine;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.api.constant.Constant;
import cn.ycbjie.ycaudioplayer.base.AppManager;
import cn.ycbjie.ycaudioplayer.base.BaseActivity;
import cn.ycbjie.ycaudioplayer.base.BaseAppHelper;
import cn.ycbjie.ycaudioplayer.base.BaseFragmentFactory;
import cn.ycbjie.ycaudioplayer.inter.OnListItemClickListener;
import cn.ycbjie.ycaudioplayer.inter.OnPlayerEventListener;
import cn.ycbjie.ycaudioplayer.model.TabEntity;
import cn.ycbjie.ycaudioplayer.service.PlayService;
import cn.ycbjie.ycaudioplayer.ui.me.MeFragment;
import cn.ycbjie.ycaudioplayer.ui.music.MusicFragment;
import cn.ycbjie.ycaudioplayer.ui.music.local.model.AudioMusic;
import cn.ycbjie.ycaudioplayer.ui.music.local.view.DialogMusicListAdapter;
import cn.ycbjie.ycaudioplayer.ui.music.local.view.PlayMusicFragment;
import cn.ycbjie.ycaudioplayer.ui.practise.PractiseFragment;
import cn.ycbjie.ycaudioplayer.ui.study.ui.fragment.HomeFragment;
import cn.ycbjie.ycaudioplayer.util.musicUtils.CoverLoader;
import cn.ycbjie.ycstatusbarlib.bar.YCAppBar;

/**
 * 关于bug整理
 * 主页面
 */
public class MainHomeActivity extends BaseActivity implements View.OnClickListener {


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
    private HomeFragment mHomeFragment;
    private PractiseFragment mPractiseFragment;
    private MusicFragment mMusicFragment;
    private MeFragment mMeFragment;
    private PlayMusicFragment mPlayFragment;

    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_PRACTISE = 1;
    private static final int FRAGMENT_MUSIC = 2;
    private static final int FRAGMENT_ME = 3;
    private static final String POSITION = "position";
    private static final String SELECT_ITEM = "selectItem";
    private int positionIndex;
    private boolean isPlayFragmentShow = false;
    private long firstClickTime = 0;


    @Override
    public void onBackPressed() {
        if (mPlayFragment != null && isPlayFragmentShow) {
            hidePlayingFragment();
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
                startActivity(SearchMusicActivity.class);
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
            mHomeFragment = BaseFragmentFactory.getInstance().getHomeFragment();
            mPractiseFragment = BaseFragmentFactory.getInstance().getPractiseFragment();
            mMusicFragment = BaseFragmentFactory.getInstance().getMusicFragment();
            mMeFragment = BaseFragmentFactory.getInstance().getMeFragment();
            int index = bundle.getInt(POSITION);
            showFragment(index);
            ctlTable.setCurrentTab(bundle.getInt(SELECT_ITEM));
        } else {
            showFragment(FRAGMENT_HOME);
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
                        showFragment(FRAGMENT_HOME);
                        break;
                    case 1:
                        showFragment(FRAGMENT_PRACTISE);
                        break;
                    case 2:
                        showFragment(FRAGMENT_MUSIC);
                        doubleClick(FRAGMENT_MUSIC);
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
            case FRAGMENT_HOME:
                /*
                 * 如果Fragment为空，就新建一个实例
                 * 如果不为空，就将它从栈中显示出来
                 */
                if (mHomeFragment == null) {
                    mHomeFragment = BaseFragmentFactory.getInstance().getHomeFragment();
                    ft.add(R.id.fl_main, mHomeFragment, HomeFragment.class.getName());
                } else {
                    ft.show(mHomeFragment);
                }
                break;
            case FRAGMENT_PRACTISE:
                if (mPractiseFragment == null) {
                    mPractiseFragment = BaseFragmentFactory.getInstance().getPractiseFragment();
                    ft.add(R.id.fl_main, mPractiseFragment, PractiseFragment.class.getName());
                } else {
                    ft.show(mPractiseFragment);
                }
                break;
            case FRAGMENT_MUSIC:
                if (mMusicFragment == null) {
                    mMusicFragment = BaseFragmentFactory.getInstance().getMusicFragment();
                    ft.add(R.id.fl_main, mMusicFragment, MusicFragment.class.getName());
                } else {
                    ft.show(mMusicFragment);
                }
                break;
            case FRAGMENT_ME:
                if (mMeFragment == null) {
                    mMeFragment = BaseFragmentFactory.getInstance().getMeFragment();
                    ft.add(R.id.fl_main, mMeFragment, MeFragment.class.getName());
                } else {
                    ft.show(mMeFragment);
                }
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
            mPlayFragment = new PlayMusicFragment();
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
            public void onChange(AudioMusic music) {
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
    private void onChangeImpl(AudioMusic music) {
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
        final List<AudioMusic> musicList = BaseAppHelper.get().getMusicList();
        final BottomDialogFragment dialog = new BottomDialogFragment();
        dialog.setFragmentManager(getSupportFragmentManager());
        dialog.setViewListener(new BottomDialogFragment.ViewListener() {
            @Override
            public void bindView(View v) {
                RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
                TextView tvPlayType = (TextView) v.findViewById(R.id.tv_play_type);
                TextView tvCollect = (TextView) v.findViewById(R.id.tv_collect);
                ImageView ivClose = (ImageView) v.findViewById(R.id.iv_close);

                recyclerView.setLayoutManager(new LinearLayoutManager(MainHomeActivity.this));
                final DialogMusicListAdapter mAdapter = new DialogMusicListAdapter(MainHomeActivity.this, musicList);
                recyclerView.setAdapter(mAdapter);
                mAdapter.updatePlayingPosition(getPlayService());
                final RecycleViewItemLine line = new RecycleViewItemLine(MainHomeActivity.this, LinearLayout.HORIZONTAL,
                        SizeUtils.dp2px(1), MainHomeActivity.this.getResources().getColor(R.color.grayLine));
                recyclerView.addItemDecoration(line);
                mAdapter.setOnItemClickListener(new OnListItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        List<AudioMusic> musicList = BaseAppHelper.get().getMusicList();
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
                                ToastUtil.showToast(MainHomeActivity.this, "收藏，后期在做");
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

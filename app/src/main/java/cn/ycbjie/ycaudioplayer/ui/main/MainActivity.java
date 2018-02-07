package cn.ycbjie.ycaudioplayer.ui.main;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.ns.yc.ycutilslib.viewPager.NoSlidingViewPager;
import com.pedaily.yc.ycdialoglib.bottomLayout.BottomDialogFragment;
import com.pedaily.yc.ycdialoglib.customToast.ToastUtil;

import org.yczbj.ycrefreshviewlib.item.RecycleViewItemLine;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.api.Constant;
import cn.ycbjie.ycaudioplayer.base.BaseActivity;
import cn.ycbjie.ycaudioplayer.base.BaseAppHelper;
import cn.ycbjie.ycaudioplayer.base.BasePagerAdapter;
import cn.ycbjie.ycaudioplayer.inter.OnListItemClickListener;
import cn.ycbjie.ycaudioplayer.inter.OnPlayerEventListener;
import cn.ycbjie.ycaudioplayer.model.TabEntity;
import cn.ycbjie.ycaudioplayer.service.PlayService;
import cn.ycbjie.ycaudioplayer.ui.music.local.model.LocalMusic;
import cn.ycbjie.ycaudioplayer.ui.music.local.view.DialogMusicListAdapter;
import cn.ycbjie.ycaudioplayer.ui.music.local.view.PlayMusicFragment;
import cn.ycbjie.ycaudioplayer.ui.me.MeFragment;
import cn.ycbjie.ycaudioplayer.ui.music.MusicFragment;
import cn.ycbjie.ycaudioplayer.ui.practise.PractiseFragment;
import cn.ycbjie.ycaudioplayer.ui.study.ui.fragment.StudyFragment;
import cn.ycbjie.ycaudioplayer.util.AppUtils;
import cn.ycbjie.ycaudioplayer.util.musicUtils.CoverLoader;

/**
 * 关于bug整理
 * 1.当定时结束后，程序崩溃
 */
public class MainActivity extends BaseActivity implements View.OnClickListener{

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
    @Bind(R.id.tv_search)
    TextView tvSearch;
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
        initPlayServiceListener();
        parseIntent();
    }


    /**
     * 这个方法的作用是？？？
     * @param intent        intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        parseIntent();
    }

    @Override
    public void initListener() {
        ivMenu.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
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
        //当在播放音频详细页面切换歌曲的时候，需要刷新底部控制器，和音频详细页面的数据
        onChangeImpl(getPlayService().getPlayingMusic());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_menu:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.tv_search:
                startActivity(SearchMusicActivity.class);
                break;
            case R.id.tv_local_music:
                if(musicFragment!=null){
                    musicFragment.vpMusic.setCurrentItem(0);
                    flPlayBar.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_online_music:
                if(musicFragment!=null){
                    musicFragment.vpMusic.setCurrentItem(1);
                    flPlayBar.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_cut_music:
                if(musicFragment!=null){
                    musicFragment.vpMusic.setCurrentItem(2);
                    flPlayBar.setVisibility(View.GONE);
                }
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
        View header = LayoutInflater.from(this).inflate(R.layout.navigation_header,
                navigationView, false);
        navigationView.addHeaderView(header);
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
        if(getPlayService()==null){
            return;
        }
        getPlayService().setOnPlayEventListener(new OnPlayerEventListener() {

            private MenuItem timerItem;

            /**
             * 切换歌曲
             * 主要是切换歌曲的时候需要及时刷新界面信息
             */
            @Override
            public void onChange(LocalMusic music) {
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

            /**
             * 更新定时停止播放时间
             */
            @Override
            public void onTimer(long remain) {
                if (timerItem == null) {
                    timerItem = navigationView.getMenu().findItem(R.id.action_timer);
                }
                String title = getString(R.string.menu_timer);
                timerItem.setTitle(remain == 0 ? title : AppUtils.formatTime(title + "(mm:ss)", remain));
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
        //更新进度条
        pbPlayBar.setMax((int) music.getDuration());
        pbPlayBar.setProgress((int) getPlayService().getCurrentPosition());


        /**点击MainActivity中的控制器，如何更新musicFragment中的mLocalMusicFragment呢？*/
        if (musicFragment != null && musicFragment.isAdded()) {
            musicFragment.onItemPlay();
        }
    }


    public void showListDialog() {
        final List<LocalMusic> musicList = BaseAppHelper.get().getMusicList();
        final BottomDialogFragment dialog = new BottomDialogFragment();
        dialog.setFragmentManager(getSupportFragmentManager());
        dialog.setViewListener(new BottomDialogFragment.ViewListener() {
            @Override
            public void bindView(View v) {
                RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
                TextView tv_play_type = (TextView) v.findViewById(R.id.tv_play_type);
                TextView tv_collect = (TextView) v.findViewById(R.id.tv_collect);
                ImageView iv_close = (ImageView) v.findViewById(R.id.iv_close);

                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                final DialogMusicListAdapter mAdapter = new DialogMusicListAdapter(MainActivity.this, musicList);
                recyclerView.setAdapter(mAdapter);
                mAdapter.updatePlayingPosition(getPlayService());
                final RecycleViewItemLine line = new RecycleViewItemLine(MainActivity.this, LinearLayout.HORIZONTAL,
                        SizeUtils.dp2px(1), MainActivity.this.getResources().getColor(R.color.grayLine));
                recyclerView.addItemDecoration(line);
                mAdapter.setOnItemClickListener(new OnListItemClickListener() {
                    @Override
                    public void onItemClick(View view , int position) {
                        getPlayService().play(position);
                        mAdapter.updatePlayingPosition(getPlayService());
                        mAdapter.notifyDataSetChanged();
                    }
                });
                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()){
                            case R.id.tv_play_type:

                                break;
                            case R.id.tv_collect:
                                ToastUtil.showToast(MainActivity.this,"收藏，后期在做");
                                break;
                            case R.id.iv_close:
                                dialog.dismissDialogFragment();
                                break;
                            default:
                                break;
                        }
                    }
                };
                tv_play_type.setOnClickListener(listener);
                tv_collect.setOnClickListener(listener);
                iv_close.setOnClickListener(listener);
            }
        });
        dialog.setLayoutRes(R.layout.dialog_bottom_list_view);
        dialog.setDimAmount(0.5f);
        dialog.setTag("BottomDialogFragment");
        dialog.setCancelOutside(true);
        //这个高度可以自己设置，十分灵活
        dialog.setHeight(ScreenUtils.getScreenHeight()*6/10);
        dialog.show();
    }



}

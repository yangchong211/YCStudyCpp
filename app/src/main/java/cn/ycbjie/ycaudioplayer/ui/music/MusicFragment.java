package cn.ycbjie.ycaudioplayer.ui.music;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.base.view.BaseFragment;
import cn.ycbjie.ycaudioplayer.base.BaseFragmentFactory;
import cn.ycbjie.ycaudioplayer.base.view.BasePagerAdapter;
import cn.ycbjie.ycaudioplayer.ui.main.ui.activity.MainActivity;
import cn.ycbjie.ycaudioplayer.ui.music.local.LocalMusicFragment;
import cn.ycbjie.ycaudioplayer.ui.music.onLine.OnLineMusicFragment;

/**
 * Created by yc on 2018/1/24.
 *
 */
public class MusicFragment extends BaseFragment implements View.OnClickListener {


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
    @Bind(R.id.vp_content)
    ViewPager vpContent;


    private MainActivity activity;
    private LocalMusicFragment mLocalMusicFragment;
    private List<Fragment> fragments;
    private String[] mMusicTitles = {"我的音乐", "在线音乐"};


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
        return R.layout.base_bar_view_pager;
    }


    @Override
    public void initView() {
        initViewPager();
        initFragment();
    }


    @Override
    public void initListener() {

    }


    @Override
    public void initData() {
        stlLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vpContent.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }


    @Override
    public void onClick(View v) {

    }


    private void initViewPager() {
        stlLayout.setTabData(mMusicTitles);
        vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                stlLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void initFragment() {
        fragments = new ArrayList<>();
        mLocalMusicFragment = BaseFragmentFactory.getInstance().getLocalMusicFragment();
        fragments.add(mLocalMusicFragment);
        fragments.add(BaseFragmentFactory.getInstance().getOnLineMusicFragment());
        BasePagerAdapter adapter = new BasePagerAdapter(getChildFragmentManager(), fragments);
        vpContent.setAdapter(adapter);
        vpContent.setCurrentItem(0);
        vpContent.setOffscreenPageLimit(fragments.size());
    }


    public void onItemPlay() {
        if (mLocalMusicFragment != null && mLocalMusicFragment.isAdded()) {
            mLocalMusicFragment.onItemPlay();
        }
    }


    public void onDoubleClick() {
        if (fragments != null && fragments.size() > 0) {
            int item = vpContent.getCurrentItem();
            switch (item){
                case 0:
                    ((LocalMusicFragment) fragments.get(item)).onRefresh();
                    break;
                case 1:
                    ((OnLineMusicFragment) fragments.get(item)).onRefresh();
                    break;
                default:
                    break;
            }
        }
    }


}

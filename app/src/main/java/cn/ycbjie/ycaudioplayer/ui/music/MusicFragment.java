package cn.ycbjie.ycaudioplayer.ui.music;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;

import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.base.BaseFragment;
import cn.ycbjie.ycaudioplayer.base.BaseFragmentFactory;
import cn.ycbjie.ycaudioplayer.base.BasePagerAdapter;
import cn.ycbjie.ycaudioplayer.ui.main.MainActivity;
import cn.ycbjie.ycaudioplayer.ui.music.local.LocalMusicFragment;
import cn.ycbjie.ycaudioplayer.ui.music.onLine.OnLineMusicFragment;

/**
 * Created by yc on 2018/1/24.
 *
 */
public class MusicFragment extends BaseFragment implements View.OnClickListener {


    @Bind(R.id.vp_content)
    public ViewPager vpContent;
    private MainActivity activity;
    private LocalMusicFragment mLocalMusicFragment;
    private List<Fragment> fragments;



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
        return R.layout.base_view_pager;
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
        activity.stlLayout.setOnTabSelectListener(new OnTabSelectListener() {
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
        vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                activity.stlLayout.setCurrentTab(position);
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

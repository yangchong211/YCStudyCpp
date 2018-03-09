package cn.ycbjie.ycaudioplayer.ui.practise;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.Bind;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.base.BaseFragment;
import cn.ycbjie.ycaudioplayer.base.BaseFragmentFactory;
import cn.ycbjie.ycaudioplayer.base.BasePagerAdapter;
import cn.ycbjie.ycaudioplayer.ui.main.MainHomeActivity;
import cn.ycbjie.ycaudioplayer.ui.practise.ui.PractiseAfterFragment;
import cn.ycbjie.ycaudioplayer.ui.practise.ui.PractiseBeforeFragment;

/**
 * Created by yc on 2018/1/24.
 */

public class PractiseFragment extends BaseFragment {


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


    private String[] mPractiseTitles = {"课前预习", "课后练习"};
    private MainHomeActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainHomeActivity) context;
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
        stlLayout.setTabData(mPractiseTitles);
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



    private void initViewPager() {
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
        ArrayList<Fragment> fragments = new ArrayList<>();
        PractiseBeforeFragment practiseBeforeFragment = BaseFragmentFactory.getInstance().getPractiseBeforeFragment();
        PractiseAfterFragment practiseAfterFragment = BaseFragmentFactory.getInstance().getPractiseAfterFragment();
        fragments.add(practiseBeforeFragment);
        fragments.add(practiseAfterFragment);
        BasePagerAdapter adapter = new BasePagerAdapter(getChildFragmentManager(), fragments);
        vpContent.setAdapter(adapter);
        vpContent.setCurrentItem(0);
        vpContent.setOffscreenPageLimit(fragments.size());
    }
    

}

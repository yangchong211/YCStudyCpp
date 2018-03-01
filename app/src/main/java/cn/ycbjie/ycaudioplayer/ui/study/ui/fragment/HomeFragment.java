package cn.ycbjie.ycaudioplayer.ui.study.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.base.BaseFragment;
import cn.ycbjie.ycaudioplayer.base.BaseFragmentFactory;
import cn.ycbjie.ycaudioplayer.base.BasePagerAdapter;
import cn.ycbjie.ycaudioplayer.ui.main.MainActivity;

/**
 * Created by yc on 2018/3/1.
 */

public class HomeFragment extends BaseFragment {


    @Bind(R.id.vp_content)
    public ViewPager vpContent;
    private MainActivity activity;

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
        ArrayList<Fragment> fragments = new ArrayList<>();
        InnovationFragment innovationFragment = BaseFragmentFactory.getInstance().getInnovationFragment();
        StudyFragment studyFragment = BaseFragmentFactory.getInstance().getStudyFragment();
        fragments.add(innovationFragment);
        fragments.add(studyFragment);
        BasePagerAdapter adapter = new BasePagerAdapter(getChildFragmentManager(), fragments);
        vpContent.setAdapter(adapter);
        vpContent.setCurrentItem(0);
        vpContent.setOffscreenPageLimit(fragments.size());
    }

}

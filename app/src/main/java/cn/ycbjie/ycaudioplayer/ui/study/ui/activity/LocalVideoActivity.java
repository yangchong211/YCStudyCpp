package cn.ycbjie.ycaudioplayer.ui.study.ui.activity;

import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;

import org.yczbj.ycrefreshviewlib.YCRefreshView;
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.item.RecycleViewItemLine;
import org.yczbj.ycvideoplayerlib.VideoPlayerManager;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.base.app.BaseApplication;
import cn.ycbjie.ycaudioplayer.base.view.BaseActivity;
import cn.ycbjie.ycaudioplayer.model.bean.VideoBean;
import cn.ycbjie.ycaudioplayer.ui.study.ui.adapter.LocalVideoAdapter;
import cn.ycbjie.ycaudioplayer.ui.study.ui.fragment.LocalVideoFragment;
import cn.ycbjie.ycaudioplayer.utils.scan.FileVideoScanManager;
import cn.ycbjie.ycstatusbarlib.bar.YCAppBar;
import cn.ycbjie.ycthreadpoollib.PoolThread;
import cn.ycbjie.ycthreadpoollib.callback.AsyncCallback;

/**
 * <pre>
 *     @author yangchong
 *     blog  :
 *     time  : 2018/2/10
 *     desc  : 本地视频页面
 *     revise:
 * </pre>
 */
public class LocalVideoActivity extends BaseActivity {

    @Bind(R.id.ll_title_menu)
    FrameLayout llTitleMenu;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.recyclerView)
    YCRefreshView recyclerView;
    private LocalVideoAdapter adapter;
    private LocalVideoFragment mLocalVideoFragment;
    private boolean isPlayFragmentShow = false;

    @Override
    protected void onStop() {
        super.onStop();
        VideoPlayerManager.instance().releaseVideoPlayer();
    }

    @Override
    public void onBackPressed() {
        if (VideoPlayerManager.instance().onBackPressed()) return;
        super.onBackPressed();
    }

    @Override
    public int getContentView() {
        return R.layout.base_bar_easy_recycle;
    }

    @Override
    public void initView() {
        YCAppBar.setStatusBarColor(this, ContextCompat.getColor(this, R.color.redTab));
        initActionBar();
        initRecyclerView();
    }

    @Override
    public void initListener() {
        llTitleMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hidePlayingFragment();
                finish();
            }
        });
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if(adapter.getAllData().size()>position && position>=0){
                    VideoBean videoBean = adapter.getAllData().get(position);
                    showPlayingFragment(videoBean);
                    recyclerView.scrollToPosition(position);
                }
            }
        });
    }

    private void showPlayingFragment(VideoBean videoBean) {
        if (isPlayFragmentShow) {
            return;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_slide_up, 0);
        if (mLocalVideoFragment == null) {
            mLocalVideoFragment = LocalVideoFragment.newInstance(videoBean);
            ft.replace(android.R.id.content, mLocalVideoFragment);
        } else {
            ft.show(mLocalVideoFragment);
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
        ft.hide(mLocalVideoFragment);
        ft.commitAllowingStateLoss();
        isPlayFragmentShow = false;
    }
    
    @Override
    public void initData() {
        recyclerView.showProgress();
        PoolThread executor = BaseApplication.getInstance().getExecutor();
        executor.setDelay(2, TimeUnit.SECONDS);
        executor.async(new Callable<List<VideoBean>>() {
            @Override
            public List<VideoBean> call() throws Exception {
                FileVideoScanManager instance = FileVideoScanManager.getInstance();
                List<VideoBean> videoBeans = instance.scanMusic(LocalVideoActivity.this);
                Log.e("AsyncCallback", "async"+videoBeans.size());
                // 做一些操作
                return videoBeans;
            }
        }, new AsyncCallback<List<VideoBean>>() {
            @Override
            public void onSuccess(List<VideoBean> list) {
                Log.e("AsyncCallback", "成功"+list.size());
                if(list.size()==0){
                    recyclerView.showEmpty();
                }else {
                    adapter.addAll(list);
                    adapter.notifyDataSetChanged();
                    recyclerView.showRecycler();
                }
            }

            @Override
            public void onFailed(Throwable t) {
                Log.e("AsyncCallback", "失败");
            }

            @Override
            public void onStart(String threadName) {
                Log.e("AsyncCallback", "开始");
            }
        });
    }


    private void initActionBar() {
        toolbarTitle.setText("本地视频");
    }


    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final RecycleViewItemLine line = new RecycleViewItemLine(this, LinearLayout.HORIZONTAL,
                SizeUtils.dp2px(1), Color.parseColor("#f5f5f7"));
        recyclerView.addItemDecoration(line);
        adapter = new LocalVideoAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SwipeRefreshLayout swipeToRefresh = recyclerView.getSwipeToRefresh();
                if (swipeToRefresh.isRefreshing()) {
                    recyclerView.setRefreshing(false);
                }else {
                    initData();
                }
            }
        });
    }


}

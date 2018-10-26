package cn.ycbjie.ycaudioplayer.ui.home.ui.activity;

import android.graphics.Color;
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

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.base.app.BaseApplication;
import cn.ycbjie.ycaudioplayer.base.view.BaseActivity;
import cn.ycbjie.ycaudioplayer.model.bean.OfficeBean;
import cn.ycbjie.ycaudioplayer.ui.home.ui.adapter.LocalOfficeAdapter;
import cn.ycbjie.ycaudioplayer.utils.scan.FileOfficeScanManager;
import cn.ycbjie.ycstatusbarlib.bar.StateAppBar;
import cn.ycbjie.ycthreadpoollib.PoolThread;
import cn.ycbjie.ycthreadpoollib.callback.AsyncCallback;

/**
 * <pre>
 *     @author yangchong
 *     blog  :
 *     time  : 2018/2/10
 *     desc  : 本地office页面
 *     revise:
 * </pre>
 */
public class LocalOfficeActivity extends BaseActivity {

    @Bind(R.id.ll_title_menu)
    FrameLayout llTitleMenu;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.recyclerView)
    YCRefreshView recyclerView;
    private LocalOfficeAdapter adapter;


    @Override
    public int getContentView() {
        return R.layout.base_bar_easy_recycle;
    }

    @Override
    public void initView() {
        StateAppBar.setStatusBarColor(this, ContextCompat.getColor(this, R.color.redTab));
        initActionBar();
        initRecyclerView();
    }

    @Override
    public void initListener() {
        llTitleMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if(adapter.getAllData().size()>position && position>=0){
                    recyclerView.scrollToPosition(position);
                }
            }
        });
    }

    
    @Override
    public void initData() {
        recyclerView.showProgress();
        PoolThread executor = BaseApplication.getInstance().getExecutor();
        executor.setDelay(2, TimeUnit.SECONDS);
        executor.async(new Callable<List<OfficeBean>>() {
            @Override
            public List<OfficeBean> call() throws Exception {
                FileOfficeScanManager instance = FileOfficeScanManager.getInstance();
                List<OfficeBean> officeBeans = instance.getFilesByType(LocalOfficeActivity.this, FileOfficeScanManager.TYPE_DOC);
                Log.e("AsyncCallback", "async"+officeBeans.size());
                // 做一些操作
                return officeBeans;
            }
        }, new AsyncCallback<List<OfficeBean>>() {
            @Override
            public void onSuccess(List<OfficeBean> list) {
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
        toolbarTitle.setText("本地Office文件");
        llTitleMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final RecycleViewItemLine line = new RecycleViewItemLine(this, LinearLayout.HORIZONTAL,
                SizeUtils.dp2px(1), Color.parseColor("#f5f5f7"));
        recyclerView.addItemDecoration(line);
        adapter = new LocalOfficeAdapter(this);
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

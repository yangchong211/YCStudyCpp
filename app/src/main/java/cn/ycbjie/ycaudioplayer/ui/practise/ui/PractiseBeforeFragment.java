package cn.ycbjie.ycaudioplayer.ui.practise.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SizeUtils;

import org.yczbj.ycrefreshviewlib.YCRefreshView;
import org.yczbj.ycrefreshviewlib.item.RecycleViewItemLine;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.base.BaseLazyFragment;
import cn.ycbjie.ycaudioplayer.ui.main.MainHomeActivity;
import cn.ycbjie.ycaudioplayer.ui.study.ui.adapter.InnovationAdapter;

/**
 * Created by yc on 2018/3/1.
 */

public class PractiseBeforeFragment extends BaseLazyFragment {

    @Bind(R.id.recyclerView)
    YCRefreshView recyclerView;
    private MainHomeActivity activity;
    private InnovationAdapter adapter;


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
        return R.layout.base_easy_recycle;
    }

    @Override
    public void initView() {
        initYCRefreshView();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onLazyLoad() {
        List<String> data = new ArrayList<>();
        for(int a=0 ; a<20 ; a++){
            data.add("假数据"+a);
        }
        adapter.addAll(data);
        adapter.notifyDataSetChanged();
    }


    private void initYCRefreshView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        final RecycleViewItemLine line = new RecycleViewItemLine(activity, LinearLayout.HORIZONTAL,
                SizeUtils.dp2px(1), Color.parseColor("#f5f5f7"));
        recyclerView.addItemDecoration(line);
        adapter = new InnovationAdapter(activity);
        recyclerView.setAdapter(adapter);
        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SwipeRefreshLayout swipeToRefresh = recyclerView.getSwipeToRefresh();
                if(swipeToRefresh.isRefreshing()){
                    recyclerView.setRefreshing(false);
                }
            }
        });
    }


}

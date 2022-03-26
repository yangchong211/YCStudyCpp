package cn.ycbjie.ycaudioplayer.ui.music.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.pedaily.yc.ycdialoglib.toast.ToastUtils;

import org.yczbj.ycrefreshviewlib.YCRefreshView;
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.item.RecycleViewItemLine;

import java.util.ArrayList;

import butterknife.BindView;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.base.view.BaseLazyFragment;
import cn.ycbjie.ycaudioplayer.ui.main.ui.activity.MainActivity;
import cn.ycbjie.ycaudioplayer.ui.music.model.OnLineSongListInfo;
import cn.ycbjie.ycaudioplayer.ui.music.view.activity.OnlineMusicActivity;
import cn.ycbjie.ycaudioplayer.ui.music.view.adapter.OnLineMusicAdapter;

/**
 * Created by yc on 2018/1/19.
 *
 */
public class OnLineMusicFragment extends BaseLazyFragment {

    @BindView(R.id.recyclerView)
    YCRefreshView recyclerView;

    private MainActivity activity;
    private ArrayList<OnLineSongListInfo> mSongLists;
    private OnLineMusicAdapter adapter;


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
        return R.layout.base_easy_recycle;
    }

    @Override
    public void initView(View view) {
        initRecyclerView();
    }

    @Override
    public void initListener() {
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if(adapter.getAllData().size()>position && position>-1){
                    OnLineSongListInfo onLineSongListInfo = adapter.getAllData().get(position);
                    Intent intent = new Intent(getContext(), OnlineMusicActivity.class);
                    intent.putExtra("music_list_type", onLineSongListInfo);
                    startActivity(intent);
                }
            }
        });
    }


    @Override
    public void initData() {

    }


    @Override
    public void onLazyLoad() {
        recyclerView.showProgress();
        getData();
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        final RecycleViewItemLine line = new RecycleViewItemLine(activity, LinearLayout.HORIZONTAL,
                SizeUtils.dp2px(1), Color.parseColor("#f5f5f7"));
        recyclerView.addItemDecoration(line);
        adapter = new OnLineMusicAdapter(activity);
        recyclerView.setAdapter(adapter);
        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(NetworkUtils.isConnected()){
                    onLazyLoad();
                }else {
                    ToastUtils.showRoundRectToast("没有网络");
                }
            }
        });
    }


    private void getData() {
        mSongLists = new ArrayList<>();
        String[] titles = getResources().getStringArray(R.array.online_music_list_title);
        String[] types = getResources().getStringArray(R.array.online_music_list_type);
        for (int i = 0; i < titles.length; i++) {
            OnLineSongListInfo info = new OnLineSongListInfo();
            info.setTitle(titles[i]);
            info.setType(types[i]);
            mSongLists.add(info);
        }
        adapter.addAll(mSongLists);
        adapter.notifyDataSetChanged();
        recyclerView.showRecycler();
    }


    public void onRefresh() {
        RecyclerView mRecyclerView = this.recyclerView.getRecyclerView();
        int firstVisibleItemPosition = ((LinearLayoutManager)
                mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        if (firstVisibleItemPosition == 0) {
            onLazyLoad();
            return;
        }
        mRecyclerView.scrollToPosition(5);
        mRecyclerView.smoothScrollToPosition(0);
    }


}

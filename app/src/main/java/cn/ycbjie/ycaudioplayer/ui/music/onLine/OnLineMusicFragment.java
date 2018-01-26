package cn.ycbjie.ycaudioplayer.ui.music.onLine;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SizeUtils;

import org.yczbj.ycrefreshviewlib.YCRefreshView;
import org.yczbj.ycrefreshviewlib.item.RecycleViewItemLine;

import java.util.ArrayList;

import butterknife.Bind;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.base.BaseFragment;
import cn.ycbjie.ycaudioplayer.ui.main.MainActivity;
import cn.ycbjie.ycaudioplayer.ui.music.onLine.model.bean.OnLineSongListInfo;
import cn.ycbjie.ycaudioplayer.ui.music.onLine.view.OnLineMusicAdapter;

/**
 * Created by yc on 2018/1/19.
 */

public class OnLineMusicFragment extends BaseFragment {

    @Bind(R.id.recyclerView)
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
        return R.layout.fragment_music_local;
    }

    @Override
    public void initView() {
        initRecyclerView();
    }

    @Override
    public void initListener() {

    }


    @Override
    public void initData() {
        getData();
    }


    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        final RecycleViewItemLine line = new RecycleViewItemLine(activity, LinearLayout.HORIZONTAL,
                SizeUtils.dp2px(1), Color.parseColor("#f5f5f7"));
        recyclerView.addItemDecoration(line);
        adapter = new OnLineMusicAdapter(activity);
        recyclerView.setAdapter(adapter);
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
    }


}

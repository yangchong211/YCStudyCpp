package cn.ycbjie.ycaudioplayer.ui.main;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.base.BaseActivity;
import cn.ycbjie.ycaudioplayer.model.bean.SearchMusic;
import cn.ycbjie.ycaudioplayer.ui.main.ui.SearchMusicAdapter;
import cn.ycbjie.ycaudioplayer.api.http.OnLineMusicModel;
import cn.ycbjie.ycaudioplayer.util.LogUtils;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yc on 2018/2/7
 */
public class SearchMusicActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private static final String TAG = SearchMusicActivity.class.getName();
    private List<SearchMusic.Song> mSearchMusicList;
    private SearchMusicAdapter adapter;
    protected Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mHandler!=null){
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }


    @Override
    public int getContentView() {
        return R.layout.activity_search_music;
    }


    @Override
    public void initView() {
        initToolBar();
        initRecyclerView();
    }


    @Override
    public void initListener() {

    }


    @Override
    public void initData() {

    }


    private void initToolBar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_music, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.onActionViewExpanded();
        searchView.setQueryHint(getString(R.string.search_tips));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchMusic(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setSubmitButtonEnabled(true);
        try {
            Field field = searchView.getClass().getDeclaredField("mGoButton");
            field.setAccessible(true);
            ImageView mGoButton = (ImageView) field.get(searchView);
            mGoButton.setImageResource(R.drawable.ic_menu_search);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onCreateOptionsMenu(menu);
    }



    private void initRecyclerView() {
        mSearchMusicList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchMusicAdapter(this, mSearchMusicList);
        recyclerView.setAdapter(adapter);
        /*final RecycleViewItemLine line = new RecycleViewItemLine(this, LinearLayout.HORIZONTAL,
                SizeUtils.dp2px(1), Color.parseColor("#f5f5f7"));
        recyclerView.addItemDecoration(line);*/
    }



    /**
     * 搜索
     * @param query         内容
     */
    private void searchMusic(String query) {
        OnLineMusicModel model = OnLineMusicModel.getInstance();
        model.startSearchMusic(OnLineMusicModel.METHOD_SEARCH_MUSIC,query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SearchMusic>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(TAG + e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(SearchMusic searchMusic) {
                        if(searchMusic!=null){
                            mSearchMusicList.clear();
                            mSearchMusicList.addAll(searchMusic.getSong());
                            adapter.notifyDataSetChanged();
                            recyclerView.requestFocus();
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    recyclerView.scrollToPosition(0);
                                }
                            });
                            LogUtils.e(TAG + "请求成功");
                        }else {
                            LogUtils.e(TAG + "请求失败");
                        }
                    }
                });
    }


}

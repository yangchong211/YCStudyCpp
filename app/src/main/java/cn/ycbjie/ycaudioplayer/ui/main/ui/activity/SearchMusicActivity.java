package cn.ycbjie.ycaudioplayer.ui.main.ui.activity;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import com.ns.yc.ycutilslib.loadingDialog.LoadDialog;
import com.pedaily.yc.ycdialoglib.toast.ToastUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.base.view.BaseActivity;
import cn.ycbjie.ycaudioplayer.executor.download.AbsDownloadSearchMusic;
import cn.ycbjie.ycaudioplayer.executor.share.AbsShareOnlineMusic;
import cn.ycbjie.ycaudioplayer.inter.listener.OnMoreClickListener;
import cn.ycbjie.ycaudioplayer.model.bean.SearchMusic;
import cn.ycbjie.ycaudioplayer.ui.main.ui.adapter.SearchMusicAdapter;
import cn.ycbjie.ycaudioplayer.api.http.OnLineMusicModel;
import cn.ycbjie.ycaudioplayer.utils.logger.AppLogUtils;
import cn.ycbjie.ycaudioplayer.utils.musicUtils.FileMusicUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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
        adapter.setOnMoreClickListener(new OnMoreClickListener() {
            @Override
            public void onMoreClick(int position) {
                final SearchMusic.Song song = mSearchMusicList.get(position);
                AlertDialog.Builder dialog = new AlertDialog.Builder(SearchMusicActivity.this);
                dialog.setTitle(song.getSongname());
                String path = FileMusicUtils.getMusicDir() + FileMusicUtils.getMp3FileName(song.getArtistname(), song.getSongname());
                File file = new File(path);
                int itemsId = file.exists() ? R.array.search_music_dialog_no_download : R.array.search_music_dialog;
                dialog.setItems(itemsId, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            // 分享
                            case 0:
                                share(song);
                                break;
                            // 下载
                            case 1:
                                download(song);
                                break;
                            default:
                                break;
                        }
                    }
                });
                dialog.show();
            }
        });
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
     * 分享音乐
     * @param song           实体类
     */
    private void share(SearchMusic.Song song) {
        new AbsShareOnlineMusic(this, song.getSongname(), song.getSongid(),"") {
            @Override
            public void onPrepare() {
                LoadDialog.show(SearchMusicActivity.this,"下载中……");
            }

            @Override
            public void onExecuteSuccess(Void aVoid) {
                LoadDialog.dismiss(SearchMusicActivity.this);
            }

            @Override
            public void onExecuteFail(Exception e) {
                LoadDialog.dismiss(SearchMusicActivity.this);
            }
        }.execute();
    }



    /**
     * 下载音乐
     * @param song           实体类
     */
    private void download(final SearchMusic.Song song) {
        new AbsDownloadSearchMusic(this, song) {
            @Override
            public void onPrepare() {
                LoadDialog.show(SearchMusicActivity.this,"下载中……");
            }

            @Override
            public void onExecuteSuccess(Void aVoid) {
                LoadDialog.dismiss(SearchMusicActivity.this);
                ToastUtils.showRoundRectToast("下载成功"+song.getSongname());
            }

            @Override
            public void onExecuteFail(Exception e) {
                LoadDialog.dismiss(SearchMusicActivity.this);
                ToastUtils.showRoundRectToast("下载失败");
            }
        }.execute();
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
                .subscribe(new Consumer<SearchMusic>() {
                    @Override
                    public void accept(SearchMusic searchMusic) throws Exception {
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
                            AppLogUtils.e(TAG + "请求成功");
                        }else {
                            AppLogUtils.e(TAG + "请求失败");
                        }
                    }
                });
    }


}

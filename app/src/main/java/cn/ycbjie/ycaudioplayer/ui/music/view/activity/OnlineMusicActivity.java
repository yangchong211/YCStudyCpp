package cn.ycbjie.ycaudioplayer.ui.music.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.ns.yc.ycutilslib.loadingDialog.LoadDialog;
import com.pedaily.yc.ycdialoglib.customToast.ToastUtil;

import org.yczbj.ycrefreshviewlib.YCRefreshView;
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;

import java.io.File;

import butterknife.Bind;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.api.http.OnLineMusicModel;
import cn.ycbjie.ycaudioplayer.base.view.BaseActivity;
import cn.ycbjie.ycaudioplayer.executor.download.AbsDownloadOnlineMusic;
import cn.ycbjie.ycaudioplayer.executor.online.PlayOnlineMusic;
import cn.ycbjie.ycaudioplayer.executor.share.AbsShareOnlineMusic;
import cn.ycbjie.ycaudioplayer.inter.listener.OnMoreClickListener;
import cn.ycbjie.ycaudioplayer.model.bean.AudioBean;
import cn.ycbjie.ycaudioplayer.ui.music.view.adapter.LineMusicAdapter;
import cn.ycbjie.ycaudioplayer.ui.music.view.fragment.PlayMusicFragment;
import cn.ycbjie.ycaudioplayer.ui.music.model.OnLineSongListInfo;
import cn.ycbjie.ycaudioplayer.ui.music.model.OnlineMusicList;
import cn.ycbjie.ycaudioplayer.utils.musicUtils.FileMusicUtils;
import cn.ycbjie.ycaudioplayer.utils.musicUtils.ImageUtils;
import cn.ycbjie.ycstatusbarlib.bar.YCAppBar;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by yc on 2018/1/29.
 *
 */

public class OnlineMusicActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.recyclerView)
    YCRefreshView recyclerView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.ll_title_menu)
    FrameLayout llTitleMenu;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;

    private OnLineSongListInfo mListInfo;
    private LineMusicAdapter adapter;
    private int mOffset = 0;
    private static final String MUSIC_LIST_SIZE = "10";
    private boolean isPlayFragmentShow = false;
    private PlayMusicFragment mPlayFragment;

    @Override
    public void onBackPressed() {
        if (mPlayFragment != null && isPlayFragmentShow) {
            hidePlayingFragment();
            return;
        }
        super.onBackPressed();
    }


    @Override
    public int getContentView() {
        return R.layout.base_bar_easy_recycle;
    }


    @Override
    public void initView() {
        YCAppBar.setStatusBarColor(this, ContextCompat.getColor(this, R.color.redTab));
        initIntentData();
        initToolBar();
        initRecyclerView();
    }


    private void initToolBar() {
        if(mListInfo!=null){
            toolbarTitle.setText(mListInfo.getTitle());
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


    private void initIntentData() {
        mListInfo = (OnLineSongListInfo) getIntent().getSerializableExtra("music_list_type");
    }


    @Override
    public void initListener() {
        llTitleMenu.setOnClickListener(this);
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if(adapter.getAllData().size()>position && position>-1){
                    OnlineMusicList.SongListBean onlineMusic = adapter.getAllData().get(position);
                    playMusic(onlineMusic);
                    ToastUtils.showShort(onlineMusic.getTitle()+"点击呢！！！");
                }
            }
        });
        adapter.setOnMoreClickListener(new OnMoreClickListener() {
            @Override
            public void onMoreClick(int position) {
                //这个地方需要+1
                showMoreDialog(position + 1);
            }
        });
    }


    @Override
    public void initData() {
        recyclerView.showProgress();
        getData(mOffset);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_title_menu:
                finish();
                break;
            default:
                break;
        }
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LineMusicAdapter(this);
        recyclerView.setAdapter(adapter);
    }


    private void addHeader(final OnlineMusicList onlineMusicList) {
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                return getLayoutInflater().inflate(R.layout.header_online_music, parent, false);
            }

            @Override
            public void onBindView(View view) {
                final ImageView iv_header_bg = (ImageView) view.findViewById(R.id.iv_header_bg);
                final ImageView iv_cover = (ImageView) view.findViewById(R.id.iv_cover);
                TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
                TextView tv_update_date = (TextView) view.findViewById(R.id.tv_update_date);
                TextView tv_comment = (TextView) view.findViewById(R.id.tv_comment);

                tv_title.setText(onlineMusicList.getBillboard().getName());
                tv_update_date.setText(getString(R.string.recent_update, onlineMusicList.getBillboard().getUpdate_date()));
                tv_comment.setText(onlineMusicList.getBillboard().getComment());
                Glide.with(OnlineMusicActivity.this)
                        .load(onlineMusicList.getBillboard().getPic_s640())
                        .asBitmap()
                        .placeholder(R.drawable.default_cover)
                        .error(R.drawable.default_cover)
                        .override(200, 200)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                iv_cover.setImageBitmap(resource);
                                iv_header_bg.setImageBitmap(ImageUtils.blur(resource));
                            }
                        });
            }
        });
    }


    private void getData(final int offset) {
        OnLineMusicModel model = OnLineMusicModel.getInstance();
        model.getSongListInfo(OnLineMusicModel.METHOD_GET_MUSIC_LIST,
                mListInfo.getType(), MUSIC_LIST_SIZE, String.valueOf(offset))
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<OnlineMusicList>() {
                    @Override
                    public void accept(OnlineMusicList onlineMusicList) throws Exception {
                        if (onlineMusicList == null || onlineMusicList.getSong_list() == null || onlineMusicList.getSong_list().size() == 0) {
                            return;
                        }
                        addHeader(onlineMusicList);
                        recyclerView.showRecycler();
                        adapter.addAll(onlineMusicList.getSong_list());
                        adapter.notifyDataSetChanged();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (throwable instanceof RuntimeException) {
                            // 歌曲全部加载完成
                            recyclerView.showError();
                            return;
                        }
                        if (offset == 0) {
                            recyclerView.showError();
                        } else {
                            ToastUtils.showShort(R.string.load_fail);
                        }
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });
    }


    private void showMoreDialog(int position) {
        final OnlineMusicList.SongListBean onlineMusic = adapter.getAllData().get(position);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(adapter.getAllData().get(position).getTitle());
        String path = FileMusicUtils.getMusicDir() + FileMusicUtils.getMp3FileName(
                onlineMusic.getArtist_name(), onlineMusic.getTitle());
        File file = new File(path);
        int itemsId = file.exists() ? R.array.online_music_dialog_without_download : R.array.online_music_dialog;
        dialog.setItems(itemsId, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    // 分享
                    case 0:
                        share(onlineMusic);
                        break;
                    // 查看歌手信息
                    case 1:
                        lookArtistInfo(onlineMusic);
                        break;
                    // 下载
                    case 2:
                        download(onlineMusic);
                        break;
                    default:
                        break;
                }
            }
        });
        dialog.show();
    }

    /**
     * 展示页面
     */
    private void showPlayingFragment() {
        if (isPlayFragmentShow) {
            return;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_slide_up, 0);
        if (mPlayFragment == null) {
            mPlayFragment = PlayMusicFragment.newInstance("OnLine");
            ft.replace(android.R.id.content, mPlayFragment);
        } else {
            ft.show(mPlayFragment);
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
        ft.hide(mPlayFragment);
        ft.commitAllowingStateLoss();
        isPlayFragmentShow = false;
    }


    private void playMusic(OnlineMusicList.SongListBean onlineMusic) {
        new PlayOnlineMusic(this, onlineMusic) {
            @Override
            public void onPrepare() {

            }

            @Override
            public void onExecuteSuccess(AudioBean music) {
                getPlayService().play(music);
                showPlayingFragment();
                ToastUtils.showShort("正在播放" + music.getTitle());

            }

            @Override
            public void onExecuteFail(Exception e) {
                ToastUtils.showShort(R.string.unable_to_play);
            }
        }.execute();
    }


    /**
     * 分享音乐
     *
     * @param onlineMusic 实体类
     */
    private void share(OnlineMusicList.SongListBean onlineMusic) {
        new AbsShareOnlineMusic(this, onlineMusic.getTitle(), onlineMusic.getSong_id()) {
            @Override
            public void onPrepare() {
                LoadDialog.show(OnlineMusicActivity.this, "下载中……");
            }

            @Override
            public void onExecuteSuccess(Void aVoid) {
                LoadDialog.dismiss(OnlineMusicActivity.this);
            }

            @Override
            public void onExecuteFail(Exception e) {
                LoadDialog.dismiss(OnlineMusicActivity.this);
            }
        }.execute();
    }


    /**
     * 查看歌手信息
     *
     * @param onlineMusic 实体类
     */
    private void lookArtistInfo(OnlineMusicList.SongListBean onlineMusic) {
        Intent intent = new Intent(this, ArtistInfoActivity.class);
        intent.putExtra("artist_id", onlineMusic.getTing_uid());
        startActivity(intent);
    }


    /**
     * 下载音乐
     *
     * @param onlineMusic 实体类
     */
    private void download(final OnlineMusicList.SongListBean onlineMusic) {
        new AbsDownloadOnlineMusic(this, onlineMusic) {
            @Override
            public void onPrepare() {
                LoadDialog.show(OnlineMusicActivity.this, "下载中……");
            }

            @Override
            public void onExecuteSuccess(Void aVoid) {
                LoadDialog.dismiss(OnlineMusicActivity.this);
                ToastUtil.showToast(OnlineMusicActivity.this, "下载成功" + onlineMusic.getTitle());
            }

            @Override
            public void onExecuteFail(Exception e) {
                LoadDialog.dismiss(OnlineMusicActivity.this);
                ToastUtil.showToast(OnlineMusicActivity.this, "下载失败");
            }
        }.execute();
    }


}

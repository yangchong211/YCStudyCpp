package cn.ycbjie.ycaudioplayer.ui.music.view.fragment;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.pedaily.yc.ycdialoglib.customToast.ToastUtil;

import org.yczbj.ycrefreshviewlib.YCRefreshView;
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.base.BaseAppHelper;
import cn.ycbjie.ycaudioplayer.base.view.BaseLazyFragment;
import cn.ycbjie.ycaudioplayer.inter.listener.OnMoreClickListener;
import cn.ycbjie.ycaudioplayer.ui.main.ui.activity.MainActivity;
import cn.ycbjie.ycaudioplayer.model.bean.AudioBean;
import cn.ycbjie.ycaudioplayer.ui.music.view.adapter.LocalMusicAdapter;
import cn.ycbjie.ycaudioplayer.ui.music.view.activity.MusicInfoActivity;


/**
 * <pre>
 *     @author yangchong
 *     blog  :
 *     time  : 2017/6/6
 *     desc  : 本地音乐list也米娜
 *     revise:
 * </pre>
 */
public class LocalMusicFragment extends BaseLazyFragment implements View.OnClickListener {


    @Bind(R.id.recyclerView)
    YCRefreshView recyclerView;

    private MainActivity activity;
    private LocalMusicAdapter adapter;
    private List<AudioBean> music;

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
        adapter.setOnMoreClickListener(new OnMoreClickListener() {
            @Override
            public void onMoreClick(int position) {
                if (music.size() >= position) {
                    final AudioBean localMusic = music.get(position);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                    dialog.setTitle(localMusic.getTitle());
                    dialog.setItems(R.array.local_music_dialog, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                // 分享
                                case 0:
                                    shareMusic(localMusic);
                                    break;
                                // 设为铃声
                                case 1:
                                    requestSetRingtone(localMusic);
                                    break;
                                // 查看歌曲信息
                                case 2:
                                    MusicInfoActivity.start(getContext(), localMusic);
                                    break;
                                // 删除
                                case 3:

                                    break;
                                default:
                                    break;
                            }
                        }
                    });
                    dialog.show();
                }
            }
        });
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                List<AudioBean> musicList = BaseAppHelper.get().getMusicList();
                if(musicList!=null && musicList.size()>0 && musicList.size()>position && position>=0){
                    getPlayService().play(position);
                    adapter.updatePlayingPosition(getPlayService());
                    adapter.notifyDataSetChanged();
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
        getMusicData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }


    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new LocalMusicAdapter(activity);
        recyclerView.setAdapter(adapter);
        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(NetworkUtils.isConnected()){
                    onLazyLoad();
                }else {
                    ToastUtil.showToast(activity,"没有网络");
                }
            }
        });
    }


    private void getMusicData() {
        //第一种方法，直接扫描，推荐不要使用，如果本地音乐少可以使用。如果几百首，那么会导致线程阻塞和卡顿
        //music = FileScanManager.getInstance().scanMusic(activity);
        //第二种方法，在服务中扫描，推荐使用
        music = BaseAppHelper.get().getMusicList();
        if (music.size() > 0) {
            adapter.clear();
            adapter.addAll(music);
            adapter.notifyDataSetChanged();
            adapter.updatePlayingPosition(getPlayService());
            recyclerView.showRecycler();
        } else {
            recyclerView.showEmpty();
        }
    }


    /**
     * 分享
     */
    private void shareMusic(AudioBean localMusic) {
        File file = new File(localMusic.getPath());
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("audio/*");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        startActivity(Intent.createChooser(intent, getString(R.string.share)));
    }

    /**
     * 设置为铃声
     */
    private void requestSetRingtone(AudioBean localMusic) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.System.canWrite(getContext())) {
            ToastUtils.showShort(R.string.no_permission_setting);
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            intent.setData(Uri.parse("package:" + getContext().getPackageName()));
            startActivityForResult(intent, 0);
        } else {
            setRingtone(localMusic);
        }
    }

    /**
     * 设置铃声
     */
    private void setRingtone(AudioBean localMusic) {
        Uri uri = MediaStore.Audio.Media.getContentUriForPath(localMusic.getPath());
        // 查询音乐文件在媒体库是否存在
        Cursor cursor = getContext().getContentResolver().query(uri, null,
                MediaStore.MediaColumns.DATA + "=?", new String[]{localMusic.getPath()}, null);
        if (cursor == null) {
            return;
        }
        if (cursor.moveToFirst() && cursor.getCount() > 0) {
            String _id = cursor.getString(0);
            ContentValues values = new ContentValues();
            values.put(MediaStore.Audio.Media.IS_MUSIC, true);
            values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
            values.put(MediaStore.Audio.Media.IS_ALARM, false);
            values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);
            values.put(MediaStore.Audio.Media.IS_PODCAST, false);

            getContext().getContentResolver().update(uri, values, MediaStore.MediaColumns.DATA + "=?",
                    new String[]{localMusic.getPath()});
            Uri newUri = ContentUris.withAppendedId(uri, Long.valueOf(_id));
            RingtoneManager.setActualDefaultRingtoneUri(getContext(), RingtoneManager.TYPE_RINGTONE, newUri);
            ToastUtils.showShort("设置铃声成功");
        }
        cursor.close();
    }

    /**
     * 点击MainActivity中的控制器，更新musicFragment中的mLocalMusicFragment
     */
    public void onItemPlay() {
        if (getPlayService().getPlayingMusic().getType() == AudioBean.Type.LOCAL) {
            recyclerView.scrollToPosition(getPlayService().getPlayingPosition());
        }
        adapter.updatePlayingPosition(getPlayService());
        adapter.notifyDataSetChanged();
    }

    public void onRefresh() {
        RecyclerView mRecyclerView = recyclerView.getRecyclerView();
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

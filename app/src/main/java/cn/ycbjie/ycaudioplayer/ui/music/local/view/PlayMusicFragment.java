package cn.ycbjie.ycaudioplayer.ui.music.local.view;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.pedaily.yc.ycdialoglib.bottomLayout.BottomDialogFragment;
import com.pedaily.yc.ycdialoglib.customToast.ToastUtil;

import org.yczbj.ycrefreshviewlib.item.RecycleViewItemLine;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.api.constant.Constant;
import cn.ycbjie.ycaudioplayer.base.BaseAppHelper;
import cn.ycbjie.ycaudioplayer.base.BaseFragment;
import cn.ycbjie.ycaudioplayer.executor.SearchLrc;
import cn.ycbjie.ycaudioplayer.inter.OnListItemClickListener;
import cn.ycbjie.ycaudioplayer.inter.OnPlayerEventListener;
import cn.ycbjie.ycaudioplayer.model.enums.PlayModeEnum;
import cn.ycbjie.ycaudioplayer.ui.main.MainHomeActivity;
import cn.ycbjie.ycaudioplayer.ui.music.local.model.AudioMusic;
import cn.ycbjie.ycaudioplayer.util.other.AppUtils;
import cn.ycbjie.ycaudioplayer.util.musicUtils.CoverLoader;
import cn.ycbjie.ycaudioplayer.util.musicUtils.FileMusicUtils;
import cn.ycbjie.ycaudioplayerlib.lrc.YCLrcCustomView;
import cn.ycbjie.ycstatusbarlib.bar.YCAppBar;

/**
 * Created by yc on 2018/1/24.
 */

public class PlayMusicFragment extends BaseFragment implements View.OnClickListener, OnPlayerEventListener {

    @Bind(R.id.iv_play_page_bg)
    ImageView ivPlayPageBg;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_artist)
    TextView tvArtist;
    @Bind(R.id.iv_share)
    ImageView ivShare;
    @Bind(R.id.iv_playing_fav)
    ImageView ivPlayingFav;
    @Bind(R.id.iv_playing_down)
    ImageView ivPlayingDown;
    @Bind(R.id.iv_playing_cmt)
    ImageView ivPlayingCmt;
    @Bind(R.id.iv_playing_more)
    ImageView ivPlayingMore;
    @Bind(R.id.ll_music_tool)
    LinearLayout llMusicTool;
    @Bind(R.id.tv_current_time)
    TextView tvCurrentTime;
    @Bind(R.id.sb_progress)
    SeekBar sbProgress;
    @Bind(R.id.tv_total_time)
    TextView tvTotalTime;
    @Bind(R.id.iv_mode)
    ImageView ivMode;
    @Bind(R.id.iv_prev)
    ImageView ivPrev;
    @Bind(R.id.iv_play)
    ImageView ivPlay;
    @Bind(R.id.iv_next)
    ImageView ivNext;
    @Bind(R.id.iv_other)
    ImageView ivOther;
    @Bind(R.id.ll_content)
    LinearLayout llContent;
    @Bind(R.id.lrc_view)
    YCLrcCustomView lrcView;
    @Bind(R.id.sb_volume)
    SeekBar sbVolume;
    @Bind(R.id.iv_playing_velocity)
    ImageView ivPlayingVelocity;
    private MainHomeActivity activity;
    private int mLastProgress;
    /**
     * 是否拖进度，默认是false
     */
    private boolean isDraggingProgress;
    private AudioManager mAudioManager;


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

    /**
     * 返回监听
     */
    private void onBackPressed() {
        getActivity().onBackPressed();
        ivBack.setEnabled(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ivBack.setEnabled(true);
            }
        }, 300);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(mVolumeReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        getContext().registerReceiver(mVolumeReceiver, filter);
    }


    @Override
    public int getContentView() {
        return R.layout.fragment_play_music;
    }


    @Override
    public void initView() {
        initSystemBar();
        initPlayMode();
        initVolume();
    }


    @Override
    public void initListener() {
        ivBack.setOnClickListener(this);
        ivMode.setOnClickListener(this);
        ivPlay.setOnClickListener(this);
        ivPrev.setOnClickListener(this);
        ivNext.setOnClickListener(this);
        ivOther.setOnClickListener(this);
        ivPlayingVelocity.setOnClickListener(this);
        initSeekBarListener();
    }


    private void initSeekBarListener() {
        SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (seekBar == sbProgress) {
                    if (Math.abs(progress - mLastProgress) >= DateUtils.SECOND_IN_MILLIS) {
                        tvCurrentTime.setText(AppUtils.formatTime("mm:ss", progress));
                        mLastProgress = progress;
                    }
                }
            }

            /**
             * 通知用户已启动触摸手势,开始触摸时调用
             * @param seekBar               seekBar
             */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (seekBar == sbProgress) {
                    isDraggingProgress = true;
                }
            }


            /**
             * 通知用户已结束触摸手势,触摸结束时调用
             * @param seekBar               seekBar
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar == sbProgress) {
                    isDraggingProgress = false;
                    //如果是正在播放，或者暂停，那么直接拖动进度
                    if (getPlayService().isPlaying() || getPlayService().isPausing()) {
                        //获取进度
                        int progress = seekBar.getProgress();
                        //直接移动进度
                        getPlayService().seekTo(progress);
                        lrcView.updateTime(progress);
                    } else {
                        //其他情况，直接设置进度为0
                        seekBar.setProgress(0);
                    }
                } else if (seekBar == sbVolume) {
                    mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, seekBar.getProgress(),
                            AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                }
            }
        };
        sbProgress.setOnSeekBarChangeListener(onSeekBarChangeListener);
        sbVolume.setOnSeekBarChangeListener(onSeekBarChangeListener);
    }

    @Override
    public void initData() {
        setViewData(getPlayService().getPlayingMusic());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_mode:
                switchPlayMode();
                break;
            case R.id.iv_play:
                play();
                break;
            case R.id.iv_next:
                next();
                break;
            case R.id.iv_prev:
                prev();
                break;
            case R.id.iv_other:
                showListDialog();
                break;
            case R.id.iv_playing_velocity:
                setPlayingVelocity();
                break;
            default:
                break;
        }
    }

    private void prev() {
        if (getPlayService() != null) {
            ToastUtils.showShort(R.string.state_prev);
            getPlayService().prev();
        }
    }

    private void next() {
        if (getPlayService() != null) {
            ToastUtils.showShort(R.string.state_next);
            getPlayService().next();
        }
    }

    private void play() {
        if (getPlayService() != null) {
            getPlayService().playPause();
        }
    }

    private void switchPlayMode() {
        int playMode = SPUtils.getInstance(Constant.SP_NAME).getInt(Constant.PLAY_MODE, 0);
        PlayModeEnum mode = PlayModeEnum.valueOf(playMode);
        switch (mode) {
            case LOOP:
                mode = PlayModeEnum.SHUFFLE;
                ToastUtils.showShort(R.string.mode_shuffle);
                break;
            case SHUFFLE:
                mode = PlayModeEnum.SINGLE;
                ToastUtils.showShort(R.string.mode_one);
                break;
            case SINGLE:
                mode = PlayModeEnum.LOOP;
                ToastUtils.showShort(R.string.mode_loop);
                break;
            default:
                break;
        }
        SPUtils.getInstance(Constant.SP_NAME).put(Constant.PLAY_MODE, mode.value());
        initPlayMode();
    }

    public void showListDialog() {
        final List<AudioMusic> musicList = BaseAppHelper.get().getMusicList();
        final BottomDialogFragment dialog = new BottomDialogFragment();
        dialog.setFragmentManager(getChildFragmentManager());
        dialog.setViewListener(new BottomDialogFragment.ViewListener() {
            @Override
            public void bindView(View v) {
                RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
                TextView tv_play_type = (TextView) v.findViewById(R.id.tv_play_type);
                TextView tv_collect = (TextView) v.findViewById(R.id.tv_collect);
                ImageView iv_close = (ImageView) v.findViewById(R.id.iv_close);

                recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                final DialogMusicListAdapter mAdapter = new DialogMusicListAdapter(activity, musicList);
                recyclerView.setAdapter(mAdapter);
                mAdapter.updatePlayingPosition(getPlayService());
                final RecycleViewItemLine line = new RecycleViewItemLine(activity, LinearLayout.HORIZONTAL,
                        SizeUtils.dp2px(1), activity.getResources().getColor(R.color.grayLine));
                recyclerView.addItemDecoration(line);
                mAdapter.setOnItemClickListener(new OnListItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        List<AudioMusic> musicList = BaseAppHelper.get().getMusicList();
                        getPlayService().play(musicList,position);
                        mAdapter.updatePlayingPosition(getPlayService());
                        mAdapter.notifyDataSetChanged();
                    }
                });
                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.tv_play_type:
                                switchPlayMode();
                                break;
                            case R.id.tv_collect:
                                ToastUtil.showToast(activity, "收藏，后期在做");
                                break;
                            case R.id.iv_close:
                                dialog.dismissDialogFragment();
                                break;
                            default:
                                break;
                        }
                    }
                };
                tv_play_type.setOnClickListener(listener);
                tv_collect.setOnClickListener(listener);
                iv_close.setOnClickListener(listener);
            }
        });
        dialog.setLayoutRes(R.layout.dialog_bottom_list_view);
        dialog.setDimAmount(0.5f);
        dialog.setTag("BottomDialogFragment");
        dialog.setCancelOutside(true);
        //这个高度可以自己设置，十分灵活
        dialog.setHeight(ScreenUtils.getScreenHeight() * 7 / 10);
        dialog.show();
    }

    /**
     * 设置播放速度
     */
    private void setPlayingVelocity() {

    }


    /**
     * 沉浸式状态栏
     */
    private void initSystemBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int top = AppUtils.getStatusBarHeight(activity);
            llContent.setPadding(0, top, 0, 0);
        }
    }

    private void initPlayMode() {
        int playMode = SPUtils.getInstance(Constant.SP_NAME).getInt(Constant.PLAY_MODE, 0);
        ivMode.setImageLevel(playMode);
    }

    /**
     * 初始化音量
     */
    private void initVolume() {
        mAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        sbVolume.setMax(mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        sbVolume.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
    }

    /**
     * 发送广播接收者
     */
    private BroadcastReceiver mVolumeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            sbVolume.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        }
    };


    /**
     * 填充页面数据
     *
     * @param playingMusic 正在播放的音乐
     */
    @SuppressLint("SetTextI18n")
    private void setViewData(AudioMusic playingMusic) {
        if (playingMusic == null) {
            return;
        }
        tvTitle.setText(playingMusic.getTitle());
        tvArtist.setText(playingMusic.getArtist());
        sbProgress.setProgress((int) getPlayService().getCurrentPosition());
        sbProgress.setSecondaryProgress(0);
        sbProgress.setMax((int) playingMusic.getDuration());
        mLastProgress = 0;
        tvCurrentTime.setText("00:00");
        tvTotalTime.setText(AppUtils.formatTime("mm:ss", playingMusic.getDuration()));
        setCoverAndBg(playingMusic);
        setLrc(playingMusic);
        if (getPlayService().isPlaying() || getPlayService().isPreparing()) {
            ivPlay.setSelected(true);
            //mAlbumCoverView.start();
        } else {
            ivPlay.setSelected(false);
            //mAlbumCoverView.pause();
        }
    }

    private void setCoverAndBg(AudioMusic music) {
        //mAlbumCoverView.setCoverBitmap(CoverLoader.getInstance().loadRound(music));
        ivPlayPageBg.setImageBitmap(CoverLoader.getInstance().loadBlur(music));
    }


    /**
     * 设置歌词
     *
     * @param playingMusic 正在播放的音乐
     */
    private void setLrc(final AudioMusic playingMusic) {
        if (playingMusic.getType() == AudioMusic.Type.LOCAL) {
            String lrcPath = FileMusicUtils.getLrcFilePath(playingMusic);
            if (!TextUtils.isEmpty(lrcPath)) {
                loadLrc(lrcPath);
            } else {
                new SearchLrc(playingMusic.getArtist(), playingMusic.getTitle()) {
                    @Override
                    public void onPrepare() {
                        loadLrc("");
                        setLrcLabel("正在搜索歌词");
                    }

                    @Override
                    public void onExecuteSuccess(@NonNull String lrcPath) {
                        loadLrc(lrcPath);
                        setLrcLabel("暂时无歌词");
                    }

                    @Override
                    public void onExecuteFail(Exception e) {
                        setLrcLabel("加载歌词失败");
                    }
                }.execute();
            }
        } else {
            String lrcPath = FileMusicUtils.getLrcDir() +
                    FileMusicUtils.getLrcFileName(playingMusic.getArtist(), playingMusic.getTitle());
            loadLrc(lrcPath);
        }
    }


    private void loadLrc(String path) {
        File file = new File(path);
        lrcView.loadLrc(file);
    }


    private void setLrcLabel(String label) {
        lrcView.setLabel(label);
    }


    /**
     * ---------------通过MainActivity进行调用-----------------------------
     **/
    @Override
    public void onChange(AudioMusic music) {
        setViewData(music);
    }

    @Override
    public void onPlayerStart() {
        ivPlay.setSelected(true);
    }

    @Override
    public void onPlayerPause() {
        ivPlay.setSelected(false);
    }

    @Override
    public void onUpdateProgress(int progress) {
        //如果没有拖动进度，则开始更新进度条进度
        if (!isDraggingProgress) {
            sbProgress.setProgress(progress);
        }
        lrcView.updateTime(progress);
    }

    @Override
    public void onBufferingUpdate(int percent) {
        sbProgress.setSecondaryProgress(sbProgress.getMax() * 100 / percent);
    }

    @Override
    public void onTimer(long remain) {

    }


}

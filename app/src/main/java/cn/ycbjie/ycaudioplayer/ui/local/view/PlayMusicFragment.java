package cn.ycbjie.ycaudioplayer.ui.local.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;

import butterknife.Bind;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.api.Constant;
import cn.ycbjie.ycaudioplayer.base.BaseFragment;
import cn.ycbjie.ycaudioplayer.inter.OnPlayerEventListener;
import cn.ycbjie.ycaudioplayer.model.enums.PlayModeEnum;
import cn.ycbjie.ycaudioplayer.ui.MainActivity;
import cn.ycbjie.ycaudioplayer.ui.local.model.LocalMusic;
import cn.ycbjie.ycaudioplayer.util.AppUtils;
import cn.ycbjie.ycaudioplayer.util.musicUtils.CoverLoader;

/**
 * Created by yc on 2018/1/24.
 */

public class PlayMusicFragment extends BaseFragment implements View.OnClickListener ,OnPlayerEventListener {

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
    private MainActivity activity;
    private int mLastProgress;



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
        return R.layout.fragment_play_music;
    }

    @Override
    public void initView() {
        initSystemBar();
        initPlayMode();
    }

    @Override
    public void initListener() {
        ivBack.setOnClickListener(this);
        ivMode.setOnClickListener(this);
        ivPlay.setOnClickListener(this);
        ivPrev.setOnClickListener(this);
        ivNext.setOnClickListener(this);
    }

    @Override
    public void initData() {
        setViewData(getPlayService().getPlayingMusic());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
            default:
                break;
        }
    }

    private void prev() {
        if(getPlayService()!=null){
            ToastUtils.showShort(R.string.state_prev);
            getPlayService().prev();
        }
    }

    private void next() {
        if(getPlayService()!=null){
            ToastUtils.showShort(R.string.state_next);
            getPlayService().next();
        }
    }

    private void play() {
        if(getPlayService()!=null){
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
        SPUtils.getInstance(Constant.SP_NAME).put(Constant.PLAY_MODE,mode.value());
        initPlayMode();
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
     * 填充页面数据
     * @param playingMusic          正在播放的音乐
     */
    @SuppressLint("SetTextI18n")
    private void setViewData(LocalMusic playingMusic) {
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
        tvTotalTime.setText(AppUtils.formatTime("mm:ss",playingMusic.getDuration()));
        setCoverAndBg(playingMusic);
        //setLrc(playingMusic);
        if (getPlayService().isPlaying() || getPlayService().isPreparing()) {
            ivPlay.setSelected(true);
            //mAlbumCoverView.start();
        } else {
            ivPlay.setSelected(false);
            //mAlbumCoverView.pause();
        }
    }

    private void setCoverAndBg(LocalMusic music) {
        //mAlbumCoverView.setCoverBitmap(CoverLoader.getInstance().loadRound(music));
        ivPlayPageBg.setImageBitmap(CoverLoader.getInstance().loadBlur(music));
    }



    /**---------------通过MainActivity进行调用-----------------------------**/
    @Override
    public void onChange(LocalMusic music) {
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


}

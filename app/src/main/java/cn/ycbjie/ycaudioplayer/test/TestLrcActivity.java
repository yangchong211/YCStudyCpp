package cn.ycbjie.ycaudioplayer.test;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.base.BaseActivity;
import cn.ycbjie.ycaudioplayerlib.lrc.YCLrcCustomView;

/**
 * 用来测试歌词展示View
 */
public class TestLrcActivity extends BaseActivity {

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
    @Bind(R.id.sb_volume)
    SeekBar sbVolume;
    @Bind(R.id.lrc_view)
    YCLrcCustomView lrcView;
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

    private MediaPlayer mediaPlayer = new MediaPlayer();
    private Handler handler = new Handler();

    @Override
    public int getContentView() {
        return R.layout.fragment_play_music;
    }

    @Override
    public void initView() {
        try {
            mediaPlayer.reset();
            AssetFileDescriptor fileDescriptor = getAssets().openFd("chengdu.mp3");
            mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    sbVolume.setMax(mediaPlayer.getDuration());
                    sbVolume.setProgress(0);
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    lrcView.updateTime(0);
                    sbVolume.setProgress(0);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        lrcView.loadLrc(getLrcText("chengdu.lrc"));
    }

    @Override
    public void initListener() {
        lrcView.setOnPlayClickListener(new YCLrcCustomView.OnPlayClickListener() {
            @Override
            public boolean onPlayClick(long time) {
                mediaPlayer.seekTo((int) time);
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    handler.post(runnable);
                }
                return true;
            }
        });
        sbVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
                lrcView.updateTime(seekBar.getProgress());
            }
        });
        ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    handler.post(runnable);
                } else {
                    mediaPlayer.pause();
                    handler.removeCallbacks(runnable);
                }
            }
        });
    }

    @Override
    public void initData() {

    }

    private String getLrcText(String fileName) {
        String lrcText = null;
        try {
            InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            lrcText = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lrcText;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer.isPlaying()) {
                long time = mediaPlayer.getCurrentPosition();
                lrcView.updateTime(time);
                sbVolume.setProgress((int) time);
            }

            handler.postDelayed(this, 300);
        }
    };

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(runnable);
        mediaPlayer.reset();
        mediaPlayer.release();
        mediaPlayer = null;
        super.onDestroy();
    }


}

package cn.ycbjie.ycaudioplayer.ui.cut;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.czt.mp3recorder.MP3Recorder;
import com.pedaily.yc.ycdialoglib.toast.ToastUtil;
import com.bjie.audio.MP3RadioStreamDelegate;
import com.bjie.audio.MP3RadioStreamPlayer;
import com.yc.wave.AudioPlayer;
import com.yc.wave.AudioWaveView;
import com.yc.wave.FileUtils;


import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import butterknife.Bind;

import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.base.BaseFragment;



import static android.content.ContentValues.TAG;

/**
 * Created by yc on 2018/1/19.
 */

public class CutEditMusicFragment extends BaseFragment implements View.OnClickListener {


    @Bind(R.id.ll_cut_mp3)
    LinearLayout llCutMp3;
    @Bind(R.id.ll_change_mp3)
    LinearLayout llChangeMp3;
    @Bind(R.id.tv_start_pause)
    TextView tvStartPause;
    @Bind(R.id.iv_recoding)
    ImageView ivRecoding;
    @Bind(R.id.ll_start_recording)
    LinearLayout llStartRecording;
    @Bind(R.id.ll_start_listen)
    LinearLayout llStartListen;
    @Bind(R.id.ll_save_file)
    LinearLayout llSaveFile;
    @Bind(R.id.chronometer)
    Chronometer chronometer;
    @Bind(R.id.audioWave)
    AudioWaveView audioWave;
    @Bind(R.id.seekBar)
    SeekBar seekBar;
    @Bind(R.id.tv_listen)
    TextView tvListen;


    private String filePath;
    private MP3Recorder mRecorder;

    boolean mIsRecord = false;
    boolean mIsPlay = false;
    boolean seekBarTouch;
    boolean playEnd;


    private AudioPlayer audioPlayer;
    private MP3RadioStreamPlayer player;
    private long timeWhenPaused;
    private Timer timer = new Timer();


    @Override
    public void onDestroy() {
        if(timer!=null){
            timer.cancel();
            timer = null;
        }
        super.onDestroy();
        if(audioWave!=null){
            audioWave.stopView();
        }
        if(player!=null){
            player.stop();
        }
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_music_edit;
    }

    @Override
    public void initView() {
        tvStartPause.setText("录音");
        initAudioWave();
        initSeekBar();
    }

    @Override
    public void initListener() {
        llCutMp3.setOnClickListener(this);
        llChangeMp3.setOnClickListener(this);
        llStartRecording.setOnClickListener(this);
        llStartListen.setOnClickListener(this);
        llSaveFile.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_cut_mp3:
                StopRecording();
                break;
            case R.id.ll_change_mp3:
                ResetRecoding();
                break;
            case R.id.ll_start_recording:
                startRecordingOrPause();
                break;
            case R.id.ll_start_listen:
                startListening();
                break;
            case R.id.ll_save_file:

                break;
            default:
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    private void initAudioWave() {
        audioPlayer = new AudioPlayer(getActivity(), new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    //更新的时间
                    case AudioPlayer.HANDLER_CUR_TIME:

                        break;
                    //播放结束
                    case AudioPlayer.HANDLER_COMPLETE:
                        mIsPlay = false;
                        break;
                    //播放开始
                    case AudioPlayer.HANDLER_PREPARED:

                        break;
                    //播放错误
                    case AudioPlayer.HANDLER_ERROR:
                        ResetRecoding();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 只有当播放时才显示seekBar，录音时隐藏
     */
    private void initSeekBar() {
        seekBar.setEnabled(false);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                seekBarTouch = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBarTouch = false;
                if (!playEnd) {
                    player.seekTo(seekBar.getProgress());
                }
            }
        });
    }


    private void startTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (playEnd || player == null || !seekBar.isEnabled()) {
                    return;
                }
                long position = player.getCurPosition();
                if (position > 0 && !seekBarTouch) {
                    seekBar.setProgress((int) position);
                }
            }
        }, 1000, 1000);
    }


    /**
     * 开始播放获取暂停
     */
    private void startRecordingOrPause() {
        String s = tvStartPause.getText().toString();
        if(s.equals("录音")){
            tvStartPause.setText("暂停");
            ivRecoding.setBackgroundResource(R.drawable.host_pause_in_track_item);
            ToastUtil.showToast(getActivity(),"开始录音");
            //开始倒计时
            chronometer.setBase(SystemClock.elapsedRealtime()+ timeWhenPaused);
            chronometer.start();
            startRecording();
        }else if(s.equals("暂停")){
            tvStartPause.setText("录音");
            ivRecoding.setBackgroundResource(R.drawable.host_play_in_track_item);
            ToastUtil.showToast(getActivity(),"暂停录音");
            timeWhenPaused = chronometer.getBase() - SystemClock.elapsedRealtime();
            chronometer.stop();
            stopRecording();
        }
        //监听器
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {

            @Override
            public void onChronometerTick(Chronometer chronometer) {
                //SystemClock.elapsedRealtime()系统当前时间
                //chronometer.getBase() 记录计时器开始时的时间
                /*if ((SystemClock.elapsedRealtime() - chronometer.getBase()) > 10*1000 ){
                    chronometer.setBase(SystemClock.elapsedRealtime());
                }*/
            }
        });
        //chronometer.setText(FormatMiss(1000));
    }


    public String FormatMiss(int time){
        String hh=time/3600>9?time/3600+"":"0"+time/3600;
        String mm=(time% 3600)/60>9?(time% 3600)/60+"":"0"+(time% 3600)/60;
        String ss=(time% 3600) % 60>9?(time% 3600) % 60+"":"0"+(time% 3600) % 60;
        return hh+":"+mm+":"+ss;
    }



    /**
     * 播放录音
     */
    private void startListening() {
        if (TextUtils.isEmpty(filePath) || !new File(filePath).exists()) {
            Toast.makeText(getActivity(), "文件不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        mIsPlay = true;
        //可以正常播放声音
        //audioPlayer.playUrl(filePath);
        //resolvePlayUI();


        if (playEnd) {
            player.stop();
            tvListen.setText("暂停播放");
            seekBar.setEnabled(true);
            play();
            return;
        }

        if(player!=null){
            if (player.isPause()) {
                tvListen.setText("暂停");
                ToastUtil.showToast(getActivity(),"暂停播放");
                player.setPause(false);
                seekBar.setEnabled(false);
            } else {
                tvListen.setText("播放");
                ToastUtil.showToast(getActivity(),"播放录音");
                player.setPause(true);
                seekBar.setEnabled(true);
            }
        }else {
            ToastUtil.showToast(getActivity(),"开始播放录音");
            play();
        }

    }

    /**
     * 播放
     */
    private void play() {
        //播放波形图
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
        player = new MP3RadioStreamPlayer();
        player.setUrlString(filePath);
        startTimer();
        player.setDelegate(new MP3RadioStreamDelegate() {
            @Override
            public void onRadioPlayerPlaybackStarted(final MP3RadioStreamPlayer player) {
                Log.i(TAG, "onRadioPlayerPlaybackStarted");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        playEnd = false;
                        seekBar.setMax((int) player.getDuration());
                        seekBar.setEnabled(true);
                    }
                });
            }

            @Override
            public void onRadioPlayerStopped(MP3RadioStreamPlayer player) {
                Log.i(TAG, "onRadioPlayerStopped");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        playEnd = true;
                        seekBar.setEnabled(false);
                    }
                });
            }

            @Override
            public void onRadioPlayerError(MP3RadioStreamPlayer player) {
                Log.i(TAG, "onRadioPlayerError");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        playEnd = false;
                        seekBar.setEnabled(false);
                    }
                });
            }

            @Override
            public void onRadioPlayerBuffering(MP3RadioStreamPlayer player) {
                Log.i(TAG, "onRadioPlayerBuffering");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        seekBar.setEnabled(false);
                    }
                });
            }
        });

        //控件默认的间隔是1
        int size = ScreenUtils.getScreenWidth() / SizeUtils.dp2px(1);
        player.setDataList(audioWave.getRecList(), size);
        //player.setWaveSpeed(1100);
        //mRecorder.setDataList(audioWave.getRecList(), size);
        //player.setStartWaveTime(5000);
        //audioWave.setDrawBase(false);
        audioWave.setBaseRecorder(player);
        audioWave.startView();
        try {
            player.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 重置录音
     */
    private void ResetRecoding() {
        filePath = "";
        if (mIsPlay) {
            ToastUtil.showToast(getActivity(),"重新录音");
            mIsPlay = false;
            audioPlayer.pause();
        }
        //resolveNormalUI();
    }

    /**
     * 停止录音
     */
    private void StopRecording() {
        ToastUtil.showToast(getActivity(),"结束录音");
        //resolveStopUI();
        if (mRecorder != null && mRecorder.isRecording()) {
            mRecorder.setPause(false);
            mRecorder.stop();
            audioWave.stopView();
            //停止录音时，注意时间
            timeWhenPaused = 0;
            chronometer.stop();
            chronometer.setBase(SystemClock.elapsedRealtime());
        }
        mIsRecord = false;
    }

    /**
     * 开始录音
     */
    @SuppressLint("HandlerLeak")
    private void startRecording() {
        filePath = FileUtils.getAppPath();
        File file = new File(filePath);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Toast.makeText(getActivity(), "创建文件失败", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        int offset = SizeUtils.dp2px(1);
        filePath = FileUtils.getAppPath() + UUID.randomUUID().toString() + ".mp3";
        mRecorder = new MP3Recorder(new File(filePath));
        //控件默认的间隔是1
        int size = ScreenUtils.getScreenWidth() / offset;

        mRecorder.setDataList(audioWave.getRecList(), size);

        //高级用法
        //int size = (getScreenWidth(getActivity()) / 2) / dip2px(getActivity(), 1);
        //mRecorder.setWaveSpeed(600);
        //mRecorder.setDataList(audioWave.getRecList(), size);
        //audioWave.setDrawStartOffset((getScreenWidth(getActivity()) / 2));
        //audioWave.setDrawReverse(true);
        //audioWave.setDataReverse(true);

        //自定义paint
        //Paint paint = new Paint();
        //paint.setColor(Color.GRAY);
        //paint.setStrokeWidth(4);
        //audioWave.setLinePaint(paint);
        //audioWave.setOffset(offset);

        mRecorder.setErrorHandler(new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == MP3Recorder.ERROR_TYPE) {
                    Toast.makeText(getActivity(), "没有麦克风权限", Toast.LENGTH_SHORT).show();
                    resolveError();
                }
            }
        });

        //audioWave.setBaseRecorder(mRecorder);

        try {
            mRecorder.start();
            audioWave.startView();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "录音出现异常", Toast.LENGTH_SHORT).show();
            resolveError();
            return;
        }
        //resolveRecordUI();
        mIsRecord = true;
    }

    /**
     * 暂停录音
     */
    private void stopRecording() {
        if (!mIsRecord){
            return;
        }
        //resolvePauseUI();
        if (mRecorder.isPause()) {
            //resolveRecordUI();
            audioWave.setPause(false);
            mRecorder.setPause(false);
        } else {
            audioWave.setPause(true);
            mRecorder.setPause(true);
        }
    }

    /**
     * 录音异常
     */
    private void resolveError() {
        //resolveNormalUI();
        FileUtils.deleteFile(filePath);
        filePath = "";
        if (mRecorder != null && mRecorder.isRecording()) {
            mRecorder.stop();
            audioWave.stopView();
        }
    }


    /**
     * 设置出来录音按钮可以点击，其他都不可点击
     */
    private void resolveNormalUI() {
        llStartRecording.setEnabled(true);
        llCutMp3.setEnabled(false);
        llChangeMp3.setEnabled(false);
        llStartListen.setEnabled(false);
        llSaveFile.setEnabled(false);
    }


    private void resolveRecordUI() {
        llStartRecording.setEnabled(true);
        llCutMp3.setEnabled(false);
        llChangeMp3.setEnabled(false);
        llStartListen.setEnabled(false);
        llSaveFile.setEnabled(false);
    }


    /**
     * 暂停录音
     */
    private void resolvePauseUI() {
        llStartRecording.setEnabled(true);
        llCutMp3.setEnabled(false);
        llChangeMp3.setEnabled(false);
        llStartListen.setEnabled(false);
        llSaveFile.setEnabled(false);
    }


    /**
     * 停止录音
     */
    private void resolveStopUI() {
        llStartRecording.setEnabled(false);
        llCutMp3.setEnabled(false);
        llChangeMp3.setEnabled(true);
        llStartListen.setEnabled(false);
        llSaveFile.setEnabled(false);
    }


    /**
     * 播放录音
     */
    private void resolvePlayUI() {
        llStartRecording.setEnabled(false);
        llCutMp3.setEnabled(false);
        llChangeMp3.setEnabled(false);
        llStartListen.setEnabled(true);
        llSaveFile.setEnabled(false);
    }


}

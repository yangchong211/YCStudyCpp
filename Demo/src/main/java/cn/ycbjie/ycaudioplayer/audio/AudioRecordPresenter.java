package cn.ycbjie.ycaudioplayer.audio;

import android.content.Context;
import android.media.AudioFormat;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.yc.audioadapter.AudioRecordService;
import com.yc.daudio.AudioConfigModel;
import com.yc.daudio.AudioFilePathRule;
import com.yc.daudio.IAudioDataListener;
import com.yc.daudio.OnAudioCallback;

import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;


public class AudioRecordPresenter implements AudioRecordContract.Presenter {

    private static final String TAG = "AudioRecordTest";

    private AudioRecordContract.View view;
    private Context context;

    private AudioRecordService audioRecordService;
    private Handler mainHandler;

    private long startTime = 0;

    public AudioRecordPresenter(final AudioRecordContract.View view, Context context) {
        this.view = view;
        this.context = context;
        mainHandler = new Handler(Looper.getMainLooper());
        audioRecordService = new AudioRecordService();
    }

    @Override
    public void startRecord() {
        audioRecordService.setCallback(new OnAudioCallback() {
            @Override
            public void onStart() {
                Log.e(TAG, "Audio recording onStart");
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        view.startRecord();
                    }
                });
            }

            @Override
            public void onError(int errorCode, String des) {
                Log.e(TAG, "errorCode:" + errorCode + ";des:" + des);
                view.showErrorMessage(des);
            }

            @Override
            public void onException(Throwable e, String des) {
                Log.e(TAG, "异常:" + des);
            }

            @Override
            public void onBeginNewPeriod(int count) {
                Log.e(TAG, "开始新的一段音频");
            }

            @Override
            public void onEndLastPeriod(final AudioFilePathRule.AudioRecordFileType fileType, int count,
                                        final InputStream inputStream, final byte[] header,
                                        final long createTime, long timeLength) {
                Log.e(TAG, "结束一段音频，加密存储");
                if (inputStream == null) {
                    Log.e(TAG, "原始文件流为空");
                    return;
                }
                Log.d(TAG, "当前录制线程:" + Thread.currentThread().getName());

                // TODO: 写文件耗时
                ExecutorService service = Executors.newSingleThreadExecutor(new ThreadFactory() {
                    @Override
                    public Thread newThread(@NonNull Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setName("Audio Write File");
                        thread.setPriority(Thread.MAX_PRIORITY);
                        return thread;
                    }
                });
                service.execute(new Runnable() {
                    @Override
                    public void run() {
                        // TODO 实际应该是加密路径，与原路径不同
                        final String desPath = FileUtils.getAudioFilePath(fileType, createTime);
                        Log.d(TAG, "当前写文件线程:" + Thread.currentThread().getName());
                        FileUtils.writeAudioFile(desPath, inputStream, header);

                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                view.updateRecord(desPath);
                            }
                        });
                    }
                });
            }

            @Override
            public void onStop() {
                Log.e(TAG, "Audio recording onStop");
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        view.stopRecord();
                    }
                });
            }
        });

        audioRecordService.addAudioNewDataListener(new IAudioDataListener() {
            @Override
            public boolean onNewData(byte[] data, int start, int length) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - startTime >= 30 * 1000) {
                    Log.e(TAG, "音频源数据:" + length);
                    startTime = currentTime;
                }
                return true;
            }
        });
        AudioConfigModel audioConfig = new AudioConfigModel(30 * 1000, 16000,
                AudioConfigModel.AUDIO_FORMAT_AMR_NB,
                AudioFormat.ENCODING_PCM_16BIT,
                AudioFormat.CHANNEL_IN_MONO, 2560);
        AudioFilePathRule audioFilePathRule = new AudioFilePathRule() {
            @Override
            public String getFilePath(AudioRecordFileType fileType, int count,
                                      long createTime, long timeLength) {
                return FileUtils.getAudioFilePath(fileType, createTime);
            }
        };
        audioRecordService.startRecord(audioConfig, audioFilePathRule);
    }

    @Override
    public void stopRecord() {
        audioRecordService.stopRecord();
    }

    @Override
    public boolean isRecording() {
        return audioRecordService.isRecording();
    }

    // Test
    public void updateAudioDuration(long periodLength) {
        audioRecordService.updateAudioPeriodLength(periodLength);
    }
}

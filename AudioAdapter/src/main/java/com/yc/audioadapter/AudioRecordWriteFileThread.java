package com.yc.audioadapter;

import android.media.AudioRecord;
import android.text.TextUtils;
import android.util.Log;

import com.yc.daudio.AudioConfigModel;
import com.yc.daudio.AudioFilePathRule;
import com.yc.daudio.IAudioDataListener;
import com.yc.daudio.IAudioProcess;
import com.yc.daudio.OnAudioCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class AudioRecordWriteFileThread extends Thread {

    private final AudioRecord audioRecord;
    private final AudioFilePathRule filePathRule;
    private final AudioConfigModel audioConfig;
    private final TimePeriod timePeriod;
    private OnAudioCallback audioCallback;
    private CopyOnWriteArrayList<IAudioDataListener> onNewDataListenerList;

    private boolean isStop;
    private int count;
    private long lastPrintExceptionTime;

    /**
     * Different audio process
     */
    private IAudioProcess audioProcess;

    /**
     * @param audioRecord
     * @param audioConfig
     * @param filePathRule rule to new file when interval is coming
     * @param timePeriod   split timePeriod
     */
    public AudioRecordWriteFileThread(AudioRecord audioRecord, AudioConfigModel audioConfig, AudioFilePathRule filePathRule,
                                      TimePeriod timePeriod) {
        this(audioRecord, audioConfig, filePathRule, timePeriod, null);
    }

    /**
     * @param audioRecord
     * @param audioConfig
     * @param filePathRule  rule to new file when interval is coming
     * @param timePeriod    split timePeriod
     * @param audioCallback progress audioCallback, when start, stop, error. newRecordFile, endLastRecordFile
     */
    public AudioRecordWriteFileThread(AudioRecord audioRecord, AudioConfigModel audioConfig, AudioFilePathRule filePathRule,
                                      TimePeriod timePeriod, OnAudioCallback audioCallback) {
        super("Audio Record Thread");
        this.audioRecord = audioRecord;
        this.audioConfig = audioConfig;
        this.filePathRule = filePathRule;
        this.timePeriod = timePeriod;
        this.audioCallback = audioCallback;

        initAudioProcess();

        count = 0;
        lastPrintExceptionTime = 0;
        isStop = false;
    }

    private void initAudioProcess() {
        this.audioProcess = AudioToolUtils.getAudioProcessor(audioConfig);
        try {
            audioProcess.init();
        } catch (IOException e) {
            if (audioCallback != null) {
                audioCallback.onException(e, audioConfig.getAudioTypeFormat()
                        + " 音频处理器初始化失败:" + Log.getStackTraceString(e));
            }
        }
    }

    @Override
    public void run() {
        super.run();
        if (!AudioToolUtils.isSupportedFormat(audioProcess)) {
            if (audioCallback != null) {
                audioCallback.onError(OnAudioCallback.ERROR_FORMAT_NOT_SUPPORT,
                        "未支持的音频格式，格式为:" + audioConfig.getAudioTypeFormat());
            }
            return;
        }

        if (audioCallback != null) {
            audioCallback.onStart();
        }
        while (!isStop) {
            long currentPeriodTime = 0l;
            String currentPeriodFilePath = null;
            ByteArrayOutputStream currentPeriodOutStream = null;
            int readLength;
            byte data[] = new byte[audioConfig.getReadBufferSize()];
            // get data
            while ((readLength = audioRecord.read(data, 0, audioConfig.getReadBufferSize())) != -1 && !isStop) {
                if (timePeriod.isNewPeriod(currentPeriodTime)) {
                    count++;

                    // save last data if exist
                    saveAndEncryptFile(currentPeriodFilePath, currentPeriodOutStream, currentPeriodTime);

                    // init new period params
                    currentPeriodFilePath = filePathRule.getFilePath(audioProcess.getFileType(), count,
                            currentPeriodTime = timePeriod.getCurrentPeriodTime(), timePeriod.getPeriodLength());
                    currentPeriodOutStream = new ByteArrayOutputStream(audioConfig.getReadBufferSize() * 128);
                    if (audioCallback != null) {
                        audioCallback.onBeginNewPeriod(count);
                    }
                }

                try {
                    // transform data according to audio format
                    byte newData[] = audioProcess.transformData(data, readLength);
                    if (newData != null && newData.length > 0) {
                        if (currentPeriodOutStream != null) {
                            currentPeriodOutStream.write(newData, 0, newData.length);
                        }
                    }
                } catch (IOException e) {
                    exceptionLimit(e, currentPeriodTime, currentPeriodFilePath);
                }

                // send to outer listener
                if (onNewDataListenerList != null) {
                    for (IAudioDataListener onNewDataListener : onNewDataListenerList) {
                        if (onNewDataListener != null) {
                            onNewDataListener.onNewData(data, 0, readLength);
                        }
                    }
                }
            }

            // if stop, save data
            if (isStop) {
                saveAndEncryptFile(currentPeriodFilePath, currentPeriodOutStream, currentPeriodTime);
            }
        }

        if (isStop && audioCallback != null) {
            audioCallback.onStop();
        }
        try {
            audioProcess.release();
        } catch (IOException e) {
            exceptionLimit(e);
        }
    }

    public AudioRecordWriteFileThread setAudioCallback(OnAudioCallback audioCallback) {
        this.audioCallback = audioCallback;
        return this;
    }

    public AudioRecordWriteFileThread setOnNewDataListenerList(
            CopyOnWriteArrayList<IAudioDataListener> onNewDataListenerList) {
        this.onNewDataListenerList = onNewDataListenerList;
        return this;
    }

    public boolean isStop() {
        return isStop;
    }

    public AudioRecordWriteFileThread setStop(boolean stop) {
        isStop = stop;
        return this;
    }

    private void saveAndEncryptFile(String currentPeriodFilePath, ByteArrayOutputStream currentPeriodOutStream,
                                    long currentPeriodTime) {
        if (currentPeriodOutStream == null || TextUtils.isEmpty(currentPeriodFilePath)) {
            // no need audioCallback for first time
            return;
        }

        if (audioCallback != null) {
            audioCallback.onEndLastPeriod(audioProcess.getFileType(), count,
                    audioProcess.toInputStream(currentPeriodOutStream), audioProcess.getHeader(),
                    currentPeriodTime, audioConfig.getPeriodLength());
        }
    }

    private boolean exceptionLimit(Throwable e, long createTime, String filePath) {
        long currentTime = System.currentTimeMillis();
        // print exception 1 minute at most once
        if (currentTime - lastPrintExceptionTime >= 60 * 1000) {
            if (audioCallback != null) {
                audioCallback.onException(e, "创建于: " + createTime + " 的音频文件处理失败"
                        + "，源文件地址为: " + filePath
                        + ", 异常为: " + Log.getStackTraceString(e));
            }
            lastPrintExceptionTime = currentTime;
            return true;
        }
        return false;
    }

    private boolean exceptionLimit(Throwable e) {
        long currentTime = System.currentTimeMillis();
        // print exception 1 minute at most once
        if (currentTime - lastPrintExceptionTime >= 60 * 1000) {
            if (audioCallback != null) {
                audioCallback.onException(e, "");
            }
            lastPrintExceptionTime = currentTime;
            return true;
        }
        return false;
    }
}
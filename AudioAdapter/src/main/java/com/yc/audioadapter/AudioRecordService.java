package com.yc.audioadapter;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

import com.yc.daudio.AudioConfigModel;
import com.yc.daudio.AudioFilePathRule;
import com.yc.daudio.IAudioDataListener;
import com.yc.daudio.IAudioRecord;
import com.yc.daudio.OnAudioCallback;

import java.util.concurrent.CopyOnWriteArrayList;

public class AudioRecordService implements IAudioRecord {

    public static final int DEFAULT_INTERVAL = 30 * 1000;

    private AudioFilePathRule filePathRule;
    private TimePeriod timePeriod;
    private AudioConfigModel audioConfig;
    private OnAudioCallback audioCallback;

    private AudioRecord audioRecord;
    private AudioRecordWriteFileThread audioRecordWriteFileThread;

    private final CopyOnWriteArrayList<IAudioDataListener> onNewDataListenerList;

    private volatile boolean isRecording = false;

    public AudioRecordService() {
        onNewDataListenerList = new CopyOnWriteArrayList<>();
    }

    @Override
    public void startRecord(AudioConfigModel audioConfig, AudioFilePathRule filePathRule) {
        if (isRecording) {
            return;
        }

        synchronized (AudioRecordService.class) {
            if (isRecording) {
                return;
            }

            isRecording = true;
            init(audioConfig, filePathRule);
            createAudioRecordAndThread();

            audioRecordWriteFileThread.start();
            audioRecord.startRecording();
        }
    }

    private void init(AudioConfigModel audioConfig, AudioFilePathRule filePathRule) {
        this.audioConfig = audioConfig != null ? audioConfig :
                new AudioConfigModel(DEFAULT_INTERVAL, 16000,
                        AudioConfigModel.AUDIO_FORMAT_AMR_NB,
                        AudioFormat.ENCODING_PCM_16BIT,
                        AudioFormat.CHANNEL_IN_MONO, 2560);
        this.filePathRule = filePathRule;

        // periodLength should be greater than or equal to 5000 ms
        long periodLength = this.audioConfig.getPeriodLength() >= 5000 ?
                this.audioConfig.getPeriodLength() : DEFAULT_INTERVAL;
        this.audioConfig.setPeriodLength(periodLength);
        this.timePeriod = new TimePeriod(periodLength);
    }

    private void createAudioRecordAndThread() {
        int bufferSize = AudioRecord.getMinBufferSize(audioConfig.getSampleRateInHz(), audioConfig.getChannelConfig(),
                audioConfig.getAudioFormat());
        if (bufferSize < 1) {
            bufferSize = 2048;
        }
        try {
            audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, audioConfig.getSampleRateInHz(),
                    audioConfig.getChannelConfig(),
                    audioConfig.getAudioFormat(), bufferSize);
        } catch (Throwable tr) {
            // invalid buffer size
            audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, audioConfig.getSampleRateInHz(),
                    audioConfig.getChannelConfig(),
                    audioConfig.getAudioFormat(), 2048);
        }
        audioRecordWriteFileThread = new AudioRecordWriteFileThread(audioRecord, audioConfig, filePathRule,
                timePeriod, audioCallback);
        audioRecordWriteFileThread.setOnNewDataListenerList(onNewDataListenerList);
        audioRecordWriteFileThread.setName("Audio Record Thread");
        audioRecordWriteFileThread.setPriority(Thread.MAX_PRIORITY);
    }

    @Override
    public void stopRecord() {
        if (!isRecording) {
            return;
        }

        synchronized (AudioRecordService.class) {
            if (!isRecording) {
                return;
            }

            audioRecordWriteFileThread.setStop(true);
            audioRecord.stop();
            audioRecord.release();
            audioRecord = null;
            audioCallback = null;
            onNewDataListenerList.clear();
            isRecording = false;
        }
    }

    @Override
    public boolean isRecording() {
        return isRecording;
    }

    @Override
    public void updateAudioPeriodLength(long periodLength) {
        if (periodLength >= 5 && periodLength != audioConfig.getPeriodLength()) {
            this.audioConfig.setPeriodLength(periodLength);
            this.timePeriod.setPeriodLength(periodLength);
        }
    }

    @Override
    public IAudioRecord setCallback(OnAudioCallback callback) {
        this.audioCallback = callback;
        if (audioRecordWriteFileThread != null) {
            audioRecordWriteFileThread.setAudioCallback(callback);
        }
        return this;
    }

    @Override
    public IAudioRecord addAudioNewDataListener(IAudioDataListener dAudioNewDataListener) {
        if (dAudioNewDataListener == null) {
            throw new RuntimeException("dAudioNewDataListener cannot be null.");
        }

        synchronized (onNewDataListenerList) {
            if (!onNewDataListenerList.contains(dAudioNewDataListener)) {
                onNewDataListenerList.add(dAudioNewDataListener);
            }
        }
        return this;
    }

    @Override
    public IAudioRecord removeAudioNewDataListener(IAudioDataListener dAudioNewDataListener) {
        if (dAudioNewDataListener == null) {
            throw new RuntimeException("dAudioNewDataListener cannot be null.");
        }

        synchronized (onNewDataListenerList) {
            onNewDataListenerList.remove(dAudioNewDataListener);
        }
        return this;
    }
}

package com.yc.daudio;

import android.media.AudioFormat;
import android.media.AudioRecord;

import java.nio.ByteBuffer;

/**
 * 音频录制相关配置
 */
public class AudioConfigModel {

    /**
     * 上报音频编码格式：AMR NB
     */
    public static final int AUDIO_FORMAT_AMR_NB = 1;

    /**
     * 上报音频编码格式：AMR WB
     */
    public static final int AUDIO_FORMAT_AMR_WB = 2;

    /**
     * 上报音频编码格式：MP3
     */
    public static final int AUDIO_FORMAT_MP3 = 3;

    /**
     * 上报音频编码格式：AAC -LC/LTP
     */
    public static final int AUDIO_FORMAT_LC_AAC = 4;

    /**
     * 上报音频编码格式：PCM/WAV (Encoder: Android 4.1+)
     */
    public static final int AUDIO_FORMAT_WAV = 5;

    /**
     * 上报音频编码格式：HE-AACv2 (enhanced AAC+)
     */
    public static final int AUDIO_FORMAT_HE_AAC_V2 = 6;

    /**
     * 上报音频编码格式：BV
     */
    public static final int AUDIO_FORMAT_BV = 7;

    /**
     * 分段的音频时长
     */
    private long periodLength;

    /**
     * 采样率
     */
    private int sampleRateInHz;

    /**
     * 音频格式类型：<br />
     * {@link #AUDIO_FORMAT_AMR_NB},
     * {@link #AUDIO_FORMAT_AMR_WB},
     * {@link #AUDIO_FORMAT_MP3},
     * {@link #AUDIO_FORMAT_LC_AAC},
     * {@link #AUDIO_FORMAT_WAV},
     * {@link #AUDIO_FORMAT_HE_AAC_V2},
     * {@link #AUDIO_FORMAT_BV}
     */
    private int audioTypeFormat;

    /**
     * 音频数据格式：<br />
     * {@link AudioFormat#ENCODING_PCM_8BIT},
     * {@link AudioFormat#ENCODING_PCM_16BIT},
     * and {@link AudioFormat#ENCODING_PCM_FLOAT}
     */
    private int audioFormat;

    /**
     * 音频通道：<br />
     * {@link AudioFormat#CHANNEL_IN_MONO},
     * {@link AudioFormat#CHANNEL_IN_STEREO}
     */
    private int channelConfig;

    /**
     * {@link AudioRecord#read(ByteBuffer, int)} 读取的字节数<br />
     * 不是创建 {@link AudioRecord} 时需要的 bufferSize
     */
    private int readBufferSize;

    /**
     * MIC Id, 单 MIC 忽略
     */
    private int micId;

    /**
     * 构造
     * @param periodLength      分段的音频时长
     * @param sampleRateInHz    采样率
     * @param audioTypeFormat   音频格式类型
     * @param audioFormat       音频数据格式
     * @param channelConfig     音频通道
     * @param readBufferSize    读取的字节数
     */
    public AudioConfigModel(long periodLength, int sampleRateInHz,
                            int audioTypeFormat, int audioFormat,
                            int channelConfig, int readBufferSize) {
        this.periodLength = periodLength;
        this.sampleRateInHz = sampleRateInHz;
        this.audioTypeFormat = audioTypeFormat;
        this.audioFormat = audioFormat;
        this.channelConfig = channelConfig;
        this.readBufferSize = readBufferSize;
    }

    public long getPeriodLength() {
        return periodLength;
    }

    public void setPeriodLength(long periodLength) {
        this.periodLength = periodLength;
    }

    public int getSampleRateInHz() {
        return sampleRateInHz;
    }

    public AudioConfigModel setSampleRateInHz(int sampleRateInHz) {
        this.sampleRateInHz = sampleRateInHz;
        return this;
    }

    public int getAudioTypeFormat() {
        return audioTypeFormat;
    }

    public AudioConfigModel setAudioTypeFormat(int audioTypeFormat) {
        this.audioTypeFormat = audioTypeFormat;
        return this;
    }

    public int getAudioFormat() {
        return audioFormat;
    }

    public AudioConfigModel setAudioFormat(int audioFormat) {
        this.audioFormat = audioFormat;
        return this;
    }

    public int getChannelConfig() {
        return channelConfig;
    }

    public AudioConfigModel setChannelConfig(int channelConfig) {
        this.channelConfig = channelConfig;
        return this;
    }

    public int getReadBufferSize() {
        return readBufferSize;
    }

    public AudioConfigModel setReadBufferSize(int readBufferSize) {
        this.readBufferSize = readBufferSize;
        return this;
    }

    public int getMicId() {
        return micId;
    }

    public void setMicId(int micId) {
        this.micId = micId;
    }
}

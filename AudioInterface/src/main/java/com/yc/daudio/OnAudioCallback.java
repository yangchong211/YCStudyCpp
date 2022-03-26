package com.yc.daudio;

import java.io.InputStream;

/**
 * 音频录制生命周期回调
 */
public interface OnAudioCallback {

    /**
     * 音频格式不支持
     */
    int ERROR_FORMAT_NOT_SUPPORT = 0;

    /**
     * 录制开始
     */
    void onStart();

    /**
     * 录制发生错误
     *
     * @param errorCode 错误码
     * @param des       错误描述信息
     */
    void onError(int errorCode, String des);

    /**
     * 录制发生异常
     *
     * @param e   异常
     * @param des 异常描述信息
     */
    void onException(Throwable e, String des);

    /**
     * 新的一段音频开始
     *
     * @param count 新生成的音频文件计数，音频服务开始后从 1 起累加
     */
    void onBeginNewPeriod(int count);

    /**
     * 一段音频结束
     *
     * @param fileType    音频文件类型，根据类型创建音频存储文件<br />
     *                    {@link AudioFilePathRule.AudioRecordFileType#TYPE_AUDIO_AMR} and
     *                    {@link AudioFilePathRule.AudioRecordFileType#TYPE_AUDIO_BV}
     * @param count       新生成的音频文件计数，音频服务开始后从 1 起累加
     * @param inputStream 一段音频的输入流，用于加密、存储
     * @param header      音频格式的文件头，如 amr {0x23, 0x21, 0x41, 0x4D, 0x52, 0x0A}
     * @param createTime  当前这段音频的创建时间
     * @param timeLength  单个音频时长
     */
    void onEndLastPeriod(AudioFilePathRule.AudioRecordFileType fileType,
                         int count, InputStream inputStream, byte[] header,
                         long createTime, long timeLength);

    /**
     * 录制结束
     */
    void onStop();
}




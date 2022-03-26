package com.yc.daudio;

/**
 * 音频录制接口，用于开始、结束录制，设置回调等
 * <ul>
 * <li>{@link #startRecord(AudioConfigModel, AudioFilePathRule)} 开始录制音频</li>
 * <li>{@link #stopRecord()} 停止录制音频</li>
 * <li>{@link #isRecording()} 判断音频是否正在录制</li>
 * <li>{@link #updateAudioPeriodLength(long)} 录制过程中更改音频分段时长</li>
 * <li>{@link #setCallback(OnAudioCallback)} 设置音频录制生命周期的回调</li>
 * <li>{@link #addAudioNewDataListener(IAudioDataListener)} 注册原始音频数据的回调</li>
 * <li>{@link #removeAudioNewDataListener(IAudioDataListener)} 移除原始音频数据的回调</li>
 * </ul>
 */
public interface IAudioRecord {

    /**
     * 开始录制音频
     *
     * @param dAudioConfig 音频相关配置
     * @param filePathRule 音频存储路径规则
     */
    void startRecord(AudioConfigModel dAudioConfig, AudioFilePathRule filePathRule);

    /**
     * 停止录制音频
     */
    void stopRecord();

    /**
     * 判断音频是否正在录制
     *
     * @return true 正在录制，false 未在录制
     */
    boolean isRecording();

    /**
     * 录制过程中更改音频分段时长<br />
     * 录制之前通过 {@link AudioConfigModel#periodLength} 设置
     *
     * @param periodLength 音频分段时长，单位 ms
     */
    void updateAudioPeriodLength(long periodLength);

    /**
     * 设置音频录制生命周期的回调
     *
     * @param audioCallback {@link OnAudioCallback}
     * @return
     */
    IAudioRecord setCallback(OnAudioCallback audioCallback);

    /**
     * 注册原始音频数据的回调
     *
     * @param dAudioNewDataListener {@link IAudioDataListener}
     * @return
     */
    IAudioRecord addAudioNewDataListener(IAudioDataListener dAudioNewDataListener);

    /**
     * 移除原始音频数据的回调
     *
     * @param dAudioNewDataListener {@link IAudioDataListener}
     * @return
     */
    IAudioRecord removeAudioNewDataListener(IAudioDataListener dAudioNewDataListener);
}

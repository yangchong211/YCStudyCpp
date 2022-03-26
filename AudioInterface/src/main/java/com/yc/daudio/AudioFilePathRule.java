package com.yc.daudio;

/**
 * 音频存储路径回调
 */
public interface AudioFilePathRule {

    /**
     * 获取音频存储的文件路径
     *
     * @param fileType   音频文件类型，根据类型创建音频存储文件<br />
     *                   {@link AudioRecordFileType#TYPE_AUDIO_AMR} and
     *                   {@link AudioRecordFileType#TYPE_AUDIO_BV}
     * @param count      新生成的音频文件计数，音频服务开始后从 1 起累加
     * @param createTime 创建文件的时间
     * @param timeLength 单个音频的时长
     * @return
     */
    String getFilePath(AudioRecordFileType fileType, int count, long createTime, long timeLength);

    /**
     * 录制的文件格式(后缀)，与 {@link AudioConfigModel#audioTypeFormat} 不同
     */
    enum AudioRecordFileType {

        TYPE_AUDIO_AMR(1),
        TYPE_AUDIO_MP3(2),
        TYPE_AUDIO_AAC(3),
        TYPE_AUDIO_WAV(4),
        TYPE_AUDIO_PCM(5),
        TYPE_AUDIO_BV(6);

        int type;

        AudioRecordFileType(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }
    }
}

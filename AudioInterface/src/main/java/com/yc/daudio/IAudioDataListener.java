package com.yc.daudio;

import android.media.AudioRecord;

import java.nio.ByteBuffer;

/**
 * 音频原始数据的回调
 */
public interface IAudioDataListener {

    /**
     * 抛出通过 {@link AudioRecord#read(ByteBuffer, int)} 读取的原始音频数据
     *
     * @param data   音频数据，大小不超过 {@link AudioConfigModel#readBufferSize}
     * @param start  音频数据的起始偏移量
     * @param length 实际读取的字节数
     * @return
     */
    boolean onNewData(byte[] data, int start, int length);
}

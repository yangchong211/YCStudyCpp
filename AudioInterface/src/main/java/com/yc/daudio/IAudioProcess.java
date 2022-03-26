package com.yc.daudio;

import android.media.AudioFormat;

import com.yc.daudio.AudioFilePathRule.AudioRecordFileType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 音频处理接口，不同的音频格式有不同的处理
 */
public interface IAudioProcess {

    /**
     * 初始化操作
     *
     * @return true 初始化成功，false 初始化失败
     * @throws IOException
     */
    boolean init() throws IOException;

    /**
     * 获取音频录制文件类型
     *
     * @return 如果不支持，返回 null
     */
    AudioRecordFileType getFileType();

    /**
     * 是否需要转换音频原始数据，如 <br />
     * {@link AudioFormat#ENCODING_PCM_16BIT} 转 {@link AudioFormat#ENCODING_PCM_8BIT}
     *
     * @return true 需要转换，false 不需要转换
     */
    boolean isNeedTransformData();

    /**
     * 音频格式的头部信息，如 <br />
     * amr {0x23, 0x21, 0x41, 0x4D, 0x52, 0x0A}
     *
     * @return 头部信息
     */
    byte[] getHeader();

    /**
     * 音频原始数据(或经过 {@link #transformData(byte[], int)} 转换后的音频数据)转换为输入流形式，
     * 用于加密、存储
     *
     * @param byteArrayOutputStream 音频数据
     * @return 转换为输入流的音频数据
     */
    InputStream toInputStream(ByteArrayOutputStream byteArrayOutputStream);

    /**
     * 数据转换
     *
     * @param sourceData   源数据
     * @param sourceLength 源数据长度
     * @return 目标数据
     * @throws IOException
     */
    byte[] transformData(byte sourceData[], int sourceLength) throws IOException;

    /**
     * 音频处理完毕后释放资源
     *
     * @return
     * @throws IOException
     */
    boolean release() throws IOException;
}

package com.yc.audioadapter;


import com.yc.daudio.AudioFilePathRule;
import com.yc.daudio.IAudioProcess;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 压缩音频数据为bv格式
 * 注:每压缩一个bv文件，需要重新new一个对象&release
 * 在不同线程中使用时，线程停止时一定要调用release，否则setParam时会出现-102错误
 */
public class AudioBVCompress implements IAudioProcess {

    private boolean isInited = false;

    public AudioBVCompress() {
    }

    public boolean init() throws IOException {
        setParam();
        int res = vadJni.init();
        if (0 != res) {
            throw new IOException("vadJni init return " + res);
        }
        isInited = true;
        return true;
    }

    private void setParam() throws IOException {
        Map<Integer, Integer> paramMap = new HashMap<>();
        paramMap.put(1, 110);
        paramMap.put(2, 1875);
        paramMap.put(3, 15);
        paramMap.put(4, 5);
        paramMap.put(8, 5);
        paramMap.put(9, 5);
        paramMap.put(10, 0);
        paramMap.put(11, 0);
        paramMap.put(13, 16000);
        paramMap.put(14, 4);
        paramMap.put(15, 15);
        paramMap.put(16, 10);
        paramMap.put(21, 1);

        int res = -1;
        for (int key : paramMap.keySet()) {
            res = vadJni.setParam(key, paramMap.get(key));
            if (0 != res) {
                throw new IOException("audio bv compress setParam " + paramMap.get(key) + "return " + res);
            }
        }
    }

    @Override
    public AudioFilePathRule.AudioRecordFileType getFileType() {
        return AudioFilePathRule.AudioRecordFileType.TYPE_AUDIO_BV;
    }

    @Override
    public boolean isNeedTransformData() {
        return true;
    }

    @Override
    public byte[] getHeader() {
        return null;
    }

    @Override
    public byte[] transformData(byte[] sourceData, int sourceLength) throws IOException {
        if (!isInited) {
            return null;
        }

        if (sourceData == null || sourceData.length < sourceLength) {
            return sourceData;
        }

        byte[] newData = sourceData;
        if (sourceLength != sourceData.length) {
            newData = new byte[sourceLength];
            System.arraycopy(sourceData, 0, newData, 0, sourceLength);
        }

        byte[] bvData = null;
        short[] data = vadJni.byteToShortArray(newData);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(newData.length);
        try {
            int res = vadJni.sendData(data, data.length);
            if (0 != res) {
                throw new IOException("vadJni sendData return " + res);
            }
            byte[] callbackData = new byte[newData.length];
            while (true) {
                int cn = vadJni.getFeedbackData(callbackData, callbackData.length);
                if (cn < 0) {
                    throw new IOException("vadJni getFeedbackData return " + cn);
                } else if (cn == 0) {
                    break;
                } else {
                    byteArrayOutputStream.write(callbackData, 0, cn);
                }
            }
        } finally {
            bvData = byteArrayOutputStream.toByteArray();
            try {
                byteArrayOutputStream.close();
            } catch (IOException e) {

            }
        }
        return bvData;
    }

    public boolean release() throws IOException {
        int res = vadJni.exit();
        if (0 != res) {
            throw new IOException("vadJni exit return " + res);
        }
        return true;
    }

    @Override
    public InputStream toInputStream(ByteArrayOutputStream byteArrayOutputStream) {
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }
}

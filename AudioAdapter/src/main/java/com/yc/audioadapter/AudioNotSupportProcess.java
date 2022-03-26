package com.yc.audioadapter;

import com.yc.daudio.AudioFilePathRule.AudioRecordFileType;
import com.yc.daudio.IAudioProcess;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class AudioNotSupportProcess implements IAudioProcess {

    @Override
    public boolean init() throws IOException {
        return true;
    }

    @Override
    public AudioRecordFileType getFileType() {
        return null;
    }

    @Override
    public boolean isNeedTransformData() {
        return false;
    }

    @Override
    public byte[] transformData(byte[] sourceData, int sourceLength) throws IOException {
        return null;
    }

    @Override
    public boolean release() throws IOException {
        return true;
    }

    @Override
    public byte[] getHeader() {
        return null;
    }

    @Override
    public InputStream toInputStream(ByteArrayOutputStream byteArrayOutputStream) {
        return null;
    }
}

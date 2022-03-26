package com.yc.audioadapter;

import android.media.AudioFormat;
import android.util.Log;

import com.yc.daudio.AudioFilePathRule.AudioRecordFileType;
import com.yc.daudio.IAudioProcess;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;


public class AudioAmrProcess implements IAudioProcess {

    private static final String TAG = AudioAmrProcess.class.getSimpleName();

    private final int audioFormat;

    private final AmrConverter mConverter;

    private byte[] pcmData;
    // PCM tail data while data less than SAMPLES_PER_FRAME
    private byte[] pcmTailData;

    public AudioAmrProcess(int audioFormat) {
        this.audioFormat = audioFormat;
        this.mConverter = new AmrConverter();
    }

    @Override
    public boolean init() {
        mConverter.init();
        return true;
    }

    @Override
    public AudioRecordFileType getFileType() {
        return AudioRecordFileType.TYPE_AUDIO_AMR;
    }

    @Override
    public byte[] getHeader() {
        // return amr file header
        return new byte[]{0x23, 0x21, 0x41, 0x4D, 0x52, 0x0A};
    }

    @Override
    public boolean isNeedTransformData() {
        /**
         *  ENCODING_PCM_16BIT or ENCODING_PCM_8BIT
         *  ENCODING_PCM_FLOAT need to add more process
         */
        return audioFormat == AudioFormat.ENCODING_PCM_16BIT;
    }

    /**
     * Transform data
     *
     * @param sourceData
     * @param sourceLength
     * @return
     */
    @Override
    public byte[] transformData(byte[] sourceData, int sourceLength) {
        if (sourceData == null || sourceData.length < sourceLength || !isNeedTransformData()) {
            return sourceData;
        }

        if (sourceLength <= 1) {
            return null;
        }

        // ENCODING_PCM_16BIT, 16khz -> 8khz, get every two byte
        int newLength = ((sourceLength - 2) / 4) * 2 + 2;
        byte newData[] = new byte[newLength];
        int i = 0;
        while (i < newLength) {
            if (i * 2 >= sourceLength) {
                continue;
            }

            newData[i] = sourceData[i * 2];
            if (i + 1 < newLength) {
                newData[i + 1] = sourceData[i * 2 + 1];
            }
            i += 2;
        }

        return pcm2Amr(newData);
    }

    @Override
    public boolean release() {
        mConverter.release();
        return true;
    }

    @Override
    public InputStream toInputStream(ByteArrayOutputStream byteArrayOutputStream) {
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    /**
     * Convert pcm to amr
     *
     * @param data pcm data
     * @return amr data
     */
    public byte[] pcm2Amr(byte[] data) {
        if (data == null || data.length == 0) {
            return new byte[0];
        }

        // merge cached data and new data
        byte[] newData = data;
        if (pcmTailData != null) {
            newData = new byte[pcmTailData.length + data.length];
            System.arraycopy(pcmTailData, 0, newData, 0, pcmTailData.length);
            System.arraycopy(data, 0, newData, pcmTailData.length, data.length);
        }

        // split data which less than 320
        int tailLength = newData.length % (AmrConverter.SAMPLES_PER_FRAME * 2);
        if (tailLength != 0) {
            if (pcmTailData == null || pcmTailData.length != tailLength) {
                pcmTailData = new byte[tailLength];
            }
            if (pcmData == null || pcmData.length != newData.length - tailLength) {
                pcmData = new byte[newData.length - tailLength];
            }
            System.arraycopy(newData, 0, pcmData, 0, newData.length - tailLength);
            System.arraycopy(newData, newData.length - tailLength, pcmTailData, 0, tailLength);
        } else {
            pcmTailData = null;
            if (pcmData == null || pcmData.length != newData.length) {
                pcmData = new byte[newData.length];
            }
            System.arraycopy(newData, 0, pcmData, 0, newData.length);
        }

        // convert pcm to amr
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int pcmLength = pcmData.length;
        int start = 0;
        try {
            while (start < pcmLength) {
                byte[] out = mConverter.convert(pcmData, start, AmrConverter.SAMPLES_PER_FRAME * 2);
                start += AmrConverter.SAMPLES_PER_FRAME * 2;
                if (out != null) {
                    outputStream.write(out);
                }
            }
        } catch (Exception e) {
            Log.d(TAG, Log.getStackTraceString(e));
        }
        return outputStream.toByteArray();
    }
}

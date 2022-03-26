package com.yc.audioadapter;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodecList;
import android.media.MediaFormat;

import java.io.IOException;
import java.nio.ByteBuffer;


public class AmrConverter {

    private static String TAG = AmrConverter.class.getSimpleName();

    private MediaCodec mCodec;
    private MediaCodec.BufferInfo mInfo;

    // frame is 20 msec at 8.000 khz
    public final static int SAMPLES_PER_FRAME = 8000 * 20 / 1000;
    private int mBufIn = 0;
    private int mBufOut = 0;
    private boolean mSawOutputEOS;
    private byte[] outData;

    @TargetApi(21)
    public void init() {
        MediaFormat format = new MediaFormat();
        format.setString(MediaFormat.KEY_MIME, MediaFormat.MIMETYPE_AUDIO_AMR_NB);
        format.setInteger(MediaFormat.KEY_SAMPLE_RATE, 8000);
        format.setInteger(MediaFormat.KEY_CHANNEL_COUNT, 1);
        format.setInteger(MediaFormat.KEY_BIT_RATE, 12200);

        MediaCodecList mcl = new MediaCodecList(MediaCodecList.REGULAR_CODECS);
        String name = mcl.findEncoderForFormat(format);
        if (name != null) {
            try {
                mCodec = MediaCodec.createByCodecName(name);
                mCodec.configure(format,
                        null /* surface */,
                        null /* crypto */,
                        MediaCodec.CONFIGURE_FLAG_ENCODE);
                mCodec.start();
            } catch (IOException e) {
                if (mCodec != null) {
                    mCodec.release();
                }
                mCodec = null;
            }
        }
        mInfo = new MediaCodec.BufferInfo();
    }

    @TargetApi(21)
    public byte[] convert(byte[] data, int offset, int length) {
        if (mCodec == null) {
            throw new IllegalStateException("Codec not open");
        }

        if (mBufOut >= mBufIn && !mSawOutputEOS) {
            // no data left in buffer, refill it
            mBufOut = 0;
            mBufIn = 0;

            int index = mCodec.dequeueInputBuffer(0);
            if (index >= 0) {
                ByteBuffer buf = mCodec.getInputBuffer(index);
                if (buf != null) {
                    // this buf size only support less than 320
                    buf.put(data, offset, length);
                }
                mCodec.queueInputBuffer(index,
                        0 /* offset */,
                        length,
                        0 /* presentationTimeUs */,
                        length < SAMPLES_PER_FRAME * 2 ? MediaCodec.BUFFER_FLAG_END_OF_STREAM : 0 /* flags */);
            }

            index = mCodec.dequeueOutputBuffer(mInfo, -1);
            if (index >= 0) {
                mBufIn = mInfo.size;

                if (outData == null || outData.length != mBufIn) {
                    outData = new byte[mBufIn];
                }
                ByteBuffer out = mCodec.getOutputBuffer(index);
                if (out != null) {
                    out.get(outData, 0 /* offset */, mBufIn /* length */);
                }
                mCodec.releaseOutputBuffer(index, false /* render */);
                if ((mInfo.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
                    mSawOutputEOS = true;
                }
            }
        }

        if (mBufOut < mBufIn) {
            // there is data in the buffer
            if (length > mBufIn - mBufOut) {
                length = mBufIn - mBufOut;
            }
            mBufOut += length;
            return outData != null ? outData : new byte[0];
        }

        if (mSawOutputEOS) {
            // data is no enough
            return null;
        }

        return new byte[0];
    }

    public void release() {
        try {
            if (mCodec != null) {
                mCodec.release();
            }
        } finally {
            mCodec = null;
        }
    }
}

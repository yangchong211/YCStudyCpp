package com.yc.audioadapter;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class vadJni {

    static {
        try {
            System.loadLibrary("DDLocalSpeechVad");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    public static native int init();

    public static native int exit();

    public static native int sendData(short[] pDataBuf, int iLen);

    public static native int getFeedbackData(byte[] pDataBuf, int iLen);

    public static native int detect();

    public static native int setParam(int type, int val);

    public static native int getParam(int type);

    public static native int setLogLevel(int iLevel);

    public static short[] byteToShortArray(byte[] byteArray) {
        short[] shortArray = new short[byteArray.length / 2];
        ByteBuffer.wrap(byteArray).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(shortArray);
        return shortArray;
    }
}

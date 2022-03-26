package com.yc.camera.callback;

import java.io.FileDescriptor;

/**
 * 主要提供视频实时数据流回调
 * YUV 格式:YUY2:YUV422
 */
public interface OnPreviewCallback {

    int FORMAT_YUV_420_888 = 1;
    int FORMAT_NV21 = 2;
    int FORMAT_YUY2 = 3;
    int FORMAT_YV12 = 4;
    int FORMAT_RGB_888 = 5;
    int FORMAT_JPEG = 6;
    int FORMAT_ARGB = 7;

    /**
     * （不建议使用这种方式）实时数据流yuv  回调
     * 目前YUV 支持的格式YUY2:YUV422
     *
     * @param bytes    YUV 实时流一帧数据
     * @param cameraId 摄像头ID：主要区分前摄/后摄
     * @param size     一帧YUV 大小
     * @param format   数据格式
     */
    void onPreviewFrame(byte[] bytes, int cameraId, int size, int format);

    @Deprecated
    void onPreviewFrame(byte[] bytes, int cameraId, int size);

    /**
     * 建议通过Fd的方式回调给上层，减少没有必要的copy过程
     * 通过fd 获取 YUV数据，主要解决zero_copy 的过程
     *
     * @param fileDescriptor fd引用
     * @param cameraId       摄像头ID：主要区分前摄/后摄
     * @param size:YUV       大小
     * @param format         数据格式
     */
    void onPreviewFd(FileDescriptor fileDescriptor, int cameraId, int size, int format);

    void onPreviewFd(FileDescriptor fileDescriptor, int cameraId, int size);
}

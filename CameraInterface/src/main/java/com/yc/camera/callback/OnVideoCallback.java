package com.yc.camera.callback;

import com.yc.camera.ICameraDevice;
import com.yc.camera.model.VideoModel;

/**
 * 视频每段录制完成的回调
 */
public interface OnVideoCallback {

    /**
     * {@link #onVideoTaken}录像完成，并且视频文件被成功保存，通过 path 可以得到视频文件的路径。
     */
    int VIDEO_EVENT_ADD_FILE_IN_GALLERY = 0;
    /**
     * 视频文件被删除时，会收到此通知，一 般发生在循环录制时，通过删除之前的视频文件释放空间。
     */
    int VIDEO_EVENT_DELETE_FILE_IN_GALLERY = 1;
    /**
     * 录制视频的时候检测到 SDCard 已经满了。
     */
    int VIDEO_EVENT_SDCARD_FULL = 2;
    /**
     * 录制开始
     */
    int VIDEO_EVENT_RECORD_STATUS_START = 3;
    /**
     * 录制结束
     */
    int VIDEO_EVENT_RECORD_STATUS_STOP = 4;
    /**
     * 录制视频过程中发生了错误。
     */
    int VIDEO_EVENT_RECORD_RECORDING_ERROR = 5;
    /**
     * 录制视频时检测到 SDCard 被损坏了，如果 SDCard 的写入速度小于某个阀值，也可能被认为损坏了。
     */
    int VIDEO_EVENT_RECORD_SDCARD_DAMAGED = 6;
    /**
     * 目前未用
     * 低分辨率打点开始。
     */
    int VIDEO_EVENT_LOWRES_KEYPOINT_START = 7;
    /**
     * 低分辨率打点结束
     */
    int VIDEO_EVENT_LOWRES_KEYPOINT_STOP = 8;
    /**
     * 打点开始。 标记视频回调
     * {@link ICameraDevice#setUserMarkFilePath(String)}
     */
    int VIDEO_EVENT_KEYPOINT_START = 9;
    /**
     * 打点结束。
     * {@link ICameraDevice#setUserMarkFilePath(String)}
     */
    int VIDEO_EVENT_KEYPOINT_STOP = 10;


    /**
     * 录制过程中 SDCard 读写太慢
     * {@link OnVideoCallback#VIDEO_EVENT_RECORD_RECORDING_ERROR}
     */
    int ERROR_SDCARD_TOO_SLOW = -1;

    /**
     * 录制过程中 SDCard 只读
     * {@link OnVideoCallback#VIDEO_EVENT_RECORD_RECORDING_ERROR}
     */
    int ERROR_SDCARD_READ_ONLY = -2;

    /**
     * 录制过程中 SDCard 挂载异常：Transport endpoint is not connected
     * {@link OnVideoCallback#VIDEO_EVENT_RECORD_RECORDING_ERROR}
     */
    int ERROR_SDCARD_TRANSPORT = -3;

    /**
     * 视频录制完成的回调
     */
    void onVideoTaken(VideoModel dVideo);

    /**
     * 每帧视频数据回调
     *
     * @param bytes
     * @param dataType
     * @param size
     */
    void onVideoFrame(byte[] bytes, int dataType, int size);
}

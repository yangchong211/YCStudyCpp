package com.yc.camera.model;

import java.io.Serializable;

/**
 * 视频回调类型
 */
public class VideoModel implements Serializable {

    //事件类型
    private int eventType;
    //摄像头ID
    private int cameraId;
    //文件路径
    private String path;
    //视频开始时间
    private long startTime;
    //视频结束时间
    private long endTime;
    // 视频时长 毫秒
    private long duration;
    // 加密类型，0 是未加密，1，加密
    private int encryptType;
    // 错误类型
    private int errorCode;


    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public int getCameraId() {
        return cameraId;
    }

    public void setCameraId(int cameraId) {
        this.cameraId = cameraId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getEncryptType() {
        return encryptType;
    }

    public void setEncryptType(int encryptType) {
        this.encryptType = encryptType;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}

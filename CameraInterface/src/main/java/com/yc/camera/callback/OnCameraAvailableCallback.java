package com.yc.camera.callback;

/**
 * 摄像头状态回调接口类
 */
public interface OnCameraAvailableCallback {
    /**
     * 摄像头被拔回调
     */
    int STATUS_CAMERA_REMOVED = 0;
    /**
     * 摄像头挂载成功回调
     */
    int STATUS_CAMERA_ADDED = 1;


    /**
     * @param cameraId 摄像头ID,区分前后摄
     * @param status   摄像头状态 {@link #STATUS_CAMERA_REMOVED},{@link #STATUS_CAMERA_ADDED}
     */
    void onAvailable(int cameraId, int status);

}

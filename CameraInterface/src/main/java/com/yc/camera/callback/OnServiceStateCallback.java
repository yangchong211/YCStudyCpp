package com.yc.camera.callback;

/**
 * 监控录制服务是否可用的回调
 */
public interface OnServiceStateCallback {

    int SERVICE_STATE_NORMAL = 1;

    int SERVICE_STATE_DEATH = 0;

    /**
     * @param cameraId 摄像头ID
     * @param state    0 表示服务挂掉，1表示服务端正常
     * @param reason   挂掉原因
     */
    void onState(int cameraId, int state, String reason);

}

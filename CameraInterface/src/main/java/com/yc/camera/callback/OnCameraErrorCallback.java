package com.yc.camera.callback;

public interface OnCameraErrorCallback {

    int ERROR_CAMERA_DISCONNECTED = 1;
    int ERROR_CAMERA_UNAVAILABLE = 2;
    int ERROR_CAMERA_HW_EXCEPTION = 3;

    void onCameraError(int cameraId, String error);
}

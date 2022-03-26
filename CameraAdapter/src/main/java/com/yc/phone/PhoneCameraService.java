package com.yc.phone;

import android.hardware.Camera;
import android.util.Log;
import android.util.SparseArray;

import com.yc.camera.ICameraDevice;
import com.yc.camera.ICameraService;
import com.yc.camera.callback.OnCameraAvailableCallback;
import com.yc.camera.callback.OnServiceStateCallback;
import com.yc.camera.exception.CameraException;
import com.yc.camera.model.CameraInfo;


public class PhoneCameraService implements ICameraService {

    private static final String TAG = "PhoneCameraService";

    private SparseArray<ICameraDevice> cameras = new SparseArray<>();

    @Override
    public ICameraDevice openCamera(int cameraId) throws CameraException {
        ICameraDevice dCameraDevice;
        dCameraDevice = cameras.get(cameraId);
        if (dCameraDevice != null) {
            return dCameraDevice;
        }

        Camera camera;
        try {
            camera = Camera.open(cameraId);
        } catch (Throwable e) {
            Log.e(TAG, Log.getStackTraceString(e));
            throw new CameraException("打开摄像头异常");
        }
        if (camera == null) {
            throw new CameraException("摄像头对象为空");
        }
        dCameraDevice = new PhoneCameraDevice(camera);
        cameras.put(cameraId, dCameraDevice);
        return dCameraDevice;
    }

    @Override
    public void closeCamera(int cameraId) throws CameraException {
        try {
            cameras.remove(cameraId);
        } catch (Throwable e) {
            throw new CameraException("关闭摄像头异常");
        }
    }

    @Override
    public boolean isServiceAlive() {
        return true;
    }

    @Override
    public int getNumberOfCameras() {
        return Camera.getNumberOfCameras();
    }

    @Override
    public int getCameraState(int i) {
        // 熄火状态下的 camera 状态接口, Phone 没有熄火状态
        return -1;
    }

    @Override
    public CameraInfo getCameraInfo(int i) {
        return null;
    }

    @Override
    public int queryCarEngineState() {
        return 0;
    }

    @Override
    public void addCameraAvailableCallback(OnCameraAvailableCallback dCameraAvailableCallback) {

    }

    @Override
    public void removeCameraAvailableCallback(OnCameraAvailableCallback dCameraAvailableCallback) {

    }

    @Override
    public void addCarServiceStateCallback(OnServiceStateCallback dCarServiceStateCallback) {

    }

    @Override
    public void removeCarServiceStateCallback(OnServiceStateCallback dCarServiceStateCallback) {

    }
}

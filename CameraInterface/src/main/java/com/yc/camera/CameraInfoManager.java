package com.yc.camera;


import com.yc.camera.model.CameraInfo;

/**
 * camera
 */
public class CameraInfoManager {

    private int cameraNum;
    private int mainSensorId = -1;
    private int cvbsId = -1;
    private int cvbs2Id = -1;
    private int usbId = -1;
    private int mainDriverId = -1;

    private static CameraInfoManager mCameraInfoManager;

    public static synchronized CameraInfoManager newInstance() {
        if (mCameraInfoManager == null) {
            mCameraInfoManager = new CameraInfoManager();
        }
        return mCameraInfoManager;

    }

    private CameraInfoManager() {
    }

    /**
     * update current cameras info
     */
    public synchronized boolean updateCameraInfo() {
        if (!CameraManager.get().isServiceAlive()) return false;
        try {
            int camNum;
            int mainID = -1;
            int cvbsID = -1;
            int cvbs2ID = -1;
            int usbID = -1;
            int driverID = -1;
            camNum = CameraManager.get().getNumberOfCameras();
            for (int i = 0; i < camNum; i++) {
                CameraInfo cameraInfo = CameraManager.get().getCameraInfo(i);
                if (cameraInfo == null) return false;
                int status = cameraInfo.getStatus();
                if (status == CameraInfo.CAMERA_STATUS_NORMAL) {
                    switch (cameraInfo.facing) {
                        case CameraInfo.CAMERA_FACING_FRONT:
                            mainID = cameraInfo.cameraId;
                            break;
                        case CameraInfo.CAMERA_FACING_BACK_CVBS:
                            cvbsID = cameraInfo.cameraId;
                            break;
                        case CameraInfo.CAMERA_FACING_BACK_CVBS_2:
                            cvbs2ID = cameraInfo.cameraId;
                            break;
                        case CameraInfo.CAMERA_FACING_BACK_USB:
                            usbID = cameraInfo.cameraId;
                            break;
                        case CameraInfo.CAMERA_FACING_MAIN_DRIVER:
                            driverID = cameraInfo.cameraId;
                            break;
                    }
                }
            }
            updateCache(camNum, mainID, cvbsID, cvbs2ID, usbID, driverID);
        } catch (Throwable e) {
            return false;
        }
        return true;
    }

    public int getDMSCameraId() {
        return mainDriverId;
    }

    public int getMainSensorId() {
        return mainSensorId;
    }

    public int getCvbsCameraId() {
        return cvbsId;
    }

    public int getCvbs2CameraId() {
        return cvbs2Id;
    }

    public int getUsbCameraId() {
        return usbId;
    }

    public int getBackCameraId() {
        if (getUsbCameraId() != -1) {//如果usb id不为空说明是USBCamera 直接返回
            return getUsbCameraId();
        }
        if (getCvbsCameraId() != -1) {
            return getCvbsCameraId();
        }
        if (getCvbs2CameraId() != -1) {
            return getCvbs2CameraId();
        }
        return -1;
    }

    public int getCameraId(boolean isFront) {
        if (isFront) {
            return getMainSensorId();
        } else {
            return getBackCameraId();
        }
    }

    public boolean isFrontCamera(int cameraId) {
        int frontId = getMainSensorId();
        return cameraId == frontId;
    }

    public int getCameraNumber() {
        return cameraNum;
    }

    private void updateCache(int camNum, int mainID, int cvbsID, int cvbs2ID, int usbID, int driverID) {
        cameraNum = camNum;
        mainSensorId = mainID;
        cvbsId = cvbsID;
        cvbs2Id = cvbs2ID;
        usbId = usbID;
        mainDriverId = driverID;
    }
}

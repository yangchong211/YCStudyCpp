package com.yc.camera.model;

import java.io.Serializable;

public class CameraInfo implements Serializable {
    public static final int CAMERA_FACING_FRONT = 0;
    public static final int CAMERA_FACING_BACK_CVBS = 1;
    public static final int CAMERA_FACING_BACK_CVBS_2 = 2;
    public static final int CAMERA_FACING_BACK_USB = 3;
    public static final int CAMERA_FACING_MAIN_DRIVER = 4;


    public static final int CAMERA_STATUS_NORMAL = 0;
    public static final int CAMERA_STATUS_EXCEPTION = -1;

    /**
     * 摄像头类型
     * {@link #CAMERA_FACING_FRONT }
     * {@link #CAMERA_FACING_BACK_CVBS}
     * {@link #CAMERA_FACING_BACK_CVBS_2}
     * {@link #CAMERA_FACING_BACK_USB}
     */
    public int facing;

    /**
     * 摄像头ID
     */
    public int cameraId;

    /**
     * The orientation of the camera image.
     */
    public int orientation;

    /**
     * 摄像头状态 0表示正常；-1表示状态不正常
     * {@link #CAMERA_STATUS_NORMAL }
     * {@link #CAMERA_STATUS_EXCEPTION}
     */
    public int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getFacing() {
        return facing;
    }

    public void setFacing(int facing) {
        this.facing = facing;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getCameraId() {
        return cameraId;
    }

    public void setCameraId(int cameraId) {
        this.cameraId = cameraId;
    }
}

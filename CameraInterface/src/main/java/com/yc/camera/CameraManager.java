package com.yc.camera;

import com.yc.camera.callback.OnCameraAvailableCallback;
import com.yc.camera.callback.OnServiceStateCallback;
import com.yc.camera.exception.CameraException;
import com.yc.camera.model.CameraInfo;

/**
 * Dcamera 通用接口管理类
 * 提供一整套录制解决方案
 */
public class CameraManager {

    public static CameraManager instance;

    private ICameraService iDCameraService;

    /**
     * 获取{@link CameraManager}实例
     *
     * @return 返回 实例对象
     */
    public static synchronized CameraManager get() {
        if (instance == null) {
            instance = new CameraManager();
        }
        return instance;
    }

    /**
     * 在App 启动时必须进行初始化操作
     * 只需要在Application初始化一次即可
     *
     * @param imDCameraService {@link ICameraService}
     */
    public void init(ICameraService imDCameraService) {
        if (iDCameraService == null) {
            this.iDCameraService = imDCameraService;
        }
    }

    /**
     * 打开摄像头操作
     *
     * @param cameraId 摄像头ID,用于区分前后摄
     * @return {@link ICameraDevice} 返回用于操作的摄像头相关的类对象
     * @throws CameraException
     */
    public ICameraDevice openCamera(int cameraId) throws CameraException {
        if (iDCameraService == null) {
            throw new CameraException("DCamera Service is not init,please init");
        }
        return iDCameraService.openCamera(cameraId);
    }

    /**
     * 关闭摄像头操作
     *
     * @param cameraId 摄像头ID,用于区分前后摄
     * @throws CameraException
     */
    public void closeCamera(int cameraId) throws CameraException {
        if (iDCameraService == null) {
            throw new CameraException("DCamera Service is not init,please init");
        }
        iDCameraService.closeCamera(cameraId);
    }

    /**
     * 获取DCameraService 服务
     *
     * @return
     * @throws CameraException
     */
    public ICameraService getDCameraService() throws CameraException {
        if (iDCameraService == null) {
            throw new CameraException("DCamera Service is not init,please init");
        }
        return iDCameraService;
    }

    /**
     * 判断录制服务是否可用
     *
     * @return true 表示可用，false 不可用
     * @throws CameraException
     */
    public boolean isServiceAlive() {
        return iDCameraService != null && iDCameraService.isServiceAlive();
    }

    /**
     * 获取车辆运行状态
     *
     * @return
     */
    public int queryCarEngineState() {
        return iDCameraService == null ? -3 : iDCameraService.queryCarEngineState();
    }


    /**
     * 获取摄像头个数
     *
     * @return
     * @throws CameraException
     */
    public int getNumberOfCameras() {
        return iDCameraService != null ? iDCameraService.getNumberOfCameras() : 0;
    }

    /**
     * 获取CameraInfo 数据
     */
    public CameraInfo getCameraInfo(int cameraId) {
        return iDCameraService != null ?
                iDCameraService.getCameraInfo(cameraId) : null;
    }


    /**
     * 注册摄像头状态回调
     *
     * @param callback 摄像头状态回调接口
     */
    public void addCameraAvailableCallback(OnCameraAvailableCallback callback) {
        if (iDCameraService != null) {
            iDCameraService.addCameraAvailableCallback(callback);
        }
    }

    /**
     * 注销摄像头状态回调
     *
     * @param callback 摄像头状态回调接口
     */
    public void removeCameraAvailableCallback(OnCameraAvailableCallback callback) {
        if (iDCameraService != null) {
            iDCameraService.removeCameraAvailableCallback(callback);
        }
    }

    /**
     * 添加录制服务监听
     *
     * @param callback
     */
    public void addCameraServiceStateCallback(OnServiceStateCallback callback) {
        if (iDCameraService != null) {
            iDCameraService.addCarServiceStateCallback(callback);
        }
    }

    /**
     * 删除录制服务监听
     *
     * @param car
     */
    public void removeCarcorderStateCallback(OnServiceStateCallback car) {
        if (iDCameraService != null) {
            iDCameraService.removeCarServiceStateCallback(car);
        }
    }

    /**
     * 获取摄像头状态，判断摄像头资源是否正常释放
     * 这个接口可在熄火情况下调用判断摄像头、录制资源有否正常释放掉
     *
     * @param cameraId 摄像头ID,用于区分前后摄
     * @return -1: 代表camera已经被close掉了，资源也都释放了。
     * 0：代表IDLE状态，open了camera，底层不在preview也不在recorder。
     * 1：代表Preview状态，apk调用startPreview成功会标记为此状态。
     * 2:  代表Recording状态，apk调用startRecord成功会标记为此状态。
     * 3：代表一路变两路，apk调用startSubVideoFrame会标记为此状态。
     * <p>
     * 各个状态直接的转变关系：
     * [state: -1] -> openCamera ->[ state:0] -> startPreview ->[state: 1] -> startRecord -> [state: 2] -> startSubVideoFrame -> [state: 3]
     * [state: 3] -> stopSubVideoFrame  -> [state: 2] -> stoptRecord  ->  [state: 1] -> stopPreview -> [ state:0]  -> closeCamera -> [state: -1]
     */
    public int getCameraState(int cameraId) {
        if (iDCameraService != null) {
            return iDCameraService.getCameraState(cameraId);
        }
        return -1;
    }


}

package com.yc.camera;

import com.yc.camera.callback.OnCameraAvailableCallback;
import com.yc.camera.callback.OnServiceStateCallback;
import com.yc.camera.exception.CameraException;
import com.yc.camera.model.CameraInfo;

/**
 * 主要用于提供摄像头服务是否存活的判断，以及摄像头打开及关闭
 */
public interface ICameraService {

    /**
     * 打开摄像头操作
     *
     * @param cameraId 区分前后摄
     * @return
     * @throws CameraException
     */
    ICameraDevice openCamera(int cameraId) throws CameraException;

    /**
     * 关闭摄像头操作
     *
     * @param cameraId 区分前后摄
     * @throws CameraException
     */
    void closeCamera(int cameraId) throws CameraException;

    /**
     * 判断录制服务是否存活
     *
     * @return true 表示第三方录制服务是否可用，false 服务不可用
     */
    boolean isServiceAlive();

    /**
     * 获取摄像头个数
     *
     * @return
     */
    int getNumberOfCameras();




    /**
     * Returns the information about a particular camera.
     * If {@link #getNumberOfCameras()} returns N, the valid id is 0 to N-1.
     *
     * @param cameraId
     * @return
     */
    CameraInfo getCameraInfo(int cameraId);

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
    int getCameraState(int cameraId);

    /**
     * 查询车辆运行状态
     * 0：表示熄火
     * 1：表示点火
     * -1 表示异常
     * -3 表示server未初始化
     *
     * @return
     */
    int queryCarEngineState();

    /**
     * 注册摄像头回调
     *
     * @param callback 摄像头状态回调：摄像头挂载成功、摄像头被拔或者挂载失败
     */
    void addCameraAvailableCallback(OnCameraAvailableCallback callback);

    /**
     * 注销摄像头状态的回调
     *
     * @param callback 摄像头状态回调：摄像头挂载成功、摄像头被拔或者挂载失败
     */
    void removeCameraAvailableCallback(OnCameraAvailableCallback callback);

    /**
     * 添加录制服务监听
     *
     * @param serviceStateCallback
     */
    void addCarServiceStateCallback(OnServiceStateCallback serviceStateCallback);

    /**
     * 删除录制服务监听
     *
     * @param serviceStateCallback
     */
    void removeCarServiceStateCallback(OnServiceStateCallback serviceStateCallback);


}

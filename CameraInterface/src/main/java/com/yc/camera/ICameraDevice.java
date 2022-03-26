package com.yc.camera;

import android.graphics.SurfaceTexture;
import android.view.Surface;

import com.yc.camera.callback.OnCameraErrorCallback;
import com.yc.camera.callback.OnPictureCallback;
import com.yc.camera.callback.OnPreviewCallback;
import com.yc.camera.callback.OnShutterCallback;
import com.yc.camera.callback.OnVideoCallback;
import com.yc.camera.exception.CameraException;
import com.yc.camera.exception.TakePicException;

/**
 * 摄像头相关类
 * <p>提供摄像头状态判断<p/>
 * <p>录制相关接口</p>
 * <p>相关参数设置</p>
 */
public interface ICameraDevice {

    /**
     * {@link #getState()}
     */
    int STATE_IDLE = 0;
    /**
     * {@link #getState()}
     */
    int STATE_PREVIEW = 1;
    /**
     * {@link #getState()}
     */
    int STATE_RECORDING = 2;
    /**
     * {@link #getState()}
     */
    int STATE_SUBVIDEO = 3;

    /**
     * 设置 Preview 的 Surface
     *
     * @param surface
     * @return true 设置成功
     * @throws CameraException
     */
    boolean setPreviewSurface(Surface surface) throws CameraException;

    /**
     * 设置 YUV 回调
     *
     * @param yuvCallback {@link OnPreviewCallback}
     * @throws CameraException
     */
    void setYuvCallback(OnPreviewCallback yuvCallback) throws CameraException;

    /**
     * 是否打开快门声音
     *
     * @param enable true 表示打开快门声音，false 表示关闭
     * @throws CameraException
     */
    void enableShutterSound(boolean enable) throws CameraException;

    /**
     * 开启YUV实时流回调
     *
     * @param yuvFrameType {@link YUVFrameType}
     * @throws CameraException {@link CameraException}
     * @see YUVFrameType
     */
    void startYuvVideoFrame(YUVFrameType yuvFrameType) throws CameraException;

    /**
     * 停止 YUV 回调
     *
     * @param yuvFrameType {@link YUVFrameType}
     * @throws CameraException
     * @see YUVFrameType
     */
    void stopYuvVideoFrame(YUVFrameType yuvFrameType) throws CameraException;

    /**
     * YUV 启动时数据来源
     */
    enum YUVFrameType {
        /**
         * 表示从录制的数据中copy一份数据实时给到上层
         * 前提是必须开启录制成功
         */
        yuvRecordFrame(1),
        /**
         * 表示从PreviewClient数据中callback 一份给到上层
         * 不依赖录制
         */
        yuvPreviewFrame(2),
        /**
         * 表示从PreviewClient数据中callback以fd形式给到上层
         * 不依赖录制
         */
        yuvFdFrame(3);
        final int type;

        YUVFrameType(int type) {
            this.type = type;
        }
    }

    /**
     * 强制刷内存中的视频数据到文件中
     * 主要解决在紧急情况下设备突然断电，内存中的视频数据无法落盘的问题
     *
     * @throws CameraException
     */
    void setFlushCurRecorderFile() throws CameraException;

    /**
     * 获取摄像头当前的状态<br/>
     * 0：STATE_IDLE  {@link #STATE_IDLE}   等待状态<br/>
     * 1：STATE_PREVIEW {@link #STATE_PREVIEW}  预览状态<br/>
     * 2：STATE_RECORDING {@link #STATE_RECORDING}录制状态<br/>
     * 3：STATE_SUBVIDEO  {@link #STATE_SUBVIDEO}开启第二路数据
     * <p>
     * 1)STATE_IDLE -(startPreview)-> STATE_PREVIEW -(startRecord)->
     * STATE_RECIRDING -(startSubVideoFrame)-> STATE_SUBVIDEO ;
     * </p>
     * <p>
     * 2)STATE_SUBVIDEO -(stopSubVideoFrame)-> STATE_RECORDING -
     * (stopRecord)-> STATE_PREVIEW -(stopPreview)-> STATE_IDLE ;
     * <p>
     * 3)STATE_PREVIEW -(takePicture)-> STATE_IDLE ;<br/>
     * 该接口返回的是底层保存的状态，即使 app 异常退出，比如还在录制的时候 异常退出了，待应用重新启动后，依然可以通过此接口拿到 camera 当前的状态，
     * 此时返回的还是录制状态。
     *
     * @return 状态码
     */
    int getState();

    /**
     * 设置标记视频存储的文件路径
     * 需要配合 {@link #lockRecordingVideo(int, String)} 一起使用
     *
     * @param dir 文件目录
     * @throws CameraException
     */
    void setUserMarkFilePath(String dir) throws CameraException;

    /**
     * 锁定指定时长的视频文件
     *
     * @param duration      锁定时长
     * @param protectedType "start" 或 "stop"
     * @throws CameraException
     */
    void lockRecordingVideo(int duration, String protectedType) throws CameraException;

    /**
     * 录制过程中设置保存视频文件的目录<br/>
     * 与 {@link ICameraParameters#setOutputFile(String)} 不同的是该接口可以在录制过程中调用
     * 在录制过程中 动态切换录制文件目录
     * 当前正在录制的文件完成之后，才会生效
     *
     * @param dir 文件目录
     * @throws CameraException
     */
    void setRecordingSdcardPath(String dir) throws CameraException;

    /**
     * 拍照
     *
     * @param path            拍照图片的存储路径
     * @param shutterCallback {@link OnShutterCallback}快门提示音响起时的回调
     * @param pictureCallback {@link OnPictureCallback}拍照完成时的回调
     * @throws TakePicException
     */
    void takePicture(String path, OnShutterCallback shutterCallback,
                     OnPictureCallback pictureCallback) throws TakePicException;

    /**
     * 设置录制视频文件的时长，在启动录制之后设置，
     * 设置后会在下一个录制的视频文件中生效<br/>
     * 启动录制之前通过 {@link ICameraParameters#setVideoRotateDuration(long)} 设置
     *
     * @param duration_ms 单位为毫秒
     * @throws CameraException
     */
    void setVideoRotateDuration(int duration_ms) throws CameraException;

    /**
     * 设置录制相关事件的回调<br/>
     * 通过 setVideoCallback(null) 解除回调
     *
     * @param videoCallback {@link OnVideoCallback}
     * @throws CameraException
     */
    boolean setVideoCallback(OnVideoCallback videoCallback) throws CameraException;

    /**
     * 停止摄像头预览画面
     *
     * @throws CameraException
     */
    void stopPreview() throws CameraException;

    /**
     * 显示摄像头预览画面<br/>
     * 必须先调用 {@link ICameraDevice#setPreviewSurface(Surface)}
     *
     * @throws CameraException
     */
    void startPreview() throws CameraException;

    /**
     * 设置 CameraDevice 参数，修改参数后必须调用此接口才能生效
     *
     * @param parameters {@link ICameraParameters}设置的参数集合
     * @throws CameraException
     */
    void setParameters(ICameraParameters parameters) throws CameraException;

    /**
     * 获取 CameraDevice 参数集合
     *
     * @return 参数集合
     * @throws CameraException
     */
    ICameraParameters getParameters() throws CameraException;

    /**
     * 开始录制
     *
     * @return >=-1 表示录制成功，其他都失败
     * @throws CameraException
     */
    int startRecord() throws CameraException;

    /**
     * 停止录制
     *
     * @return >=-1 表示成功，其他都失败
     * @throws CameraException
     */
    int stopRecord() throws CameraException;

    /**
     * 释放摄像头资源，注销相关回调
     *
     * @return true 释放成功
     * @throws CameraException
     */
    boolean release() throws CameraException;

    void configHdrCaptureRequest(String hdrStatus) throws CameraException;

    /**
     * 设置视频是否使用底层帧加密逻辑
     *
     * @param mode 0表示不加密；1表示加密
     * @throws CameraException
     */
    void setEncryptionMode(int mode) throws CameraException;

    /**
     * 设置 Preview 的 SurfaceTexture
     *
     * @param surfaceTexture
     * @return true 设置成功
     * @throws CameraException
     */
    boolean setPreviewSurfaceTexture(SurfaceTexture surfaceTexture) throws CameraException;


    /**
     * 设置主流的数据处理模式
     *
     * @param mode
     * @throws CameraException
     */
    void setMainVideoFrameMode(MainVideoFrameMode mode) throws CameraException;

    /**
     * 开启sub流
     *
     * @throws CameraException
     */
    void startSubVideoFrame() throws CameraException;

    /**
     * 关闭sub 流
     *
     * @throws CameraException
     */
    void stopSubVideoFrame() throws CameraException;

    /**
     * 底层异常时抛异常处理
     *
     * @param cameraErrorCallback
     */
    void setCameraErrorCallback(OnCameraErrorCallback cameraErrorCallback);

    enum MainVideoFrameMode {
        DISABLE(0),
        SOURCE(1),
        PACKET(2),
        DUAL(3);

        final int mode;

        MainVideoFrameMode(int mode) {
            this.mode = mode;
        }
    }
}

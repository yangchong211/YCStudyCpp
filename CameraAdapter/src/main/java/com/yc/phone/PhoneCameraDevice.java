package com.yc.phone;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;

import com.yc.camera.ICameraDevice;
import com.yc.camera.ICameraParameters;
import com.yc.camera.callback.OnCameraErrorCallback;
import com.yc.camera.callback.OnPictureCallback;
import com.yc.camera.callback.OnPreviewCallback;
import com.yc.camera.callback.OnShutterCallback;
import com.yc.camera.callback.OnVideoCallback;
import com.yc.camera.exception.CameraException;
import com.yc.camera.exception.TakePicException;

import java.lang.reflect.Method;
import java.util.concurrent.ArrayBlockingQueue;

public class PhoneCameraDevice implements ICameraDevice, Camera.PreviewCallback {

    private Camera camera;
    private VideoCodec mVideoCodec;

    private PhoneParameters parameters;

    private final int yuvQueueSize = 15;

    private ArrayBlockingQueue<byte[]> yuvQueue = new ArrayBlockingQueue<>(yuvQueueSize);

    private OnPreviewCallback dyuvCallback;
    private boolean isStartYUVFrame = false;

    private OnVideoCallback dVideoCallback;

    private boolean isStartedPreview = false;

    public PhoneCameraDevice(Camera camera) {
        this.camera = camera;
        parameters = new PhoneParameters(camera);
        mVideoCodec = new VideoCodec(parameters.getExtraParameters());
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        putYUVData(data);
        if (isStartYUVFrame && dyuvCallback != null) {
            int cameraId = parameters.getExtraParameters().getCameraId();
            // Default NV21, parameters#getPreviewFormat need convert
            dyuvCallback.onPreviewFrame(data, cameraId,
                    data.length, OnPreviewCallback.FORMAT_NV21);
        }
    }

    private void putYUVData(byte[] buffer) {
        try {
            if (isRecording()) {
                if (yuvQueue.size() >= yuvQueueSize) {
                    yuvQueue.poll();
                }
                yuvQueue.add(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isRecording() {
        return mVideoCodec != null && mVideoCodec.isRunning();
    }

    @Override
    public boolean setPreviewSurface(Surface surface) throws CameraException {
        try {
            camera.setDisplayOrientation(90); // TODO screenOrientation
            // Should be invoke camera#setPreviewDisplay
//            camera.setPreviewDisplay();
            Method method = Camera.class.getMethod("setPreviewSurface", Surface.class);
            method.invoke(camera, surface);
        } catch (Throwable e) {
            throw new CameraException("设置 Preview Surface 异常");
        }
        return true;
    }

    @Override
    public void setYuvCallback(OnPreviewCallback dPreviewCallback) throws CameraException {
        this.dyuvCallback = dPreviewCallback;
    }

    @Override
    public void enableShutterSound(boolean enable) throws CameraException {
        camera.enableShutterSound(enable);

        int cameraId = parameters.getExtraParameters().getCameraId();
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, cameraInfo);
        if (!cameraInfo.canDisableShutterSound && !enable) {
            throw new CameraException("不允许设置静音拍照");
        }
    }

    @Override
    public void startYuvVideoFrame(YUVFrameType yuvFrameType) {
        isStartYUVFrame = true;
    }

    @Override
    public void stopYuvVideoFrame(YUVFrameType yuvFrameType) {
        isStartYUVFrame = false;
    }

    @Override
    public void setFlushCurRecorderFile() throws CameraException {

    }

    @Override
    public int getState() {
        int cameraState;
        if (isRecording()) {
            cameraState = STATE_RECORDING;
        } else if (isStartedPreview) {
            cameraState = STATE_PREVIEW;
        } else {
            cameraState = STATE_IDLE;
        }
        return cameraState;
    }

    @Override
    public void setUserMarkFilePath(String s) throws CameraException {

    }

    @Override
    public void lockRecordingVideo(int i, String s) throws CameraException {

    }

    @Override
    public void setRecordingSdcardPath(String s) {
        parameters.getExtraParameters().setOutputDir(s);
    }

    @Override
    public void takePicture(String s, OnShutterCallback dShutterCallback, OnPictureCallback dPictureCallback) throws TakePicException {
        if (TextUtils.isEmpty(s)) {
            throw new TakePicException("拍照路径为空，不进行拍照");
        }
        try {
            int cameraId = parameters.getExtraParameters().getCameraId();
            int jpegQuality = parameters.getExtraParameters().getJpegQuality();
            camera.takePicture(new ShutterCallbackImpl(dShutterCallback), null,
                    new PictureCallbackImpl(dPictureCallback, s, cameraId, jpegQuality));
        } catch (Throwable e) {
            throw new TakePicException("拍照过程中异常:" + Log.getStackTraceString(e));
        }
    }

    @Override
    public void setVideoRotateDuration(int i) {
        parameters.getExtraParameters().setLoopDuration(i);
        mVideoCodec.updateConfig(parameters.getExtraParameters());
    }

    @Override
    public boolean setVideoCallback(OnVideoCallback dVideoCallback) {
        this.dVideoCallback = dVideoCallback;
        mVideoCodec.setVideoCallback(dVideoCallback);
        return true;
    }

    @Override
    public void stopPreview() throws CameraException {
        try {
            // 必须调用，防止release后出现异常:Camera is being used after Camera.release() was called
            camera.setPreviewCallback(null);
            camera.stopPreview();
            isStartedPreview = false;
        } catch (Throwable e) {
            throw new CameraException("停止预览异常");
        }
    }

    @Override
    public void startPreview() throws CameraException {
        try {
            camera.setPreviewCallback(this);
            camera.startPreview();
            isStartedPreview = true;
        } catch (Throwable e) {
            throw new CameraException("开启预览异常");
        }
    }

    @Override
    public synchronized void setParameters(ICameraParameters dParameters) throws CameraException {
        if (dParameters == null) return;
        if (camera == null) return;
        parameters.setParameters();
        mVideoCodec.updateConfig(parameters.getExtraParameters());
    }

    @Override
    public synchronized ICameraParameters getParameters() {
        parameters.updateParameters();
        return parameters;
    }

    @Override
    public int startRecord() {
        try {
            mVideoCodec.startCodecThread(yuvQueue);
        } catch (CameraException e) {
            return -1; // 已启动录制
        }
        return 0;
    }

    @Override
    public int stopRecord() {
        mVideoCodec.stopCodecThread();
        return 0;
    }

    @Override
    public boolean release() throws CameraException {
        try {
            camera.release();
            camera = null;
            parameters = null;
            yuvQueue.clear();
        } catch (Throwable e) {
            throw new CameraException("释放摄像头资源异常");
        }
        return true;
    }

    @Override
    public void configHdrCaptureRequest(String s) throws CameraException {

    }

    @Override
    public void setEncryptionMode(int i) throws CameraException {

    }

    @Override
    public boolean setPreviewSurfaceTexture(SurfaceTexture surfaceTexture) throws CameraException {
        return false;
    }

    @Override
    public void setMainVideoFrameMode(MainVideoFrameMode mainVideoFrameMode) throws CameraException {

    }

    @Override
    public void startSubVideoFrame() throws CameraException {

    }

    @Override
    public void stopSubVideoFrame() throws CameraException {

    }

    @Override
    public void setCameraErrorCallback(OnCameraErrorCallback cameraErrorCallback) {

    }
}

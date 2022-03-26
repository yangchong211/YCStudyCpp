package com.yc.phone;

import android.graphics.Rect;
import android.hardware.Camera;
import android.media.CamcorderProfile;

import com.yc.camera.ICameraParameters;
import com.yc.camera.exception.CameraException;
import com.yc.camera.exception.WatermarkException;
import com.yc.camera.model.SizeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Provide camera and recording parameters
 */
public class PhoneParameters implements ICameraParameters {

    private Camera camera;

    private Camera.Parameters parameters;

    private PhoneExtraParameters phoneExtraParameters;

    public PhoneParameters(Camera camera) {
        this.camera = camera;
    }

    public synchronized PhoneExtraParameters getExtraParameters() {
        if (phoneExtraParameters == null) {
            phoneExtraParameters = new PhoneExtraParameters();
        }
        return phoneExtraParameters;
    }

    public synchronized void setParameters() throws CameraException {
        if (camera == null) {
            throw new CameraException("摄像头对象为空");
        }
        if (parameters == null) {
            throw new CameraException("摄像头参数对象为空");
        }
        camera.setParameters(parameters);
    }

    public synchronized void updateParameters() {
        if (camera != null) {
            if (parameters != null) {
                camera.setParameters(parameters);
            }
            parameters = camera.getParameters();
        }
    }

    @Override
    public void setRecordSnapShotMode(RecordSnapShotMode mode) throws CameraException {
        throw new CameraException("Phone 暂不支持设置拍照模式");
    }

    @Override
    public List<Integer> getSupportedPreviewFrameRates() throws CameraException {
        if (parameters == null) {
            throw new CameraException("摄像头参数对象为空");
        }
        try {
            return parameters.getSupportedPreviewFrameRates();
        } catch (Throwable e) {
            throw new CameraException("获取支持的预览帧率列表异常");
        }
    }

    @Override
    public void setPreviewFpsRange(int min, int max) {
        if (parameters == null) {
            return;
        }
        parameters.setPreviewFpsRange(min, max);
    }

    @Override
    public void setCameraId(int i) throws CameraException {
        if (phoneExtraParameters == null) {
            throw new CameraException("录制额外参数对象为空");
        }
        phoneExtraParameters.setCameraId(i);
    }

    @Override
    public void setCameraBackLight(int value) throws CameraException {
        if (parameters == null) {
            throw new CameraException("摄像头参数对象为空");
        }
        if (value == 0) {
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
        } else if (value == 1) {
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
        } else if (value == 2) {
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        }
    }

    @Override
    public void setWatermarkTextColor(int i) throws WatermarkException {
        if (phoneExtraParameters == null) {
            throw new WatermarkException("录制额外参数对象为空");
        }
        phoneExtraParameters.setWatermarkTextColor(i);
    }

    @Override
    public void setWatermarkImgPath(String s) throws WatermarkException {
        if (phoneExtraParameters == null) {
            throw new WatermarkException("录制额外参数对象为空");
        }
        phoneExtraParameters.setWatermarkImgPath(s);
    }

    @Override
    public void setWatermarkArea(int i, int i1, int i2, int i3) throws WatermarkException {
        if (phoneExtraParameters == null) {
            throw new WatermarkException("录制额外参数对象为空");
        }
        Rect rect = new Rect(i, i1, i2, i3);
        phoneExtraParameters.setWatermarkTextArea(rect);
    }

    @Override
    public void setWatermarkImgArea(int i, int i1, int i2, int i3) throws WatermarkException {
        if (phoneExtraParameters == null) {
            throw new WatermarkException("录制额外参数对象为空");
        }
        Rect rect = new Rect(i, i1, i2, i3);
        phoneExtraParameters.setWatermarkImgArea(rect);
    }

    @Override
    public void setWatermarkTimestampFormat(String s) throws WatermarkException {
        if (phoneExtraParameters == null) {
            throw new WatermarkException("录制额外参数对象为空");
        }
        // TODO format convert
        phoneExtraParameters.setWatermarkTimestampFormat(s);
    }

    @Override
    public void setWatermarkTextSize(float v) throws WatermarkException {
        if (phoneExtraParameters == null) {
            throw new WatermarkException("录制额外参数对象为空");
        }
        phoneExtraParameters.setWatermarkTextSize(v);
    }

    @Override
    public void setWatermarkTextPosition(float v, float v1) throws WatermarkException {
        if (phoneExtraParameters == null) {
            throw new WatermarkException("录制额外参数对象为空");
        }
        PhoneExtraParameters.Position pos = new PhoneExtraParameters.Position(v, v1);
        phoneExtraParameters.setWatermarkTextPosition(pos);
    }

    @Override
    public void setWatermarkTextShadowColor(int i) throws WatermarkException {
        if (phoneExtraParameters == null) {
            throw new WatermarkException("录制额外参数对象为空");
        }
        phoneExtraParameters.setWatermarkTextShadowColor(i);
    }

    @Override
    public void setWatermarkTextShadowOffset(float v, float v1) throws WatermarkException {
        if (phoneExtraParameters == null) {
            throw new WatermarkException("录制额外参数对象为空");
        }
        PhoneExtraParameters.Position offset = new PhoneExtraParameters.Position(v, v1);
        phoneExtraParameters.setWatermarkTextShadowOffset(offset);
    }

    @Override
    public void setWatermarkTextShadowRadius(float v) throws WatermarkException {
        if (phoneExtraParameters == null) {
            throw new WatermarkException("录制额外参数对象为空");
        }
        phoneExtraParameters.setWatermarkTextShadowRadius(v);
    }

    @Override
    public void setPreviewSize(int i, int i1) throws CameraException {
        if (phoneExtraParameters == null) {
            throw new CameraException("录制额外参数对象为空");
        }
        if (parameters == null) {
            throw new CameraException("摄像头参数对象为空");
        }
        parameters.setRecordingHint(true);
        phoneExtraParameters.setPreviewWidth(i);
        phoneExtraParameters.setPreviewHeight(i1);
        parameters.setPreviewSize(i, i1);
        // auto focus
        List<String> focusModeList = parameters.getSupportedFocusModes();
        if (focusModeList.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
        } else if (focusModeList.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }
    }

    @Override
    public SizeModel getPreviewSize() throws CameraException {
        if (parameters == null) {
            throw new CameraException("参数对象为空");
        }
        Camera.Size size = parameters.getPreviewSize();
        SizeModel dSize = null;
        if (size != null) {
            dSize = new SizeModel(size.width, size.height);
        }
        return dSize;
    }

    @Override
    public List<SizeModel> getSupportedPreviewSizes() throws CameraException {
        if (parameters == null) {
            throw new CameraException("摄像头参数对象为空");
        }
        List<Camera.Size> sizes = parameters.getSupportedPreviewSizes();
        List<SizeModel> dSizes = new ArrayList<>();
        for (Camera.Size size : sizes) {
            if (size != null) {
                dSizes.add(new SizeModel(size.width, size.height));
            }
        }
        return dSizes;
    }

    @Override
    public void setYUVCallbackType(YUVCallbackType type) throws CameraException {
        throw new CameraException("Phone 暂不支持");
    }

    @Override
    public void enablePictureWatermarkText(boolean b) throws WatermarkException {
        if (phoneExtraParameters == null) {
            throw new WatermarkException("录制额外参数对象为空");
        }
        phoneExtraParameters.setEnablePictureWatermarkText(b);
    }

    @Override
    public void enableVideoWatermarkText(boolean b) throws WatermarkException {
        if (phoneExtraParameters == null) {
            throw new WatermarkException("录制额外参数对象为空");
        }
        phoneExtraParameters.setEnableVideoWatermarkText(b);
    }

    @Override
    public void enableVideoWatermarkImage(boolean b) throws WatermarkException {
        if (phoneExtraParameters == null) {
            throw new WatermarkException("录制额外参数对象为空");
        }
        phoneExtraParameters.setEnableVideoWatermarkImage(b);
    }

    @Override
    public void enablePreviewWatermarkText(boolean b) throws WatermarkException {
        if (phoneExtraParameters == null) {
            throw new WatermarkException("录制额外参数对象为空");
        }
        phoneExtraParameters.setEnablePreviewWatermarkText(b);
    }

    @Override
    public void enablePreviewWatermarkImage(boolean b) throws WatermarkException {
        if (phoneExtraParameters == null) {
            throw new WatermarkException("录制额外参数对象为空");
        }
        phoneExtraParameters.setEnablePreviewWatermarkImage(b);
    }

    @Override
    public void enableRecordStartRing(boolean b) throws CameraException {
        if (phoneExtraParameters == null) {
            throw new CameraException("录制额外参数对象为空");
        }
        phoneExtraParameters.setEnableRecordStartRing(b);
    }

    @Override
    public void enableVideoCallback(boolean b) throws CameraException {
        if (phoneExtraParameters == null) {
            throw new CameraException("录制额外参数对象为空");
        }
        phoneExtraParameters.setEnableVideoWithEncryptCallback(b);
        phoneExtraParameters.setEnableVideoWithTimeCallback(b);
    }

    @Override
    public void setSDCardSpeedLimit(int i) throws CameraException {
        if (phoneExtraParameters == null) {
            throw new CameraException("录制额外参数对象为空");
        }
        phoneExtraParameters.setSdcardSpeedLimit(i);
    }

    @Override
    public void setFreeSizeLimit(int i) throws CameraException {
        if (phoneExtraParameters == null) {
            throw new CameraException("录制额外参数对象为空");
        }
        phoneExtraParameters.setFreeSizeLimit(i);
    }

    @Override
    public void setVideoProfile(CamcorderProfile profile) throws CameraException {
        if (phoneExtraParameters == null) {
            throw new CameraException("录制额外参数对象为空");
        }
        phoneExtraParameters.setVideoProfile(profile);
    }

    @Override
    public void setVideoSize(int i, int i1) throws CameraException {
        if (phoneExtraParameters == null) {
            throw new CameraException("录制额外参数对象为空");
        }
        phoneExtraParameters.setVideoWidth(i);
        phoneExtraParameters.setVideoHeight(i1);
    }

    @Override
    public void setVideoRotateDuration(long l) throws CameraException {
        if (phoneExtraParameters == null) {
            throw new CameraException("录制额外参数对象为空");
        }
        phoneExtraParameters.setLoopDuration(l);
    }

    @Override
    public void setVideoEncodingBitRate(int i) throws CameraException {
        if (phoneExtraParameters == null) {
            throw new CameraException("录制额外参数对象为空");
        }
        phoneExtraParameters.setBitRate(i);
    }

    @Override
    public void setVideoFrameRate(int i) throws CameraException {
        if (phoneExtraParameters == null) {
            throw new CameraException("录制额外参数对象为空");
        }
        phoneExtraParameters.setFrameRate(i);
    }

    @Override
    public void setOutputFileFormat(OutputFormat format) throws CameraException {
        if (phoneExtraParameters == null) {
            throw new CameraException("录制额外参数对象为空");
        }
        phoneExtraParameters.setOutputFormat(format);
    }

    @Override
    public void setRecordingMuteAudio(boolean b) throws CameraException {
        if (phoneExtraParameters == null) {
            throw new CameraException("录制额外参数对象为空");
        }
        phoneExtraParameters.setRecordingMuteAudio(b);
    }

    @Override
    public void setOutputFile(String s) throws CameraException {
        if (phoneExtraParameters == null) {
            throw new CameraException("录制额外参数对象为空");
        }
        phoneExtraParameters.setOutputDir(s);
    }

    @Override
    public void setOutputFileNamePrefix(String name) throws CameraException {
        if (phoneExtraParameters == null) {
            throw new WatermarkException("录制额外参数对象为空");
        }
        phoneExtraParameters.setOutputDir(name);
    }

    @Override
    public void setDropCamFrame(int i) throws CameraException {
        if (phoneExtraParameters == null) {
            throw new CameraException("录制额外参数对象为空");
        }
        phoneExtraParameters.setDropCamFrame(i);
    }

    @Override
    public void setJpegQuality(int i) throws CameraException {
        if (parameters == null) {
            throw new CameraException("摄像头参数对象为空");
        }
        if (phoneExtraParameters == null) {
            throw new CameraException("录制额外参数对象为空");
        }
        parameters.setJpegQuality(i);
        phoneExtraParameters.setJpegQuality(i);

    }

    @Override
    public void setPictureSize(int i, int i1) throws CameraException {
        if (parameters == null) {
            throw new CameraException("摄像头参数对象为空");
        }
        parameters.setPictureSize(i, i1);
    }

    @Override
    public int getCameraBackLight() throws CameraException {
        return -1;
    }

    @Override
    public void setWatermarkTextEx1(String s) throws WatermarkException {

    }

    @Override
    public void setWatermarkTextAreaEx1(int i, int i1, int i2, int i3) throws WatermarkException {

    }

    @Override
    public void setWatermarkTextEx2(String s) throws WatermarkException {

    }

    @Override
    public void setWatermarkTextAreaEx2(int i, int i1, int i2, int i3) throws WatermarkException {

    }

    @Override
    public void setWatermarkTextEx3(String s) throws WatermarkException {

    }

    @Override
    public void setWatermarkTextAreaEx3(int i, int i1, int i2, int i3) throws WatermarkException {

    }

    @Override
    public void setWatermarkFontFile(String s) throws WatermarkException {

    }

    @Override
    public void setPreviewFormat(int i) {
        if (parameters == null) {
            return;
        }
        parameters.setPreviewFormat(i);
    }

    @Override
    public int getPreviewFormat() {
        if (parameters != null) {
            parameters.getPreviewFormat();
        }
        return 0;
    }

    @Override
    public void enablePictureWatermarkImage(boolean b) throws WatermarkException {

    }

    @Override
    public void setSubVideoProfile(CamcorderProfile camcorderProfile) throws CameraException {

    }

    @Override
    public void setSubVideoFrameMode(SubVideoFrameMode subVideoFrameMode) throws CameraException {

    }

    @Override
    public void setSubDropFrame(int i) throws CameraException {

    }
}

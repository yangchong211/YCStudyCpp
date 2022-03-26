package com.yc.phone;

import android.graphics.Rect;
import android.media.CamcorderProfile;

import com.yc.camera.ICameraParameters;

/**
 * Save some extra parameters
 */
public class PhoneExtraParameters {

    private static final String MIME_TYPE = "video/avc";

    private int cameraId;

    /**录制相关*/
    private long loopDuration;
    private ICameraParameters.OutputFormat outputFormat;

    private int previewWidth = 1280;
    private int previewHeight = 720;
    private int videoWidth = 1280;
    private int videoHeight = 720;

    private int bitRate = 2 * 1024 * 1024;
    private int frameRate = 30; // default 30fps
    private int iFrameInterval = 2; // default 2s
    private String outputDir;

    private String mimeType = MIME_TYPE;

    private long timeoutUs = 10000;

    private boolean enableRecordStartRing;
    private boolean enableVideoWithEncryptCallback;
    private boolean enableVideoWithTimeCallback;
    private int sdcardSpeedLimit;
    private int freeSizeLimit;
    private CamcorderProfile videoProfile;
    private boolean recordingMuteAudio;
    private int dropCamFrame;
    private int jpegQuality;

    /**水印相关*/
    private int watermarkTextColor;
    private String watermarkImgPath;
    private Rect watermarkTextArea;
    private Rect watermarkImgArea;
    private String watermarkTimestampFormat;
    private float watermarkTextSize;
    private Position watermarkTextPosition;
    private int watermarkTextShadowColor;
    private Position watermarkTextShadowOffset;
    private float watermarkTextShadowRadius;
    private boolean enablePictureWatermarkText;
    private boolean enableVideoWatermarkText;
    private boolean enableVideoWatermarkImage;
    private boolean enablePreviewWatermarkText;
    private boolean enablePreviewWatermarkImage;

    public int getCameraId() {
        return cameraId;
    }

    public void setCameraId(int cameraId) {
        this.cameraId = cameraId;
    }

    public long getLoopDuration() {
        return loopDuration;
    }

    public void setLoopDuration(long loopDuration) {
        this.loopDuration = loopDuration;
    }

    public int getPreviewWidth() {
        return previewWidth;
    }

    public void setPreviewWidth(int previewWidth) {
        this.previewWidth = previewWidth;
    }

    public int getPreviewHeight() {
        return previewHeight;
    }

    public void setPreviewHeight(int previewHeight) {
        this.previewHeight = previewHeight;
    }

    public int getBitRate() {
        return bitRate;
    }

    public void setBitRate(int bitRate) {
        this.bitRate = bitRate;
    }

    public int getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(int frameRate) {
        this.frameRate = frameRate;
    }

    public int getiFrameInterval() {
        return iFrameInterval;
    }

    public void setiFrameInterval(int iFrameInterval) {
        this.iFrameInterval = iFrameInterval;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public long getTimeoutUs() {
        return timeoutUs;
    }

    public void setTimeoutUs(long timeoutUs) {
        this.timeoutUs = timeoutUs;
    }

    public int getWatermarkTextColor() {
        return watermarkTextColor;
    }

    public void setWatermarkTextColor(int watermarkTextColor) {
        this.watermarkTextColor = watermarkTextColor;
    }

    public String getWatermarkImgPath() {
        return watermarkImgPath;
    }

    public void setWatermarkImgPath(String watermarkImgPath) {
        this.watermarkImgPath = watermarkImgPath;
    }

    public Rect getWatermarkTextArea() {
        return watermarkTextArea;
    }

    public void setWatermarkTextArea(Rect watermarkTextArea) {
        this.watermarkTextArea = watermarkTextArea;
    }

    public Rect getWatermarkImgArea() {
        return watermarkImgArea;
    }

    public void setWatermarkImgArea(Rect watermarkImgArea) {
        this.watermarkImgArea = watermarkImgArea;
    }

    public String getWatermarkTimestampFormat() {
        return watermarkTimestampFormat;
    }

    public void setWatermarkTimestampFormat(String watermarkTimestampFormat) {
        this.watermarkTimestampFormat = watermarkTimestampFormat;
    }

    public float getWatermarkTextSize() {
        return watermarkTextSize;
    }

    public void setWatermarkTextSize(float watermarkTextSize) {
        this.watermarkTextSize = watermarkTextSize;
    }

    public Position getWatermarkTextPosition() {
        return watermarkTextPosition;
    }

    public void setWatermarkTextPosition(Position watermarkTextPosition) {
        this.watermarkTextPosition = watermarkTextPosition;
    }

    public int getWatermarkTextShadowColor() {
        return watermarkTextShadowColor;
    }

    public void setWatermarkTextShadowColor(int watermarkTextShadowColor) {
        this.watermarkTextShadowColor = watermarkTextShadowColor;
    }

    public Position getWatermarkTextShadowOffset() {
        return watermarkTextShadowOffset;
    }

    public void setWatermarkTextShadowOffset(Position watermarkTextShadowOffset) {
        this.watermarkTextShadowOffset = watermarkTextShadowOffset;
    }

    public float getWatermarkTextShadowRadius() {
        return watermarkTextShadowRadius;
    }

    public void setWatermarkTextShadowRadius(float watermarkTextShadowRadius) {
        this.watermarkTextShadowRadius = watermarkTextShadowRadius;
    }

    public boolean isEnablePictureWatermarkText() {
        return enablePictureWatermarkText;
    }

    public void setEnablePictureWatermarkText(boolean enablePictureWatermarkText) {
        this.enablePictureWatermarkText = enablePictureWatermarkText;
    }

    public boolean isEnableVideoWatermarkText() {
        return enableVideoWatermarkText;
    }

    public void setEnableVideoWatermarkText(boolean enableVideoWatermarkText) {
        this.enableVideoWatermarkText = enableVideoWatermarkText;
    }

    public boolean isEnableVideoWatermarkImage() {
        return enableVideoWatermarkImage;
    }

    public void setEnableVideoWatermarkImage(boolean enableVideoWatermarkImage) {
        this.enableVideoWatermarkImage = enableVideoWatermarkImage;
    }

    public boolean isEnablePreviewWatermarkText() {
        return enablePreviewWatermarkText;
    }

    public void setEnablePreviewWatermarkText(boolean enablePreviewWatermarkText) {
        this.enablePreviewWatermarkText = enablePreviewWatermarkText;
    }

    public boolean isEnablePreviewWatermarkImage() {
        return enablePreviewWatermarkImage;
    }

    public void setEnablePreviewWatermarkImage(boolean enablePreviewWatermarkImage) {
        this.enablePreviewWatermarkImage = enablePreviewWatermarkImage;
    }

    public ICameraParameters.OutputFormat getOutputFormat() {
        return outputFormat;
    }

    public void setOutputFormat(ICameraParameters.OutputFormat outputFormat) {
        this.outputFormat = outputFormat;
    }

    public int getVideoWidth() {
        return videoWidth;
    }

    public void setVideoWidth(int videoWidth) {
        this.videoWidth = videoWidth;
    }

    public int getVideoHeight() {
        return videoHeight;
    }

    public void setVideoHeight(int videoHeight) {
        this.videoHeight = videoHeight;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public boolean isEnableRecordStartRing() {
        return enableRecordStartRing;
    }

    public void setEnableRecordStartRing(boolean enableRecordStartRing) {
        this.enableRecordStartRing = enableRecordStartRing;
    }

    public boolean isEnableVideoWithEncryptCallback() {
        return enableVideoWithEncryptCallback;
    }

    public void setEnableVideoWithEncryptCallback(boolean enableVideoWithEncryptCallback) {
        this.enableVideoWithEncryptCallback = enableVideoWithEncryptCallback;
    }

    public boolean isEnableVideoWithTimeCallback() {
        return enableVideoWithTimeCallback;
    }

    public void setEnableVideoWithTimeCallback(boolean enableVideoWithTimeCallback) {
        this.enableVideoWithTimeCallback = enableVideoWithTimeCallback;
    }

    public int getSdcardSpeedLimit() {
        return sdcardSpeedLimit;
    }

    public void setSdcardSpeedLimit(int sdcardSpeedLimit) {
        this.sdcardSpeedLimit = sdcardSpeedLimit;
    }

    public int getFreeSizeLimit() {
        return freeSizeLimit;
    }

    public void setFreeSizeLimit(int freeSizeLimit) {
        this.freeSizeLimit = freeSizeLimit;
    }

    public CamcorderProfile getVideoProfile() {
        return videoProfile;
    }

    public void setVideoProfile(CamcorderProfile videoProfile) {
        this.videoProfile = videoProfile;
    }

    public boolean isRecordingMuteAudio() {
        return recordingMuteAudio;
    }

    public void setRecordingMuteAudio(boolean recordingMuteAudio) {
        this.recordingMuteAudio = recordingMuteAudio;
    }

    public int getDropCamFrame() {
        return dropCamFrame;
    }

    public void setDropCamFrame(int dropCamFrame) {
        this.dropCamFrame = dropCamFrame;
    }

    public int getJpegQuality() {
        return jpegQuality;
    }

    public void setJpegQuality(int jpegQuality) {
        this.jpegQuality = jpegQuality;
    }

    public static class Position {
        private float x;
        private float y;

        public Position(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }
    }
}

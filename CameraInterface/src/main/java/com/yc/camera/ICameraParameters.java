package com.yc.camera;

import android.media.CamcorderProfile;

import com.yc.camera.callback.OnVideoCallback;
import com.yc.camera.exception.CameraException;
import com.yc.camera.exception.WatermarkException;
import com.yc.camera.model.SizeModel;

import java.util.List;

/**
 * 提供录制和拍照的一次参数配置
 */
public interface ICameraParameters {

    /**
     * 设置 AHD 摄像头的拍照模式<br/>
     * {@link RecordSnapShotMode}
     *
     * @param mode 拍照模式
     * @throws CameraException
     */
    void setRecordSnapShotMode(RecordSnapShotMode mode) throws CameraException;

    /**
     * 获取 Camera 支持的所有帧率
     *
     * @return Preview 帧率列表
     * @throws CameraException
     */
    List<Integer> getSupportedPreviewFrameRates() throws CameraException;

    /**
     * 设置当前的 Camera Id
     *
     * @param cameraId Camera Id 0 或 1
     * @throws CameraException
     */
    void setCameraId(int cameraId) throws CameraException;

    /**
     * 2 : 关掉后红外灯
     * 1 : 根据外部光线确定是否打开红外灯
     * 0 : 强制打开红外灯
     *
     * @param value
     * @throws CameraException
     */
    void setCameraBackLight(int value) throws CameraException;

    int getCameraBackLight() throws CameraException;

    /**
     * 是否允许录制回调<br/>
     * 设置为 true，{@link  ICameraDevice#setVideoCallback(OnVideoCallback)} 有回调
     *
     * @param enable true 允许
     * @throws CameraException
     */
    void enableVideoCallback(boolean enable) throws CameraException;

    /**
     * 设置水印文字颜色，默认红色，可以用 {@link android.graphics.Color}
     *
     * @param color ARGB 模式
     * @throws WatermarkException
     */
    void setWatermarkTextColor(int color) throws WatermarkException;

    /**
     * 设置水印图片的路径，必须是完整路径
     *
     * @param path 图片路径
     * @throws WatermarkException
     */
    void setWatermarkImgPath(String path) throws WatermarkException;

    /**
     * 设置文字水印的区域
     *
     * @param left   水印区域的 x 轴起始位置
     * @param top    水印区域的 y 轴起始位置
     * @param right  水印区域的 x 轴结束位置
     * @param bottom 水印区域的 y 轴结束位置
     * @throws WatermarkException
     */
    void setWatermarkArea(int left, int top, int right, int bottom) throws WatermarkException;

    /**
     * 设置图片水印的区域
     *
     * @param left   水印区域的 x 轴起始位置
     * @param top    水印区域的 y 轴起始位置
     * @param right  水印区域的 x 轴结束位置
     * @param bottom 水印区域的 y 轴结束位置
     * @throws WatermarkException
     */
    void setWatermarkImgArea(int left, int top, int right, int bottom) throws WatermarkException;

    /**
     * 水印显示的时间格式
     *
     * @param format
     * @throws WatermarkException
     */
    void setWatermarkTimestampFormat(String format) throws WatermarkException;

    /**
     * 设置水印文字大小，通常设置为文字水印区域高度或小于区域高度
     *
     * @param size 单位像素
     * @throws WatermarkException
     */
    void setWatermarkTextSize(float size) throws WatermarkException;

    /**
     * 设置水印文字相对于水印区域的偏移量
     *
     * @param x 相对应水印区域起始位置的 x 轴方向偏移量
     * @param y 相对应水印区域起始位置的 y 轴方向偏移量
     * @throws WatermarkException
     */
    void setWatermarkTextPosition(float x, float y) throws WatermarkException;

    /**
     * 设置水印文字的阴影颜色
     *
     * @param shadowColor 16进制值
     * @throws WatermarkException
     */
    void setWatermarkTextShadowColor(int shadowColor) throws WatermarkException;

    /**
     * 设置水印文字的阴影偏移量
     *
     * @param x 方向的偏移量
     * @param y 方向的偏移量
     * @throws WatermarkException
     */
    void setWatermarkTextShadowOffset(float x, float y) throws WatermarkException;

    /**
     * 设置水印文字的阴影半径
     *
     * @param radius 大于 0 才会显示阴影
     * @throws WatermarkException
     */
    void setWatermarkTextShadowRadius(float radius) throws WatermarkException;

    /**
     * 设置1号拓展水印内容
     *
     * @param text 水印内容文本信息
     * @throws WatermarkException
     */
    void setWatermarkTextEx1(String text) throws WatermarkException;

    /**
     * 设置预览帧率
     * @param min
     * @param max
     */
    void setPreviewFpsRange(int min, int max);


    /**
     * 设置1号拓展水印的范围
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @throws WatermarkException
     */
    void setWatermarkTextAreaEx1(int left, int top, int right, int bottom) throws WatermarkException;

    /**
     * 设置2号拓展水印内容
     *
     * @param text 水印内容文本信息
     * @throws WatermarkException
     */
    void setWatermarkTextEx2(String text) throws WatermarkException;

    /**
     * 设置2号拓展水印的范围
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @throws WatermarkException
     */
    void setWatermarkTextAreaEx2(int left, int top, int right, int bottom) throws WatermarkException;

    /**
     * 设置2号拓展水印内容
     *
     * @param text 水印内容文本信息
     * @throws WatermarkException
     */
    void setWatermarkTextEx3(String text) throws WatermarkException;

    /**
     * 设置3号拓展水印的范围
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @throws WatermarkException
     */
    void setWatermarkTextAreaEx3(int left, int top, int right, int bottom) throws WatermarkException;

    /**
     * 设置水印字体，需要显示中文时使用
     *
     * @param file 字体文件路径
     * @throws WatermarkException
     */
    void setWatermarkFontFile(String file) throws WatermarkException;

    /**
     * 设置 Preview 画面的大小<br/>
     * {@link ICameraDevice#startPreview()} 之前设置生效
     *
     * @param width
     * @param height
     * @throws CameraException
     */
    void setPreviewSize(int width, int height) throws CameraException;

    /**
     * 设置回调的Preview 实时流数据类型
     *
     * @param pixelFormat 类型 {@link android.graphics.ImageFormat}
     */
    void setPreviewFormat(int pixelFormat);

    /**
     * 获取实时流格式
     *
     * @return 类型 {@link android.graphics.ImageFormat}
     */
    int getPreviewFormat();

    /**
     * 设置 YUV 回调类型<br/>
     * {@link YUVCallbackType}
     *
     * @param yuvCallbackType
     * @throws CameraException
     */
    void setYUVCallbackType(YUVCallbackType yuvCallbackType) throws CameraException;

    /**
     * 设置是否允许在拍照图片上加文字水印
     *
     * @param enable true 允许
     * @throws WatermarkException
     */
    void enablePictureWatermarkText(boolean enable) throws WatermarkException;

    /**
     * 设置是否允许在拍照时加图片水印
     *
     * @param enable true 允许
     * @throws WatermarkException
     */
    void enablePictureWatermarkImage(boolean enable) throws WatermarkException;

    /**
     * 设置是否允许录制时加文字水印
     *
     * @param enable true 允许
     * @throws WatermarkException
     */
    void enableVideoWatermarkText(boolean enable) throws WatermarkException;


    /**
     * 设置是否允许录制时加图片水印
     *
     * @param enable true 允许
     * @throws WatermarkException
     */
    void enableVideoWatermarkImage(boolean enable) throws WatermarkException;

    /**
     * 设置是否允许 Preview 加文字水印
     *
     * @param enable true 允许
     * @throws WatermarkException
     */
    void enablePreviewWatermarkText(boolean enable) throws WatermarkException;

    /**
     * 设置是否允许 Preview 加图片水印
     *
     * @param enable true 允许
     * @throws WatermarkException
     */
    void enablePreviewWatermarkImage(boolean enable) throws WatermarkException;

    /**
     * 启动录制时是否静音
     *
     * @param enable true 不静音，false 静音
     * @throws CameraException
     */
    void enableRecordStartRing(boolean enable) throws CameraException;

    /**
     * 设置 SDCard 限速
     *
     * @param size 单位 KB
     * @throws CameraException
     */
    void setSDCardSpeedLimit(int size) throws CameraException;

    /**
     * 剩余空间大小的限制。如果剩余空间小于该值，会做循环删除视频文件
     *
     * @param limitSize 单位 MB
     * @throws CameraException
     */
    void setFreeSizeLimit(int limitSize) throws CameraException;

    /**
     * 设置摄像机配置信息，{@link CamcorderProfile}
     *
     * @param videoProfile
     * @throws CameraException
     */
    void setVideoProfile(CamcorderProfile videoProfile) throws CameraException;

    /**
     * 设置录制视频画面的大小<br/>
     * {@link ICameraDevice#startRecord()} 之前设置生效
     *
     * @param width
     * @param height
     * @throws CameraException
     */
    void setVideoSize(int width, int height) throws CameraException;

    /**
     * 设置每个视频文件的时长
     *
     * @param duration_ms 单位毫秒
     * @throws CameraException
     */
    void setVideoRotateDuration(long duration_ms) throws CameraException;

    /**
     * 设置视频录制码率<br/>
     * {@link ICameraParameters#setVideoProfile(CamcorderProfile)} 之后调用生效
     *
     * @param mEncodingBitRate
     * @throws CameraException
     */
    void setVideoEncodingBitRate(int mEncodingBitRate) throws CameraException;

    /**
     * 设置帧率
     *
     * @param fps
     * @throws CameraException
     */
    void setVideoFrameRate(int fps) throws CameraException;

    /**
     * 设置视频文件的格式，如 MP4
     *
     * @param outputFormat
     * @throws CameraException
     */
    void setOutputFileFormat(OutputFormat outputFormat) throws CameraException;

    /**
     * 设置录制过程中是否关闭 MIC
     *
     * @param isMuteAudio true 关闭
     * @throws CameraException
     */
    void setRecordingMuteAudio(boolean isMuteAudio) throws CameraException;

    /**
     * 设置视频文件的保存目录
     *
     * @param dir 目录
     * @throws CameraException
     */
    void setOutputFile(String dir) throws CameraException;

    /**
     * 设置视频文件的文件前缀
     *
     * @param name 目录
     * @throws CameraException
     */
    void setOutputFileNamePrefix(String name) throws CameraException;

    /**
     * 设置掉帧处理。如配置30帧，Drop 1帧，变为15帧
     *
     * @param frame
     * @throws CameraException
     */
    void setDropCamFrame(int frame) throws CameraException;

    /**
     * 获取摄像头支持的previewSize
     *
     * @return
     */
    List<SizeModel> getSupportedPreviewSizes() throws CameraException;

    /**
     * 获取当前设置的previewsize
     *
     * @return
     * @throws CameraException
     */
    SizeModel getPreviewSize() throws CameraException;

    /**
     * 设置图片质量
     *
     * @param quality 范围 1-100
     * @throws CameraException
     */
    void setJpegQuality(int quality) throws CameraException;

    /**
     * 设置拍照图片的大小。录制状态下拍照，则拍照图片大小和视频大小一致
     *
     * @param width  图片宽度
     * @param height 图片高度
     * @throws CameraException
     */
    void setPictureSize(int width, int height) throws CameraException;


    /**
     * 设置第二路流配置信息，{@link CamcorderProfile}
     *
     * @param videoProfile
     * @throws CameraException
     */
    void setSubVideoProfile(CamcorderProfile videoProfile) throws CameraException;

    /**
     * 设置第二路流的framemode
     *
     * @param mode
     * @throws CameraException
     */
    void setSubVideoFrameMode(SubVideoFrameMode mode) throws CameraException;


    /**
     * 设置Sub流丢帧
     *
     * @param frame
     * @throws CameraException
     */
    void setSubDropFrame(int frame) throws CameraException;


    enum OutputFormat {
        MPEG_4(2),
        MPEG2TS(8);

        final int format;

        OutputFormat(int format) {
            this.format = format;
        }
    }

    enum YUVCallbackType {
        yuvCBOnly(1),
        yuvCBAndRecord(2);

        final int type;

        YUVCallbackType(int type) {
            this.type = type;
        }
    }

    enum RecordSnapShotMode {
        RBSS(0),
        DBSS(1);

        final int mode;

        RecordSnapShotMode(int mode) {
            this.mode = mode;
        }
    }


    /*** sub 流frame-mode*/
    enum SubVideoFrameMode {
        DISABLE(0),
        SOURCE(1),
        PACKET(2),
        DUAL(3);

        final int mode;

        SubVideoFrameMode(int mode) {
            this.mode = mode;
        }
    }
}

package com.yc.phone;

import android.hardware.Camera;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.util.Log;

import com.yc.camera.callback.OnVideoCallback;
import com.yc.camera.exception.CameraException;
import com.yc.camera.model.VideoModel;

import java.io.File;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 实现分段录制视频保存
 */
public class VideoCodec {

    private static final String TAG = "VideoCodec";
    private static final String SUFFIX_MP4 = ".mp4";

    private MediaCodec mediaEncoder;
    private MediaMuxer mediaMuxer;
    private byte[] mediaInputByte = null;
    private int videoTrackIndex;
    private boolean isInit = false;
    private boolean isRunning;
    private long recordTime;

    private int cameraId;
    private boolean isFaceCamera = false;

    private String path;

    private PhoneExtraParameters mPhoneExtraParameters;
    private int cameraWidth;
    private int cameraHeight;
    private int bitRate;
    private int frameRate;
    private int iFrameInterval;
    private String mimeType;

    private long timeLoop = 0;
    private int colorFormat = 0;

    private long startPerVideoTime = 0;

    private OnVideoCallback dVideoCallback;

    public VideoCodec(PhoneExtraParameters phoneExtraParameters) {
        updateConfig(phoneExtraParameters);
    }

    /**
     * 设置的预览数据不支持摄像头数据时需要更新配置参数
     *
     * @param phoneExtraParameters
     */
    public void updateConfig(PhoneExtraParameters phoneExtraParameters) {
        this.mPhoneExtraParameters = phoneExtraParameters;
        cameraWidth = phoneExtraParameters.getPreviewWidth();
        cameraHeight = phoneExtraParameters.getPreviewHeight();
        mediaInputByte = new byte[cameraWidth * cameraHeight / 2 * 3];
        bitRate = phoneExtraParameters.getBitRate();
        frameRate = phoneExtraParameters.getFrameRate();
        iFrameInterval = phoneExtraParameters.getiFrameInterval();
        mimeType = phoneExtraParameters.getMimeType();
        timeLoop = phoneExtraParameters.getLoopDuration();
        cameraId = phoneExtraParameters.getCameraId();
        isFaceCamera = isFaceCamera();
    }

    private boolean isFaceCamera() {
        return cameraId == Camera.CameraInfo.CAMERA_FACING_FRONT;
    }

    public void setVideoCallback(OnVideoCallback dVideoCallback) {
        this.dVideoCallback = dVideoCallback;
    }

    /**
     * 开启线程进行编码并分段保存
     */
    public void startCodecThread(final ArrayBlockingQueue<byte[]> YUVQueue) throws CameraException {
        if (isRunning) {
            throw new CameraException("正在录制，不能重复启动录制");
        }
        isRunning = true;
        Thread codecThread = new Thread(new Runnable() {
            @Override
            public void run() {

                long startTime = System.currentTimeMillis();

                while (isRunning) {
                    if (!isInit) {
                        startTime = System.currentTimeMillis();
                        startPerVideoTime = startTime;
                    }
                    long time = System.currentTimeMillis() - startTime;
                    if (time <= timeLoop) {
                        if (YUVQueue.size() > 0) {
                            byte[] input = YUVQueue.poll();
                            drainEncoder(input, mediaInputByte);
                        }
                    } else {
                        Log.i(TAG, "this time > timeloop so call this 'release' method ");
                        release();
                    }
                }
                Log.i(TAG, "the while loop ends, so call this 'release' method ");
                release();
            }
        });
        codecThread.setName("Video Record Thread");
        codecThread.setPriority(Thread.MAX_PRIORITY);
        codecThread.start();
        onVideoTaken(OnVideoCallback.VIDEO_EVENT_RECORD_STATUS_START,
                cameraId, "", 0, 0);
    }

    /**
     * 对外停止录制的接口
     */
    public void stopCodecThread() {
        isRunning = false;
        onVideoTaken(OnVideoCallback.VIDEO_EVENT_RECORD_STATUS_STOP,
                cameraId, path, startPerVideoTime, startPerVideoTime + timeLoop);
    }

    /**
     * 判断是否正在录制
     *
     * @return
     */
    public boolean isRunning() {
        return isRunning;
    }

    private void initMediaMuxer() throws Exception {
        try {
            path = getOutputFile();
            mediaMuxer = new MediaMuxer(path,
                    MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
            Log.i(TAG, "video_path:" + path);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("init media muxer is failure :" + e.getMessage());
        }
    }


    private void initMediaEncoder() throws Exception {
        try {
            MediaCodecInfo codecInfo = selectCodec(mimeType);
            if (codecInfo == null) {
                // Don't fail CTS if they don't have an AVC codec (not here, anyway).
                Log.e(TAG, "Unable to find an appropriate codec for " + mimeType);
                return;
            }

            /**不同设备支持的colorformat 不同*/
            colorFormat = selectColorFormat(codecInfo, mimeType);

            /**创建一个Format用来设置Codec的格式*/
            MediaFormat format = MediaFormat.createVideoFormat(mimeType, cameraWidth, cameraHeight);
            //yuv420sp
            format.setInteger(MediaFormat.KEY_COLOR_FORMAT, colorFormat);

            /**码率*/
            format.setInteger(MediaFormat.KEY_BIT_RATE, bitRate);
            /**帧数*/
            format.setInteger(MediaFormat.KEY_FRAME_RATE, frameRate);
            /**关键帧*/
            format.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, iFrameInterval);

            mediaEncoder = MediaCodec.createEncoderByType(mimeType);

            mediaEncoder.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);

            mediaEncoder.start();

        } catch (Exception e) {
            throw new Exception("init media encoder is failure:" + e.getMessage());
        }
    }

    /**
     * init video config for record
     */
    private void init() {
        try {
            if (isInit) return;
            Log.i(TAG, "init video recorder config ");
            isInit = true;
            recordTime = System.nanoTime();
            initMediaEncoder();
            initMediaMuxer();
        } catch (Exception e) {
//            checkIsDelete(true, path);
            Log.e(TAG, "init Error:" + Log.getStackTraceString(e));
        }
    }

    private void release() {
        try {
            isInit = false;
            Log.i(TAG, "release");
            releaseMediaEncoder();
            releaseMediaMuxer();
//            checkIsDelete(false, path);
            onVideoTaken(OnVideoCallback.VIDEO_EVENT_ADD_FILE_IN_GALLERY,
                    cameraId, path, startPerVideoTime, startPerVideoTime + timeLoop);
        } catch (Exception e) {
//            checkIsDelete(true, path);
            path = "单个视频录制完成时，释放资源异常: " + path;
            onVideoTaken(OnVideoCallback.VIDEO_EVENT_RECORD_RECORDING_ERROR,
                    cameraId, path, startPerVideoTime, startPerVideoTime + timeLoop);
        }
    }

    private void onVideoTaken(int eventType, int cameraId, String path,
                              long startTime, long endTime) {
        if (dVideoCallback != null) {
            VideoModel dVideo = new VideoModel();
            dVideo.setCameraId(cameraId);
            dVideo.setPath(path);
            dVideo.setDuration(endTime - startTime);
            dVideo.setEncryptType(0); // TODO encrypt
            dVideo.setEndTime(endTime);
            dVideo.setStartTime(startTime);
            dVideo.setEventType(eventType);
            dVideo.setErrorCode(0);  // TODO errorCode handle
            dVideoCallback.onVideoTaken(dVideo);
        }
    }


    /**
     * check whether the file is 0
     *
     * @return
     */
    private boolean checkFileIsEmpty(String path) {
        File filePath = new File(path);
        if (filePath.exists() && filePath.length() < 1024) {
            Log.i(TAG, "this file is empty so need delete:" + filePath.length());
            return true;
        }
        Log.i(TAG, "this file is not empty");
        return false;
    }


    /**
     * judge from isException
     *
     * @param isException true must be delete file
     * @param path        file path
     */
    private void checkIsDelete(boolean isException, String path) {
        if (isException || checkFileIsEmpty(path)) {
            if (new File(path).exists()) {
                Log.e(TAG, "delete empty file,isException: " + isException + ";path:" + path);
//                RecordFileStorage.deleteNullFile(path);
            }
        }
    }


    /**
     * feed data from inputSurface
     *
     * @param input
     * @param mediaInputByte
     */
    private void drainEncoder(byte[] input, byte[] mediaInputByte) {

        init();

        try {
            if (isSemiPlanarYUV(colorFormat)) {
                NV21toI420SemiPlanar(input, mediaInputByte, cameraWidth, cameraHeight);
            } else {
                swapNV21toI420(input, mediaInputByte, cameraWidth, cameraHeight);
            }

            ByteBuffer[] inputBuffers = mediaEncoder.getInputBuffers();
            ByteBuffer[] outputBuffers = mediaEncoder.getOutputBuffers();

            int inputIndex = mediaEncoder.dequeueInputBuffer(-1);
            if (inputIndex >= 0) {
                ByteBuffer inputBuffer = inputBuffers[inputIndex];
                inputBuffer.clear();
                inputBuffer.put(mediaInputByte);
                mediaEncoder.queueInputBuffer(inputIndex, 0,
                        mediaInputByte.length,
                        Math.abs(recordTime - System.nanoTime()) / 1000, 0);
            }

            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            int outputBufferIndex = -1;
            do {
                if (mediaEncoder == null || mediaMuxer == null) {
                    outputBufferIndex = -1;
                    break;
                }
                outputBufferIndex = mediaEncoder.dequeueOutputBuffer(bufferInfo, mPhoneExtraParameters.getTimeoutUs());
                if (outputBufferIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                    videoTrackIndex = mediaMuxer.addTrack(mediaEncoder.getOutputFormat());
                    mediaMuxer.start();
                } else if (outputBufferIndex == MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED) {
                    outputBuffers = mediaEncoder.getOutputBuffers();
                } else if (outputBufferIndex >= 0) {

                    ByteBuffer outputBuffer = outputBuffers[outputBufferIndex];
                    mediaMuxer.writeSampleData(videoTrackIndex, outputBuffer, bufferInfo);

                    mediaEncoder.releaseOutputBuffer(outputBufferIndex, false);
                }
            } while (outputBufferIndex >= 0);

        } catch (Exception e) {
            Log.e(TAG, "视频录制中数据解析失败:" + e.getMessage());
        }
    }


    /**
     * NV21 is a 4:2:0 YCbCr, For 1 NV21 pixel: YYYYYYYY VUVU I420YUVSemiPlanar
     * is a 4:2:0 YUV, For a single I420 pixel: YYYYYYYY UVUV Apply NV21 to
     * I420YUVSemiPlanar(NV12) Refer to https://wiki.videolan.org/YUV/
     */
    private void NV21toI420SemiPlanar(byte[] nv21bytes, byte[] i420bytes,
                                      int width, int height) {
        System.arraycopy(nv21bytes, 0, i420bytes, 0, width * height);
        for (int i = width * height; i < nv21bytes.length; i += 2) {
            i420bytes[i] = nv21bytes[i + 1];
            i420bytes[i + 1] = nv21bytes[i];
        }
    }

    public static byte[] YV12toYUV420Planar(byte[] input, byte[] output, int width, int height) {
        /*
         * COLOR_FormatYUV420Planar is I420 which is like YV12, but with U and V reversed.
         * So we just have to reverse U and V.
         */
        final int frameSize = width * height;
        final int qFrameSize = frameSize / 4;

        System.arraycopy(input, 0, output, 0, frameSize); // Y
        System.arraycopy(input, frameSize, output, frameSize + qFrameSize, qFrameSize); // Cr (V)
        System.arraycopy(input, frameSize + qFrameSize, output, frameSize, qFrameSize); // Cb (U)

        return output;
    }

    /**
     * Returns the first codec capable of encoding the specified MIME type, or null if no
     * match was found.
     */
    private static MediaCodecInfo selectCodec(String mimeType) {
        int numCodecs = MediaCodecList.getCodecCount();
        for (int i = 0; i < numCodecs; i++) {
            MediaCodecInfo codecInfo = MediaCodecList.getCodecInfoAt(i);
            if (!codecInfo.isEncoder()) {
                continue;
            }
            String[] types = codecInfo.getSupportedTypes();
            for (int j = 0; j < types.length; j++) {
                if (types[j].equalsIgnoreCase(mimeType)) {
                    return codecInfo;
                }
            }
        }
        return null;
    }

    /**
     * Returns a color format that is supported by the codec and by this test code.  If no
     * match is found, this throws a test failure -- the set of formats known to the test
     * should be expanded for new platforms.
     */
    private static int selectColorFormat(MediaCodecInfo codecInfo, String mimeType) {
        MediaCodecInfo.CodecCapabilities capabilities = codecInfo.getCapabilitiesForType(mimeType);
        for (int i = 0; i < capabilities.colorFormats.length; i++) {
            int colorFormat = capabilities.colorFormats[i];
            if (isRecognizedFormat(colorFormat)) {
                return colorFormat;
            }
        }
        return 0;   // not reached
    }

    /**
     * Returns true if this is a color format that this test code understands (i.e. we know how
     * to read and generate frames in this format).
     */
    private static boolean isRecognizedFormat(int colorFormat) {
        switch (colorFormat) {
            // these are the formats we know how to handle for this test
            case MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Planar:
            case MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420PackedPlanar:
            case MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420SemiPlanar:
            case MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420PackedSemiPlanar:
            case MediaCodecInfo.CodecCapabilities.COLOR_TI_FormatYUV420PackedSemiPlanar:
                return true;
            default:
                return false;
        }
    }

    /**
     * Returns true if the specified color format is semi-planar YUV.  Throws an exception
     * if the color format is not recognized (e.g. not YUV).
     */
    private static boolean isSemiPlanarYUV(int colorFormat) {
        switch (colorFormat) {
            case MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Planar:
            case MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420PackedPlanar:
                return false;
            case MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420SemiPlanar:
            case MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420PackedSemiPlanar:
            case MediaCodecInfo.CodecCapabilities.COLOR_TI_FormatYUV420PackedSemiPlanar:
                return true;
            default:
                throw new RuntimeException("unknown format " + colorFormat);
        }
    }

    private void swapNV21toNV12(byte[] nv21bytes, byte[] nv12bytes, int width, int height) {
        byte bTmp = 0;
        final int iSize = width * height;
        for (int i = iSize; i < iSize + iSize / 2; i += 2) {
            bTmp = nv21bytes[i + 1];
            nv21bytes[i + 1] = nv21bytes[i];
            nv21bytes[i] = bTmp;
        }
        System.arraycopy(nv21bytes, 0, nv12bytes, 0, nv21bytes.length);
    }

    private static void swapNV21toI420(byte[] nv21bytes, byte[] i420bytes, int width, int height) {
        final int iSize = width * height;
        System.arraycopy(nv21bytes, 0, i420bytes, 0, iSize);

        for (int iIndex = 0; iIndex < iSize / 2; iIndex += 2) {
            i420bytes[iSize + iIndex / 2 + iSize / 4] = nv21bytes[iSize + iIndex]; //U
            i420bytes[iSize + iIndex / 2] = nv21bytes[iSize + iIndex + 1]; //V
        }
    }

    private void releaseMediaMuxer() {
        try {
            if (mediaMuxer != null) {
                mediaMuxer.stop();
                mediaMuxer.release();
                mediaMuxer = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void releaseMediaEncoder() {
        try {
            if (mediaEncoder != null) {
                mediaEncoder.stop();
                mediaEncoder.release();
                mediaEncoder = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String getOutputFile() {
        String dir = mPhoneExtraParameters.getOutputDir();
        return dir + File.separator + getFileName();
    }

    private String getFileName() {
        String frontOrBack = isFaceCamera ? "front" : "back";
        return "Phone_" + frontOrBack + "_" + new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date()) + SUFFIX_MP4;
    }
}

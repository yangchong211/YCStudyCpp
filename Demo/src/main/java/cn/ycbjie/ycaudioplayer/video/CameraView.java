package cn.ycbjie.ycaudioplayer.video;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.Toast;

import com.yc.camera.CameraManager;
import com.yc.camera.ICameraDevice;
import com.yc.camera.ICameraParameters;
import com.yc.camera.callback.OnPictureCallback;
import com.yc.camera.callback.OnPreviewCallback;
import com.yc.camera.callback.OnVideoCallback;
import com.yc.camera.model.VideoModel;

import java.io.File;
import java.io.FileDescriptor;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.hardware.Camera.CameraInfo.CAMERA_FACING_BACK;
import static android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT;

public class CameraView implements SurfaceHolder.Callback {

    public static final String TAG = CameraMainActivity.TAG;

    private static final String DIR_ROOT = "phone_demo";
    private static final String DIR_MEDIA = "media";
    private static final String DIR_FRONT = "front";
    private static final String DIR_BACK = "back";
    private static final String DIR_PIC = "pic";

    private Context mContext;
    private ICameraDevice mDCameraDevice;
    private SurfaceHolder mSurfaceHolder;

    private String tag;

    // switch front or back
    private final boolean isFacing;

    public CameraView(Context context, boolean isFacing) {
        this.mContext = context;
        this.isFacing = isFacing;
        this.tag = TAG + "_" + (isFacing ? DIR_FRONT : DIR_BACK);
    }

    public void initCameraDevice() {
        if (mDCameraDevice != null) return;
        try {
            Log.d(tag, "打开摄像头:" + isFacing);
            mDCameraDevice = CameraManager.get().openCamera(isFacing ? CAMERA_FACING_FRONT : CAMERA_FACING_BACK);
            setCameraParameters();

            mDCameraDevice.setYuvCallback(mDYUVCallback);
            mDCameraDevice.startYuvVideoFrame(null);
            mDCameraDevice.setVideoCallback(mVideoCallback);
            Log.d(tag, "打开摄像头成功");
        } catch (Throwable e) {
            mDCameraDevice = null;
            Log.e(tag, "打开摄像头异常:" + Log.getStackTraceString(e));
        }
        if (mDCameraDevice != null) {
            try {
                mDCameraDevice.enableShutterSound(false);
            } catch (Throwable e) {
                Log.e(tag, "异常:" + Log.getStackTraceString(e));
            }
        }
    }

    public void startPreviewAndRecord() {
        Log.d(tag, "开启预览和录制:" + Thread.currentThread().getName());
        setPreviewSurface();
        startPreview();
        startRecord();
    }

    private void setCameraParameters() {
        try {
            ICameraParameters parameters = mDCameraDevice.getParameters();
            parameters.setCameraId(isFacing ? CAMERA_FACING_FRONT : CAMERA_FACING_BACK);
            parameters.setOutputFileFormat(ICameraParameters.OutputFormat.MPEG_4);
            parameters.setOutputFile(getOutputDir(isFacing));
            parameters.setVideoRotateDuration(30 * 1000);
            parameters.setVideoEncodingBitRate(2 * 1024 * 1024);
            parameters.setPreviewSize(1280, 720);
            parameters.setVideoSize(1280, 720);
            parameters.setPictureSize(1280, 720);
            parameters.setJpegQuality(90);
            mDCameraDevice.setParameters(parameters);
        } catch (Throwable e) {
            Log.e(tag, "异常:" + Log.getStackTraceString(e));
        }
    }

    private String getOutputDir(boolean isFacing) {
        String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String dir = sdPath + File.separator
                + DIR_ROOT + File.separator
                + DIR_MEDIA + File.separator
                + (isFacing ? DIR_FRONT : DIR_BACK);
        File file = new File(dir);
        if (!file.exists()) {
            boolean ret = file.mkdirs();
            Log.d(tag, "创建录制文件夹:" + ret);
        }
        return dir;
    }

    private String getPicOutputPath() {
        String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String dir = sdPath + File.separator
                + DIR_ROOT + File.separator
                + DIR_PIC;
        File file = new File(dir);
        if (!file.exists()) {
            boolean ret = file.mkdirs();
            Log.d(tag, "创建拍照文件夹:" + ret);
        }
        return dir + File.separator + getPicName();
    }

    private String getPicName() {
        String frontOrBack = isFacing ? "front" : "back";
        return "Pic_" + frontOrBack + "_" + new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date()) + ".jpg";
    }

    private final OnPreviewCallback mDYUVCallback = new OnPreviewCallback() {

        @Override
        public void onPreviewFrame(byte[] bytes, int cameraId, int size, int format) {
//            Log.d(tag, "YUV帧数据:" + cameraId + ";" + size + ";" + format);
        }

        @Override
        public void onPreviewFd(FileDescriptor fileDescriptor, int cameraId, int size, int format) {

        }

        @Override
        public void onPreviewFrame(byte[] bytes, int cameraId, int size) {

        }

        @Override
        public void onPreviewFd(FileDescriptor fileDescriptor, int cameraId, int size) {

        }
    };

    private final OnVideoCallback mVideoCallback = new OnVideoCallback() {
        @Override
        public void onVideoTaken(VideoModel dVideo) {
            if (dVideo == null) {
                Log.d(tag, "录制回调结果为空");
                return;
            }
            int eventType = dVideo.getEventType();
            int cameraId = dVideo.getCameraId();
            String path = dVideo.getPath();
            long startTime = dVideo.getStartTime();
            long endTime = dVideo.getEndTime();
            switch (eventType) {
                case VIDEO_EVENT_ADD_FILE_IN_GALLERY:
                    Log.d(tag, "录制成功:" + path + ";" + cameraId
                            + ";" + startTime + ";" + endTime);
                    break;
                case VIDEO_EVENT_RECORD_STATUS_START:
                    Log.d(tag, "开始录制:" + path);
                    break;
                case VIDEO_EVENT_RECORD_STATUS_STOP:
                    Log.d(tag, "结束录制:" + path);
                    break;
                case VIDEO_EVENT_RECORD_RECORDING_ERROR:
                    Log.d(tag, "录制异常:" + path);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onVideoFrame(byte[] bytes, int dataType, int size) {

        }
    };

    private final OnPictureCallback mDPictureCallback = new OnPictureCallback() {
        @Override
        public void onPictureTaken(String s) {
            if (TextUtils.isEmpty(s)) {
                Toast.makeText(mContext, "拍照失败", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(mContext, "拍照成功:" + s, Toast.LENGTH_SHORT).show();
            restartPreview(); // 解决拍照预览卡住问题
        }
    };

    public void takePicture() {
        try {
            if (mDCameraDevice != null) {
                mDCameraDevice.takePicture(getPicOutputPath(), null, mDPictureCallback);
            }
        } catch (Throwable e) {
            Log.d(tag, "拍照异常:" + Log.getStackTraceString(e));
        }
    }

    public void release() {
        try {
            if (mDCameraDevice != null) {
                mDCameraDevice.stopRecord();
                mDCameraDevice.stopPreview();
                mDCameraDevice.setYuvCallback(null);
                mDCameraDevice.setVideoCallback(null);
                mDCameraDevice.release();
                mDCameraDevice = null;
                CameraManager.get().closeCamera(isFacing ? CAMERA_FACING_FRONT : CAMERA_FACING_BACK);
            }
        } catch (Throwable e) {
            Log.e(tag, "异常:" + Log.getStackTraceString(e));
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(tag, "surfaceCreated");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(tag, "surfaceChanged");
        this.mSurfaceHolder = holder;
        initCameraDevice();
        startPreviewAndRecord();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(tag, "surfaceDestroyed");
        release();
    }

    private void setPreviewSurface() {
        if (mDCameraDevice != null) {
            try {
                Log.d(tag, "设置预览Surface");
                mDCameraDevice.setPreviewSurface(mSurfaceHolder.getSurface());
            } catch (Throwable e) {
                Log.d(tag, "异常:" + Log.getStackTraceString(e));
            }
        }
    }

    private void startPreview() {
        if (mDCameraDevice != null) {
            try {
                Log.d(tag, "开启预览");
                mDCameraDevice.startPreview();
            } catch (Throwable e) {
                Log.d(tag, "异常:" + Log.getStackTraceString(e));
            }
        }
    }

    private void restartPreview() {
        if (mDCameraDevice != null) {
            try {
                Log.d(tag, "重置预览页面");
                mDCameraDevice.stopPreview();
                mDCameraDevice.startPreview();
            } catch (Throwable e) {
                Log.d(tag, "重置预览异常:" + Log.getStackTraceString(e));
            }
        }
    }

    private void startRecord() {
        if (mDCameraDevice != null) {
            try {
                Log.d(tag, "开启录制");
                mDCameraDevice.startRecord();
            } catch (Throwable e) {
                Log.d(tag, "异常:" + Log.getStackTraceString(e));
            }
        }
    }
}

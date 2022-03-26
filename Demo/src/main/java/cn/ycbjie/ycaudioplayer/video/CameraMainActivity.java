package cn.ycbjie.ycaudioplayer.video;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.yc.camera.CameraManager;
import com.yc.phone.PhoneCameraService;

import java.util.ArrayList;
import java.util.List;

import cn.ycbjie.ycaudioplayer.R;

public class CameraMainActivity extends AppCompatActivity {

    public static final String TAG = "PhoneDemo";

    private static final int REQUEST_SINGLE = 1001;
    private static final int REQUEST_MULTI = 1002;

    private SurfaceView backSurfaceView;
    private SurfaceView frontSurfaceView;

    private CameraView mBackCamera;
    private CameraView mFrontCamera; // not supported

    // switch front or back
    private final boolean isFacing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        CameraManager.get().init(new PhoneCameraService());

        checkPermissions();
    }

    private void checkPermissions() {
        List<String> deniedPermissions = new ArrayList<>();
        List<String> needPermissions = new ArrayList<>();
        needPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        needPermissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        needPermissions.add(Manifest.permission.CAMERA);
        needPermissions.add(Manifest.permission.RECORD_AUDIO);
        for (String permission : needPermissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permission);
                Log.d(TAG, "权限未申请:" + permission);
            }
        }

        if (deniedPermissions.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    deniedPermissions.toArray(new String[deniedPermissions.size()]),
                    REQUEST_MULTI);
        } else {
            Log.d(TAG, "所有权限已授权");
            mBackCamera.initCameraDevice();
//            mFrontCamera.initCameraDevice();
        }
    }

    private void initView() {
        mBackCamera = new CameraView(this, isFacing);
//        mFrontCamera = new CameraView(this, !isFacing);

        backSurfaceView = findViewById(R.id.sf_preview_big);
        frontSurfaceView = findViewById(R.id.sf_preview_small);

        backSurfaceView.setKeepScreenOn(true);
        backSurfaceView.getHolder().addCallback(mBackCamera);
        backSurfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        frontSurfaceView.setVisibility(View.GONE);
        frontSurfaceView.setZOrderMediaOverlay(true);

        findViewById(R.id.bt_take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBackCamera.takePicture();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFrontCamera != null) {
            mFrontCamera.release();
        }
        if (mBackCamera != null) {
            mBackCamera.release();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_MULTI:
                if (grantResults.length > 0) {
                    boolean allGranted = true;
                    for (int i = 0; i < permissions.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            Log.d(TAG, "未授权:" + permissions[i]);
                            allGranted = false;
                        } else {
                            Log.d(TAG, "已授权:" + permissions[i]);
                        }
                    }
                    if (allGranted) {
                        Log.d(TAG, "申请的所有权限已授权");
                        if (mFrontCamera != null) {
                            mFrontCamera.initCameraDevice();
                            mFrontCamera.startPreviewAndRecord();
                        }
                        if (mBackCamera != null) {
                            mBackCamera.initCameraDevice();
                            mBackCamera.startPreviewAndRecord();
                        }
                    }
                }
                break;
        }
    }
}

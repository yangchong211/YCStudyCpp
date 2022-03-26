package com.yc.phone;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;

import com.yc.camera.callback.OnPictureCallback;

import java.io.FileOutputStream;
import java.io.IOException;


public class PictureCallbackImpl implements Camera.PictureCallback {

    private OnPictureCallback dPictureCallback;
    private String path;
    private int cameraId;
    private int jpegQuality;

    public PictureCallbackImpl(OnPictureCallback dPictureCallback, String path, int cameraId, int jpegQuality) {
        this.dPictureCallback = dPictureCallback;
        this.path = path;
        this.cameraId = cameraId;
        this.jpegQuality = jpegQuality;
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
        // orientation fixed to portrait
        Bitmap bitmap = rotationBitmap(bmp, 90, cameraId);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.JPEG, jpegQuality, fos);
            fos.flush();
        } catch (IOException e) {
            path = "";
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        dPictureCallback.onPictureTaken(path);
    }

    private Bitmap rotationBitmap(Bitmap bmp, int rotation, int cameraId) {
        Matrix matrix = new Matrix();
        matrix.postRotate(rotation);
        if (cameraId == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            matrix.postScale(1, -1); // front mirror
        }
        Bitmap bitmap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
        return bitmap;
    }
}

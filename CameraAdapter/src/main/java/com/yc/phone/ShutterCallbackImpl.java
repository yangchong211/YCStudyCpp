package com.yc.phone;

import android.hardware.Camera;

import com.yc.camera.callback.OnShutterCallback;


public class ShutterCallbackImpl implements Camera.ShutterCallback {

    private OnShutterCallback dShutterCallback;

    public ShutterCallbackImpl(OnShutterCallback dShutterCallback) {
        this.dShutterCallback = dShutterCallback;
    }

    @Override
    public void onShutter() {
        if (dShutterCallback != null) {
            dShutterCallback.onShutter();
        }
    }
}

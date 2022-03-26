package com.yc.camera.exception;


public class CameraException extends Exception {

    private int errorCode;

    public CameraException(int errorId, String msg) {
        super(msg);
        this.errorCode = errorId;
    }

    public CameraException(String msg) {
        super(msg);
    }

    public CameraException(String msg, Throwable e) {
        super(msg, e);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}

package com.yc.camera.callback;

/**
 * 拍照回调接口
 */
public interface OnPictureCallback {

    /**
     * onPictureTaken 中的 path 为拍照图片的保存路径，当此函数被回调时，表 示拍照已经完成。
     * 如果 path 为 app 设置下去的保存路径，表示拍照成功，如果 path 为空字符串，表示拍照失败。
     *
     * @param path 拍照成功之后的回调路径
     */
    void onPictureTaken(String path);
}

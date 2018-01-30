package cn.ycbjie.ycaudioplayer.api.http;


public abstract class HttpCallback<T> {

    public abstract void onSuccess(T t);
    public abstract void onFail(Exception e);
    public void onFinish() {}
}

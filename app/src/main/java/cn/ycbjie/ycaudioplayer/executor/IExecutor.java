package cn.ycbjie.ycaudioplayer.executor;


public interface IExecutor<T> {

    void execute();
    void onPrepare();
    void onExecuteSuccess(T t);
    void onExecuteFail(Exception e);

}

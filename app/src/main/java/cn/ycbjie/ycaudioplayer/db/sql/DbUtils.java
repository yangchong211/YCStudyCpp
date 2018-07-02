package cn.ycbjie.ycaudioplayer.db.sql;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class DbUtils {

    public interface CopyDbListener{
        void onCopyCompleted(SQLiteDatabase sqLiteDatabase);
        void onCopyError(Throwable throwable);
    }

    public void openDatabase(final Context context , final String dbName , final CopyDbListener listener){
        Observable.create(new ObservableOnSubscribe<SQLiteDatabase>() {
            @Override
            public void subscribe(ObservableEmitter<SQLiteDatabase> emitter) throws Exception {
                File file = context.getDatabasePath(dbName);
                if (!file.exists()) {
                    File parentFile = file.getParentFile();
                    //noinspection ResultOfMethodCallIgnored
                    parentFile.mkdirs();
                    FileOutputStream fos = null;
                    InputStream is = null;
                    try {
                        is = context.getAssets().open(dbName);
                        fos = new FileOutputStream(file.getPath());
                        byte[] buffer = new byte[10240];
                        int count = 0;
                        while ((count = is.read(buffer)) > 0) {
                            fos.write(buffer, 0, count);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        emitter.onNext(null);
                    } finally {
                        if(fos!=null){
                            fos.flush();
                            fos.close();
                        }
                        if(is!=null){
                            is.close();
                        }
                    }
                }

                emitter.onNext(SQLiteDatabase.openOrCreateDatabase(file.getPath(), null));
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SQLiteDatabase>() {
                    @Override
                    public void accept(SQLiteDatabase sqLiteDatabase) throws Exception {
                        if (listener != null) {
                            listener.onCopyCompleted(sqLiteDatabase);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (listener != null) {
                            listener.onCopyError(throwable);
                        }
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });

    }

}

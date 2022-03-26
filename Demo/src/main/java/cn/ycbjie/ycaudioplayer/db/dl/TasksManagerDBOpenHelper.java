package cn.ycbjie.ycaudioplayer.db.dl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2017/1/9
 * 描    述：下载Tasks帮助类
 * 修订历史：
 *          下载框架：https://github.com/lingochamp/FileDownloader
 * ================================================
 */
public class TasksManagerDBOpenHelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "tasksManager.db";
    private final static int DATABASE_VERSION = 2;

    TasksManagerDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists tasksManger(" +
                "_id integer primary key autoincrement," +
                "id integer," +
                "name varchar(200)," +
                "url varchar(200)," +
                "path verchar(200)" +
                ")";
        db.execSQL(sql);
        sql = "create table if not exists tasksMangerComplete(" +
                "_id integer primary key autoincrement," +
                "id integer," +
                "name varchar(200)," +
                "url varchar(200)," +
                "path verchar(200)" +
                ")";
        db.execSQL(sql);


        /*db.execSQL("CREATE TABLE IF NOT EXISTS "
                + TasksManagerDBController.TABLE_NAME
                + String.format(
                "("
                        + "%s INTEGER PRIMARY KEY, " // id, download id
                        + "%s VARCHAR, " // name
                        + "%s VARCHAR, " // url
                        + "%s VARCHAR " // path
                        + ")"
                , TasksManagerModel.ID
                , TasksManagerModel.NAME
                , TasksManagerModel.URL
                , TasksManagerModel.PATH
        ));

        db.execSQL("CREATE TABLE IF NOT EXISTS "
                + TasksManagerDBController.TABLE_COMPLETE_NAME
                + String.format(
                "("
                        + "%s INTEGER PRIMARY KEY, " // id, download id
                        + "%s VARCHAR, " // name
                        + "%s VARCHAR, " // url
                        + "%s VARCHAR " // path
                        + ")"
                , TasksManagerModel.ID
                , TasksManagerModel.NAME
                , TasksManagerModel.URL
                , TasksManagerModel.PATH
        ));*/
    }


    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        /*db.execSQL("CREATE TABLE IF NOT EXISTS "
                + DlTasksManagerDBController.DOWNLOADED_NAME
                + String.format(
                "("
                        + "%s INTEGER PRIMARY KEY, " // id, download id
                        + "%s VARCHAR, " // name
                        + "%s VARCHAR, " // url
                        + "%s VARCHAR " // path
                        + ")"
                , DLTasksManagerModel.ID
                , DLTasksManagerModel.LOGO
                , DLTasksManagerModel.NAME
                , DLTasksManagerModel.URL
                , DLTasksManagerModel.PATH
        ));*/
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1 && newVersion == 2) {
            db.delete(TasksManagerDBController.TABLE_NAME, null, null);
        }
    }

}

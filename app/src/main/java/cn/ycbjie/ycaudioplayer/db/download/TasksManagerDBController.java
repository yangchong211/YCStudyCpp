package cn.ycbjie.ycaudioplayer.db.download;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.util.ArrayList;
import java.util.List;

import cn.ycbjie.ycaudioplayer.base.BaseApplication;

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
public class TasksManagerDBController {

    final static String TABLE_NAME = "tasksManger";
    private final static String TABLE_COMPLETE_NAME = "tasksMangerComplete";
    private final SQLiteDatabase db;

    TasksManagerDBController() {
        TasksManagerDBOpenHelper openHelper = new TasksManagerDBOpenHelper(BaseApplication.getInstance());
        db = openHelper.getWritableDatabase();
    }

    List<TasksManagerModel> getAllTasks() {
        final Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        final List<TasksManagerModel> list = new ArrayList<>();
        try {
            if (!c.moveToLast()) {
                return list;
            }
            do {
                TasksManagerModel model = new TasksManagerModel();
                model.setId(c.getInt(c.getColumnIndex(TasksManagerModel.ID)));
                model.setName(c.getString(c.getColumnIndex(TasksManagerModel.NAME)));
                model.setUrl(c.getString(c.getColumnIndex(TasksManagerModel.URL)));
                model.setPath(c.getString(c.getColumnIndex(TasksManagerModel.PATH)));
                list.add(model);
            } while (c.moveToPrevious());
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return list;
    }


    /**
     * 获取下载完成的数据
     * @return                  list集合
     */
    List<TasksManagerModel> getDownloadedTasks() {
        final Cursor c = db.rawQuery("SELECT * FROM " + TABLE_COMPLETE_NAME, null);
        final List<TasksManagerModel> list = new ArrayList<>();
        try {
            if (!c.moveToLast()) {
                return list;
            }
            do {
                TasksManagerModel model = new TasksManagerModel();
                model.setId(c.getInt(c.getColumnIndex(TasksManagerModel.ID)));
                model.setName(c.getString(c.getColumnIndex(TasksManagerModel.NAME)));
                model.setUrl(c.getString(c.getColumnIndex(TasksManagerModel.URL)));
                model.setPath(c.getString(c.getColumnIndex(TasksManagerModel.PATH)));
                list.add(model);
            } while (c.moveToPrevious());
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return list;
    }


    /**
     * 添加到数据库
     * @param url                   下载链接
     * @param path                  下载路径
     * @return                      实体类model
     */
    TasksManagerModel addTask(final String url, final String path) {
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(path)) {
            return null;
        }
        // 必须使用文件下载器，将TasksManagerModel与文件下载器关联
        final int id = FileDownloadUtils.generateId(url, path);
        TasksManagerModel model = new TasksManagerModel();
        model.setId(id);
        model.setName(url.substring(url.lastIndexOf("/")));
        model.setUrl(url);
        model.setPath(path);
        final boolean succeed = db.insert(TABLE_NAME, null, model.toContentValues()) != -1;
        return succeed ? model : null;
    }


    /**
     * 从数据库中移除数据
     * @param url                   链接
     */
    boolean removeTasks(String url) {
        return db.delete(TABLE_NAME ,"url = ?" ,new String[]{url})!=-1;
    }

    /*------------------------------------下载完成的部分-------------------------------------------*/

    /**
     * 判断是否下载到数据库
     * @param url                   链接
     * @return                      是否下载完成
     */
    boolean isDownloadedFile(String url) {
        Cursor c = db.query(TABLE_COMPLETE_NAME,null,"url= ?",
                new String[]{url},null,null,null);
        try{
            return c.moveToNext();
        }finally {
            if (c!=null) {
                c.close();
            }
        }
    }


    /**
     * 添加到数据库
     * @param bean                  实体类
     */
    boolean addDownloadedDb(TasksManagerModel bean) {
        return db.insert(TABLE_COMPLETE_NAME ,null ,bean.toContentValues()) != -1;
    }

    /**
     * 从数据库中移除数据
     * @param url                   链接
     */
    boolean removeDownloadedTasks(String url) {
        return db.delete(TABLE_COMPLETE_NAME ,"url = ?" ,new String[]{url})!=-1;
    }


}

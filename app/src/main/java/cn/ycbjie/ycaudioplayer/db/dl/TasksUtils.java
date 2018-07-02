package cn.ycbjie.ycaudioplayer.db.dl;


import com.blankj.utilcode.util.LogUtils;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.io.File;

/**
 * <pre>
 *     @author yangchong
 *     blog  : www.pedaily.cn
 *     time  : 2018/03/22
 *     desc  : 下载工具类
 *     revise:
 * </pre>
 */
public class TasksUtils {


    /**
     * 删除
     * @param url               下载链接
     */
    public static void delete(String url){
        if(url==null || url.length()==0){
            return;
        }
        File file = new File(TasksManager.getImpl().createPath(url));
        //noinspection ResultOfMethodCallIgnored
        file.delete();
        //当删除的时候，需要移除
        TasksManager.getImpl().removeDownloaded(url);
    }


    /**
     * 开始下载
     * @param url               下载链接
     */
    public static void start(String url , String path){
        final BaseDownloadTask task = FileDownloader.getImpl().create(url)
                .setPath(path)
                .setCallbackProgressTimes(500)
                .setListener(new TaskFileDownloadListener());
        TasksManager.getImpl().addTaskForViewHolder(task);
        TasksManager.getImpl().addTask(url,path);
        task.start();
    }


    /**
     * 删除所有数据库中的下载内容信息
     */
    public static void deleteAll(){
        File file = new File(FileDownloadUtils.getDefaultSaveRootPath());
        if (!file.exists()) {
            LogUtils.e(String.format("check file files not exists %s", file.getAbsolutePath()));
            return;
        }
        if (!file.isDirectory()) {
            LogUtils.e(String.format("check file files not directory %s", file.getAbsolutePath()));
            return;
        }
        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        for (File file1 : files) {
            //noinspection ResultOfMethodCallIgnored
            file1.delete();
        }
        if(TasksManager.getImpl().getDownloadedList()!=null){
            TasksManager.getImpl().getDownloadedList().clear();
        }
        if(TasksManager.getImpl().getModelList()!=null){
            TasksManager.getImpl().getModelList().clear();
        }
    }


}

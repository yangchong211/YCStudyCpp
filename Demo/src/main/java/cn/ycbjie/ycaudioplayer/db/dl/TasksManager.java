package cn.ycbjie.ycaudioplayer.db.dl;


import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadConnectListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.lang.ref.WeakReference;
import java.util.List;

import cn.ycbjie.ycaudioplayer.ui.detail.model.DialogListBean;
import cn.ycbjie.ycaudioplayer.ui.detail.view.adapter.DialogListAdapter;
import cn.ycbjie.ycaudioplayer.ui.me.view.adapter.CacheDownloadingAdapter;

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
public class TasksManager{

    /**
     * 数据库控制器
     */
    private TasksManagerDBController dbController;
    /**
     * 正在下载集合
     */
    private List<TasksManagerModel> modelList;
    /**
     * 下载完成集合
     */
    private List<TasksManagerModel> modelListed;
    /**
     * 用来存放task的集合
     */
    private SparseArray<BaseDownloadTask> taskSparseArray = new SparseArray<>();
    /**
     * 下载链接监听器
     */
    private FileDownloadConnectListener listener;

    /**
     * 获取单利对象
     * @return              TasksManager对象
     */
    public static TasksManager getImpl() {
        return HolderClass.INSTANCE;
    }


    private final static class HolderClass {
        private final static TasksManager INSTANCE = new TasksManager();
    }


    private TasksManager() {
        //初始化数据库控制器
        dbController = new TasksManagerDBController();
        modelList = dbController.getAllTasks();
        modelListed = dbController.getDownloadedTasks();
    }


    /**
     * 添加
     * @param task                  task
     */
    public void addTaskForViewHolder(final BaseDownloadTask task) {
        taskSparseArray.put(task.getId(), task);
    }

    /**
     * 移除
     * @param id                    下载资源id
     */
    public void removeTaskForViewHolder(final int id) {
        taskSparseArray.remove(id);
    }

    /**
     * 更新数据
     * @param id                    下载资源id
     * @param holder                holder
     */
    public void updateViewHolder(final int id, final TaskViewHolderImp holder) {
        final BaseDownloadTask task = taskSparseArray.get(id);
        if (task == null) {
            return;
        }
        task.setTag(holder);
    }

    /**
     * 移除所有的集合
     */
    public void releaseTask() {
        taskSparseArray.clear();
    }

    /**
     * 销毁的时候调用该方法
     */
    public void onDestroy() {
        unregisterServiceConnectionListener();
        releaseTask();
    }


    /**
     * 创建的时候调用
     */
    public void onCreate(final WeakReference<RecyclerView.Adapter> weakReference) {
        if (!FileDownloader.getImpl().isServiceConnected()) {
            FileDownloader.getImpl().bindService();
            registerServiceConnectionListener(weakReference);
        }
    }

    /**
     * 注册服务连接
     * @param weakReference         使用弱引用
     */
    private void registerServiceConnectionListener(final WeakReference<RecyclerView.Adapter> weakReference) {
        if (listener != null) {
            FileDownloader.getImpl().removeServiceConnectListener(listener);
        }
        listener = new FileDownloadConnectListener() {
            @Override
            public void connected() {
                if (weakReference == null || weakReference.get() == null) {
                    return;
                }
                if(weakReference.get() instanceof DialogListAdapter){
                    DialogListAdapter dialogListAdapter = (DialogListAdapter) weakReference.get();
                    dialogListAdapter.postNotifyDataChanged();
                }else if(weakReference.get() instanceof CacheDownloadingAdapter){
                    CacheDownloadingAdapter adapter = (CacheDownloadingAdapter) weakReference.get();
                    adapter.postNotifyDataChanged();
                }
            }
            @Override
            public void disconnected() {
                if (weakReference == null || weakReference.get() == null) {
                    return;
                }
                if(weakReference.get() instanceof DialogListAdapter){
                    DialogListAdapter dialogListAdapter = (DialogListAdapter) weakReference.get();
                    dialogListAdapter.postNotifyDataChanged();
                }else if(weakReference.get() instanceof CacheDownloadingAdapter){
                    CacheDownloadingAdapter adapter = (CacheDownloadingAdapter) weakReference.get();
                    adapter.postNotifyDataChanged();
                }
            }
        };
        FileDownloader.getImpl().addServiceConnectListener(listener);
    }


    /**
     * 注销服务连接
     */
    private void unregisterServiceConnectionListener() {
        if(listener!=null){
            FileDownloader.getImpl().removeServiceConnectListener(listener);
            listener = null;
        }
    }

    /**
     * 服务连接是否准备好
     * @return                  true表示准备好了
     */
    public boolean isReady() {
        return FileDownloader.getImpl().isServiceConnected();
    }

    /**
     * 获取下载队列集合中的实体类
     * @param position          索引位置
     * @return                  model
     */
    public TasksManagerModel get(final int position) {
        if(modelList==null || modelList.size()==0){
            return null;
        }
        return modelList.get(position);
    }


    /**
     * 通过id值查找对应的model
     * @param id                下载id
     * @return                  model
     */
    public TasksManagerModel getById(final int id) {
        for (TasksManagerModel model : modelList) {
            //当model中id与下载id相同时，则返回该model
            if (model.getId() == id) {
                return model;
            }
        }
        return null;
    }

    /**
     * 判断是否下载完成
     * @param status            下载状态
     * @return                  是否下载完成
     * @see FileDownloadStatus
     */
    public boolean isDownloaded(final int status) {
        return status == FileDownloadStatus.completed;
    }

    /**
     * 获取下载状态
     * @param id                下载资源id
     * @param path              下载路径
     * @return                  下载的状态
     */
    public int getStatus(final int id, String path) {
        return FileDownloader.getImpl().getStatus(id, path);
    }

    /**
     * 获取任务的目标文件的总字节
     * @param id                下载资源id
     * @return                  总字节
     */
    public long getTotal(final int id) {
        return FileDownloader.getImpl().getTotal(id);
    }

    /**
     * 下载到目前为止下载的字节
     * @param id                下载资源id
     * @return                  字节
     */
    public long getSoFar(final int id) {
        return FileDownloader.getImpl().getSoFar(id);
    }

    /**
     * 获取下载队列中数量
     * @return                  数量
     */
    public int getTaskCounts() {
        if(modelList==null || modelList.size()==0){
            return 0;
        }
        return modelList.size();
    }

    /**
     * 将某个链接资源添加到下载的队列中
     * @param url               资源url
     * @return                  model
     */
    public TasksManagerModel addTask(final String url) {
        if(url==null){
            return null;
        }
        return addTask(url, createPath(url));
    }

    /**
     * 将某个链接资源添加到下载的队列中
     * @param url               资源url
     * @param path              路径
     * @return                  model
     */
    public TasksManagerModel addTask(final String url, final String path) {
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(path)) {
            return null;
        }
        //根据链接和地址查找对应id
        final int id = FileDownloadUtils.generateId(url, path);
        TasksManagerModel model = getById(id);
        if (model != null) {
            return model;
        }
        //天机到数据库
        final TasksManagerModel newModel = dbController.addTask(url, path);
        if (newModel != null) {
            modelList.add(newModel);
        }
        return newModel;
    }

    /**
     * 创建一个下载文件的路径
     * @param url               下载url
     * @return                  路径
     */
    public String createPath(final String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return FileDownloadUtils.getDefaultSaveFilePath(url);
    }

    /*----------------------------------自己创建-------------------------------------------------*/
    /**
     * 获取下载文件的下载id
     * @param url                   下载链接
     * @return                      下载id
     */
    public int getId(String url) {
        if (TextUtils.isEmpty(url)){
            return -1 ;
        }
        return FileDownloadUtils.generateId(url, createPath(url));
    }


    /**
     * 获取正在下载中的的数据
     * @return                      集合
     */
    public List<TasksManagerModel> getModelList() {
        if(modelList==null || modelList.size()==0){
            return null;
        }
        return modelList;
    }


    /**
     * 删除未下载完成的数据
     * 从下载队列中删除某个下载资源
     * @param url                   链接
     */
    public void deleteTasksManagerModel(String url) {
        if (TextUtils.isEmpty(url)) {
            return ;
        }
        for (int i = 0; i < modelList.size(); i++) {
            if (url.equals(modelList.get(i).getUrl())){
                modelList.remove(i);
                break;
            }
        }
        //并且从数据库中移除数据
        dbController.removeTasks(url);
    }


    /**
     * 移除下载完成的数据
     * @param url                   链接
     */
    public void removeDownloaded(String url) {
        if (TextUtils.isEmpty(url)) {
            return ;
        }
        for (int i = 0; i < modelListed.size(); i++) {
            if (url.equals(modelListed.get(i).getUrl())){
                modelListed.remove(i);
                break;
            }
        }
        //并且从数据库中移除数据
        dbController.removeDownloadedTasks(url);
    }


    /**
     * 判断下载是否完成
     * @param url                   链接
     * @return                      是否完成下载
     */
    public boolean isDownloadedFile(String url) {
        if(url==null || url.length()==0){
            return false;
        }
        return dbController.isDownloadedFile(url);
    }


    /**
     * 将文件音视频内容添加到数据库中
     * @param model                 model
     */
    public void addDownloadedDb(DialogListBean model) {
        String url = model.getVideo();
        String path = TasksManager.getImpl().createPath(url);
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(path)) {
            return ;
        }
        final int id = FileDownloadUtils.generateId(url, path);
        TasksManagerModel bean = new TasksManagerModel();
        bean.setId(id);
        bean.setName(model.getName());
        bean.setUrl(url);
        bean.setUrl(path);
        modelListed.add(bean);
        dbController.addDownloadedDb(bean);
    }


    /**
     * 将文件音视频内容添加到数据库中
     * @param model                 model
     */
    public void addDownloadedDB(TasksManagerModel model) {
        modelListed.add(model);
        dbController.addDownloadedDb(model);
    }

    /**
     * 获取下载完成的集合
     * @return                      集合
     */
    public List<TasksManagerModel> getDownloadedList() {
        if(modelListed==null || modelListed.size()==0){
            return null;
        }
        return modelListed;
    }

}

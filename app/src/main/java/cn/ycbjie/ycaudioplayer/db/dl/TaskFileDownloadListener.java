package cn.ycbjie.ycaudioplayer.db.dl;

import com.blankj.utilcode.util.LogUtils;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.model.FileDownloadStatus;

import cn.ycbjie.ycaudioplayer.ui.detail.view.adapter.DialogListAdapter;
import cn.ycbjie.ycaudioplayer.ui.me.view.adapter.CacheDownloadingAdapter;
import cn.ycbjie.ycaudioplayer.utils.logger.AppLogUtils;


/**
 * <pre>
 *     @author yangchong
 *     blog  : www.pedaily.cn
 *     time  : 2018/03/22
 *     desc  : 下载监听器
 *     revise:
 * </pre>
 */
public class TaskFileDownloadListener extends FileDownloadSampleListener {

    /**
     * 这个是用来监听下载的情况[没问题]
     * 1.下载队列中
     * 2.开始下载
     * 3.连接中
     * 4.下载中
     * 5.下载错误
     * 6.暂停
     * 7.完成
     */
    private TaskViewHolderImp checkCurrentHolder(final BaseDownloadTask task) {
        final TaskViewHolderImp tag = (TaskViewHolderImp) task.getTag();
        return tag;
    }

    /**
     * 队列中
     */
    @Override
    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        super.pending(task, soFarBytes, totalBytes);
        final TaskViewHolderImp tag = checkCurrentHolder(task);
        if (tag == null) {
            return;
        }
        AppLogUtils.e("taskDownloadListener----"+"pending--------");
        if(tag instanceof DialogListAdapter.ViewHolder){
            AppLogUtils.e("taskDownloadListener----"+"pending--------DialogListAdapter");
            ((DialogListAdapter.ViewHolder)tag).updateDownloading(FileDownloadStatus.pending, soFarBytes, totalBytes);
        }
        if(tag instanceof CacheDownloadingAdapter.MyViewHolder){
            AppLogUtils.e("taskDownloadListener----"+"pending--------CacheDownloadingAdapter");
            ((CacheDownloadingAdapter.MyViewHolder)tag).updateDownloading(FileDownloadStatus.pending, soFarBytes, totalBytes);
        }
    }

    /**
     * 状态: 开始下载
     */
    @Override
    protected void started(BaseDownloadTask task) {
        super.started(task);
        final TaskViewHolderImp tag = checkCurrentHolder(task);
        if (tag == null) {
            return;
        }
        AppLogUtils.e("taskDownloadListener----"+"started--------");
    }

    /**
     * 状态: 连接中
     */
    @Override
    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
        super.connected(task, etag, isContinue, soFarBytes, totalBytes);
        final TaskViewHolderImp tag = checkCurrentHolder(task);
        if (tag == null) {
            return;
        }
        AppLogUtils.e("taskDownloadListener----"+"connected--------");
        if(tag instanceof DialogListAdapter.ViewHolder){
            AppLogUtils.e("taskDownloadListener----"+"connected--------DialogListAdapter");
            ((DialogListAdapter.ViewHolder)tag).updateDownloading(FileDownloadStatus.connected, soFarBytes, totalBytes);
        }
        if(tag instanceof CacheDownloadingAdapter.MyViewHolder){
            AppLogUtils.e("taskDownloadListener----"+"connected--------CacheDownloadingAdapter");
            ((CacheDownloadingAdapter.MyViewHolder)tag).updateDownloading(FileDownloadStatus.connected, soFarBytes, totalBytes);

        }
    }

    /**
     * 状态: 下载中
     */
    @Override
    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        super.progress(task, soFarBytes, totalBytes);
        final TaskViewHolderImp tag = checkCurrentHolder(task);
        if (tag == null) {
            return;
        }
        AppLogUtils.e("taskDownloadListener----"+"progress--------");
        if(tag instanceof DialogListAdapter.ViewHolder){
            AppLogUtils.e("taskDownloadListener----"+"progress--------DialogListAdapter");
            ((DialogListAdapter.ViewHolder)tag).updateDownloading(FileDownloadStatus.progress, soFarBytes, totalBytes);
        }
        if(tag instanceof CacheDownloadingAdapter.MyViewHolder){
            AppLogUtils.e("taskDownloadListener----"+"progress--------CacheDownloadingAdapter");
            ((CacheDownloadingAdapter.MyViewHolder)tag).updateDownloading(FileDownloadStatus.progress, soFarBytes, totalBytes);
        }
    }


    /**
     * 状态: 错误
     */
    @Override
    protected void error(BaseDownloadTask task, Throwable e) {
        super.error(task, e);
        final TaskViewHolderImp tag = checkCurrentHolder(task);
        if (tag == null) {
            return;
        }
        AppLogUtils.e("taskDownloadListener----"+"error--------");
        if(tag instanceof DialogListAdapter.ViewHolder){
            AppLogUtils.e("taskDownloadListener----"+"error--------DialogListAdapter");
            ((DialogListAdapter.ViewHolder)tag).updateNotDownloaded(FileDownloadStatus.error, task.getLargeFileSoFarBytes()
                    , task.getLargeFileTotalBytes());
        }
        if(tag instanceof CacheDownloadingAdapter.MyViewHolder){
            AppLogUtils.e("taskDownloadListener----"+"error--------CacheDownloadingAdapter");
            ((CacheDownloadingAdapter.MyViewHolder)tag).updateNotDownloaded(FileDownloadStatus.error, task.getLargeFileSoFarBytes()
                    , task.getLargeFileTotalBytes());
        }
        TasksManager.getImpl().removeTaskForViewHolder(task.getId());
    }

    /**
     * 状态: 暂停
     */
    @Override
    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        super.paused(task, soFarBytes, totalBytes);
        final TaskViewHolderImp tag = checkCurrentHolder(task);
        if (tag == null) {
            return;
        }
        AppLogUtils.e("taskDownloadListener----"+"paused--------");
        if(tag instanceof DialogListAdapter.ViewHolder){
            AppLogUtils.e("taskDownloadListener----"+"paused--------DialogListAdapter");
            ((DialogListAdapter.ViewHolder)tag).updateNotDownloaded(FileDownloadStatus.paused, soFarBytes, totalBytes);
        }
        if(tag instanceof CacheDownloadingAdapter.MyViewHolder){
            AppLogUtils.e("taskDownloadListener----"+"paused--------CacheDownloadingAdapter");
            ((CacheDownloadingAdapter.MyViewHolder)tag).updateNotDownloaded(FileDownloadStatus.paused, soFarBytes, totalBytes);

        }
        TasksManager.getImpl().removeTaskForViewHolder(task.getId());
    }

    /**
     * 状态: 完成
     */
    @Override
    protected void completed(BaseDownloadTask task) {
        super.completed(task);
        final TaskViewHolderImp tag = checkCurrentHolder(task);
        if (tag == null) {
            return;
        }
        AppLogUtils.e("taskDownloadListener----"+"completed--------");
        if(tag instanceof DialogListAdapter.ViewHolder){
            AppLogUtils.e("taskDownloadListener----"+"completed--------DialogListAdapter");
            ((DialogListAdapter.ViewHolder)tag).updateDownloaded();
        }
        if(tag instanceof CacheDownloadingAdapter.MyViewHolder){
            AppLogUtils.e("taskDownloadListener----"+"completed--------CacheDownloadingAdapter");
            ((CacheDownloadingAdapter.MyViewHolder)tag).updateDownloaded();
        }
        TasksManager.getImpl().removeTaskForViewHolder(task.getId());
    }

}

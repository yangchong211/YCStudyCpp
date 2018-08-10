package cn.ycbjie.ycaudioplayer.ui.me.view.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.util.FileDownloadUtils;


import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.db.dl.TaskFileDownloadListener;
import cn.ycbjie.ycaudioplayer.db.dl.TaskViewHolderImp;
import cn.ycbjie.ycaudioplayer.db.dl.TasksManager;
import cn.ycbjie.ycaudioplayer.db.dl.TasksManagerModel;
import cn.ycbjie.ycaudioplayer.inter.listener.OnCompleteListener;
import cn.ycbjie.ycaudioplayer.inter.listener.OnListItemClickListener;
import cn.ycbjie.ycaudioplayer.inter.listener.OnMoreClickListener;
import cn.ycbjie.ycaudioplayer.utils.binding.Bind;
import cn.ycbjie.ycaudioplayer.utils.logger.AppLogUtils;

/**
 * <pre>
 *     @author yangchong
 *     blog  : www.pedaily.cn
 *     time  : 2018/03/26
 *     desc  : 正在下载适配器
 *     revise:
 * </pre>
 */
public class CacheDownloadingAdapter extends RecyclerView.Adapter<CacheDownloadingAdapter.MyViewHolder> {

    private List<TasksManagerModel> list;
    private Context mContext;

    private OnListItemClickListener listItemClickListener;
    private OnMoreClickListener moreClickListener;
    private OnCompleteListener completeListener;
    private MyViewHolder holder;

    public void setOnListItemClickListener(OnListItemClickListener listener){
        this.listItemClickListener = listener;
    }
    public void setOnMoreClickListener(OnMoreClickListener listener){
        this.moreClickListener = listener;
    }
    public void setOnCompleteListener(OnCompleteListener listener){
        this.completeListener = listener;
    }

    public CacheDownloadingAdapter(Context context) {
        this.mContext = context;
        list = new ArrayList<>();
        WeakReference weakReference = new WeakReference<>(this);
        TasksManager.getImpl().onCreate(weakReference);
    }


    public void postNotifyDataChanged() {
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
                AppLogUtils.e("postNotifyDataChanged----"+"刷新数据");
            }
        });
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_downloading_cache, parent, false);
        holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if(list!=null && list.size()>0){
            TasksManagerModel model = list.get(position);
            int id = model.getId();
            String path = model.getPath();
            holder.update(id,position);

            TasksManager.getImpl().updateViewHolder(holder.id, holder);

            holder.ivDownload.setOnClickListener(listener);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listItemClickListener!=null){
                        listItemClickListener.onItemClick(v,position);
                    }
                }
            });
            holder.ivMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            if (TasksManager.getImpl().isReady()) {
                final int status = TasksManager.getImpl().getStatus(id, path);
                if (status == FileDownloadStatus.pending || status == FileDownloadStatus.started ||
                        status == FileDownloadStatus.connected) {
                    AppLogUtils.e("onBindViewHolder----"+"updateDownloading--------");
                    // start task, but file not created yet
                    holder.updateDownloading(status, TasksManager.getImpl().getSoFar(id)
                            , TasksManager.getImpl().getTotal(id));
                } else if (!new File(path).exists() && !new File(FileDownloadUtils.getTempPath(path)).exists()) {
                    // not exist file
                    AppLogUtils.e("onBindViewHolder----"+"updateNotDownloaded--------");
                    holder.updateNotDownloaded(status, 0, 0);
                } else if (TasksManager.getImpl().isDownloaded(status)) {
                    // already downloaded and exist
                    AppLogUtils.e("onBindViewHolder----"+"updateDownloaded--------");
                    holder.updateDownloaded();
                } else if (status == FileDownloadStatus.progress) {
                    AppLogUtils.e("onBindViewHolder----"+"updateDownloading--------");
                    // downloading
                    holder.updateDownloading(status, TasksManager.getImpl().getSoFar(id)
                            , TasksManager.getImpl().getTotal(id));
                } else {
                    // not start
                    AppLogUtils.e("onBindViewHolder----"+"updateNotDownloaded--------");
                    holder.updateNotDownloaded(status, TasksManager.getImpl().getSoFar(id)
                            , TasksManager.getImpl().getTotal(id));
                }
            } else {
                //状态: 加载中...
                AppLogUtils.e("onBindViewHolder----"+"状态: 加载中--------");
            }
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements TaskViewHolderImp {

        @Bind(R.id.iv_download)
        ImageView ivDownload;
        @Bind(R.id.tv_state)
        TextView tvState;
        @Bind(R.id.ll_download)
        LinearLayout llDownload;
        @Bind(R.id.pb)
        ProgressBar pb;
        @Bind(R.id.iv_more)
        ImageView ivMore;
        @Bind(R.id.iv_check)
        ImageView ivCheck;

        private int position;
        public int id;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void update(int id, int position) {
            this.id = id;
            this.position = position;
        }

        @Override
        public void updateDownloaded() {
            AppLogUtils.e("ViewHolder----"+"updateDownloaded--------");
            pb.setMax(1);
            pb.setProgress(1);
            tvState.setText("下载完成");
            ivDownload.setBackgroundResource(R.drawable.ic_note_btn_play_white);
            ivDownload.setTag(R.drawable.ic_note_btn_play_white);

            TasksManagerModel model = list.get(holder.position);
            TasksManager.getImpl().deleteTasksManagerModel(model.getUrl());
            if (!TasksManager.getImpl().isDownloadedFile(model.getUrl())) {
                TasksManager.getImpl().addDownloadedDB(model);
                //下载完成的item隐藏，后期思考如何刷新正在下载recyclerView中的数据
                //notifyItemChanged(position);
                //postNotifyDataChanged();          //这种直接导致崩溃
            }
            if(completeListener!=null){
                completeListener.downloadCompleted(model , holder.position);
            }
        }

        @Override
        public void updateNotDownloaded(int status, long sofar, long total) {
            AppLogUtils.e("ViewHolder----"+"updateNotDownloaded--------"+status+"---"+sofar+"---"+total);
            if (sofar > 0 && total > 0) {
                final float percent = sofar / (float) total;
                pb.setProgress((int) (percent * 100));
            } else {
                pb.setProgress(0);
            }
            switch (status) {
                case FileDownloadStatus.error:
                    ivDownload.setBackgroundResource(R.drawable.ic_note_btn_play_white);
                    ivDownload.setTag(R.drawable.ic_note_btn_play_white);
                    tvState.setText("错误");
                    break;
                case FileDownloadStatus.paused:
                    ivDownload.setBackgroundResource(R.drawable.ic_note_btn_play_white);
                    ivDownload.setTag(R.drawable.ic_note_btn_play_white);
                    tvState.setText("暂停");
                    break;
                default:
                    ivDownload.setBackgroundResource(R.drawable.ic_note_btn_play_white);
                    ivDownload.setTag(R.drawable.ic_note_btn_play_white);
                    break;
            }
        }

        @Override
        public void updateDownloading(int status, long sofar, long total) {
            AppLogUtils.e("ViewHolder----"+"updateDownloading--------");
            final float percent = sofar / (float) total;
            pb.setProgress((int) (percent * 100));
            switch (status) {
                //排队中
                case FileDownloadStatus.pending:
                    ivDownload.setBackgroundResource(R.drawable.ic_note_btn_play_white);
                    ivDownload.setTag(R.drawable.ic_note_btn_play_white);
                    AppLogUtils.e("ViewHolder----"+"排队中--------");
                    tvState.setText("排队中");
                    break;
                //开始下载
                case FileDownloadStatus.started:
                    ivDownload.setBackgroundResource(R.drawable.ic_note_btn_play_white);
                    ivDownload.setTag(R.drawable.ic_note_btn_play_white);
                    AppLogUtils.e("ViewHolder----"+"开始下载--------");
                    tvState.setText("开始下载");
                    break;
                //链接中
                case FileDownloadStatus.connected:
                    ivDownload.setBackgroundResource(R.drawable.ic_note_btn_play_white);
                    ivDownload.setTag(R.drawable.ic_note_btn_play_white);
                    AppLogUtils.e("ViewHolder----"+"链接中--------");
                    tvState.setText("链接中");
                    break;
                //下载中
                case FileDownloadStatus.progress:
                    ivDownload.setBackgroundResource(R.drawable.ic_note_btn_pause_white);
                    ivDownload.setTag(R.drawable.ic_note_btn_pause_white);
                    AppLogUtils.e("ViewHolder----"+"下载中--------");
                    tvState.setText("下载中");
                    break;
                //默认
                default:
                    ivDownload.setBackgroundResource(R.drawable.ic_note_btn_pause_white);
                    ivDownload.setTag(R.drawable.ic_note_btn_pause_white);
                    AppLogUtils.e("ViewHolder----"+"默认--------");
                    tvState.setText("默认");
                    break;
            }
        }
    }

    public View.OnClickListener listener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if (v.getTag() == null) {
                return;
            }
            AppLogUtils.e("listener----"+"--------");
            int imgResId = (int) holder.ivDownload.getTag();
            TasksManagerModel model = list.get(holder.position);
            String path = model.getPath();
            String url = model.getUrl();
            AppLogUtils.e("listener----"+url+ "--------");
            switch (imgResId) {
                case R.drawable.ic_note_btn_play_white:
                    AppLogUtils.e("listener----"+"------开始下载--");
                    // 开始下载
                    final BaseDownloadTask task = FileDownloader.getImpl().create(url)
                            .setPath(path)
                            .setCallbackProgressTimes(500)
                            .setListener(new TaskFileDownloadListener());
                    TasksManager.getImpl().addTaskForViewHolder(task);
                    TasksManager.getImpl().updateViewHolder(holder.id, holder);
                    task.start();
                    break;
                case R.drawable.ic_note_btn_pause_white:
                    AppLogUtils.e("listener----"+"------暂停下载--");
                    // 暂停下载
                    FileDownloader.getImpl().pause(holder.id);
                    holder.ivDownload.setBackgroundResource(R.drawable.ic_note_btn_play_white);
                    holder.ivDownload.setTag(R.drawable.ic_note_btn_play_white);
                    break;
                default:
                    break;
            }
        }
    };


    public void addAllData(List<TasksManagerModel> data) {
        if(list==null){
            return;
        }
        if (list.size() > 0) {
            list.clear();
        }
        list.addAll(data);
        notifyDataSetChanged();
    }


}

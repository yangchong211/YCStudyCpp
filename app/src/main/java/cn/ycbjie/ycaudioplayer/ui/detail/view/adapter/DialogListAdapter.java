package cn.ycbjie.ycaudioplayer.ui.detail.view.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.ns.yc.ycprogresslib.CircleProgressbar;


import java.io.File;
import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.db.dl.TaskViewHolderImp;
import cn.ycbjie.ycaudioplayer.db.dl.TasksManager;
import cn.ycbjie.ycaudioplayer.db.dl.TasksUtils;
import cn.ycbjie.ycaudioplayer.ui.detail.model.DialogListBean;

/**
 * <pre>
 *     @author yangchong
 *     blog  : www.pedaily.cn
 *     time  : 2017/03/22
 *     desc  : 适配器adapter
 *     revise:
 * </pre>
 */
public class DialogListAdapter extends RecyclerView.Adapter<DialogListAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<DialogListBean> mList;
    private Context mContext;
    private OnItemClickListener onItemClickListener;
    private ViewHolder holder;

    private final String STATE_START = "start";
    private final String STATE_PAUSE = "pause";
    private final String STATE_DETAIL = "detail";

    public DialogListAdapter(Context mContext, List<DialogListBean> mList) {
        this.mList = mList;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        WeakReference weakReference = new WeakReference<>(this);
        TasksManager.getImpl().onCreate(weakReference);
    }


    public void postNotifyDataChanged() {
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
                LogUtils.e("postNotifyDataChanged----"+"刷新数据");
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        holder = new ViewHolder(inflater.inflate(R.layout.item_download_video, parent, false));
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        DialogListBean model = mList.get(position);
        final String path = TasksManager.getImpl().createPath(model.getVideo());
        int id = TasksManager.getImpl().getId(model.getVideo());
        holder.update(id, position);

        holder.llDownload.setTag(holder);
        TasksManager.getImpl().updateViewHolder(holder.id, holder);
        LogUtils.e("onBindHolder----"+path+ "、、、、、"+id);

        holder.tvTitle.setText(mList.get(position).getTitle());
        holder.tvTime.setText("时长98:00：12");
        setCircleProgressbar();

        holder.tvState.setTag(STATE_START);
        holder.llDownload.setOnClickListener(listener);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });


        if (TasksManager.getImpl().isReady()) {
            final int status = TasksManager.getImpl().getStatus(id, path);
            if (status == FileDownloadStatus.pending || status == FileDownloadStatus.started ||
                    status == FileDownloadStatus.connected) {
                LogUtils.e("onBindHolder----"+"updateDownloading--------");
                // start task, but file not created yet
                holder.updateDownloading(status, TasksManager.getImpl().getSoFar(id)
                        , TasksManager.getImpl().getTotal(id));
            } else if (!new File(path).exists() && !new File(FileDownloadUtils.getTempPath(path)).exists()) {
                // not exist file
                LogUtils.e("onBindHolder----"+"updateNotDownloaded--------");
                holder.updateNotDownloaded(status, 0, 0);
            } else if (TasksManager.getImpl().isDownloaded(status)) {
                // already downloaded and exist
                LogUtils.e("onBindHolder----"+"updateDownloaded--------");
                holder.updateDownloaded();
            } else if (status == FileDownloadStatus.progress) {
                LogUtils.e("onBindHolder----"+"updateDownloading--------");
                // downloading
                holder.updateDownloading(status, TasksManager.getImpl().getSoFar(id)
                        , TasksManager.getImpl().getTotal(id));
            } else {
                // not start
                LogUtils.e("onBindHolder----"+"updateNotDownloaded--------");
                holder.updateNotDownloaded(status, TasksManager.getImpl().getSoFar(id)
                        , TasksManager.getImpl().getTotal(id));
            }
        } else {
            //加载中...
            LogUtils.e("onBindHolder----"+"加载中--------");
        }
    }

    private void setCircleProgressbar() {
        //设置类型
        holder.circlePb.setProgressType(CircleProgressbar.ProgressType.COUNT);
        //设置圆形的填充颜色
        holder.circlePb.setInCircleColor(mContext.getResources().getColor(R.color.colorTransparent));
        //设置外部轮廓的颜色
        holder.circlePb.setOutLineColor(mContext.getResources().getColor(R.color.gray3));
        //设置进度监听
        holder.circlePb.setCountdownProgressListener(1, progressListener);
        //设置外部轮廓的颜色
        holder.circlePb.setOutLineWidth(1);
        //设置进度条线的宽度
        holder.circlePb.setProgressLineWidth(2);
        //设置进度
        holder.circlePb.setProgress(0);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements TaskViewHolderImp {

        private int position;
        public int id;

        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.iv_download)
        ImageView ivDownload;
        @Bind(R.id.circle_pb)
        CircleProgressbar circlePb;
        @Bind(R.id.tv_state)
        TextView tvState;
        @Bind(R.id.ll_download)
        LinearLayout llDownload;


        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void update(int id, int position) {
            this.id = id;
            this.position = position;
        }

        @Override
        public void updateDownloaded() {
            LogUtils.e("ViewHolder----"+"updateDownloaded--------");
            //当下载完成后，隐藏圆环控件，显示删除图标
            circlePb.setVisibility(View.GONE);
            circlePb.setProgress(1);
            ivDownload.setBackgroundResource(R.drawable.icon_cache_delete);
            tvState.setTag(STATE_DETAIL);
            tvState.setText("下载完成");

            DialogListBean model = mList.get(holder.position);
            TasksManager.getImpl().deleteTasksManagerModel(model.getVideo());
            if (!TasksManager.getImpl().isDownloadedFile(model.getVideo())) {
                TasksManager.getImpl().addDownloadedDb(model);
            }
        }

        @SuppressLint("DefaultLocale")
        @Override
        public void updateDownloading(int status, long sofar, long total) {
            LogUtils.e("ViewHolder----"+"updateDownloading--------");
            final float percent = sofar / (float) total;
            circlePb.setProgress((int) (percent * 100));
            switch (status) {
                //排队中
                case FileDownloadStatus.pending:
                    ivDownload.setBackgroundResource(R.drawable.icon_cache_download);
                    LogUtils.e("ViewHolder----"+"排队中--------");
                    tvState.setTag(STATE_START);
                    tvState.setText("排队中");
                    break;
                //开始下载
                case FileDownloadStatus.started:
                    ivDownload.setBackgroundResource(R.drawable.icon_cache_download);
                    LogUtils.e("ViewHolder----"+"开始下载--------");
                    tvState.setTag(STATE_START);
                    tvState.setText("开始下载");
                    break;
                //链接中
                case FileDownloadStatus.connected:
                    ivDownload.setBackgroundResource(R.drawable.icon_cache_download);
                    LogUtils.e("ViewHolder----"+"链接中--------");
                    tvState.setTag(STATE_START);
                    tvState.setText("链接中");
                    break;
                //下载中
                case FileDownloadStatus.progress:
                    ivDownload.setBackgroundResource(R.drawable.icon_cache_play);
                    LogUtils.e("ViewHolder----"+"下载中--------");
                    tvState.setTag(STATE_PAUSE);
                    tvState.setText("下载中");
                    break;
                //默认
                default:
                    ivDownload.setBackgroundResource(R.drawable.icon_cache_play);
                    LogUtils.e("ViewHolder----"+"默认--------");
                    tvState.setTag(STATE_PAUSE);
                    tvState.setText("默认");
                    break;
            }
        }

        @Override
        public void updateNotDownloaded(int status, long sofar, long total) {
            LogUtils.e("ViewHolder----"+"updateNotDownloaded--------"+status+"---"+sofar+"---"+total);
            if (sofar > 0 && total > 0) {
                final float percent = sofar / (float) total;
                circlePb.setProgress((int) (percent * 100));
            } else {
                circlePb.setProgress(0);
            }
            switch (status) {
                case FileDownloadStatus.error:
                    ivDownload.setBackgroundResource(R.drawable.icon_cache_download);
                    tvState.setText("错误");
                    break;
                case FileDownloadStatus.paused:
                    ivDownload.setBackgroundResource(R.drawable.icon_cache_download);
                    tvState.setText("暂停");
                    break;
                default:
                    ivDownload.setBackgroundResource(R.drawable.icon_cache_download);
                    break;
            }
            tvState.setTag(STATE_START);
        }
    }

    private CircleProgressbar.OnCountdownProgressListener progressListener =
            new CircleProgressbar.OnCountdownProgressListener() {
                @Override
                public void onProgress(int what, int progress) {
                    if (what == 1) {
                        holder.circlePb.setText("0");
                    }
                }
            };


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int id);
    }

    /*--------------------------------------下载----------------------------------------------*/

    public View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LogUtils.e("listener----"+"事件点击呢--------");
            if (v.getTag() == null) {
                return;
            }
            Object tag = v.getTag();
            LogUtils.e("listener----"+"====" +tag.toString());
            ViewHolder holder = (ViewHolder) v.getTag();
            String state = (String) holder.tvState.getTag();
            DialogListBean model = mList.get(holder.position);
            LogUtils.e("listener----"+"状态" +state);


            switch (state) {
                //下载
                case STATE_START:
                    String path = TasksManager.getImpl().createPath(model.getVideo());
                    TasksUtils.start(model.getVideo(),path);
                    TasksManager.getImpl().updateViewHolder(holder.id, holder);
                    LogUtils.e("listener----" + "STATE_START--------");
                    holder.tvState.setText("开始下载");
                    break;
                //暂停
                case STATE_PAUSE:
                    FileDownloader.getImpl().pause(holder.id);
                    holder.ivDownload.setBackgroundResource(R.drawable.icon_cache_download);
                    holder.tvState.setTag(STATE_START);
                    LogUtils.e("listener----" + "STATE_PAUSE--------");
                    holder.tvState.setText("暂停下载");
                    break;
                //删除
                case STATE_DETAIL:
                    TasksUtils.delete(model.getVideo());
                    holder.updateNotDownloaded(FileDownloadStatus.INVALID_STATUS, 0, 0);
                    holder.ivDownload.setBackgroundResource(R.drawable.icon_cache_download);
                    holder.tvState.setTag(STATE_START);
                    holder.circlePb.setVisibility(View.VISIBLE);
                    holder.tvState.setText("已经删除");
                    LogUtils.e("listener----" + "STATE_DETAIL--------");
                    break;
                default:
                    break;
            }
        }
    };

}

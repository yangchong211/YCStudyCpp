package cn.ycbjie.ycaudioplayer.ui.me.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.db.dl.TasksManagerModel;
import cn.ycbjie.ycaudioplayer.inter.listener.OnListItemClickListener;
import cn.ycbjie.ycaudioplayer.inter.listener.OnMoreClickListener;

/**
 * <pre>
 *     @author yangchong
 *     blog  : www.pedaily.cn
 *     time  : 2018/03/26
 *     desc  : 下载完成适配器
 *     revise:
 * </pre>
 */

public class CacheDownloadedAdapter extends RecyclerView.Adapter<CacheDownloadedAdapter.MyViewHolder> {


    private List<TasksManagerModel> list;
    private Context context;
    private OnListItemClickListener listItemClickListener;
    private OnMoreClickListener moreClickListener;

    public void setOnListItemClickListener(OnListItemClickListener listener){
        this.listItemClickListener = listener;
    }
    public void setOnMoreClickListener(OnMoreClickListener listener){
        this.moreClickListener = listener;
    }

    public CacheDownloadedAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_downloaded_cache, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.iv_more)
        ImageView ivMore;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }
    }

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

    /**
     * 插入一条数据
     * @param model         model
     */
    public void insertData(TasksManagerModel model) {
        if(list==null){
            return;
        }
        list.add(0,model);
    }


}

package cn.ycbjie.ycaudioplayer.ui.local.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.base.BaseAppHelper;
import cn.ycbjie.ycaudioplayer.inter.OnListItemClickListener;
import cn.ycbjie.ycaudioplayer.service.PlayService;
import cn.ycbjie.ycaudioplayer.ui.local.model.LocalMusic;

/**
 * Created by yc on 2018/1/26.
 */

public class DialogMusicListAdapter extends RecyclerView.Adapter<DialogMusicListAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private Context context;
    private List<LocalMusic> localMusics;
    /**
     * 正在播放音乐的索引位置
     */
    private int mPlayingPosition;

    public DialogMusicListAdapter(Context context, List<LocalMusic> musicList) {
        this.localMusics = musicList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_dialog_list_view, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if(localMusics!=null && localMusics.size()>0){
            holder.tvTitle.setText(localMusics.get(position).getTitle());
            holder.tvAuthor.setText(localMusics.get(position).getArtist());
            if (position == mPlayingPosition) {
                holder.vPlaying.setVisibility(View.VISIBLE);
            } else {
                holder.vPlaying.setVisibility(View.GONE);
            }
        }
        //条目点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(view, position);
                }
            }
        });
        holder.ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return localMusics.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.v_playing)
        View vPlaying;
        @Bind(R.id.iv_del)
        ImageView ivDel;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_author)
        TextView tvAuthor;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnListItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnListItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 当播放位置发生了变化，那么就可以更新播放位置视图
     * @param playService       PlayService
     */
    public void updatePlayingPosition(PlayService playService) {
        if (playService.getPlayingMusic() != null &&
                playService.getPlayingMusic().getType() == LocalMusic.Type.LOCAL) {
            mPlayingPosition = playService.getPlayingPosition();
        } else {
            mPlayingPosition = -1;
        }
    }


}

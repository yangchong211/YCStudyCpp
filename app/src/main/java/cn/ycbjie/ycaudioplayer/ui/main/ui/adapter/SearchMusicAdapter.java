package cn.ycbjie.ycaudioplayer.ui.main.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.inter.listener.OnMoreClickListener;
import cn.ycbjie.ycaudioplayer.model.bean.SearchMusic;
import cn.ycbjie.ycaudioplayer.utils.app.ImageUtil;

public class SearchMusicAdapter extends RecyclerView.Adapter<SearchMusicAdapter.VideoViewHolder> {


    private Context mContext;
    private List<SearchMusic.Song> music;

    public SearchMusicAdapter(Context context, List<SearchMusic.Song> music) {
        mContext = context;
        this.music = music;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_search_music, parent, false);
        return new VideoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        ImageUtil.loadImgByPicasso(mContext,R.drawable.image_default,holder.ivCover);
        holder.tvTitle.setText(music.get(position).getSongname());
        holder.tvArtist.setText(music.get(position).getArtistname());
        holder.ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onMoreClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return music == null ? 0 : music.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_cover)
        ImageView ivCover;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_artist)
        TextView tvArtist;
        @BindView(R.id.iv_more)
        ImageView ivMore;

        VideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    private OnMoreClickListener mListener;
    public void setOnMoreClickListener(OnMoreClickListener listener) {
        mListener = listener;
    }

}

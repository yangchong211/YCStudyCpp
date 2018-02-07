package cn.ycbjie.ycaudioplayer.ui.main.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.model.bean.SearchMusic;

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
    public void onBindViewHolder(VideoViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return music==null ? 0 : music.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {


        VideoViewHolder(View itemView) {
            super(itemView);

        }

    }


}

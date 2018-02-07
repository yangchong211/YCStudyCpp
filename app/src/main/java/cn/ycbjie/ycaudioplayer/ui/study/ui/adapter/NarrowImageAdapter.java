package cn.ycbjie.ycaudioplayer.ui.study.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.viewHolder.BaseViewHolder;

import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.ui.study.model.VideoPlayerFavorite;


public class NarrowImageAdapter extends RecyclerArrayAdapter<VideoPlayerFavorite> {


    public NarrowImageAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new NarrowImageViewHolder(parent);
    }

    private static class NarrowImageViewHolder extends BaseViewHolder<VideoPlayerFavorite> {

        NarrowImageViewHolder(ViewGroup parent) {
            super(parent, R.layout.view_audio_player_favorite);
        }

        @Override
        public void setData(VideoPlayerFavorite data) {

        }
    }
}

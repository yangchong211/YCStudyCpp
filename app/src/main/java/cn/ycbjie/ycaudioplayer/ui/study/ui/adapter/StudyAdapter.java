package cn.ycbjie.ycaudioplayer.ui.study.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.viewHolder.BaseViewHolder;

import cn.ycbjie.ycaudioplayer.R;


public class StudyAdapter extends RecyclerArrayAdapter<String> {

    public StudyAdapter(Activity activity) {
        super(activity);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoPlayerViewHolder(parent);
    }


    private class VideoPlayerViewHolder extends BaseViewHolder<String> {

        ImageView iv_movie_photo;
        TextView tv_movie_title , tv_movie_directors ,tv_movie_casts,tv_movie_genres ,tv_movie_rating_rate;
        View view_color;

        VideoPlayerViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_study_news);
        }

        @Override
        public void setData(String data) {
            super.setData(data);

        }
    }
}

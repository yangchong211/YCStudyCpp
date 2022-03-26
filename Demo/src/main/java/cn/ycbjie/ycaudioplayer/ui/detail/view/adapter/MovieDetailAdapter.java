package cn.ycbjie.ycaudioplayer.ui.detail.view.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.ViewGroup;

import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.viewHolder.BaseViewHolder;

import butterknife.ButterKnife;
import cn.ycbjie.ycaudioplayer.R;


public class MovieDetailAdapter extends RecyclerArrayAdapter<String> {

    public MovieDetailAdapter(Activity activity) {
        super(activity);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieViewHolder(parent);
    }

    public class MovieViewHolder extends BaseViewHolder<String> {


        MovieViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_detail_audio);
            ButterKnife.bind(this, itemView);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void setData(String data) {
            super.setData(data);

        }


    }


}

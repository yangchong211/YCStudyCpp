package cn.ycbjie.ycaudioplayer.ui.detail.view.adapter;

import android.app.Activity;
import android.view.ViewGroup;

import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.viewHolder.BaseViewHolder;

import cn.ycbjie.ycaudioplayer.R;


public class DetailVideoAdapter extends RecyclerArrayAdapter<String> {

    public DetailVideoAdapter(Activity activity) {
        super(activity);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoPlayerViewHolder(parent);
    }


    private class VideoPlayerViewHolder extends BaseViewHolder<String> {


        VideoPlayerViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_detail_audio);
        }

        @Override
        public void setData(String data) {
            super.setData(data);
        }
    }
}

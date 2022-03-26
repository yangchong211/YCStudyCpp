package cn.ycbjie.ycaudioplayer.ui.home.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
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

        TextView tv_title;

        VideoPlayerViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_study_news);
            tv_title = getView(R.id.tv_title);
        }

        @Override
        public void setData(String data) {
            super.setData(data);
            tv_title.setText(data);
            if (mItemLongClickListener!=null){
                tv_title.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        return mItemLongClickListener.onLongClick(tv_title,getAdapterPosition());
                    }
                });
            }
        }
    }

    public void setOnLongClickListener(OnLongClickListener listener){
        this.mItemLongClickListener = listener;
    }
    private OnLongClickListener mItemLongClickListener;

    public interface OnLongClickListener {
        boolean onLongClick(View view , int position);
    }

}

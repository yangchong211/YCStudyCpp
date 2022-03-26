package cn.ycbjie.ycaudioplayer.ui.detail.view.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.viewHolder.BaseViewHolder;

import cn.ycbjie.ycaudioplayer.R;


public class DetailAudioAdapter extends RecyclerArrayAdapter<String> {

    public DetailAudioAdapter(Activity activity) {
        super(activity);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoPlayerViewHolder(parent);
    }


    private class VideoPlayerViewHolder extends BaseViewHolder<String> {


        private ImageView mIvAudioImage;
        private TextView mTvAudioTitle;


        VideoPlayerViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_detail_audio);

            mIvAudioImage = getView(R.id.iv_audio_image);
            mTvAudioTitle = getView(R.id.tv_audio_title);


            //绑定子view的点击事件
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemChildClickListener!=null){
                        onItemChildClickListener.OnItemChildClickListener(v,getDataPosition());
                    }
                }
            };
            mTvAudioTitle.setOnClickListener(listener);
        }

        @Override
        public void setData(String data) {
            super.setData(data);
        }
    }

    private OnItemChildClickListener onItemChildClickListener;
    public interface OnItemChildClickListener{
        void OnItemChildClickListener(View view, int position);
    }

    public void setOnItemChildClickListener(OnItemChildClickListener listener){
        this.onItemChildClickListener = listener;
    }


}

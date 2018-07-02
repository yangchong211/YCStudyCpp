package cn.ycbjie.ycaudioplayer.ui.study.ui.adapter;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.viewHolder.BaseViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ycbjie.ycaudioplayer.R;


public class InnovationAdapter extends RecyclerArrayAdapter<String> {

    public InnovationAdapter(Activity activity) {
        super(activity);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoPlayerViewHolder(parent);
    }


    public class VideoPlayerViewHolder extends BaseViewHolder<String> {

        @Bind(R.id.iv_image)
        ImageView ivImage;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_count)
        TextView tvCount;
        @Bind(R.id.tv_author)
        TextView tvAuthor;
        @Bind(R.id.tv_school)
        TextView tvSchool;
        @Bind(R.id.tv_content)
        TextView tvContent;
        @Bind(R.id.tv_place)
        TextView tvPlace;
        @Bind(R.id.fl_video)
        FrameLayout flVideo;
        @Bind(R.id.fl_audio)
        FrameLayout flAudio;
        @Bind(R.id.fl_innovation)
        FrameLayout flInnovation;

        VideoPlayerViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_innovation_news);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setData(String data) {
            super.setData(data);
            tvTitle.setText("批判性思维第五讲");
        }
    }
}

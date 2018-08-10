package cn.ycbjie.ycaudioplayer.ui.study.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.TimeUtils;

import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.viewHolder.BaseViewHolder;

import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.model.bean.VideoBean;
import cn.ycbjie.ycaudioplayer.utils.app.ImageUtil;


public class LocalVideoAdapter extends RecyclerArrayAdapter<VideoBean> {

    public LocalVideoAdapter(Activity activity) {
        super(activity);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new LocalViewHolder(parent);
    }


    public class LocalViewHolder extends BaseViewHolder<VideoBean> {


        @Bind(R.id.v_playing)
        View vPlaying;
        @Bind(R.id.iv_cover)
        ImageView ivCover;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_artist)
        TextView tvArtist;
        @Bind(R.id.iv_more)
        ImageView ivMore;
        @Bind(R.id.v_divider)
        View vDivider;

        LocalViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_local_video);
            ButterKnife.bind(this, itemView);

            ivMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
        @Override
        public void setData(VideoBean data) {
            super.setData(data);
            Bitmap videoThumbnail = data.getVideoThumbnail();
            if(videoThumbnail!=null){
                Drawable drawable = ImageUtils.bitmap2Drawable(videoThumbnail);
                ivCover.setBackground(drawable);
            }
            tvTitle.setText(data.getTitle());
            String string = TimeUtils.millis2String(data.getDuration(), new SimpleDateFormat("mm:ss"));
            tvArtist.setText("时长:"+string+"   分辨率:"+data.getResolution()+"   大小"+data.getFileSize());
            vDivider.setVisibility(View.VISIBLE);
        }
    }
}

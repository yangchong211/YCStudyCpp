package cn.ycbjie.ycaudioplayer.ui.home.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.viewHolder.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.model.bean.OfficeBean;


public class LocalOfficeAdapter extends RecyclerArrayAdapter<OfficeBean> {

    public LocalOfficeAdapter(Activity activity) {
        super(activity);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new LocalViewHolder(parent);
    }


    public class LocalViewHolder extends BaseViewHolder<OfficeBean> {


        @BindView(R.id.v_playing)
        View vPlaying;
        @BindView(R.id.iv_cover)
        ImageView ivCover;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_artist)
        TextView tvArtist;
        @BindView(R.id.iv_more)
        ImageView ivMore;
        @BindView(R.id.v_divider)
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
        public void setData(OfficeBean data) {
            super.setData(data);

        }
    }
}

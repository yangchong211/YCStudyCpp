package cn.ycbjie.ycaudioplayer.ui.local.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.viewHolder.BaseViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.service.PlayService;
import cn.ycbjie.ycaudioplayer.ui.local.model.LocalMusic;
import cn.ycbjie.ycaudioplayer.util.musicUtils.CoverLoader;
import cn.ycbjie.ycaudioplayer.util.musicUtils.FileMusicUtils;

public class LocalMusicAdapter extends RecyclerArrayAdapter<LocalMusic> {

    /**
     * 正在播放音乐的索引位置
     */
    private int mPlayingPosition;

    public LocalMusicAdapter(Activity activity) {
        super(activity);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    class ViewHolder extends BaseViewHolder<LocalMusic> {

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

        ViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_local_music);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setData(LocalMusic data) {
            super.setData(data);
            if(data!=null){
                Bitmap cover = CoverLoader.getInstance().loadThumbnail(data);
                ivCover.setImageBitmap(cover);
                tvTitle.setText(data.getTitle());
                String artist = FileMusicUtils.getArtistAndAlbum(data.getArtist(), data.getAlbum());
                tvArtist.setText(artist);
                ivMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            mListener.onMoreClick(getAdapterPosition());
                        }
                    }
                });
                vDivider.setVisibility(isShowDivider(getAdapterPosition()) ? View.VISIBLE : View.GONE);
                if (getAdapterPosition() == mPlayingPosition) {
                    vPlaying.setVisibility(View.VISIBLE);
                } else {
                    vPlaying.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    private boolean isShowDivider(int position) {
        return position != getAllData().size() - 1;
    }

    /**
     * 当播放位置发生了变化，那么就可以更新播放位置视图
     * @param playService       PlayService
     */
    public void updatePlayingPosition(PlayService playService) {
        if (playService.getPlayingMusic() != null &&
                playService.getPlayingMusic().getType() == LocalMusic.Type.LOCAL) {
            mPlayingPosition = playService.getPlayingPosition();
        } else {
            mPlayingPosition = -1;
        }
    }


    private OnMoreClickListener mListener;
    public void setOnMoreClickListener(OnMoreClickListener listener) {
        mListener = listener;
    }
    public interface OnMoreClickListener {
        void onMoreClick(int position);
    }

}

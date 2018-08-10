package cn.ycbjie.ycaudioplayer.ui.news.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import org.yczbj.ycrefreshviewlib.viewHolder.BaseViewHolder;

import cn.ycbjie.ycaudioplayer.R;


public class HeaderTitleViewHolder extends BaseViewHolder {


    private final TextView tvTitle;

    public HeaderTitleViewHolder(View itemView) {
        super(itemView);
        tvTitle = (TextView) getView(R.id.tv_title);
    }


    public void bindData(String name, Context mContext) {
        if(name!=null && name.length()>0){
            tvTitle.setText(name);
        }
    }
}

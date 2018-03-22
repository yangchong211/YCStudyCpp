package cn.ycbjie.ycaudioplayer.ui.practise.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yc.cn.ycbaseadapterlib.BaseViewHolder;

import java.util.List;

import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.ui.practise.model.PractiseAfterBean;

/**
 * Created by yc on 2018/3/20.
 */

public class HeaderTitleViewHolder extends BaseViewHolder {


    private final TextView tvTitle;

    public HeaderTitleViewHolder(View itemView) {
        super(itemView);
        tvTitle = getView(R.id.tv_title);
    }


    public void bindData(String name, Context mContext) {
        if(name!=null && name.length()>0){
            tvTitle.setText(name);
        }
    }
}

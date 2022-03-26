package cn.ycbjie.ycaudioplayer.ui.news.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;

import org.yczbj.ycrefreshviewlib.viewHolder.BaseViewHolder;

import java.util.List;

import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.kotlin.view.activity.AndroidActivity;


public class ButtonViewHolder extends BaseViewHolder {


    private final TextView tv_home_first;
    private final TextView tv_home_second;
    private final TextView tv_home_third;
    private final TextView tv_home_four;
    private final TextView tv_home_five;

    public ButtonViewHolder(View itemView) {
        super(itemView);
        tv_home_first = (TextView) getView(R.id.tv_home_first);
        tv_home_second = (TextView) getView(R.id.tv_home_second);
        tv_home_third = (TextView) getView(R.id.tv_home_third);
        tv_home_four = (TextView) getView(R.id.tv_home_four);
        tv_home_five = (TextView) getView(R.id.tv_home_five);
    }


    public void bindData(List<String> mButtonList, Context mContext) {
        if(mButtonList!=null && mButtonList.size()>0){
            tv_home_first.setText(mButtonList.get(0));
            tv_home_second.setText(mButtonList.get(1));
            tv_home_third.setText(mButtonList.get(2));
            tv_home_four.setText(mButtonList.get(3));
            tv_home_five.setText(mButtonList.get(4));


            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()){
                        case R.id.tv_home_first:
                            ActivityUtils.startActivity(AndroidActivity.class);
                            break;
                    }
                }
            };

            tv_home_first.setOnClickListener(listener);
            tv_home_first.setOnClickListener(listener);
            tv_home_first.setOnClickListener(listener);
            tv_home_first.setOnClickListener(listener);
            tv_home_first.setOnClickListener(listener);
        }
    }
}

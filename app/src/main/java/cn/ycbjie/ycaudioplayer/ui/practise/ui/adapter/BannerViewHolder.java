package cn.ycbjie.ycaudioplayer.ui.practise.ui.adapter;

import android.content.Context;
import android.view.View;

import com.yc.cn.ycbannerlib.BannerView;
import com.yc.cn.ycbannerlib.util.SizeUtil;
import com.yc.cn.ycbaseadapterlib.BaseViewHolder;

import java.util.ArrayList;

import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.ui.study.ui.adapter.BannerPagerAdapter;

/**
 * Created by yc on 2018/3/20.
 */

public class BannerViewHolder extends BaseViewHolder {

    private final BannerView mBanner;

    public BannerViewHolder(View itemView) {
        super(itemView);
        mBanner = getView(R.id.banner);
    }


    public void bindData(ArrayList<String> mBannerList, Context mContext) {
        mBanner.setHintGravity(1);
        mBanner.setAnimationDuration(1000);
        mBanner.setPlayDelay(2000);
        mBanner.setHintPadding(0, 0, 0, SizeUtil.dip2px(mContext, 10));
        mBanner.setAdapter(new BannerPagerAdapter(mContext, mBannerList));
    }
}

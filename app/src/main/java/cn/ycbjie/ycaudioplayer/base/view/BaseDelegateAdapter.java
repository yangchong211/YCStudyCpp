package cn.ycbjie.ycaudioplayer.base.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;

import org.yczbj.ycrefreshviewlib.viewHolder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import cn.ycbjie.ycaudioplayer.constant.Constant;
import cn.ycbjie.ycaudioplayer.ui.practise.ui.adapter.AdSecondViewHolder;
import cn.ycbjie.ycaudioplayer.ui.practise.ui.adapter.AdViewHolder;
import cn.ycbjie.ycaudioplayer.ui.practise.ui.adapter.BannerViewHolder;
import cn.ycbjie.ycaudioplayer.ui.practise.ui.adapter.BottomViewHolder;
import cn.ycbjie.ycaudioplayer.ui.practise.ui.adapter.ButtonViewHolder;
import cn.ycbjie.ycaudioplayer.ui.practise.ui.adapter.FooterMoreViewHolder;
import cn.ycbjie.ycaudioplayer.ui.practise.ui.adapter.HeaderTitleViewHolder;
import cn.ycbjie.ycaudioplayer.ui.practise.ui.adapter.HotNewsHolder;
import cn.ycbjie.ycaudioplayer.ui.practise.ui.adapter.IndustryDynamicsViewHolder;
import cn.ycbjie.ycaudioplayer.ui.practise.ui.adapter.PopularizeNewsHolder;
import cn.ycbjie.ycaudioplayer.ui.practise.ui.adapter.ShareContentViewHolder;
import cn.ycbjie.ycaudioplayer.ui.practise.ui.adapter.TrendAnalysisViewHolder;


/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2017/9/18
 * 描    述：Vlayout框架基类适配器
 * 修订历史：
 * ================================================
 */
public class BaseDelegateAdapter extends DelegateAdapter.Adapter<BaseViewHolder> {

    private LayoutHelper mLayoutHelper;
    private int mCount = -1;
    private int mLayoutId = -1;
    private Context mContext;
    private int mViewTypeItem = -1;
    private List<String> mList;
    private ArrayList<String> mBannerList;
    private List<String> mButtonData;
    private String mTitleList;

    public BaseDelegateAdapter(Context context, LayoutHelper layoutHelper,
                                  int layoutId, int count, int viewTypeItem , List<String> list) {
        this.mContext = context;
        this.mCount = count;
        this.mLayoutHelper = layoutHelper;
        this.mLayoutId = layoutId;
        this.mViewTypeItem = viewTypeItem;
        this.mList = list;
    }

    public BaseDelegateAdapter(Context context, LayoutHelper layoutHelper, int layoutId, int count, int viewTypeItem) {
        this(context,layoutHelper,layoutId,count,viewTypeItem ,null);
    }


    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        switch (viewType){
            case Constant.ViewType.TYPE_BANNER:
                return new BannerViewHolder(inflater.inflate(mLayoutId, parent, false));
            case Constant.ViewType.TYPE_VIEW:
                return new ButtonViewHolder(inflater.inflate(mLayoutId, parent, false));
            case Constant.ViewType.TYPE_GV:
                return new HotNewsHolder(inflater.inflate(mLayoutId, parent, false));
            case Constant.ViewType.TYPE_AD:
                return new AdViewHolder(inflater.inflate(mLayoutId, parent, false));
            case Constant.ViewType.TYPE_LIST2:
                return new PopularizeNewsHolder(inflater.inflate(mLayoutId, parent, false));
            case Constant.ViewType.TYPE_AD2:
                return new AdSecondViewHolder(inflater.inflate(mLayoutId, parent, false));
            case Constant.ViewType.TYPE_GV2:
                return new IndustryDynamicsViewHolder(inflater.inflate(mLayoutId, parent, false));
            case Constant.ViewType.TYPE_LIST3:
                return new TrendAnalysisViewHolder(inflater.inflate(mLayoutId, parent, false));
            case Constant.ViewType.TYPE_GV_BOTTOM:
                return new ShareContentViewHolder(inflater.inflate(mLayoutId, parent, false));
            case Constant.ViewType.TYPE_LIST4:
                return new BottomViewHolder(inflater.inflate(mLayoutId, parent, false));
            case Constant.ViewType.TYPE_TITLE:
                return new HeaderTitleViewHolder(inflater.inflate(mLayoutId, parent, false));
            case Constant.ViewType.TYPE_MORE:
                return new HeaderTitleViewHolder(inflater.inflate(mLayoutId, parent, false));
            default:
                break;
        }
        return null;
    }

    /**
     * 子类adapter实现
     * @param holder                    holder
     * @param position                  索引
     */
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (holder instanceof BannerViewHolder) {
            ((BannerViewHolder)holder).bindData(mBannerList, mContext);
        } else if(holder instanceof ButtonViewHolder){
            ((ButtonViewHolder)holder).bindData(mButtonData, mContext);
        }else if(holder instanceof HotNewsHolder){
            ((HotNewsHolder)holder).bindData(mList, mContext);
        }else if(holder instanceof AdViewHolder){
            ((AdViewHolder)holder).bindData(mList, mContext);
        }else if(holder instanceof PopularizeNewsHolder){
            ((PopularizeNewsHolder)holder).bindData(mList, mContext);
        }else if(holder instanceof AdSecondViewHolder){
            ((AdSecondViewHolder)holder).bindData(mList, mContext);
        }else if(holder instanceof IndustryDynamicsViewHolder){
            ((IndustryDynamicsViewHolder)holder).bindData(mList, mContext);
        }else if(holder instanceof TrendAnalysisViewHolder){
            ((TrendAnalysisViewHolder)holder).bindData(mList, mContext);
        }else if(holder instanceof ShareContentViewHolder){
            ((ShareContentViewHolder)holder).bindData(mList, mContext);
        }else if(holder instanceof BottomViewHolder){
            ((BottomViewHolder)holder).bindData(mList, mContext);
        }else if(holder instanceof HeaderTitleViewHolder){
            ((HeaderTitleViewHolder)holder).bindData(mTitleList, mContext);
        }else if(holder instanceof FooterMoreViewHolder){
            ((FooterMoreViewHolder)holder).bindData(mList, mContext);
        }
    }

    /**
     * 必须重写不然会出现滑动不流畅的情况
     */
    @Override
    public int getItemViewType(int position) {
        return mViewTypeItem;
    }

    /**
     * 条目数量
     */
    @Override
    public int getItemCount() {
        if(mViewTypeItem == Constant.ViewType.TYPE_LIST2
                || mViewTypeItem == Constant.ViewType.TYPE_LIST3
                || mViewTypeItem == Constant.ViewType.TYPE_LIST4){
            return mList==null ? mCount : mList.size();
        }
        return mCount;
    }


    /**
     * 将轮播图数据
     * @param arrayList
     */
    public void setBannerData(ArrayList<String> arrayList) {
        this.mBannerList = arrayList;
    }

    /**
     * 设置按钮数据
     * @param buttonData
     */
    public void setButtonData(List<String> buttonData) {
        this.mButtonData = buttonData;
    }

    public void setData(List<String> data) {
        this.mList = data;
    }

}

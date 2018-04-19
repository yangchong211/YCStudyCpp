package cn.ycbjie.ycaudioplayer.ui.study.ui.fragment;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.pedaily.yc.ycdialoglib.customToast.ToastUtil;
import com.yc.cn.ycbannerlib.BannerView;
import com.yc.cn.ycbannerlib.util.SizeUtil;

import org.yczbj.ycrefreshviewlib.YCRefreshView;
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.item.RecycleViewItemLine;
import org.yczbj.ycrefreshviewlib.item.SpaceViewItemLine;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.base.BaseFragment;
import cn.ycbjie.ycaudioplayer.ui.main.MainHomeActivity;
import cn.ycbjie.ycaudioplayer.ui.study.model.VideoPlayerFavorite;
import cn.ycbjie.ycaudioplayer.ui.study.ui.adapter.BannerPagerAdapter;
import cn.ycbjie.ycaudioplayer.ui.study.ui.adapter.NarrowImageAdapter;
import cn.ycbjie.ycaudioplayer.ui.study.ui.adapter.StudyAdapter;

/**
 * Created by yc on 2018/1/24.
 */

public class StudyFragment extends BaseFragment {

    @Bind(R.id.recyclerView)
    YCRefreshView recyclerView;
    private MainHomeActivity activity;
    private StudyAdapter adapter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainHomeActivity) context;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }


    @Override
    public int getContentView() {
        return R.layout.base_easy_recycle;
    }


    @Override
    public void initView() {
        initYCRefreshView();
    }


    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        List<String> data = new ArrayList<>();
        for(int a=0 ; a<10 ; a++){
            data.add("假数据"+a);
        }
        adapter.addAll(data);
        adapter.notifyDataSetChanged();
    }


    private void initYCRefreshView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        final RecycleViewItemLine line = new RecycleViewItemLine(activity, LinearLayout.HORIZONTAL,
                SizeUtils.dp2px(1), Color.parseColor("#f5f5f7"));
        recyclerView.addItemDecoration(line);
        adapter = new StudyAdapter(activity);
        recyclerView.setAdapter(adapter);
        addHeader();
        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SwipeRefreshLayout swipeToRefresh = recyclerView.getSwipeToRefresh();
                if(swipeToRefresh.isRefreshing()){
                    recyclerView.setRefreshing(false);
                }
            }
        });
    }


    /**
     * 添加头部
     */
    private void addHeader() {
        adapter.removeAllHeader();
        initTopHeaderView();
        initHeaderButton();
        initHeaderTitle();
        initVideoContentView();
        initFooterTitle();
        initHorizontalView();
    }

    private void initTopHeaderView() {
        final ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("http://bpic.wotucdn.com/11/66/23/55bOOOPIC3c_1024.jpg!/fw/780/quality/90/unsharp/true/compress/true/watermark/url/L2xvZ28ud2F0ZXIudjIucG5n/repeat/true");
        arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505470629546&di=194a9a92bfcb7754c5e4d19ff1515355&imgtype=0&src=http%3A%2F%2Fpics.jiancai.com%2Fimgextra%2Fimg01%2F656928666%2Fi1%2FT2_IffXdxaXXXXXXXX_%2521%2521656928666.jpg");
        arrayList.add("http://bpic.wotucdn.com/11/66/23/55bOOOPIC3c_1024.jpg!/fw/780/quality/90/unsharp/true/compress/true/watermark/url/L2xvZ28ud2F0ZXIudjIucG5n/repeat/true");
        arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505470629546&di=194a9a92bfcb7754c5e4d19ff1515355&imgtype=0&src=http%3A%2F%2Fpics.jiancai.com%2Fimgextra%2Fimg01%2F656928666%2Fi1%2FT2_IffXdxaXXXXXXXX_%2521%2521656928666.jpg");
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                return LayoutInflater.from(activity).inflate(R.layout.head_audio_banner, parent, false);
            }


            @Override
            public void onBindView(View headerView) {
                // 绑定数据
                BannerView mBanner = (BannerView) headerView.findViewById(R.id.banner);
                mBanner.setHintGravity(1);
                mBanner.setAnimationDuration(1000);
                mBanner.setPlayDelay(2000);
                mBanner.setHintPadding(0,0,0, SizeUtil.dip2px(activity,10));
                mBanner.setAdapter(new BannerPagerAdapter(activity, arrayList));
            }
        });
    }


    private void initHeaderButton() {
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                return LayoutInflater.from(activity).inflate(R.layout.head_audio_button, parent, false);
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
    }


    private void initHeaderTitle() {
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                return LayoutInflater.from(activity).inflate(R.layout.head_audio_title, parent, false);
            }

            @Override
            public void onBindView(View headerView) {
                LinearLayout llChange = (LinearLayout) headerView.findViewById(R.id.ll_change);
                final ImageView ivChange = (ImageView) headerView.findViewById(R.id.iv_change);
                llChange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startPropertyAnim(ivChange);
                    }
                });
            }
        });
    }


    private void initVideoContentView() {
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                return LayoutInflater.from(activity).inflate(R.layout.head_audio_news, parent, false);
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
    }


    private void initFooterTitle() {
        adapter.addFooter(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                return LayoutInflater.from(activity).inflate(R.layout.head_audio_title, parent, false);
            }

            @Override
            public void onBindView(View headerView) {
                TextView tvTitle = (TextView) headerView.findViewById(R.id.tv_title);
                LinearLayout llChange = (LinearLayout) headerView.findViewById(R.id.ll_change);
                final ImageView ivChange = (ImageView) headerView.findViewById(R.id.iv_change);

                tvTitle.setText("猜你喜欢");
                llChange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startPropertyAnim(ivChange);
                    }
                });
            }
        });
    }


    private void initHorizontalView() {
        adapter.addFooter(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                RecyclerView recyclerView = new RecyclerView(parent.getContext()){
                    //为了不打扰RecyclerView的滑动操作，可以这样处理
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public boolean onTouchEvent(MotionEvent event) {
                        super.onTouchEvent(event);
                        return true;
                    }
                };
                RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(SizeUtils.dp2px(10),SizeUtils.dp2px(5),
                        SizeUtils.dp2px(10),SizeUtils.dp2px(10));
                recyclerView.setLayoutParams(layoutParams);
                final NarrowImageAdapter narrowAdapter;
                recyclerView.setAdapter(narrowAdapter = new NarrowImageAdapter(parent.getContext()));
                recyclerView.setLayoutManager(new LinearLayoutManager(parent.getContext(), LinearLayoutManager.HORIZONTAL,false));
                recyclerView.addItemDecoration(new SpaceViewItemLine(SizeUtils.dp2px(8)));

                narrowAdapter.setMore(R.layout.view_more_horizontal, new RecyclerArrayAdapter.OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.showToast(activity,"没有更多呢！");
                            }
                        },1000);
                    }
                });
                List<VideoPlayerFavorite> favoriteList = new ArrayList<>();
                for(int a=0 ; a<10 ; a++){
                    VideoPlayerFavorite videoPlayerFavorite = new VideoPlayerFavorite(
                            "这个是猜你喜欢的标题",R.drawable.bg_small_tree_min,"");
                    favoriteList.add(videoPlayerFavorite);

                }
                narrowAdapter.addAll(favoriteList);
                return recyclerView;
            }

            @Override
            public void onBindView(View headerView) {
                //这里的处理别忘了
                ((ViewGroup)headerView).requestDisallowInterceptTouchEvent(true);
            }
        });
    }


    private void startPropertyAnim(ImageView ivChange) {
        // 第二个参数"rotation"表明要执行旋转
        // 0f -> 360f，从旋转360度，也可以是负值，负值即为逆时针旋转，正值是顺时针旋转。
        ObjectAnimator anim = ObjectAnimator.ofFloat(ivChange, "rotation", 0f, 360f);
        anim.setDuration(800);
        anim.start();
    }


}

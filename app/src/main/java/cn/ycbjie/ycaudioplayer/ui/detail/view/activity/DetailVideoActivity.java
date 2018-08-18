package cn.ycbjie.ycaudioplayer.ui.detail.view.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.pedaily.yc.ycdialoglib.bottomLayout.BottomDialogFragment;
import com.pedaily.yc.ycdialoglib.customToast.ToastUtil;

import org.yczbj.ycrefreshviewlib.YCRefreshView;
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.item.RecycleViewItemLine;
import org.yczbj.ycvideoplayerlib.ConstantKeys;
import org.yczbj.ycvideoplayerlib.VideoPlayer;
import org.yczbj.ycvideoplayerlib.VideoPlayerController;
import org.yczbj.ycvideoplayerlib.VideoPlayerManager;
import org.yczbj.ycvideoplayerlib.VideoPlayerUtils;
import org.yczbj.ycvideoplayerlib.listener.OnPlayOrPauseListener;
import org.yczbj.ycvideoplayerlib.listener.OnVideoBackListener;
import org.yczbj.ycvideoplayerlib.listener.OnVideoControlListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.constant.Constant;
import cn.ycbjie.ycaudioplayer.base.view.BaseActivity;
import cn.ycbjie.ycaudioplayer.base.BaseAppHelper;
import cn.ycbjie.ycaudioplayer.base.app.BaseApplication;
import cn.ycbjie.ycaudioplayer.constant.BaseConfig;
import cn.ycbjie.ycaudioplayer.inter.listener.OnPlayerEventListener;
import cn.ycbjie.ycaudioplayer.model.bean.AudioBean;
import cn.ycbjie.ycaudioplayer.ui.detail.contract.DetailVideoContract;
import cn.ycbjie.ycaudioplayer.ui.detail.model.DialogListBean;
import cn.ycbjie.ycaudioplayer.ui.detail.presenter.DetailVideoPresenter;
import cn.ycbjie.ycaudioplayer.ui.detail.view.adapter.DetailVideoAdapter;
import cn.ycbjie.ycaudioplayer.ui.detail.view.adapter.DialogListAdapter;
import cn.ycbjie.ycaudioplayer.ui.detail.view.adapter.MovieCatalogueAdapter;
import cn.ycbjie.ycaudioplayer.ui.detail.view.fragment.DetailAudioFragment;
import cn.ycbjie.ycaudioplayer.utils.file.SDUtils;
import cn.ycbjie.ycaudioplayer.utils.logger.AppLogUtils;
import cn.ycbjie.ycthreadpoollib.PoolThread;


/**
 * <pre>
 *     @author yangchong
 *     blog  : www.pedaily.cn
 *     time  : 2018/03/22
 *     desc  : 视频播放详情页面
 *     revise:
 * </pre>
 */
public class DetailVideoActivity extends BaseActivity implements DetailVideoContract.View, View.OnClickListener {


    @Bind(R.id.rl_video_detail)
    RelativeLayout rlVideoDetail;
    @Bind(R.id.video_player)
    VideoPlayer videoPlayer;
    @Bind(R.id.recyclerView)
    YCRefreshView recyclerView;
    @Bind(R.id.tv_title_left)
    TextView tvTitleLeft;
    @Bind(R.id.view_left)
    View viewLeft;
    @Bind(R.id.tv_title_right)
    TextView tvTitleRight;
    @Bind(R.id.view_right)
    View viewRight;
    @Bind(R.id.ll_left)
    LinearLayout llLeft;
    @Bind(R.id.ll_right)
    LinearLayout llRight;

    private boolean isPlayFragmentShow = false;
    private DetailAudioFragment mDetailAudioFragment;
    private DetailVideoAdapter adapter;

    private DetailVideoContract.Presenter presenter = new DetailVideoPresenter(this);
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onStop() {
        super.onStop();
        VideoPlayerManager.instance().releaseVideoPlayer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initPlayServiceListener();
    }


    @Override
    public void onBackPressed() {
        if (mDetailAudioFragment != null && isPlayFragmentShow) {
            hidePlayingFragment();
            return;
        }
        if (VideoPlayerManager.instance().onBackPressed()) {
            return;
        }
        getPlayService().setOnPlayEventListener(null);
        super.onBackPressed();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.subscribe();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unSubscribe();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_detail;
    }

    @Override
    public void initView() {
        if (!checkServiceAlive()) {
            return;
        }
        initVideoPlayerSize();
        initVideoPlayer();
        initIndicator();
        initYCRefreshView();
    }

    @Override
    public void initListener() {
        llLeft.setOnClickListener(this);
        llRight.setOnClickListener(this);
        initPlayServiceListener();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch(newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        AppLogUtils.e("滑动距离"+"recyclerView已经停止滚动");
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        AppLogUtils.e("滑动距离"+"recyclerView正在被拖拽");
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING:
                        AppLogUtils.e("滑动距离"+"recyclerView正在依靠惯性滚动");
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                float y = recyclerView.getY();
                int scrollY = recyclerView.getScrollY();
                AppLogUtils.e("滑动距离"+dy+"----"+y+"-------"+scrollY);
                //获取第一个可见的位置
                int position = linearLayoutManager.findFirstVisibleItemPosition();
                //int scrollYDistance = getScrollYDistance(position);
                //LogUtils.e("滑动距离111----"+position+"====="+scrollYDistance);
                //判断是否滑到了顶部
                boolean isTopScroll = recyclerView.canScrollVertically(-1);
                AppLogUtils.e("滑动距离、、、"+isTopScroll);

                if(position<1){
                    setViewIndicator(true);
                }else {
                    setViewIndicator(false);
                }
            }
        });
    }


    public int getScrollYDistance(int position) {
        //获取第一个可见view，并且获取高度
        View firstChildView = linearLayoutManager.findViewByPosition(position);
        int itemHeight = firstChildView.getHeight();
        return (position) * itemHeight - firstChildView.getTop();
    }


    @Override
    public void initData() {
        presenter.getData();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                setViewIndicator(true);
                break;
            case R.id.ll_right:
                setViewIndicator(false);
                break;
            default:
                break;
        }
    }


    /**
     * 设置视频宽高比是16：9
     */
    private void initVideoPlayerSize() {
        int screenWidth = ScreenUtils.getScreenWidth();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.height = (int) (9 * screenWidth / 16.0f);
        videoPlayer.setLayoutParams(params);
    }


    private void initVideoPlayer() {
        videoPlayer.release();
        //设置播放类型
        videoPlayer.setPlayerType(VideoPlayer.TYPE_IJK);
        //是否从上一次的位置继续播放
        videoPlayer.continueFromLastPosition(true);
        //设置播放速度
        videoPlayer.setSpeed(1.0f);
        //网络视频地址
        videoPlayer.setUp(Constant.DEVIE_URL, null);
        //设置视频地址和请求头部
        //创建视频控制器
        VideoPlayerController controller = new VideoPlayerController(this);
        controller.setTopVisibility(true);
        controller.setOnVideoBackListener(new OnVideoBackListener() {
            @Override
            public void onBackClick() {
                onBackPressed();
            }
        });
        controller.setOnPlayOrPauseListener(new OnPlayOrPauseListener() {
            @Override
            public void onPlayOrPauseClick(boolean isPlaying) {
                if (isPlaying) {
                    ToastUtil.showToast(DetailVideoActivity.this, "暂停视频");
                } else {
                    ToastUtil.showToast(DetailVideoActivity.this, "开始播放");
                    if (getPlayService().isPlaying() || getPlayService().isPreparing()) {
                        getPlayService().pause();
                        videoPlayer.seekTo(BaseConfig.INSTANCE.getPosition());
                    }
                }
            }
        });
        controller.setOnVideoControlListener(new OnVideoControlListener() {
            @Override
            public void onVideoControlClick(int type) {
                switch (type) {
                    case ConstantKeys.VideoControl.DOWNLOAD:
                        showDownloadDialog();
                        break;
                    case ConstantKeys.VideoControl.AUDIO:
                        showPlayingFragment();
                        break;
                    case ConstantKeys.VideoControl.SHARE:
                        ToastUtil.showToast(DetailVideoActivity.this, "分享内容");
                        break;
                    default:
                        break;
                }
            }
        });
        //设置视频控制器
        videoPlayer.setController(controller);
    }


    /**
     * 初始化指示器
     */
    private void initIndicator() {
        tvTitleLeft.setText("课程简介");
        tvTitleRight.setText("课程目录课程目录");
        int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        tvTitleLeft.measure(spec,spec);
        tvTitleRight.measure(spec,spec);
        int widthLeft = tvTitleLeft.getMeasuredWidth();
        int widthRight = tvTitleRight.getMeasuredWidth();
        AppLogUtils.e("width"+tvTitleLeft.getWidth() + "---"+tvTitleRight.getWidth()
                + "---"+tvTitleLeft.getMeasuredWidth());
        AppLogUtils.e("width"+"----------"+widthLeft+"-----"+widthRight);
        LinearLayout.LayoutParams paramsLeft = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, SizeUtils.dp2px(2));
        paramsLeft.width = widthLeft;
        viewLeft.setLayoutParams(paramsLeft);
        LinearLayout.LayoutParams paramsRight = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, SizeUtils.dp2px(2));
        paramsRight.width = widthRight;
        viewRight.setLayoutParams(paramsRight);
        setViewIndicator(true);
    }


    private void setViewIndicator(boolean view){
        if(view){
            viewLeft.setVisibility(View.VISIBLE);
            viewRight.setVisibility(View.INVISIBLE);
            tvTitleLeft.setTextColor(getResources().getColor(R.color.color_3));
            tvTitleRight.setTextColor(getResources().getColor(R.color.blackText));
        }else {
            viewLeft.setVisibility(View.INVISIBLE);
            viewRight.setVisibility(View.VISIBLE);
            tvTitleLeft.setTextColor(getResources().getColor(R.color.blackText));
            tvTitleRight.setTextColor(getResources().getColor(R.color.color_3));
        }
    }


    private void initYCRefreshView() {
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        final RecycleViewItemLine line = new RecycleViewItemLine(this, LinearLayout.HORIZONTAL,
                SizeUtils.dp2px(1), Color.parseColor("#f5f5f7"));
        recyclerView.addItemDecoration(line);
        adapter = new DetailVideoAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setRefreshing(false);
        recyclerView.scrollTo(0,0);
        recyclerView.scrollBy(0,0);
        addHeader();
    }


    private void addHeader() {
        adapter.removeAllHeader();
        initContentTitleHeader();
        initCourseCatalogueTitle();
        initCatalogueListView();
        initCommentTitleView();
        PoolThread executor = BaseApplication.getInstance().getExecutor();
        executor.setDelay(1, TimeUnit.SECONDS);
        executor.execute(new Runnable() {
            @Override
            public void run() {

            }
        });
    }


    private void initContentTitleHeader() {
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                View viewHeader = LayoutInflater.from(DetailVideoActivity.this).inflate(R.layout.head_audio_title, parent, false);
                return viewHeader;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
    }


    private void initCourseCatalogueTitle() {
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                return LayoutInflater.from(DetailVideoActivity.this)
                        .inflate(R.layout.header_video_header_catalogue, parent, false);
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
    }


    private void initCatalogueListView() {
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                RecyclerView recyclerView = new RecyclerView(parent.getContext()) {
                    /**
                     * 为了不打扰RecyclerView的滑动操作，可以这样处理
                     */
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public boolean onTouchEvent(MotionEvent event) {
                        super.onTouchEvent(event);
                        return true;
                    }
                };
                RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 0, 0, 0);
                recyclerView.setLayoutParams(layoutParams);
                final MovieCatalogueAdapter narrowAdapter;
                recyclerView.setAdapter(narrowAdapter = new MovieCatalogueAdapter(parent.getContext()));
                recyclerView.setLayoutManager(new LinearLayoutManager(parent.getContext(), LinearLayoutManager.VERTICAL, false));
                recyclerView.addItemDecoration(new RecycleViewItemLine(parent.getContext(), SizeUtils.px2dp(1)));
                List<String> data = new ArrayList<>();
                for (int a = 0; a < 3; a++) {
                    data.add("假数据" + a);
                }
                narrowAdapter.addAll(data);
                return recyclerView;
            }

            @Override
            public void onBindView(View headerView) {
                //这里的处理别忘了
                ((ViewGroup) headerView).requestDisallowInterceptTouchEvent(true);
            }
        });
    }


    private void initCommentTitleView() {
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                return LayoutInflater.from(DetailVideoActivity.this)
                        .inflate(R.layout.head_audio_title, parent, false);
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
    }


    /**
     * 展示页面
     */
    private void showPlayingFragment() {
        if (isPlayFragmentShow) {
            return;
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_slide_up, 0);
        if (mDetailAudioFragment == null) {
            mDetailAudioFragment = DetailAudioFragment.newInstance("Video");
            ft.add(android.R.id.content, mDetailAudioFragment);
        } else {
            ft.show(mDetailAudioFragment);
        }
        ft.commitAllowingStateLoss();
        isPlayFragmentShow = true;
        AppLogUtils.e("fragment数量+DetailVideoActivity" + FragmentUtils.getAllFragments(getSupportFragmentManager()).size());

        if (videoPlayer.isPlaying() || videoPlayer.isBufferingPlaying()) {
            videoPlayer.pause();
        }

        //当视频正在播放，准备播放时，点击音视频切换按钮，先暂停视频，然后记录视频播放位置，show音频播放页面
        //当视频已经暂停，播放错误，播放停止时，点击音视频切换按钮，直接记录视频播放位置，show音频播放页面
        BaseConfig.INSTANCE.setPosition(videoPlayer.getCurrentPosition());
        AppLogUtils.e("播放位置----视频页开始显示音频--" + videoPlayer.getCurrentPosition());

        if (mDetailAudioFragment != null) {
            mDetailAudioFragment.setViewData(BaseAppHelper.get().getMusicList().get(0));
            if (getPlayService().isDefault() || getPlayService().isPausing()) {
                getPlayService().seekTo((int) BaseConfig.INSTANCE.getPosition());
                getPlayService().playPause();
            }
        }
    }


    /**
     * 隐藏页面
     */
    private void hidePlayingFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(0, R.anim.fragment_slide_down);
        ft.hide(mDetailAudioFragment);
        ft.commitAllowingStateLoss();
        isPlayFragmentShow = false;
    }


    /**
     * 弹出下载弹窗
     */
    private void showDownloadDialog() {
        final List<DialogListBean> list = new ArrayList<>();
        for (int a = 0; a < Constant.VideoPlayerList.length; a++) {
            DialogListBean dialogListBean = new DialogListBean();
            dialogListBean.setVideo(Constant.VideoPlayerList[a]);
            dialogListBean.setTitle("下载的内容");
            list.add(dialogListBean);
        }
        final BottomDialogFragment dialog = new BottomDialogFragment();
        dialog.setFragmentManager(getSupportFragmentManager());
        dialog.setViewListener(new BottomDialogFragment.ViewListener() {
            @Override
            public void bindView(View v) {
                RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
                ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                ImageView ivDownload = (ImageView) v.findViewById(R.id.iv_download);
                TextView tvDataSize = (TextView) v.findViewById(R.id.tv_data_size);
                tvDataSize.setText(getDataText(list));

                recyclerView.setLayoutManager(new LinearLayoutManager(DetailVideoActivity.this));
                DialogListAdapter mAdapter = new DialogListAdapter(DetailVideoActivity.this, list);
                recyclerView.setAdapter(mAdapter);
                final RecycleViewItemLine line = new RecycleViewItemLine(
                        DetailVideoActivity.this, LinearLayout.HORIZONTAL,
                        SizeUtils.dp2px(1),
                        DetailVideoActivity.this.getResources().getColor(R.color.grayLine));
                recyclerView.addItemDecoration(line);
                mAdapter.setOnItemClickListener(new DialogListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        ToastUtil.showToast(DetailVideoActivity.this, "被点击呢" + position);
                    }
                });
                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.iv_cancel:
                                if (VideoPlayerUtils.isActivityLiving(DetailVideoActivity.this)) {
                                    dialog.dismissDialogFragment();
                                }
                                break;
                            case R.id.iv_download:

                                break;
                            default:
                                break;
                        }
                    }
                };
                ivCancel.setOnClickListener(listener);
                ivDownload.setOnClickListener(listener);
            }
        });
        dialog.setLayoutRes(R.layout.dialog_download_video);
        dialog.setDimAmount(0.5f);
        dialog.setTag("BottomDialog");
        dialog.setCancelOutside(true);
        //这个高度可以自己设置，十分灵活
        dialog.setHeight(ScreenUtils.getScreenHeight()-videoPlayer.getHeight()- BarUtils.getStatusBarHeight());
        dialog.show();
    }

    private String getDataText(List<DialogListBean> list) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(list.size());
        stringBuffer.append("个/");
        String size = Formatter.formatShortFileSize(this, 10003200);
        stringBuffer.append(size);
        stringBuffer.append("   ");
        stringBuffer.append("可用空间");
        stringBuffer.append(Formatter.formatFileSize(this, SDUtils.getAvailableSize()));
        return stringBuffer.toString();
    }

    @Override
    public void setDataView(List<String> data) {
        adapter.addAll(data);
        adapter.notifyDataSetChanged();
    }

    /**
     * 初始化服务播放音频播放进度监听器
     * 这个是要是通过监听即时更新主页面的底部控制器视图
     * 同时还要同步播放详情页面mPlayFragment的视图
     */
    public void initPlayServiceListener() {
        if (getPlayService() == null) {
            return;
        }
        getPlayService().setOnPlayEventListener(new OnPlayerEventListener() {
            /**
             * 切换歌曲
             * 主要是切换歌曲的时候需要及时刷新界面信息
             */
            @Override
            public void onChange(AudioBean music) {
                if (mDetailAudioFragment != null && mDetailAudioFragment.isAdded()) {
                    mDetailAudioFragment.onChange(music);
                }
            }

            /**
             * 继续播放
             * 主要是切换歌曲的时候需要及时刷新界面信息，比如播放暂停按钮
             */
            @Override
            public void onPlayerStart() {
                if (mDetailAudioFragment != null && mDetailAudioFragment.isAdded()) {
                    mDetailAudioFragment.onPlayerStart();
                }
            }

            /**
             * 暂停播放
             * 主要是切换歌曲的时候需要及时刷新界面信息，比如播放暂停按钮
             */
            @Override
            public void onPlayerPause() {
                if (mDetailAudioFragment != null && mDetailAudioFragment.isAdded()) {
                    mDetailAudioFragment.onPlayerPause();
                }
            }

            /**
             * 更新进度
             * 主要是播放音乐或者拖动进度条时，需要更新进度
             */
            @Override
            public void onUpdateProgress(int progress) {
                if (mDetailAudioFragment != null && mDetailAudioFragment.isAdded()) {
                    mDetailAudioFragment.onUpdateProgress(progress);
                    BaseConfig.INSTANCE.setPosition(progress);
                }
            }

            @Override
            public void onBufferingUpdate(int percent) {
                if (mDetailAudioFragment != null && mDetailAudioFragment.isAdded()) {
                    mDetailAudioFragment.onBufferingUpdate(percent);
                }
            }

            @Override
            public void onTimer(long remain) {

            }
        });
    }


}

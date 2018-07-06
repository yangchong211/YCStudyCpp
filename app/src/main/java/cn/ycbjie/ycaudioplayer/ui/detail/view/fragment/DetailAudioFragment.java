package cn.ycbjie.ycaudioplayer.ui.detail.view.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;


import org.yczbj.ycrefreshviewlib.YCRefreshView;
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.item.RecycleViewItemLine;
import org.yczbj.ycvideoplayerlib.VideoPlayerUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.base.BaseAppHelper;
import cn.ycbjie.ycaudioplayer.constant.BaseConfig;
import cn.ycbjie.ycaudioplayer.base.view.BaseFragment;
import cn.ycbjie.ycaudioplayer.inter.listener.OnPlayerEventListener;
import cn.ycbjie.ycaudioplayer.ui.detail.view.activity.DetailVideoActivity;
import cn.ycbjie.ycaudioplayer.ui.detail.view.adapter.DetailAudioAdapter;
import cn.ycbjie.ycaudioplayer.ui.main.ui.activity.MainActivity;
import cn.ycbjie.ycaudioplayer.model.bean.AudioBean;
import cn.ycbjie.ycaudioplayer.utils.logger.AppLogUtils;


/**
 * <pre>
 *     @author yangchong
 *     blog  : www.pedaily.cn
 *     time  : 2018/03/22
 *     desc  : 音频播放详情页面
 *     revise:
 * </pre>
 */
public class DetailAudioFragment extends BaseFragment implements View.OnClickListener , OnPlayerEventListener {

    @Bind(R.id.ll_title_menu)
    FrameLayout flTitleMenu;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_current_time)
    TextView tvCurrentTime;
    @Bind(R.id.sb_progress)
    SeekBar sbProgress;
    @Bind(R.id.tv_total_time)
    TextView tvTotalTime;
    @Bind(R.id.iv_prev)
    ImageView ivPrev;
    @Bind(R.id.iv_play)
    ImageView ivPlay;
    @Bind(R.id.iv_next)
    ImageView ivNext;
    @Bind(R.id.recyclerView)
    YCRefreshView recyclerView;

    private Activity activity;
    private DetailAudioAdapter adapter;
    /**
     * 是否拖进度，默认是false
     */
    private boolean isDraggingProgress = false;
    private int mLastProgress;
    private static final String TAG = "DetailAudioFragment";
    private String type;
    private AudioBean audioBean;

    public static DetailAudioFragment newInstance(String type) {
        Bundle bundle = new Bundle();
        bundle.putString(TAG, type);
        DetailAudioFragment newsArticleView = new DetailAudioFragment();
        newsArticleView.setArguments(bundle);
        return newsArticleView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    /**
     * 返回监听
     */
    private void onBackPressed() {
        getActivity().onBackPressed();
        flTitleMenu.setEnabled(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                flTitleMenu.setEnabled(true);
            }
        }, 300);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(activity!=null){
            if(hidden){
                //当该页面隐藏时
                if(activity instanceof DetailVideoActivity){
                    AppLogUtils.e("视频播放"+"DetailVideoActivity-----------");
                }else if(activity instanceof MainActivity){
                    AppLogUtils.e("视频播放"+"MainActivity------");
                }
            }else {
                //当页面展现时
                if(activity instanceof MainActivity){
                    AppLogUtils.e("视频播放"+"MainActivity");
                }else if(activity instanceof DetailVideoActivity){
                    AppLogUtils.e("视频播放"+"DetailVideoActivity");
                }
            }
        }
        AppLogUtils.e("DetailAudioFragment"+"-----onHiddenChanged-----"+hidden);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppLogUtils.e("DetailAudioFragment"+"-----onViewCreated-----");
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_detail_audio;
    }


    @Override
    public void initView(View view) {
        type = getArguments().getString(TAG);
        initToolBar();
        initYCRefreshView();
    }


    private void initToolBar() {
        int statusBarHeight = BarUtils.getStatusBarHeight();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(50));
        params.topMargin = statusBarHeight;
        toolbar.setLayoutParams(params);
    }


    @Override
    public void initListener() {
        flTitleMenu.setOnClickListener(this);
        ivPlay.setOnClickListener(this);
        ivPrev.setOnClickListener(this);
        ivNext.setOnClickListener(this);
        initSeekBarListener();
    }


    @Override
    public void initData() {
        List<String> data = new ArrayList<>();
        for(int a=0 ; a<3 ; a++){
            data.add("假数据"+a);
        }
        adapter.addAll(data);
        adapter.notifyDataSetChanged();
        setViewData(BaseAppHelper.get().getAudioList().get(0));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_title_menu:
                onBackPressed();
                break;
            case R.id.iv_play:
                play();
                break;
            case R.id.iv_next:
                next();
                break;
            case R.id.iv_prev:
                prev();
                break;
            default:
                break;
        }
    }


    private void initYCRefreshView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        final RecycleViewItemLine line = new RecycleViewItemLine(activity, LinearLayout.HORIZONTAL,
                SizeUtils.dp2px(1), Color.parseColor("#f5f5f7"));
        recyclerView.addItemDecoration(line);
        adapter = new DetailAudioAdapter(activity);
        recyclerView.setAdapter(adapter);
        recyclerView.setRefreshing(false);
        addHeader();
    }


    private void addHeader() {
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                return LayoutInflater.from(activity).inflate(R.layout.head_audio_title, parent, false);
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
    }


    @SuppressLint("SetTextI18n")
    public void setViewData(AudioBean audioBean) {
        if (audioBean == null || toolbarTitle==null) {
            return;
        }
        this.audioBean = audioBean;
        toolbarTitle.setText(audioBean.getTitle()==null?"音频标题":audioBean.getTitle());
        sbProgress.setSecondaryProgress(0);
        sbProgress.setMax((int) audioBean.getDuration());
        sbProgress.setProgress((int) getPlayService().getCurrentPosition());
        tvCurrentTime.setText("00:00");
        tvTotalTime.setText(VideoPlayerUtils.formatTime(audioBean.getDuration()));
        if (getPlayService().isPlaying() || getPlayService().isPreparing()) {
            ivPlay.setSelected(true);
        } else {
            ivPlay.setSelected(false);
        }
    }


    private void initSeekBarListener() {
        SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (seekBar == sbProgress) {
                    if (Math.abs(progress - mLastProgress) >= DateUtils.SECOND_IN_MILLIS) {
                        tvCurrentTime.setText(VideoPlayerUtils.formatTime(progress));
                        if(audioBean!=null){
                            tvTotalTime.setText(VideoPlayerUtils.formatTime(audioBean.getDuration() - progress));
                        }
                        mLastProgress = progress;
                    }
                }
            }

            /**
             * 通知用户已启动触摸手势,开始触摸时调用
             * @param seekBar               seekBar
             */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (seekBar == sbProgress) {
                    isDraggingProgress = true;
                }
            }


            /**
             * 通知用户已结束触摸手势,触摸结束时调用
             * @param seekBar               seekBar
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar == sbProgress) {
                    isDraggingProgress = false;
                    //如果是正在播放，或者暂停，那么直接拖动进度
                    if (getPlayService().isPlaying() || getPlayService().isPausing()) {
                        //获取进度
                        int progress = seekBar.getProgress();
                        //直接移动进度
                        getPlayService().seekTo(progress);
                    } else {
                        //其他情况，直接设置进度为0
                        seekBar.setProgress(0);
                    }
                }
            }
        };
        sbProgress.setOnSeekBarChangeListener(onSeekBarChangeListener);
    }


    private void prev() {
        if (getPlayService() != null) {
            if(getPlayService().isHavePre()){
                ToastUtils.showShort(R.string.state_prev);
                getPlayService().prev();
            }else {
                ToastUtils.showShort("没有上一曲");
            }
        }
    }


    private void next() {
        if (getPlayService() != null) {
            if(getPlayService().isHaveNext()){
                ToastUtils.showShort(R.string.state_next);
                getPlayService().next();
            }else {
                ToastUtils.showShort("没有下一曲");
            }
        }
    }


    private void play() {
        if (getPlayService() != null) {
            getPlayService().playPause();
        }
    }


    /**
     * 切换歌曲
     * 主要是切换歌曲的时候需要及时刷新界面信息
     */
    @Override
    public void onChange(AudioBean music) {
        setViewData(music);
    }

    /**
     * 继续播放
     * 主要是切换歌曲的时候需要及时刷新界面信息，比如播放暂停按钮
     */
    @Override
    public void onPlayerStart() {
        ivPlay.setSelected(true);
    }


    /**
     * 暂停播放
     * 主要是切换歌曲的时候需要及时刷新界面信息，比如播放暂停按钮
     */
    @Override
    public void onPlayerPause() {
        ivPlay.setSelected(false);
        if(getPlayService()!=null){
            BaseConfig.INSTANCE.setPosition(getPlayService().getCurrentPosition());
        }
    }


    /**
     * 更新进度
     * 主要是播放音乐或者拖动进度条时，需要更新进度
     */
    @Override
    public void onUpdateProgress(int progress) {
        AppLogUtils.e("setOnPlayEventListener---progress---"+progress);
        if(progress>0){
            //如果没有拖动进度，则开始更新进度条进度
            if (!isDraggingProgress) {
                sbProgress.setProgress(progress);
            }
        }
    }


    @Override
    public void onBufferingUpdate(int percent) {
        AppLogUtils.e("setOnPlayEventListener---percent---"+percent);
        if(sbProgress.getMax()>0 && percent>0){
            AppLogUtils.e("setOnPlayEventListener---percent---"+ sbProgress.getMax() + "-----" +percent);
            sbProgress.setSecondaryProgress(sbProgress.getMax() * 100 / percent);
        }
    }

    @Override
    public void onTimer(long remain) {

    }


}

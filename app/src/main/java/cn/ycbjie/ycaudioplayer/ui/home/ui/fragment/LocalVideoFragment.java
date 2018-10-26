package cn.ycbjie.ycaudioplayer.ui.home.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.pedaily.yc.ycdialoglib.toast.ToastUtils;

import org.yczbj.ycvideoplayerlib.VideoPlayer;
import org.yczbj.ycvideoplayerlib.VideoPlayerController;
import org.yczbj.ycvideoplayerlib.VideoPlayerManager;
import org.yczbj.ycvideoplayerlib.listener.OnCompletedListener;
import org.yczbj.ycvideoplayerlib.listener.OnVideoBackListener;
import butterknife.Bind;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.base.view.BaseFragment;
import cn.ycbjie.ycaudioplayer.model.bean.VideoBean;
import cn.ycbjie.ycaudioplayer.ui.home.ui.activity.LocalVideoActivity;

/**
 * <pre>
 *     @author yangchong
 *     blog  :
 *     time  : 2018/2/10
 *     desc  : 本地视频页面
 *     revise:
 * </pre>
 */
public class LocalVideoFragment extends BaseFragment {

    @Bind(R.id.video_player)
    VideoPlayer videoPlayer;
    private LocalVideoActivity activity;


    //在此Fragment中设置代码如下
    @Override
    public void onStop() {
        super.onStop();
        VideoPlayerManager.instance().releaseVideoPlayer();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (LocalVideoActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }


    public static LocalVideoFragment newInstance(VideoBean videoBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("videoBean", videoBean);
        LocalVideoFragment fragment = new LocalVideoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public int getContentView() {
        return R.layout.base_video_view;
    }

    @Override
    public void initView(View view) {
        VideoBean videoBean = (VideoBean) getArguments().getSerializable("videoBean");
        initVideoPlayer(videoBean);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }


    private void initVideoPlayer(VideoBean videoBean) {
        String path = videoBean.getPath();
        String title = videoBean.getTitle();
        if(path==null ||path.length()==0){
            ToastUtils.showRoundRectToast("视频地址不能为空");
            return;
        }
        //设置播放类型
        videoPlayer.setPlayerType(VideoPlayer.TYPE_IJK);
        //是否从上一次的位置继续播放
        videoPlayer.continueFromLastPosition(true);
        //设置播放速度
        videoPlayer.setSpeed(1.0f);
        //网络视频地址
        videoPlayer.setUp(path, null);
        //设置视频地址和请求头部
        //创建视频控制器
        VideoPlayerController controller = new VideoPlayerController(activity);
        //设置标题
        controller.setTitle(title);
        //隐藏top
        controller.setTopVisibility(false);
        //设置loading类型【共两种】
        controller.setLoadingType(1);
        //设置背景
        controller.imageView().setBackgroundResource(R.color.blackText);
        //设置10秒不操作隐藏top和bottom
        controller.setHideTime(10000);
        //播放完成监听
        controller.setOnCompletedListener(new OnCompletedListener() {
            @Override
            public void onCompleted() {

            }
        });
        controller.setOnVideoBackListener(new OnVideoBackListener() {
            @Override
            public void onBackClick() {

            }
        });
        //设置视频控制器
        videoPlayer.setController(controller);
    }

}

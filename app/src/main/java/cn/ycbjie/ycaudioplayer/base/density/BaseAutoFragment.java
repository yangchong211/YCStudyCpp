package cn.ycbjie.ycaudioplayer.base.density;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.internal.CustomAdapt;


public class BaseAutoFragment extends Fragment implements CustomAdapt {

    //由于某些原因, 屏幕旋转后 Activity 的重建, 会导致框架对 Activity 的自定义适配参数失去效果
    //所以如果您的 Activity 允许屏幕旋转, 则请在 onCreateView 手动调用一次 AutoSize.autoConvertDensity()
    //如果您的 Activity 不允许屏幕旋转, 则可以将下面调用 AutoSize.autoConvertDensity() 的代码删除掉
    public void setConvertDensity(){
        if(getActivity()!=null){
            AutoSize.autoConvertDensity(getActivity(), 667, true);
        }
    }

    /**
     * 需要注意的是暂停 AndroidAutoSize 后, AndroidAutoSize 只是停止了对后续还没有启动的 {@link Activity} 进行适配的工作
     * 但对已经启动且已经适配的 {@link Activity} 不会有任何影响
     *
     */
    public void stop() {
        if(getActivity()!=null){
            AutoSizeConfig.getInstance().stop(getActivity());
        }
    }

    /**
     * 需要注意的是重新启动 AndroidAutoSize 后, AndroidAutoSize 只是重新开始了对后续还没有启动的 {@link Activity} 进行适配的工作
     * 但对已经启动且在 stop 期间未适配的 {@link Activity} 不会有任何影响
     *
     */
    public void restart() {
        AutoSizeConfig.getInstance().restart();
    }


    @Override
    public boolean isBaseOnWidth() {
        return true;
    }

    @Override
    public float getSizeInDp() {
        return 667;
    }


}

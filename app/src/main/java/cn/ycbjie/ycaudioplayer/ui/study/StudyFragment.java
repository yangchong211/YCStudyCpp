package cn.ycbjie.ycaudioplayer.ui.study;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.yczbj.ycrefreshviewlib.YCRefreshView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.base.BaseFragment;
import cn.ycbjie.ycaudioplayer.ui.local.view.PlayMusicFragment;

/**
 * Created by yc on 2018/1/24.
 */

public class StudyFragment extends BaseFragment {

    @Bind(R.id.recyclerView)
    YCRefreshView recyclerView;

    @Override
    public int getContentView() {
        return R.layout.base_easy_recycle;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

}

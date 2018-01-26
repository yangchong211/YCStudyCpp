package cn.ycbjie.ycaudioplayer.ui.study;

import org.yczbj.ycrefreshviewlib.YCRefreshView;

import butterknife.Bind;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.base.BaseFragment;

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

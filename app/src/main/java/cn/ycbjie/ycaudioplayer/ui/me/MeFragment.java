package cn.ycbjie.ycaudioplayer.ui.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.base.BaseFragment;
import cn.ycbjie.ycaudioplayer.ui.lock.LockAudioActivity;
import cn.ycbjie.ycaudioplayer.ui.lock.LockTestActivity;
import cn.ycbjie.ycaudioplayer.ui.main.MainActivity;

/**
 * Created by yc on 2018/1/24.
 */

public class MeFragment extends BaseFragment {

    @Bind(R.id.tv_1)
    TextView tv1;
    @Bind(R.id.tv_2)
    TextView tv2;
    private MainActivity activity;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }


    @Override
    public int getContentView() {
        return R.layout.fragment_me;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lockScreen = new Intent(activity, LockAudioActivity.class);
                lockScreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(lockScreen);
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lockScreen = new Intent(activity, LockTestActivity.class);
                lockScreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(lockScreen);
            }
        });
    }

    @Override
    public void initData() {

    }

}

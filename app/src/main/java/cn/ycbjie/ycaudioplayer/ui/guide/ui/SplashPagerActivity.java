package cn.ycbjie.ycaudioplayer.ui.guide.ui;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.yc.cn.ycbannerlib.BannerView;
import com.yc.cn.ycbannerlib.adapter.AbsDynamicPagerAdapter;
import com.yc.cn.ycbannerlib.inter.OnPageListener;
import com.yc.cn.ycbannerlib.util.SizeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.api.constant.Constant;
import cn.ycbjie.ycaudioplayer.base.BaseActivity;
import cn.ycbjie.ycstatusbarlib.bar.YCAppBar;


/**
 * <pre>
 *     @author yangchong
 *     blog  : www.pedaily.cn
 *     time  : 2018/03/22
 *     desc  : 引导页
 *     revise:
 * </pre>
 */
public class SplashPagerActivity extends BaseActivity {


    @Bind(R.id.banner)
    BannerView banner;
    @Bind(R.id.btn_go)
    Button btnGo;

    private List<Integer> imageId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        YCAppBar.translucentStatusBar(this, true);
    }


    /**
     * 屏蔽返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public int getContentView() {
        return R.layout.activity_splash_pager;
    }

    @Override
    public void initView() {
        if(SPUtils.getInstance(Constant.SP_NAME).getBoolean(Constant.KEY_FIRST_SPLASH,true)){
            initGetImage();
            initBanner();
        } else {
            ActivityUtils.startActivity(GuideActivity.class);
            finish();
        }
    }

    private void initGetImage() {
        imageId = new ArrayList<>();
        TypedArray images = this.getResources().obtainTypedArray(R.array.splash_image);
        for(int a=0 ; a<4 ; a++){
            int image = images.getResourceId(a, R.drawable.bg_small_kites_min);
            imageId.add(image);
        }
        images.recycle();
    }


    @Override
    public void initListener() {
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startActivity(GuideActivity.class);
                finish();
                SPUtils.getInstance(Constant.SP_NAME).put(Constant.KEY_FIRST_SPLASH,false);
            }
        });
    }

    @Override
    public void initData() {

    }


    private void initBanner() {
        banner.setPlayDelay(0);
        banner.setHintGravity(1);
        banner.setHintPadding(SizeUtil.dip2px(this,10),0,
                SizeUtil.dip2px(this,10),SizeUtil.dip2px(this,30));
        banner.setOnPageListener(new OnPageListener() {
            @Override
            public void onPageChange(int position) {
                if(position==imageId.size()-1){
                    btnGo.setVisibility(View.VISIBLE);
                }else {
                    btnGo.setVisibility(View.GONE);
                }
            }
        });
        banner.setAdapter(new ImageNormalAdapter());
    }


    private class ImageNormalAdapter extends AbsDynamicPagerAdapter {

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            view.setImageResource(imageId.get(position));
            return view;
        }

        @Override
        public int getCount() {
            return imageId ==null ? 0 :imageId.size();
        }
    }


}

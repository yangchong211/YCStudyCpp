package cn.ycbjie.ycaudioplayer.ui.me.view.activity;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.pedaily.yc.ycdialoglib.customToast.ToastUtil;
import com.pedaily.yc.ycdialoglib.selectDialog.CustomSelectDialog;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.base.view.BaseActivity;
import cn.ycbjie.ycaudioplayer.db.dl.TasksManager;
import cn.ycbjie.ycaudioplayer.db.dl.TasksManagerModel;
import cn.ycbjie.ycaudioplayer.inter.listener.OnCompleteListener;
import cn.ycbjie.ycaudioplayer.inter.listener.OnListItemClickListener;
import cn.ycbjie.ycaudioplayer.inter.listener.OnMoreClickListener;
import cn.ycbjie.ycaudioplayer.ui.me.view.adapter.CacheDownloadedAdapter;
import cn.ycbjie.ycaudioplayer.ui.me.view.adapter.CacheDownloadingAdapter;
import cn.ycbjie.ycaudioplayer.utils.DialogUtils;
import cn.ycbjie.ycaudioplayer.utils.logger.AppLogUtils;

/**
 * <pre>
 *     @author yangchong
 *     blog  : www.pedaily.cn
 *     time  : 2018/03/26
 *     desc  : 课程缓存页面
 *     revise:
 * </pre>
 */

public class ClassDlCacheActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.ll_title_menu)
    FrameLayout flTitleMenu;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.tv_title_right)
    TextView tvTitleRight;
    @Bind(R.id.tv_start_title)
    TextView tvStartTitle;
    @Bind(R.id.recyclerView_start)
    RecyclerView recyclerViewStart;
    @Bind(R.id.tv_complete_title)
    TextView tvCompleteTitle;
    @Bind(R.id.recyclerView_complete)
    RecyclerView recyclerViewComplete;

    private CacheDownloadingAdapter cacheDownloadingAdapter;
    private CacheDownloadedAdapter cacheDownloadedAdapter;
    private List<TasksManagerModel> downloadingData;
    private List<TasksManagerModel> downloadedData;


    @Override
    public int getContentView() {
        return R.layout.activity_class_cache;
    }

    @Override
    public void initView() {
        toolbarTitle.setText("离线课程");
        tvTitleRight.setVisibility(View.VISIBLE);
        tvTitleRight.setText("批量删除");
        initRecyclerView();
    }

    @Override
    public void initListener() {
        flTitleMenu.setOnClickListener(this);
        tvTitleRight.setOnClickListener(this);
        cacheDownloadingAdapter.setOnListItemClickListener(new OnListItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtil.showToast(ClassDlCacheActivity.this,"点击事件"+position);
            }
        });
        cacheDownloadingAdapter.setOnMoreClickListener(new OnMoreClickListener() {
            @Override
            public void onMoreClick(int position) {
                showDialogAnim(position);
            }
        });
        cacheDownloadingAdapter.setOnCompleteListener(new OnCompleteListener() {
            @Override
            public void downloadCompleted(TasksManagerModel model, int position) {
                if(model!=null){
                    //downloadingData.remove(position);
                    //cacheDownloadingAdapter.notifyItemRemoved(position);

                    cacheDownloadedAdapter.insertData(model);
                    cacheDownloadedAdapter.notifyItemInserted(0);
                }
            }
        });
        cacheDownloadedAdapter.setOnListItemClickListener(new OnListItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
        cacheDownloadedAdapter.setOnMoreClickListener(new OnMoreClickListener() {
            @Override
            public void onMoreClick(int position) {
                showDialogAnim(position);
            }
        });
    }


    @Override
    public void initData() {
        downloadingData = TasksManager.getImpl().getModelList();
        if(downloadingData!=null && downloadingData.size()>0){
            cacheDownloadingAdapter.addAllData(downloadingData);
            AppLogUtils.e("initData------downloadingData-----"+downloadingData.size());
        }

        downloadedData = TasksManager.getImpl().getDownloadedList();
        if(downloadedData!=null && downloadedData.size()>0){
            cacheDownloadedAdapter.addAllData(downloadedData);
            AppLogUtils.e("initData-------downloadedData----"+downloadedData.size());
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_title_menu:
                finish();
                break;
            case R.id.tv_title_right:
                deleteData();
                break;
            default:
                break;
        }
    }


    private void initRecyclerView() {
        final LinearLayoutManager manager1 =new LinearLayoutManager(this);
        manager1.setOrientation(OrientationHelper.VERTICAL);
        cacheDownloadingAdapter = new CacheDownloadingAdapter(this);
        recyclerViewStart.setAdapter(cacheDownloadingAdapter);
        recyclerViewStart.setLayoutManager(manager1);
        recyclerViewStart.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.top=2;
            }
        });


        LinearLayoutManager manager2 =new LinearLayoutManager(this);
        manager2.setOrientation(OrientationHelper.VERTICAL);
        cacheDownloadedAdapter = new CacheDownloadedAdapter(this);
        recyclerViewComplete.setAdapter(cacheDownloadedAdapter);
        recyclerViewComplete.setLayoutManager(manager2);
        recyclerViewComplete.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.top=2;
            }
        });
    }


    private void deleteData() {
        if ("批量删除".equals(tvTitleRight.getText())) {
            tvTitleRight.setText("取消");
        }else {
            tvTitleRight.setText("批量删除");
        }
    }


    private void showDialogAnim(final int index) {
        List<String> names = new ArrayList<>();
        names.add("下载");
        names.add("分享");
        names.add("取消收藏");
        DialogUtils.showDialog(this, new CustomSelectDialog.SelectDialogListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ToastUtil.showToast(ClassDlCacheActivity.this, "下载" + index);
                        break;
                    case 1:
                        ToastUtil.showToast(ClassDlCacheActivity.this, "分享" + index);
                        break;
                    case 2:
                        ToastUtil.showToast(ClassDlCacheActivity.this, "取消收藏" + index);
                        break;
                    default:
                        break;
                }
            }
        }, names);
    }



}

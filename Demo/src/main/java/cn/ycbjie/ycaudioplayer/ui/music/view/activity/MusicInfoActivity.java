package cn.ycbjie.ycaudioplayer.ui.music.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.flyco.tablayout.SegmentTabLayout;
import com.pedaily.yc.ycdialoglib.toast.ToastUtils;

import java.io.File;
import java.util.Locale;

import butterknife.BindView;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.base.view.BaseActivity;
import cn.ycbjie.ycaudioplayer.model.bean.AudioBean;
import cn.ycbjie.ycaudioplayer.utils.musicUtils.CoverLoader;
import cn.ycbjie.ycstatusbarlib.bar.StateAppBar;

public class MusicInfoActivity extends BaseActivity {

    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.stl_layout)
    SegmentTabLayout stlLayout;
    @BindView(R.id.ll_other)
    LinearLayout llOther;
    @BindView(R.id.fl_search)
    FrameLayout flSearch;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_music_info_cover)
    ImageView ivMusicInfoCover;
    @BindView(R.id.et_music_info_title)
    EditText etMusicInfoTitle;
    @BindView(R.id.layout_music_info_title)
    TextInputLayout layoutMusicInfoTitle;
    @BindView(R.id.et_music_info_artist)
    EditText etMusicInfoArtist;
    @BindView(R.id.label_music_info_artist)
    TextInputLayout labelMusicInfoArtist;
    @BindView(R.id.et_music_info_album)
    EditText etMusicInfoAlbum;
    @BindView(R.id.label_music_info_album)
    TextInputLayout labelMusicInfoAlbum;
    @BindView(R.id.tv_music_info_duration)
    EditText tvMusicInfoDuration;
    @BindView(R.id.label_music_info_duration)
    TextInputLayout labelMusicInfoDuration;
    @BindView(R.id.tv_music_info_file_name)
    EditText tvMusicInfoFileName;
    @BindView(R.id.label_music_info_file_name)
    TextInputLayout labelMusicInfoFileName;
    @BindView(R.id.tv_music_info_file_size)
    EditText tvMusicInfoFileSize;
    @BindView(R.id.label_music_info_file_size)
    TextInputLayout labelMusicInfoFileSize;
    @BindView(R.id.tv_music_info_file_path)
    EditText tvMusicInfoFilePath;
    @BindView(R.id.label_music_info_file_path)
    TextInputLayout labelMusicInfoFilePath;
    private AudioBean mMusic;
    private File mMusicFile;
    private Bitmap mCoverBitmap;

    public static void start(Context context, AudioBean music) {
        Intent intent = new Intent(context, MusicInfoActivity.class);
        intent.putExtra("music", music);
        context.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_music_info, menu);
        return true;
    }


    @Override
    public int getContentView() {
        return R.layout.activity_local_music_info;
    }

    @Override
    public void initView() {
        StateAppBar.setStatusBarColor(this, ContextCompat.getColor(this, R.color.redTab));
        initToolBar();
        initIntentData();
        initViewData();
    }

    private void initToolBar() {
        ivMenu.setVisibility(View.GONE);
        flSearch.setVisibility(View.GONE);
        llOther.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        toolbar.setTitle("歌曲详情信息");
        toolbar.setTitleTextColor(this.getResources().getColor(R.color.white));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.action_save) {
            ToastUtils.showRoundRectToast("保存信息功能");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }


    private void initIntentData() {
        mMusic = (AudioBean) getIntent().getSerializableExtra("music");
        if (mMusic == null || mMusic.getType() != AudioBean.Type.LOCAL) {
            finish();
        }
        mMusicFile = new File(mMusic.getPath());
        mCoverBitmap = CoverLoader.getInstance().loadThumbnail(mMusic);
    }

    private void initViewData() {
        ivMusicInfoCover.setImageBitmap(mCoverBitmap);
        etMusicInfoTitle.setText(mMusic.getTitle());
        etMusicInfoTitle.setSelection(etMusicInfoTitle.length());
        etMusicInfoArtist.setText(mMusic.getArtist());
        etMusicInfoArtist.setSelection(etMusicInfoArtist.length());
        etMusicInfoAlbum.setText(mMusic.getAlbum());
        etMusicInfoAlbum.setSelection(etMusicInfoAlbum.length());
        tvMusicInfoDuration.setText(formatTime("mm:ss", mMusic.getDuration()));
        tvMusicInfoFileName.setText(mMusic.getFileName());
        tvMusicInfoFilePath.setText(mMusicFile.getParent());
    }

    private String formatTime(String pattern, long milli) {
        int m = (int) (milli / DateUtils.MINUTE_IN_MILLIS);
        int s = (int) ((milli / DateUtils.SECOND_IN_MILLIS) % 60);
        String mm = String.format(Locale.getDefault(), "%02d", m);
        String ss = String.format(Locale.getDefault(), "%02d", s);
        return pattern.replace("mm", mm).replace("ss", ss);
    }

}

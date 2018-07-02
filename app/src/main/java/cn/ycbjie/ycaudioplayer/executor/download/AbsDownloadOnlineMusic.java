package cn.ycbjie.ycaudioplayer.executor.download;

import android.app.Activity;
import android.text.TextUtils;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.pedaily.yc.ycdialoglib.customToast.ToastUtil;

import java.io.File;

import cn.ycbjie.ycaudioplayer.api.http.OnLineMusicModel;
import cn.ycbjie.ycaudioplayer.model.bean.DownloadInfo;
import cn.ycbjie.ycaudioplayer.ui.music.onLine.model.bean.OnlineMusicList;
import cn.ycbjie.ycaudioplayer.utils.musicUtils.FileMusicUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * <pre>
 *     author: yangchong
 *     blog  : www.pedaily.cn
 *     time  : 2018/01/24
 *     desc  : 下载音乐
 *     revise:
 * </pre>
 */
public abstract class AbsDownloadOnlineMusic extends AbsDownloadMusic {

    private Activity mActivity;
    private OnlineMusicList.OnlineMusic mOnlineMusic;

    protected AbsDownloadOnlineMusic(Activity activity, OnlineMusicList.OnlineMusic onlineMusic) {
        super(activity);
        mActivity = activity;
        mOnlineMusic = onlineMusic;
    }

    @Override
    protected void download() {
        final String artist = mOnlineMusic.getArtist_name();
        final String title = mOnlineMusic.getTitle();

        // 下载歌词
        String lrcFileName = FileMusicUtils.getLrcFileName(artist, title);
        File lrcFile = new File(FileMusicUtils.getLrcDir() + lrcFileName);
        if (!TextUtils.isEmpty(mOnlineMusic.getLrclink()) && !lrcFile.exists()) {
            downloadLrc(mOnlineMusic.getLrclink(), FileMusicUtils.getLrcDir(), lrcFileName);
        }else {
            ToastUtil.showToast(mActivity,"无法下载歌词");
        }

        // 下载封面
        String albumFileName = FileMusicUtils.getAlbumFileName(artist, title);
        final File albumFile = new File(FileMusicUtils.getAlbumDir(), albumFileName);
        String picUrl = mOnlineMusic.getPic_big();
        if (TextUtils.isEmpty(picUrl)) {
            picUrl = mOnlineMusic.getPic_small();
        }
        if (!albumFile.exists() && !TextUtils.isEmpty(picUrl)) {
            downloadFile(picUrl, FileMusicUtils.getAlbumDir(), albumFileName);
        }else {
            ToastUtil.showToast(mActivity,"无法下载封面");
        }

        // 获取歌曲下载链接
        getMusicDownloadInfo(mOnlineMusic.getSong_id(),artist,title,albumFile);
    }


    private void getMusicDownloadInfo(String songId, final String artist,
                                      final String title, final File albumFile) {
        OnLineMusicModel model = OnLineMusicModel.getInstance();
        model.getMusicDownloadInfo(OnLineMusicModel.METHOD_DOWNLOAD_MUSIC,songId)
                .subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.computation())
                .subscribe(new Subscriber<DownloadInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        onExecuteFail(null);
                    }

                    @Override
                    public void onNext(DownloadInfo downloadInfo) {
                        if (downloadInfo == null || downloadInfo.getBitrate() == null) {
                            onExecuteFail(null);
                            return;
                        }
                        downloadMusic(downloadInfo.getBitrate().getFile_link(), artist, title, albumFile.getPath());
                        onExecuteSuccess(null);
                    }
                });
    }


    private void downloadLrc(String lrclink, String lrcDir, String lrcFileName) {
        FileDownloader.getImpl()
                .create(lrclink)
                .setPath(lrcDir)
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                    }

                    @Override
                    protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        ToastUtil.showToast(mActivity,"下载完成");
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                    }
                }).start();
    }


    private void downloadFile(String lrclink, String lrcDir, String lrcFileName) {
        FileDownloader.getImpl()
                .create(lrclink)
                .setPath(lrcDir)
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                    }

                    @Override
                    protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        ToastUtil.showToast(mActivity,"下载完成");
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                    }
                }).start();
    }


}

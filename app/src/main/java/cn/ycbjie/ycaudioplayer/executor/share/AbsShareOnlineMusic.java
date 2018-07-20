package cn.ycbjie.ycaudioplayer.executor.share;

import android.content.Context;
import android.content.Intent;

import org.reactivestreams.Subscriber;

import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.api.http.OnLineMusicModel;
import cn.ycbjie.ycaudioplayer.executor.IExecutor;
import cn.ycbjie.ycaudioplayer.model.bean.DownloadInfo;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * <pre>
 *     author: yangchong
 *     blog  :
 *     time  : 2018/01/24
 *     desc  : 分享在线歌曲
 *     revise:
 * </pre>
 */

public abstract class AbsShareOnlineMusic implements IExecutor<Void> {

    private Context mContext;
    private String mTitle;
    private String mSongId;

    protected AbsShareOnlineMusic(Context context, String title, String songId) {
        mContext = context;
        mTitle = title;
        mSongId = songId;
    }

    @Override
    public void execute() {
        onPrepare();
        share();
    }

    private void share() {
        // 获取歌曲播放链接
        OnLineMusicModel model = OnLineMusicModel.getInstance();
        model.getMusicDownloadInfo(OnLineMusicModel.METHOD_DOWNLOAD_MUSIC,mSongId)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DownloadInfo>() {
                    @Override
                    public void accept(DownloadInfo downloadInfo) throws Exception {
                        if (downloadInfo == null || downloadInfo.getBitrate() == null) {
                            onExecuteFail(null);
                            return;
                        }
                        onExecuteSuccess(null);
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_TEXT, mContext.getString(R.string.share_music,
                                mContext.getString(R.string.app_name), mTitle,
                                downloadInfo.getBitrate().getFile_link()));
                        mContext.startActivity(Intent.createChooser(intent,
                                mContext.getString(R.string.share)));
                    }
                });
    }
}

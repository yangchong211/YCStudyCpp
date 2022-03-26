package cn.ycbjie.ycaudioplayer.executor.share;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import cn.ycbjie.ycaudioplayer.api.http.OnLineMusicModel;
import cn.ycbjie.ycaudioplayer.executor.inter.IExecutor;
import cn.ycbjie.ycaudioplayer.model.bean.DownloadInfo;
import cn.ycbjie.ycaudioplayer.utils.share.ShareComment;
import cn.ycbjie.ycaudioplayer.utils.share.ShareDetailBean;
import cn.ycbjie.ycaudioplayer.utils.share.ShareDialog;
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
    private String mImage;

    protected AbsShareOnlineMusic(Context context, String title, String songId,String image) {
        mContext = context;
        mTitle = title;
        mSongId = songId;
        mImage = image;
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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DownloadInfo>() {
                    @Override
                    public void accept(DownloadInfo downloadInfo) throws Exception {
                        if (downloadInfo == null || downloadInfo.getBitrate() == null) {
                            onExecuteFail(null);
                            return;
                        }
                        String file_link = downloadInfo.getBitrate().getFile_link();
                        onExecuteSuccess(null);

                        ShareDetailBean shareDetailBean = new ShareDetailBean();
                        shareDetailBean.setShareType(ShareComment.ShareType.SHARE_GOODS);
                        shareDetailBean.setContent("歌曲分享");
                        shareDetailBean.setTitle(mTitle);
                        shareDetailBean.setImage(mImage);
                        shareDetailBean.setUrl(file_link);
                        ShareDialog shareDialog = new ShareDialog(mContext,shareDetailBean);
                        shareDialog.show(((FragmentActivity)mContext).getSupportFragmentManager());


                        /*Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_TEXT, mContext.getString(R.string.share_music,
                                mContext.getString(R.string.app_name), mTitle,
                                file_link));
                        mContext.startActivity(Intent.createChooser(intent,
                                mContext.getString(R.string.share)));*/
                    }
                });
    }
}

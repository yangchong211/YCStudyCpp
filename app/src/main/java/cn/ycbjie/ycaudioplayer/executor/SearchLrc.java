package cn.ycbjie.ycaudioplayer.executor;

import android.text.TextUtils;

import cn.ycbjie.ycaudioplayer.model.bean.MusicLrc;
import cn.ycbjie.ycaudioplayer.model.bean.SearchMusic;
import cn.ycbjie.ycaudioplayer.api.http.OnLineMusicModel;
import cn.ycbjie.ycaudioplayer.util.musicUtils.FileMusicUtils;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * 如果本地歌曲没有歌词则从网络搜索歌词
 */
public abstract class SearchLrc implements IExecutor<String> {

    private String artist;
    private String title;

    protected SearchLrc(String artist, String title) {
        this.artist = artist;
        this.title = title;
    }

    @Override
    public void execute() {
        onPrepare();
        searchLrc();
    }


    private void searchLrc() {
        OnLineMusicModel model = OnLineMusicModel.getInstance();
        model.startSearchMusic(OnLineMusicModel.METHOD_SEARCH_MUSIC,title + "-" + artist)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SearchMusic>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(SearchMusic searchMusic) {
                        if (searchMusic == null || searchMusic.getSong() == null || searchMusic.getSong().isEmpty()) {
                            return;
                        }
                        downloadLrc(searchMusic.getSong().get(0).getSongid());
                    }
                });

    }


    private void downloadLrc(String songId) {
        OnLineMusicModel model = OnLineMusicModel.getInstance();
        model.getLrc(OnLineMusicModel.METHOD_LRC,songId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MusicLrc>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MusicLrc lrc) {
                        if (lrc == null || TextUtils.isEmpty(lrc.getLrcContent())) {
                            return;
                        }
                        String filePath = FileMusicUtils.getLrcDir() + FileMusicUtils.getLrcFileName(artist, title);
                        FileMusicUtils.saveLrcFile(filePath, lrc.getLrcContent());
                        onExecuteSuccess(filePath);
                    }
                });
    }



}

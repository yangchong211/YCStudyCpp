package cn.ycbjie.ycaudioplayer.executor.search;

import android.text.TextUtils;

import cn.ycbjie.ycaudioplayer.executor.inter.IExecutor;
import cn.ycbjie.ycaudioplayer.model.bean.MusicLrc;
import cn.ycbjie.ycaudioplayer.model.bean.SearchMusic;
import cn.ycbjie.ycaudioplayer.api.http.OnLineMusicModel;
import cn.ycbjie.ycaudioplayer.utils.musicUtils.FileMusicUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * 如果本地歌曲没有歌词则从网络搜索歌词
 */
public abstract class AbsSearchLrc implements IExecutor<String> {

    private String artist;
    private String title;

    protected AbsSearchLrc(String artist, String title) {
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
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SearchMusic>() {
                    @Override
                    public void accept(SearchMusic searchMusic) throws Exception {
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
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MusicLrc>() {
                    @Override
                    public void accept(MusicLrc musicLrc) throws Exception {
                        if (musicLrc == null || TextUtils.isEmpty(musicLrc.getLrcContent())) {
                            return;
                        }
                        String filePath = FileMusicUtils.getLrcDir() + FileMusicUtils.getLrcFileName(artist, title);
                        FileMusicUtils.saveLrcFile(filePath, musicLrc.getLrcContent());
                        onExecuteSuccess(filePath);
                    }
                });
    }



}

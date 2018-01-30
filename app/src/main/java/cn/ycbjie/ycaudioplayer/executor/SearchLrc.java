package cn.ycbjie.ycaudioplayer.executor;

import android.text.TextUtils;

import cn.ycbjie.ycaudioplayer.api.http.AppApiService;
import cn.ycbjie.ycaudioplayer.api.http.HttpCallback;
import cn.ycbjie.ycaudioplayer.model.bean.Lrc;
import cn.ycbjie.ycaudioplayer.model.bean.SearchMusic;
import cn.ycbjie.ycaudioplayer.util.musicUtils.FileMusicUtils;


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
        AppApiService.searchMusic(title + "-" + artist, new HttpCallback<SearchMusic>() {
            @Override
            public void onSuccess(SearchMusic response) {
                if (response == null || response.getSong() == null || response.getSong().isEmpty()) {
                    onFail(null);
                    return;
                }
                downloadLrc(response.getSong().get(0).getSongid());
            }

            @Override
            public void onFail(Exception e) {
                onExecuteFail(e);
            }
        });
    }


    private void downloadLrc(String songId) {
        AppApiService.getLrc(songId, new HttpCallback<Lrc>() {
            @Override
            public void onSuccess(Lrc response) {
                if (response == null || TextUtils.isEmpty(response.getLrcContent())) {
                    onFail(null);
                    return;
                }
                String filePath = FileMusicUtils.getLrcDir() + FileMusicUtils.getLrcFileName(artist, title);
                FileMusicUtils.saveLrcFile(filePath, response.getLrcContent());
                onExecuteSuccess(filePath);
            }

            @Override
            public void onFail(Exception e) {
                onExecuteFail(e);
            }
        });
    }



}

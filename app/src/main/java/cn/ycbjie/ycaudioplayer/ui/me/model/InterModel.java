package cn.ycbjie.ycaudioplayer.ui.me.model;


import cn.ycbjie.ycaudioplayer.ui.music.model.OnlineMusicList;

public interface InterModel {

    void getAboutData(AboutDataListener<OnlineMusicList> listener);
}

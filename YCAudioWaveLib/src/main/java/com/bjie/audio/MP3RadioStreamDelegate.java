package com.bjie.audio;

public interface MP3RadioStreamDelegate {

    void onRadioPlayerPlaybackStarted(MP3RadioStreamPlayer player);
    void onRadioPlayerStopped(MP3RadioStreamPlayer player);
    void onRadioPlayerError(MP3RadioStreamPlayer player);
    void onRadioPlayerBuffering(MP3RadioStreamPlayer player);

}

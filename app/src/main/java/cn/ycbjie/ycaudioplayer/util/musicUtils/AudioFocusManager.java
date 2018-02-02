package cn.ycbjie.ycaudioplayer.util.musicUtils;

import android.media.AudioManager;
import android.support.annotation.NonNull;

import cn.ycbjie.ycaudioplayer.service.PlayService;

import static android.content.Context.AUDIO_SERVICE;


public class AudioFocusManager implements AudioManager.OnAudioFocusChangeListener {

    private PlayService mPlayService;
    private AudioManager mAudioManager;
    private boolean isPausedByFocusLossTransient;
    private int mVolumeWhenFocusLossTransientCanDuck;

    /**
     * 初始化操作
     * @param playService           playService对象
     */
    public AudioFocusManager(@NonNull PlayService playService) {
        mPlayService = playService;
        mAudioManager = (AudioManager) playService.getSystemService(AUDIO_SERVICE);
    }

    /**
     * 请求音频焦点，开始播放时候调用
     * @return
     */
    public boolean requestAudioFocus() {
        return mAudioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN) == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
    }

    /**
     * 放弃音频焦点，销毁播放时候调用
     */
    public void abandonAudioFocus() {
        mAudioManager.abandonAudioFocus(this);
    }

    /**
     * 当音频焦点发生变化的时候调用这个方法，在这里可以处理逻辑
     * 欢迎访问我的GitHub：https://github.com/yangchong211
     * 如果可以的话，请star吧
     * @param focusChange       焦点改变
     */
    @Override
    public void onAudioFocusChange(int focusChange) {
        int volume;
        switch (focusChange) {
            // 重新获得焦点
            case AudioManager.AUDIOFOCUS_GAIN:
                if (!willPlay() && isPausedByFocusLossTransient) {
                    // 通话结束，恢复播放
                    mPlayService.playPause();
                }

                //获取音量
                volume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                if (mVolumeWhenFocusLossTransientCanDuck > 0 && volume ==
                        mVolumeWhenFocusLossTransientCanDuck / 2) {
                    // 恢复音量
                    mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            mVolumeWhenFocusLossTransientCanDuck, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                }

                isPausedByFocusLossTransient = false;
                mVolumeWhenFocusLossTransientCanDuck = 0;
                break;
            // 永久丢失焦点，如被其他播放器抢占
            case AudioManager.AUDIOFOCUS_LOSS:
                if (willPlay()) {
                    forceStop();
                }
                break;
            // 短暂丢失焦点，比如来了电话或者微信视频音频聊天等等
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                if (willPlay()) {
                    forceStop();
                    isPausedByFocusLossTransient = true;
                }
                break;
            // 瞬间丢失焦点，如通知
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                // 音量减小为一半
                volume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                if (willPlay() && volume > 0) {
                    mVolumeWhenFocusLossTransientCanDuck = volume;
                    mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            mVolumeWhenFocusLossTransientCanDuck / 2,
                            AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 是否在播放或者准备播放
     */
    private boolean willPlay() {
        return mPlayService.isPreparing() || mPlayService.isPlaying();
    }

    private void forceStop() {
        //当准备播放时，则停止播放；当正在播放时，则暂停播放
        if (mPlayService.isPreparing()) {
            mPlayService.stop();
        } else if (mPlayService.isPlaying()) {
            mPlayService.pause();
        }
    }
}

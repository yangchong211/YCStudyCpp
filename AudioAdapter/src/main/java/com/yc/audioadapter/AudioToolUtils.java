package com.yc.audioadapter;

import com.yc.daudio.AudioConfigModel;
import com.yc.daudio.IAudioProcess;


public class AudioToolUtils {

    /**
     * Whether format is supported
     *
     * @param audioProcess
     * @return
     */
    public static boolean isSupportedFormat(IAudioProcess audioProcess) {
        return audioProcess != null && !(audioProcess instanceof AudioNotSupportProcess);
    }

    /**
     * Get audio processor
     *
     * @param audioConfig
     * @return
     */
    public static IAudioProcess getAudioProcessor(AudioConfigModel audioConfig) {
        IAudioProcess audioProcess;
        int audioTypeFormat = audioConfig.getAudioTypeFormat();
        switch (audioTypeFormat) {
            case AudioConfigModel.AUDIO_FORMAT_AMR_NB:
                audioProcess = new AudioAmrProcess(audioConfig.getAudioFormat());
                break;
            case AudioConfigModel.AUDIO_FORMAT_BV:
                audioProcess = new AudioBVCompress();
                break;
            default:
                // use default not support audio process
                audioProcess = new AudioNotSupportProcess();
                break;
        }

        return audioProcess;
    }
}

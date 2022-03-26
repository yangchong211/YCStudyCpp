package com.yc.daudio;


public class AudioManager {

    private IAudioRecord dAudioRecordService;

    private static class SingletonHolder {
        private static final AudioManager dAudioManager = new AudioManager();
    }

    public static AudioManager get() {
        return SingletonHolder.dAudioManager;
    }

    public void init(IAudioRecord dAudioRecordService) {
        this.dAudioRecordService = dAudioRecordService;
    }

    public IAudioRecord getService() {
        if (dAudioRecordService == null) {
            dAudioRecordService = defaultDAudioRecordService;
        }
        return dAudioRecordService;
    }

    private final IAudioRecord defaultDAudioRecordService = new IAudioRecord() {
        @Override
        public void startRecord(AudioConfigModel dAudioConfig, AudioFilePathRule filePathRule) {

        }

        @Override
        public void stopRecord() {

        }

        @Override
        public boolean isRecording() {
            return false;
        }

        @Override
        public void updateAudioPeriodLength(long periodLength) {

        }

        @Override
        public IAudioRecord setCallback(OnAudioCallback audioCallback) {
            return this;
        }

        @Override
        public IAudioRecord addAudioNewDataListener(IAudioDataListener dAudioNewDataListener) {
            return this;
        }

        @Override
        public IAudioRecord removeAudioNewDataListener(IAudioDataListener dAudioNewDataListener) {
            return this;
        }
    };
}

package cn.ycbjie.ycaudioplayer.audio;

public class AudioRecordContract {

    public interface View {

        void startRecord();

        void stopRecord();

        void updateRecord(String filePath);

        void showErrorMessage(String msg);
    }

    public interface Presenter {

        void startRecord();

        void stopRecord();

        boolean isRecording();
    }
}

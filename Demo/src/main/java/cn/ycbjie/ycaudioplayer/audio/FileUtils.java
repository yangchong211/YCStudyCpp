package cn.ycbjie.ycaudioplayer.audio;

import android.os.Environment;
import android.util.Log;

import com.yc.daudio.AudioFilePathRule;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class FileUtils {

    private static final String TAG = "AudioFile";

    private static final String DIR_ROOT = "phone_demo";
    private static final String DIR_AUDIO = "audio";

    public static String getAudioFilePath(AudioFilePathRule.AudioRecordFileType fileType,
                                          long createTime) {
        if (fileType == null) return "";

        String suffix = "";
        switch (fileType) {
            case TYPE_AUDIO_AMR:
                suffix = ".amr";
                break;
            case TYPE_AUDIO_BV:
                suffix = ".bv";
                break;
        }
        return getAudioDir() + File.separator + "Audio_" + new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date(createTime)) + suffix;
    }

    private static String getAudioDir() {
        String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String dir = sdPath + File.separator
                + DIR_ROOT + File.separator
                + DIR_AUDIO;
        File file = new File(dir);
        if (!file.exists()) {
            boolean ret = file.mkdirs();
            Log.d(TAG, "创建音频录制文件夹:" + ret);
        }
        return dir;
    }

    public static void writeAudioFile(String desPath, InputStream inputStream, byte[] header) {
        if (inputStream == null) return;

        long fileStartTime = System.currentTimeMillis();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(desPath);
            if (header != null && header.length > 0) {
                fos.write(header, 0, header.length);
            }
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                fos.flush();
            }
        } catch (IOException e) {
            Log.e(TAG, "写文件异常");
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {

                }
            }
        }

        long fileEndTime = System.currentTimeMillis();
        Log.e(TAG, "写音频文件耗时:" + (fileEndTime - fileStartTime));
    }
}

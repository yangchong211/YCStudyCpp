package cn.ycbjie.ycaudioplayer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.yc.audioadapter.AudioRecordService;
import com.yc.daudio.AudioConfigModel;
import com.yc.daudio.AudioFilePathRule;
import com.yc.daudio.OnAudioCallback;

import java.io.File;
import java.io.InputStream;

import cn.ycbjie.ycstatusbarlib.bar.StateAppBar;

import static android.media.AudioFormat.CHANNEL_IN_MONO;
import static android.media.AudioFormat.ENCODING_PCM_8BIT;
import static com.yc.daudio.AudioConfigModel.AUDIO_FORMAT_MP3;

/**
 * <pre>
 *     @author yangchong
 *     blog  : www.pedaily.cn
 *     time  : 2018/03/22
 *     desc  : 引导页
 *     revise:
 * </pre>
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private AudioRecordService audioRecordService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StateAppBar.translucentStatusBar(this, true);
        audioRecordService = new AudioRecordService();
        btn1 = findViewById(R.id.btn_1);
        btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == btn1){
            AudioConfigModel audioConfig = new AudioConfigModel(
                    5,100,AUDIO_FORMAT_MP3,
                    ENCODING_PCM_8BIT,CHANNEL_IN_MONO,1024);
            AudioFilePathRule filePathRule = new AudioFilePathRule() {
                @Override
                public String getFilePath(AudioRecordFileType fileType, int count, long createTime, long timeLength) {
                    int type = fileType.getType();
                    File cacheDir = MainActivity.this.getCacheDir();
                    String path = cacheDir.getPath() + File.separator + ""+ createTime;
                    File file = new File(path);
                    return file.getAbsolutePath();
                }
            };
            audioRecordService.startRecord(audioConfig,filePathRule);
            audioRecordService.setCallback(new OnAudioCallback() {
                @Override
                public void onStart() {

                }

                @Override
                public void onError(int errorCode, String des) {

                }

                @Override
                public void onException(Throwable e, String des) {

                }

                @Override
                public void onBeginNewPeriod(int count) {

                }

                @Override
                public void onEndLastPeriod(AudioFilePathRule.AudioRecordFileType fileType, int count, InputStream inputStream, byte[] header, long createTime, long timeLength) {

                }

                @Override
                public void onStop() {

                }
            });
        } else if (v == btn2){
            audioRecordService.stopRecord();
        } else if (v == btn3){

        }
    }
}

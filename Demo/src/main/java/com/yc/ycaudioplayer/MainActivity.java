package com.yc.ycaudioplayer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yc.audioadapter.AudioRecordService;

import com.yc.ycaudioplayer.audio.AudioRecordActivity;
import com.yc.ycaudioplayer.video.CameraMainActivity;

import cn.ycbjie.ycaudioplayer.R;

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
            startActivity(new Intent(this, CameraMainActivity.class));
        } else if (v == btn2){
            startActivity(new Intent(this, AudioRecordActivity.class));
        } else if (v == btn3){

        }
    }
}

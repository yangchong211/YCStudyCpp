package cn.ycbjie.ycaudioplayer.audio;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import cn.ycbjie.ycaudioplayer.R;

public class AudioRecordActivity extends Activity implements AudioRecordContract.View, View.OnClickListener {

    private static final String TAG = "AudioRecordTest";

    private Button recordBT;
    private TextView recordInfoTV;
    private TextView recordInfoTitleTV;

    private AudioRecordPresenter presenter;

    private List<String> filePathList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_audio_record);
        initView();
        initData();
        registerReceiver();
    }

    private void initView() {
        recordBT = (Button) findViewById(R.id.record);
        recordBT.setOnClickListener(this);
        recordInfoTV = (TextView) findViewById(R.id.record_info);
        recordInfoTitleTV = (TextView) findViewById(R.id.record_info_title);
    }

    private void initData() {
        filePathList = new ArrayList<>();
        presenter = new AudioRecordPresenter(this, this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == recordBT.getId()) {
            if (presenter.isRecording()) {
                presenter.stopRecord();
            } else {
                presenter.startRecord();
            }
        }
    }

    @Override
    public void startRecord() {
        recordBT.setText(R.string.audio_stop);
        filePathList.clear();
        recordInfoTV.setText(null);
        recordInfoTitleTV.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopRecord() {
        recordBT.setText(R.string.audio_start);
        recordInfoTV.setText(TextUtils.join("\n", filePathList));
    }

    @Override
    public void updateRecord(String filePath) {
        filePathList.add(filePath);
        String info = TextUtils.join("\n", filePathList)
                + (presenter.isRecording() ? "\n......" : "");
        recordInfoTV.setText(info);
    }

    @Override
    public void showErrorMessage(String msg) {
        recordInfoTV.setText(msg);
    }

    private void registerReceiver() {
        UpdateConfigReceiver receiver = new UpdateConfigReceiver();
        IntentFilter filter = new IntentFilter(UpdateConfigReceiver.ACTION_UPDATE_CONFIG);
        registerReceiver(receiver, filter);
    }

    class UpdateConfigReceiver extends BroadcastReceiver {

        public static final String ACTION_UPDATE_CONFIG = "com.didi.dr.phonedemo.ACTION_UPDATE_CONFIG";
        public static final String EXTRA_PERIOD_LENGTH = "extra_period_length";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_UPDATE_CONFIG.equals(action)) {
                long periodLength = intent.getLongExtra(EXTRA_PERIOD_LENGTH, 30 * 1000);
                Log.d(TAG, "更改的音频时长:" + periodLength);
                presenter.updateAudioDuration(periodLength);
            }
        }
    }
}

package com.ycbjie.aptools;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class SchemeActivity extends AppCompatActivity {

    private TextView mTvDomain;
    private TextView mTvParam;
    private TextView mTvDomain1;
    private TextView mTvParam1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheme);

        intView();
    }

    private void intView() {
        mTvDomain = (TextView) findViewById(R.id.tv_domain);
        mTvDomain.setText(Html.fromHtml("<a href='yc://app/?page=main'>点我试试</a>"));
        mTvDomain.setMovementMethod(LinkMovementMethod.getInstance());

        mTvParam = (TextView) findViewById(R.id.tv_param);
        mTvParam.setText(Html.fromHtml("<a href='yc://ycbjie:8888/from?type=yangchong'>点我一下</a>"));
        mTvParam.setMovementMethod(LinkMovementMethod.getInstance());

        mTvDomain1 = (TextView) findViewById(R.id.tv_domain1);
        mTvDomain1.setText(Html.fromHtml("<a href='//yc:app/?page=main'>点我试试</a>"));
        mTvDomain1.setMovementMethod(LinkMovementMethod.getInstance());

        mTvParam1 = (TextView) findViewById(R.id.tv_param1);
        mTvParam1.setText(Html.fromHtml("<a href='//yc:ycbjie:8888/from?type=yangchong'>点我一下</a>"));
        mTvParam1.setMovementMethod(LinkMovementMethod.getInstance());
    }


}

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
    }

    @Override protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Uri uri = intent.getData();
        if (uri != null) {
            dispatchUri(uri);
        } else {

        }
    }

    private static final String SCHEME_DOMAIN = "scheme_activity";
    private void dispatchUri(Uri uri) {
        try {
            final String domain = uri.getAuthority();
            if (TextUtils.equals(SCHEME_DOMAIN, domain)) {
                final String buffer = uri.getQueryParameter("buffer");
                final int type = Integer.valueOf(uri.getQueryParameter("type"));
                Toast.makeText(this, type + " " + buffer, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
        }
    }




}

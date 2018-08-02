package com.ycbjie.aptools;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ns.yc.ycutilslib.webView.ScrollWebView;


public class WebActivity extends AppCompatActivity {

    private ScrollWebView mWebView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        mWebView = (ScrollWebView) findViewById(R.id.webView);
        initWebView();
    }

    private void initWebView() {
        //加载本地的url
        mWebView.loadUrl("file:///android_asset/index.html");
        mWebView.setWebViewClient(new myWebViewClient());
    }

    private class myWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            /*此处获取的url的scheme都是小写*/
            if (!TextUtils.isEmpty(url) && url.contains("yc")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
            return true;
        }
    }

}

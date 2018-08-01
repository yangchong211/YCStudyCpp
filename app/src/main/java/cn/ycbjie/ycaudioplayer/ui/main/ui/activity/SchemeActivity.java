package cn.ycbjie.ycaudioplayer.ui.main.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.blankj.utilcode.util.ActivityUtils;

import java.util.List;

import cn.ycbjie.ycaudioplayer.ui.guide.ui.GuideActivity;


/**
 * Android业务组件化之URL Scheme使用
 * http://www.cnblogs.com/whoislcj/p/5825333.html
 * http://blog.csdn.net/sinat_31057219/article/details/78362326
 */

public class SchemeActivity extends AppCompatActivity {

    /*URL Scheme协议格式：
    String urlStr="http://www.orangecpp.com:80/tucao?id=hello&name=lily";
    //url =            protocol + authority(host + port) + path + query
    //协议protocol=    http
    //域名authority=   www.orangecpp.com:80
    //页面path=          /tucao
    //参数query=       id=hello&name=lily

    //authority =      host + port
    //主机host=        www.orangecpp.com
    //端口port=        80*/


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri uri = getIntent().getData();
        if (uri != null) {
            //解析一个url
            // 完整的url信息
            String urlStr = uri.toString();
            Log.e( "UrlUtils","url: " + urlStr);
            // scheme部分
            String scheme = uri.getScheme();
            Log.e( "UrlUtils","scheme: " + scheme);
            // host部分
            String host = uri.getHost();
            Log.e( "UrlUtils","host: " + host);
            //port部分
            int port = uri.getPort();
            Log.e( "UrlUtils","port: " + port);
            // 访问路劲
            String path = uri.getPath();
            Log.e( "UrlUtils","path: " + path);
            List<String> pathSegments = uri.getPathSegments();
            Log.e( "UrlUtils","pathSegments: " + pathSegments.toString());
            // Query部分
            String query = uri.getQuery();
            Log.e( "UrlUtils","query: " + query);
            //获取此URI的解码权限部分。对于服务器地址，权限的结构如下：Examples: "google.com", "bob@google.com:80"
            String authority = uri.getAuthority();
            Log.e( "UrlUtils","authority: " + authority);
            //从权限获取已解码的用户信息。例如，如果权限为“任何人@google.com”，此方法将返回“任何人”。
            String userInfo = uri.getUserInfo();
            Log.e( "UrlUtils","userInfo: " + userInfo);
            //获取指定参数值
            String goodsId = uri.getQueryParameter("goodsId");
            Log.e( "UrlUtils","main: " + goodsId);
            //UrlUtils: url: https://m.dev.haowumc.com/app/financialManagement
            //UrlUtils: scheme: https
            //UrlUtils: host: m.dev.haowumc.com
            //UrlUtils: port: -1
            //UrlUtils: path: /app/financialManagement
            //UrlUtils: pathSegments: [app, financialManagement]
            //UrlUtils: query: null
            //UrlUtils: authority: m.dev.haowumc.com
            //UrlUtils: userInfo: null

            if(goodsId.equals("yangchong")){
                ActivityUtils.startActivity(GuideActivity.class);
            }
        }
        finish();
    }
}

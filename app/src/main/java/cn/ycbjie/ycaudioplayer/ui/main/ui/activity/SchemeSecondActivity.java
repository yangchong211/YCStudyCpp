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

public class SchemeSecondActivity extends AppCompatActivity {

    /*URL Scheme协议格式：
    String urlStr="http://www.ycbjie.cn:80/yc?id=hello&name=cg";
    //url =            protocol + authority(host + port) + path + query
    //协议protocol=    http
    //域名authority=   www.ycbjie.cn:80
    //页面path=          /yc
    //参数query=       id=hello&name=cg
    //authority =      host + port
    //主机host=        www.ycbjie.cn
    //端口port=        80*/


    /*
     * URL Scheme使用场景，目前1，2，5使用场景很广
     * 1.通过小程序，利用Scheme协议打开原生app
     * 2.H5页面点击锚点，根据锚点具体跳转路径APP端跳转具体的页面
     * 3.APP端收到服务器端下发的PUSH通知栏消息，根据消息的点击跳转路径跳转相关页面
     * 4.APP根据URL跳转到另外一个APP指定页面
     * 5.通过短信息中的url打开原生app
     */


    /**
     * 协议部分，随便设置 yc://app/?page=main
     */

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
            String page = uri.getQueryParameter("page");
            Log.e( "UrlUtils","main: " + page);
            //UrlUtils: url: https://m.dev.haowumc.com/app/financialManagement
            //UrlUtils: scheme: https
            //UrlUtils: host: m.dev.haowumc.com
            //UrlUtils: port: -1
            //UrlUtils: path: /app/financialManagement
            //UrlUtils: pathSegments: [app, financialManagement]
            //UrlUtils: query: null
            //UrlUtils: authority: m.dev.haowumc.com
            //UrlUtils: userInfo: null

            if(page.equals("main")){
                ActivityUtils.startActivity(GuideActivity.class);
            }
        }
        finish();
    }
}
